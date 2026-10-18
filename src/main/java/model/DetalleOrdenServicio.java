package model;

import java.util.ArrayList;

public class DetalleOrdenServicio {
    private String codigo;
    private String descripcionFalla;
    private double horasTrabajo;
    private String observacionesTecnicas;
    private EstadoIntervencion estado;
    private OrdenServicio ownedByOrden;
    private EquipoTec equipo;
    private Servicio servicio;
    private Tecnico tecnicoResponsable;
    private ArrayList<RepuestoUtilizado> repuestosUtilizados = new ArrayList<>();

    public DetalleOrdenServicio(OrdenServicio ownedByOrden, String codigo, String descripcionFalla, double horasTrabajo,
                                String observacionesTecnicas, EquipoTec equipo, Servicio servicio) {
        this.ownedByOrden = ownedByOrden;
        this.codigo = codigo;
        this.descripcionFalla = descripcionFalla;
        this.horasTrabajo = horasTrabajo;
        this.observacionesTecnicas = observacionesTecnicas;
        this.equipo = equipo;
        this.servicio = servicio;
        this.estado = EstadoIntervencion.PENDIENTE;
    }

    public String asignarTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            return "ese tecnico no existe";
        }
        if (!tecnico.isEstado()) {
            return "el tecnico esta inactivo";
        }
        if (!tecnico.estaAutorizado(equipo)) {
            return "el tecnico no puede atender este tipo de equipo";
        }
        this.tecnicoResponsable = tecnico;
        return "tecnico asignado";
    }

    public String registrarRepuesto(Repuesto repuesto, int cantidad) {
        if (!(servicio instanceof ReparacionCorrectiva)) {
            return "solo se le pueden poner repuestos a una reparacion";
        }
        if (estado == EstadoIntervencion.FINALIZADO) {
            return "ese detalle ya esta finalizado";
        }
        if (cantidad <= 0) {
            return "la cantidad tiene que ser mayor a 0";
        }
        if (repuesto.getCategoriaCompatibilidad() != equipo.getCategoria()) {
            return "ese repuesto no sirve para este equipo";
        }
        if (!repuesto.hayDisponibilidad(cantidad)) {
            return "no hay suficientes unidades de ese repuesto";
        }
        repuesto.descontar(cantidad);
        repuestosUtilizados.add(new RepuestoUtilizado(this, repuesto, cantidad));
        return "repuesto agregado";
    }

    public double calcularCostoRepuestos() {
        double total = 0;
        for (RepuestoUtilizado r : repuestosUtilizados) {
            total += r.calcularSubtotal();
        }
        return total;
    }

    // el valor depende del tipo de servicio
    public double calcularValor() {
        return servicio.calcularCosto(this);
    }

    public String actualizarEstado(EstadoIntervencion nuevoEstado) {
        if (estado == EstadoIntervencion.FINALIZADO) {
            return "ese detalle ya esta finalizado, no se puede cambiar";
        }
        if (nuevoEstado == EstadoIntervencion.FINALIZADO && tecnicoResponsable == null) {
            return "no se puede finalizar sin tecnico asignado";
        }
        this.estado = nuevoEstado;
        return "estado cambiado a " + nuevoEstado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public void setDescripcionFalla(String descripcionFalla) {
        this.descripcionFalla = descripcionFalla;
    }

    public double getHorasTrabajo() {
        return horasTrabajo;
    }

    public void setHorasTrabajo(double horasTrabajo) {
        this.horasTrabajo = horasTrabajo;
    }

    public String getObservacionesTecnicas() {
        return observacionesTecnicas;
    }

    public void setObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
    }

    public EstadoIntervencion getEstado() {
        return estado;
    }

    public OrdenServicio getOwnedByOrden() {
        return ownedByOrden;
    }

    public EquipoTec getEquipo() {
        return equipo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Tecnico getTecnicoResponsable() {
        return tecnicoResponsable;
    }

    public ArrayList<RepuestoUtilizado> getRepuestosUtilizados() {
        return repuestosUtilizados;
    }
}
