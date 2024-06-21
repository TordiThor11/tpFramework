package proyecto.tordi.framework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Menu {
    private ArrayList<Accion> acciones;
    private int cantidadMaximaThread;
    private ExecutorService executor;

    public Menu(ArrayList<Accion> acciones) {
        this.acciones = acciones;
        this.cantidadMaximaThread = 1;
    }

    public Menu(ArrayList<Accion> acciones, int cantidadMaximaThread) {
        this.acciones = acciones;
        this.cantidadMaximaThread = cantidadMaximaThread;
    }

    public Menu(String path) {
        acciones = new ArrayList<>();
        cantidadMaximaThread = 1; //Single-core por default
        if (path.endsWith(".properties")) {
            cargarDatosDesdeProperties(path);
        }
        if (path.endsWith(".json")) {
            cargarDatosDesdeJson(path);
        } else {
            System.out.println("Se quiere cargar los datos desde otro lado que no sea properties o json");
        }

    }

    private void cargarDatosDesdeJson(String path) { //Carga las acciones y la cantidad maxima de threads. Si no aclara, threads=1 por defecto.
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(new File(path));

            //Cargo las clases
            JsonNode accionesNode = root.path("acciones");
            for (JsonNode accionNode : accionesNode) {
                String accionClassName = accionNode.asText();
                try {
                    Class<?> accionClass = Class.forName(accionClassName);
                    Accion accion = (Accion) accionClass.getDeclaredConstructor().newInstance();
                    acciones.add(accion);
                } catch (Exception e) {
                    System.out.println("Error cargando la clase: " + accionClassName);
                    e.printStackTrace();
                }
            }

            //Cargo la cantidad maxima de hilos
            JsonNode threadsNode = root.path("max-threads");
            this.cantidadMaximaThread = threadsNode.asInt();

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo de configuración Json: " + path);
            e.printStackTrace();
        }
    }


    private void cargarDatosDesdeProperties(String path) {  //Carga las acciones y la cantidad maxima de threads. Si no aclara, threads=1 por defecto.
        Properties properties = new Properties();
        try (InputStream configFile = new FileInputStream(path)) {
            properties.load(configFile);

            //Agarro la las clases desde el config
            for (String clase : properties.stringPropertyNames()) {
                if (!clase.equals("max-threads")) {
                    String nombreClase = properties.getProperty(clase);
                    try {
                        Class<?> claseParaAgregar = Class.forName(nombreClase);
                        Accion accion = (Accion) claseParaAgregar.getConstructor().newInstance();
                        acciones.add(accion);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error al crear la clase ", e);
                    }
                }

            }

            //Agarro la cantidad maxima de hilos desde el config
            String maxThreadsStr = properties.getProperty("max-threads");
            if (maxThreadsStr != null) {

                cantidadMaximaThread = Integer.parseInt(maxThreadsStr);
            } else {
                System.out.println("maxThread es null");
            }

        } catch (Exception e) {
            throw new RuntimeException("No pude crear la instancia de TextoAImprimir... ", e);
        }
    }

    public void mostrar() {
        this.executor = Executors.newFixedThreadPool(cantidadMaximaThread);
        var accionesConcurrentes = new ArrayList<Accion>();
        Scanner scanner = new Scanner(System.in);
        int seleccion = 0;
        int cantidadAcciones = acciones.size();
        int indice = 1;

        System.out.println("Cantidad de hilos: " + this.cantidadMaximaThread);

        //Mostrar opciones
        while (seleccion != cantidadAcciones + 1) {
            indice = 1;
            for (Accion accion : acciones) {
                System.out.println("Opcion " + indice + " - " + accion.nombreItemMenu() + " " + accion.descripcionItemMenu());
                indice++;
            }
            System.out.println("Opcion " + indice + " - " + "cerrar menu");
            indice++;
            System.out.println("Opcion " + indice + " - " + "ejecutar concurrencia");

            //Se lee el valor de seleccion
            System.out.println("Seleccione una opcion: ");
            seleccion = scanner.nextInt();

            //logica
            if (seleccion > 0 && seleccion <= cantidadAcciones + 2) {
                if (seleccion != cantidadAcciones + 1 && seleccion != cantidadAcciones + 2) {
//                    acciones.get(seleccion - 1).ejecutar(); //Ejecutar en single-core
                    if (accionesConcurrentes.size() < cantidadMaximaThread) {
                        accionesConcurrentes.add(acciones.get(seleccion - 1));
                    } else {
                        System.out.println("No se pueden agregar mas de " + cantidadMaximaThread + " hilos");
                    }
                } else {
                    if (seleccion == cantidadAcciones + 2) {


                        System.out.println("Ejecutando concurrencia");
                        var accionesConcurrentesAdapter = new ArrayList<AccionAdapter>();
                        for (Accion accionConcurrente : accionesConcurrentes) {
                            accionesConcurrentesAdapter.add(new AccionAdapter(accionConcurrente));
//                            executor.submit(accionAdapter);
                        }

                        try {
                            executor.invokeAll(accionesConcurrentesAdapter);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        accionesConcurrentesAdapter.clear();
                        accionesConcurrentes.clear();


                    } else {
                        System.out.println("Cerrando menu...");
                        executor.shutdown();
                    }
                }
            } else {
                System.out.println("El valor " + seleccion + " no es valido, ingrese nuevamente...");
            }
        }
        scanner.close();
    }
}
