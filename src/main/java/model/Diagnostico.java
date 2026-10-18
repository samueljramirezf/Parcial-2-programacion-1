package model;

public class Diagnostico extends Servicio {

    public Diagnostico(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    // el diagnostico tiene un costo fijo
    @Override
    public double calcularCosto(DetalleOrdenServicio detalle) {
        return getTarifaBase();
    }
}
