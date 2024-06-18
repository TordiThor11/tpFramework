package proyecto.tordi.framework;

public class Start {
    private Menu menu;

    public Start(Menu menu) {
        this.menu = menu;
    }

    public void init() {
        menu.mostrar();
    }
}
