package proyecto.tordi.framework;

public class AccionAdapter implements Runnable {
    private Accion accion;

    public AccionAdapter(Accion accion) {
        this.accion = accion;
    }

    @Override
    public void run() {
        accion.ejecutar();
    }
}
