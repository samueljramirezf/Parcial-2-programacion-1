package model;

public class MantenimientoPreventivo extends Servicio {

    public MantenimientoPreventivo(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    @Override
    public double calcularCosto() {
        return getTarifaBase() * 1.2;
    }
}
