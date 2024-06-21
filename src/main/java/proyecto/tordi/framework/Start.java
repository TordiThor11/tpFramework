package proyecto.tordi.framework;

import java.util.ArrayList;

public class Start {
    private Menu menu;

    public Start(String path) {
        this.menu = new Menu(path);
    }

    public Start(ArrayList<Accion> acciones) {
        this.menu = new Menu(acciones);
    }

    public Start(ArrayList<Accion> acciones, int cantidadMaximaThread) {
        this.menu = new Menu(acciones, cantidadMaximaThread);
    }

    public void init() {
        menu.mostrar();
    }
}
