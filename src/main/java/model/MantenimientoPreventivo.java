package model;

public class MantenimientoPreventivo extends Servicio {

    public MantenimientoPreventivo(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    // el mantenimiento cobra la tarifa que corresponde al tipo de equipo
    @Override
    public double calcularCosto(DetalleOrdenServicio detalle) {
        return detalle.getEquipo().getTarifaMantenimiento();
    }
}
