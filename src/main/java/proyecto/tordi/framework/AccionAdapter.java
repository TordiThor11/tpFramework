package proyecto.tordi.framework;

import java.util.concurrent.Callable;

public class AccionAdapter implements Callable<Void> {
    private Accion accion;

    public AccionAdapter(Accion accion) {
        this.accion = accion;
    }
    
    @Override
    public Void call() throws Exception {
        accion.ejecutar();
        return null;
    }
}
