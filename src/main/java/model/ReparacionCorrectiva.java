package model;

public class ReparacionCorrectiva extends Servicio {

    public ReparacionCorrectiva(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        super(ownedByEmpresa, codigo, nombre, descripcion, tarifaBase);
    }

    @Override
    public double calcularCosto() {
        return getTarifaBase() * 1.5;
    }
}
