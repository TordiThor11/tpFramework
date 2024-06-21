package proyecto.tordi.main;

import proyecto.tordi.framework.Accion;
import proyecto.tordi.framework.Menu;
import proyecto.tordi.framework.Start;
import proyecto.tordi.utilizacion.PrimerAccion;
import proyecto.tordi.utilizacion.SegundaAccion;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Instanciando las acciones
        var acciones = new ArrayList<Accion>();
        acciones.add(new PrimerAccion());
        acciones.add(new SegundaAccion());
//        acciones.add(new PrimerAccion());


        var start = new Start(new Menu("src/main/java/proyecto/tordi/utilizacion/config.properties"));
//        var start = new Start(new Menu(acciones, 2));
//        var start = new Start(new Menu("src/main/java/proyecto/tordi/utilizacion/config.json"));
        start.init();
    }
}