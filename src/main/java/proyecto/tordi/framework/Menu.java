package proyecto.tordi.framework;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Menu {
    private ArrayList<Accion> acciones;

    public Menu(ArrayList<Accion> acciones) {
        this.acciones = acciones;
    }

    public Menu(String path) {
        acciones = new ArrayList<>();
        Properties properties = new Properties();
        try (InputStream configFile = new FileInputStream(path)) {
            properties.load(configFile);

            for (String clase : properties.stringPropertyNames()) {
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
        } catch (Exception e) {
            throw new RuntimeException("No pude crear la instancia de TextoAImprimir... ", e);
        }
    }

    public void mostrar() {
        Scanner scanner = new Scanner(System.in);
        int seleccion = 0;
        int cantidadAcciones = acciones.size();
        int indice = 1;
        while (seleccion != cantidadAcciones + 1) {
            indice = 1;
            for (Accion accion : acciones) {
                System.out.println("Opcion " + indice + " - " + accion.nombreItemMenu() + " " + accion.descripcionItemMenu());
                indice++;
            }
            System.out.println("Opcion " + indice + " - " + "cerrar menu");
            System.out.println("Seleccione una opcion: ");
            seleccion = scanner.nextInt();
            if (seleccion > 0 && seleccion <= cantidadAcciones + 1) {
                if (seleccion != cantidadAcciones + 1) {
                    acciones.get(seleccion - 1).ejecutar();
                } else {
                    System.out.println("Cerrando menu...");
                }
            } else {
                System.out.println("El valor " + seleccion + " no es valido, ingrese nuevamente...");
            }
        }
        scanner.close();
    }
}
