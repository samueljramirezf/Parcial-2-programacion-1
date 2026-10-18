package model;

public class Repuesto {
    private final String codigo;
    private final String nombre;
    private final String descripcion;
    private final double precioUnitario;
    private short cantidaDisponible;
    private final CategoriaCompatibilidad categoriaCompatibilidad;
    Empresa ownedByEmpresa;

    public Repuesto(Empresa ownedByEmpresa, CategoriaCompatibilidad categoriaCompatibilidad, short cantidaDisponible, String descripcion, String codigo, String nombre, double precioUnitario) {
        this.ownedByEmpresa = ownedByEmpresa;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
        this.cantidaDisponible = cantidaDisponible;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
    }

    public boolean hayDisponibilidad(int cantidad) {
        return cantidad > 0 && cantidaDisponible >= cantidad;
    }

    public boolean descontar(int cantidad) {
        if (!hayDisponibilidad(cantidad)) {
            return false;
        }
        cantidaDisponible -= cantidad;
        return true;
    }

    public boolean esCritico() {
        return cantidaDisponible <= 5;
    }

    public String getCodigo() {
        return codigo;
    }

    public CategoriaCompatibilidad getCategoriaCompatibilidad() {
        return categoriaCompatibilidad;
    }

    public short getCantidaDisponible() {
        return cantidaDisponible;
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
}
