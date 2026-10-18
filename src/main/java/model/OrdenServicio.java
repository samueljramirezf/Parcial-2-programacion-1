package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdenServicio {
  protected String codigo;
  protected LocalDate fecha;
  protected String observaciones;
  protected EstadoDeOrden estadoDeOrden;
  Cliente cliente;
  Empresa ownedByEmpresa;
  ArrayList<DetalleOrdenServicio> detalles = new ArrayList<>();

  public OrdenServicio(Empresa ownedByEmpresa, Cliente cliente, String codigo, LocalDate fecha, String observaciones) {
    this.ownedByEmpresa = ownedByEmpresa;
    this.cliente = cliente;
    this.codigo = codigo;
    this.fecha = fecha;
    this.observaciones = observaciones;
    this.estadoDeOrden = EstadoDeOrden.ABIERTA;
  }

  public boolean agregarDetalle(DetalleOrdenServicio detalle) {
    if (estadoDeOrden == EstadoDeOrden.ABIERTA || estadoDeOrden == EstadoDeOrden.EN_PROCESO) {
      detalles.add(detalle);
      estadoDeOrden = EstadoDeOrden.EN_PROCESO;
      return true;
    }
    return false;
  }

  public double calcularValorTotal() {
    double total = 0;
    for (DetalleOrdenServicio d : detalles) {
      total += d.calcularValor();
    }
    return total;
  }

  public boolean finalizar() {
    // segun el diagrama una orden debe tener al menos un detalle (1..*)
    if (estadoDeOrden == EstadoDeOrden.EN_PROCESO && !detalles.isEmpty()) {
      estadoDeOrden = EstadoDeOrden.FINALIZADA;
      return true;
    }
    return false;
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

  public void setEstadoDeOrden(EstadoDeOrden estadoDeOrden) {
    this.estadoDeOrden = estadoDeOrden;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public ArrayList<DetalleOrdenServicio> getDetalles() {
    return detalles;
  }
}
