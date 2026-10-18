package model;

public class ReparacionCorrectiva extends Servicio {

    public ReparacionCorrectiva(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    // mano de obra por hora mas el valor de los repuestos utilizados
    @Override
    public double calcularCosto(DetalleOrdenServicio detalle) {
        double manoDeObra = getTarifaBase() * detalle.getHorasTrabajo();
        return manoDeObra + detalle.calcularCostoRepuestos();
    }
}
