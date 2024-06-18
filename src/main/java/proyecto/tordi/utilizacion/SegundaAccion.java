package proyecto.tordi.utilizacion;

import proyecto.tordi.framework.Accion;

public class SegundaAccion implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Se ejecuto la segunda accion");
    }

    @Override
    public String nombreItemMenu() {
        return "SegundaAccion";
    }

    @Override
    public String descripcionItemMenu() {
        return "esta es la descripcion de segunda accion";
    }
}
