package proyecto.tordi.utilizacion;

import proyecto.tordi.framework.Accion;

public class PrimerAccion implements Accion {
    @Override
    public void ejecutar() {
        System.out.println("Se ejecuta primer accion");
        for (int i = 0; i < 5; i++) {
            System.out.println(i + " desde primera accion");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
