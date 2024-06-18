package proyecto.tordi.utilizacion;

import proyecto.tordi.framework.Accion;

public class PrimerAccion implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Se ejecuta primer accion");
    }

    @Override
    public String nombreItemMenu() {
        return "PrimerAccion";
    }

    @Override
    public String descripcionItemMenu() {
        return "Esta es la descripcion de primer accion";
    }
}
