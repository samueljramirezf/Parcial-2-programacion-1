package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class OrdenServicio {
  private String codigo;
  private LocalDate fecha;
  private String observaciones;
  private EstadoDeOrden estadoDeOrden;
  private Cliente cliente;
  private Empresa ownedByEmpresa;
  private ArrayList<DetalleOrdenServicio> detalles = new ArrayList<>();

  public OrdenServicio(Empresa ownedByEmpresa, Cliente cliente, String codigo, LocalDate fecha, String observaciones) {
    this.ownedByEmpresa = ownedByEmpresa;
    this.cliente = cliente;
    this.codigo = codigo;
    this.fecha = fecha;
    this.observaciones = observaciones;
    this.estadoDeOrden = EstadoDeOrden.ABIERTA;
  }

  public String agregarDetalle(DetalleOrdenServicio detalle) {
    if (estadoDeOrden != EstadoDeOrden.ABIERTA && estadoDeOrden != EstadoDeOrden.EN_PROCESO) {
      return "la orden tiene que estar abierta o en proceso";
    }
    if (buscarDetalle(detalle.getCodigo()).isPresent()) {
      return "ya hay un detalle con ese codigo en la orden";
    }
    if (detalle.getEquipo().getOwnedByCliente() != cliente) {
      return "ese equipo no es del cliente de la orden";
    }
    detalles.add(detalle);
    estadoDeOrden = EstadoDeOrden.EN_PROCESO;
    return "detalle agregado";
  }

  public Optional<DetalleOrdenServicio> buscarDetalle(String codigoDetalle) {
    for (DetalleOrdenServicio detalle : detalles) {
      if (detalle.getCodigo().equals(codigoDetalle)) {
        return Optional.of(detalle);
      }
    }
    return Optional.empty();
  }

  public double calcularValorTotal() {
    double total = 0;
    for (DetalleOrdenServicio d : detalles) {
      total += d.calcularValor();
    }
    return total;
  }

  public String finalizar() {
    // segun el diagrama una orden debe tener al menos un detalle (1..*)
    if (estadoDeOrden != EstadoDeOrden.EN_PROCESO || detalles.isEmpty()) {
      return "la orden tiene que estar en proceso y tener detalles";
    }
    for (DetalleOrdenServicio d : detalles) {
      if (d.getEstado() != EstadoIntervencion.FINALIZADO) {
        return "no se puede finalizar, el detalle " + d.getCodigo() + " no esta finalizado";
      }
    }
    estadoDeOrden = EstadoDeOrden.FINALIZADA;
    return "orden finalizada";
  }

  public String entregar() {
    if (estadoDeOrden != EstadoDeOrden.FINALIZADA) {
      return "la orden tiene que estar finalizada para entregarla";
    }
    estadoDeOrden = EstadoDeOrden.ENTREGADA;
    return "orden entregada";
  }

  public String cancelar() {
    if (estadoDeOrden != EstadoDeOrden.ABIERTA && estadoDeOrden != EstadoDeOrden.EN_PROCESO) {
      return "solo se puede cancelar si esta abierta o en proceso";
    }
    estadoDeOrden = EstadoDeOrden.CANCELADA;
    return "orden cancelada";
  }

  public String getCodigo() {
    return codigo;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public EstadoDeOrden getEstadoDeOrden() {
    return estadoDeOrden;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public Empresa getOwnedByEmpresa() {
    return ownedByEmpresa;
  }

  public ArrayList<DetalleOrdenServicio> getDetalles() {
    return detalles;
  }
}
