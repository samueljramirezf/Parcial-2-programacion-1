package model;

public abstract class Servicio {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double tarifaBase;
    Empresa ownedByEmpresa;

    public Servicio(Empresa ownedByEmpresa, String codigo, String nombre, String descripcion, double tarifaBase) {
        this.ownedByEmpresa = ownedByEmpresa;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tarifaBase = tarifaBase;
    }

    public abstract double calcularCosto();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public Empresa getOwnedByEmpresa() {
        return ownedByEmpresa;
    }
}
