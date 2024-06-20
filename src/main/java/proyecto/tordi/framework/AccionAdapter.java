package proyecto.tordi.framework;

public class AccionAdapter implements Runnable {
    private Accion accion;

    @Override
    public void run() {
        accion.ejecutar();
    }
}
