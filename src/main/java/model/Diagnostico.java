package model;

public class Diagnostico extends Servicio {

    public Diagnostico(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    @Override
    public double calcularCosto() {
        return getTarifaBase();
    }
}
