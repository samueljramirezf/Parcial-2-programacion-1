package model;

public class Repuesto {
    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final double precioUnitario;
    private int cantidadDisponible;
    private final CategoriaCompatibilidad categoriaCompatibilidad;
    private Empresa ownedByEmpresa;

    public Repuesto(Empresa ownedByEmpresa, CategoriaCompatibilidad categoriaCompatibilidad, int cantidadDisponible, String descripcion, String codigo, String nombre, double precioUnitario) {
        this.ownedByEmpresa = ownedByEmpresa;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
        this.cantidadDisponible = cantidadDisponible;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    public boolean hayDisponibilidad(int cantidad) {
        return cantidad > 0 && cantidadDisponible >= cantidad;
    }

    public boolean descontar(int cantidad) {
        if (!hayDisponibilidad(cantidad)) {
            return false;
        }
        cantidadDisponible -= cantidad;
        return true;
    }

    // el contexto dice existencias inferiores a cinco unidades
    public boolean esCritico() {
        return cantidadDisponible < 5;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public CategoriaCompatibilidad getCategoriaCompatibilidad() {
        return categoriaCompatibilidad;
    }

    public Empresa getOwnedByEmpresa() {
        return ownedByEmpresa;
    }
}
