package proyecto.tordi.main;

import proyecto.tordi.framework.Menu;
import proyecto.tordi.framework.Start;

public class Main {
    public static void main(String[] args) {
        //Instanciando las acciones
        /*var acciones = new ArrayList<Accion>();
        acciones.add(new PrimerAccion());
        acciones.add(new SegundaAccion());
        acciones.add(new PrimerAccion());*/


        var start = new Start(new Menu("src/main/java/proyecto/tordi/utilizacion/config.properties"));
        start.init();
    }
}