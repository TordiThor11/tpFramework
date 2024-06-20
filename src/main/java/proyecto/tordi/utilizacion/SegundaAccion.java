package proyecto.tordi.utilizacion;

import proyecto.tordi.framework.Accion;

public class SegundaAccion implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Se ejecuto la segunda accion");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " desde segunda accion");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
