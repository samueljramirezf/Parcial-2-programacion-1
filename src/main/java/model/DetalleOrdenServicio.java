package model;

import java.util.ArrayList;

public class DetalleOrdenServicio {
    private String codigo;
    private String descripcionFalla;
    private double horasTrabajo;
    private String observacionesTecnicas;
    private EstadoIntervencion estado;
    OrdenServicio ownedByOrden;
    EquipoTec equipo;
    Servicio servicio;
    Tecnico tecnicoResponsable;
    ArrayList<RepuestoUtilizado> repuestosUtilizados = new ArrayList<>();

    public DetalleOrdenServicio(OrdenServicio ownedByOrden, String codigo, String descripcionFalla, double horasTrabajo,
                                String observacionesTecnicas, EstadoIntervencion estado, EquipoTec equipo, Servicio servicio) {
        this.ownedByOrden = ownedByOrden;
        this.codigo = codigo;
        this.descripcionFalla = descripcionFalla;
        this.horasTrabajo = horasTrabajo;
        this.observacionesTecnicas = observacionesTecnicas;
        this.estado = estado;
        this.equipo = equipo;
        this.servicio = servicio;
    }

    public boolean asignarTecnico(Tecnico tecnico) {
        if (tecnico != null && tecnico.estaAutorizado(equipo)) {
            this.tecnicoResponsable = tecnico;
            return true;
        }
        return false;
    }

    public boolean registrarRepuesto(Repuesto repuesto, int cantidad) {
        if (repuesto.getCategoriaCompatibilidad() != equipo.getCategoria()) {
            return false;
        }
        if (!repuesto.hayDisponibilidad(cantidad)) {
            return false;
        }
        repuesto.descontar(cantidad);
        repuestosUtilizados.add(new RepuestoUtilizado(this, repuesto, cantidad));
        return true;
    }

    public double calcularValor() {
        double valor = servicio.calcularCosto() + horasTrabajo * equipo.getTarifaMantenimiento();
        for (RepuestoUtilizado r : repuestosUtilizados) {
            valor += r.calcularSubtotal();
        }
        return valor;
    }

    public void actualizarEstado(EstadoIntervencion nuevoEstado) {
        this.estado = nuevoEstado;
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
