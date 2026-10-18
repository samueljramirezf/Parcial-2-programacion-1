package model;

public class RepuestoUtilizado {
    private int cantidad;
    private double precioUnitario;
    Repuesto repuesto;
    DetalleOrdenServicio ownedByDetalle;

    public RepuestoUtilizado(DetalleOrdenServicio ownedByDetalle, Repuesto repuesto, int cantidad) {
        this.ownedByDetalle = ownedByDetalle;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        // se guarda el precio del momento por si luego cambia en el inventario
        this.precioUnitario = repuesto.getPrecioUnitario();
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public DetalleOrdenServicio getOwnedByDetalle() {
        return ownedByDetalle;
    }
}
