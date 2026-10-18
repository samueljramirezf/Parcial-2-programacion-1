package model;

import java.time.LocalDate;
import java.util.*;

public class Empresa {
    private final String nombre;
    private final String localidad;
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    private ArrayList<Tecnico> listaTecnicos = new ArrayList<>();
    // la llave es el codigo del repuesto
    private Map<String, Repuesto> inventario = new HashMap<>();
    private ArrayList<OrdenServicio> listaOrdenes = new ArrayList<>();
    private ArrayList<Servicio> catalogoServicios = new ArrayList<>();

    public Empresa(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
        // servicios que ofrece la empresa
        catalogoServicios.add(new Diagnostico(this, "diag", "diagnostico", "identificar fallas", 35000));
        catalogoServicios.add(new MantenimientoPreventivo(this, "mant", "mantenimiento", "limpieza y revision", 0));
        catalogoServicios.add(new ReparacionCorrectiva(this, "rep", "reparacion", "arreglar fallas", 45000));
    }

    // registros

    public String registrarCliente(Cliente cliente) {
        if (cliente == null) {
            return "cliente invalido";
        }
        if (estaVacio(cliente.getID()) || estaVacio(cliente.getNombreCompleto()) || estaVacio(cliente.getTelefono())
                || estaVacio(cliente.getDireccion())) {
            return "faltan datos del cliente";
        }
        if (estaVacio(cliente.getCorreo()) || !cliente.getCorreo().contains("@")) {
            return "el correo no es valido";
        }
        if (buscarCliente(cliente.getID()).isPresent()) {
            return "ya hay un cliente con ese documento";
        }
        listaClientes.add(cliente);
        return "cliente registrado";
    }

    public String registrarEquipo(EquipoTec equipo) {
        if (equipo == null || equipo.getOwnedByCliente() == null) {
            return "el equipo no tiene cliente";
        }
        if (buscarCliente(equipo.getOwnedByCliente().getID()).isEmpty()) {
            return "ese cliente no esta registrado";
        }
        if (!equipo.datosValidos()) {
            return "los datos del equipo estan mal";
        }
        // el codigo no se puede repetir en toda la empresa
        if (buscarEquipo(equipo.getCodigoRegistro()).isPresent()) {
            return "ya hay un equipo con ese codigo";
        }
        return equipo.getOwnedByCliente().registrarEquipo(equipo);
    }

    public String registrarTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            return "tecnico invalido";
        }
        if (estaVacio(tecnico.getCodigo()) || estaVacio(tecnico.getNombre()) || estaVacio(tecnico.getID())
                || tecnico.getEspecialidad() == null) {
            return "faltan datos del tecnico";
        }
        if (buscarTecnico(tecnico.getCodigo()).isPresent()) {
            return "ya hay un tecnico con ese codigo";
        }
        listaTecnicos.add(tecnico);
        return "tecnico registrado";
    }

    public String registrarRepuesto(Repuesto repuesto) {
        if (repuesto == null) {
            return "repuesto invalido";
        }
        if (estaVacio(repuesto.getCodigo()) || estaVacio(repuesto.getNombre()) || repuesto.getCategoriaCompatibilidad() == null) {
            return "faltan datos del repuesto";
        }
        if (repuesto.getPrecioUnitario() <= 0) {
            return "el precio tiene que ser mayor a 0";
        }
        if (repuesto.getCantidadDisponible() < 0) {
            return "la cantidad no puede ser negativa";
        }
        if (inventario.containsKey(repuesto.getCodigo())) {
            return "ya hay un repuesto con ese codigo";
        }
        inventario.put(repuesto.getCodigo(), repuesto);
        return "repuesto registrado";
    }

    // ordenes

    public String crearOrdenServicio(String codigo, String documentoCliente, LocalDate fecha, String observaciones) {
        if (estaVacio(codigo) || fecha == null) {
            return "falta el codigo o la fecha";
        }
        if (buscarOrden(codigo).isPresent()) {
            return "ya hay una orden con ese codigo";
        }
        Optional<Cliente> cliente = buscarCliente(documentoCliente);
        if (cliente.isEmpty()) {
            return "ese cliente no existe";
        }
        listaOrdenes.add(new OrdenServicio(this, cliente.get(), codigo, fecha, observaciones));
        return "orden creada";
    }

    public String agregarDetalleOrden(String codigoOrden, String codigoDetalle, String codigoEquipo, String codigoServicio,
                                      String descripcionFalla, double horasTrabajo) {
        Optional<OrdenServicio> orden = buscarOrden(codigoOrden);
        if (orden.isEmpty()) {
            return "esa orden no existe";
        }
        if (estaVacio(codigoDetalle) || estaVacio(descripcionFalla)) {
            return "falta el codigo del detalle o la falla";
        }
        if (horasTrabajo < 0) {
            return "las horas no pueden ser negativas";
        }
        // el equipo se busca solo en los equipos del cliente de la orden
        Optional<EquipoTec> equipo = orden.get().getCliente().buscarEquipo(codigoEquipo);
        if (equipo.isEmpty()) {
            return "el cliente no tiene ese equipo";
        }
        Optional<Servicio> servicio = buscarServicio(codigoServicio);
        if (servicio.isEmpty()) {
            return "ese servicio no existe";
        }
        DetalleOrdenServicio detalle = new DetalleOrdenServicio(orden.get(), codigoDetalle, descripcionFalla, horasTrabajo,
                "", equipo.get(), servicio.get());
        return orden.get().agregarDetalle(detalle);
    }

    public String asignarTecnico(String codigoOrden, String codigoDetalle, String codigoTecnico) {
        Optional<DetalleOrdenServicio> detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle.isEmpty()) {
            return "no se encontro la orden o el detalle";
        }
        Optional<Tecnico> tecnico = buscarTecnico(codigoTecnico);
        if (tecnico.isEmpty()) {
            return "ese tecnico no existe";
        }
        return detalle.get().asignarTecnico(tecnico.get());
    }

    public String registrarRepuestoUtilizado(String codigoOrden, String codigoDetalle, String codigoRepuesto, int cantidad) {
        Optional<DetalleOrdenServicio> detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle.isEmpty()) {
            return "no se encontro la orden o el detalle";
        }
        Optional<Repuesto> repuesto = buscarRepuesto(codigoRepuesto);
        if (repuesto.isEmpty()) {
            return "ese repuesto no existe";
        }
        return detalle.get().registrarRepuesto(repuesto.get(), cantidad);
    }

    public String actualizarEstadoDetalle(String codigoOrden, String codigoDetalle, EstadoIntervencion nuevoEstado) {
        Optional<DetalleOrdenServicio> detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle.isEmpty()) {
            return "no se encontro la orden o el detalle";
        }
        if (nuevoEstado == null) {
            return "falta el estado";
        }
        return detalle.get().actualizarEstado(nuevoEstado);
    }

    public String finalizarOrden(String codigoOrden) {
        Optional<OrdenServicio> orden = buscarOrden(codigoOrden);
        if (orden.isEmpty()) {
            return "esa orden no existe";
        }
        return orden.get().finalizar();
    }

    public String entregarOrden(String codigoOrden) {
        Optional<OrdenServicio> orden = buscarOrden(codigoOrden);
        if (orden.isEmpty()) {
            return "esa orden no existe";
        }
        return orden.get().entregar();
    }

    public String cancelarOrden(String codigoOrden) {
        Optional<OrdenServicio> orden = buscarOrden(codigoOrden);
        if (orden.isEmpty()) {
            return "esa orden no existe";
        }
        return orden.get().cancelar();
    }

    // busquedas

    public Optional<Cliente> buscarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getID().equals(documento)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    public Optional<EquipoTec> buscarEquipo(String codigoRegistro) {
        for (Cliente cliente : listaClientes) {
            Optional<EquipoTec> equipo = cliente.buscarEquipo(codigoRegistro);
            if (equipo.isPresent()) {
                return equipo;
            }
        }
        return Optional.empty();
    }

    public Optional<Tecnico> buscarTecnico(String codigo) {
        for (Tecnico tecnico : listaTecnicos) {
            if (tecnico.getCodigo().equals(codigo)) {
                return Optional.of(tecnico);
            }
        }
        return Optional.empty();
    }

    public Optional<Repuesto> buscarRepuesto(String codigo) {
        if (inventario.containsKey(codigo)) {
            return Optional.of(inventario.get(codigo));
        }
        return Optional.empty();
    }

    public Optional<Servicio> buscarServicio(String codigo) {
        for (Servicio servicio : catalogoServicios) {
            if (servicio.getCodigo().equals(codigo)) {
                return Optional.of(servicio);
            }
        }
        return Optional.empty();
    }

    public Optional<OrdenServicio> buscarOrden(String codigo) {
        for (OrdenServicio orden : listaOrdenes) {
            if (orden.getCodigo().equals(codigo)) {
                return Optional.of(orden);
            }
        }
        return Optional.empty();
    }

    public Optional<DetalleOrdenServicio> buscarDetalle(String codigoOrden, String codigoDetalle) {
        Optional<OrdenServicio> orden = buscarOrden(codigoOrden);
        if (orden.isEmpty()) {
            return Optional.empty();
        }
        return orden.get().buscarDetalle(codigoDetalle);
    }

    // consultas y reportes

    // 13. ordenes de un cliente con equipos, servicios, estados y valores
    public String consultarOrdenesCliente(String documento) {
        Optional<Cliente> cliente = buscarCliente(documento);
        if (cliente.isEmpty()) {
            return "ese cliente no existe";
        }
        String reporte = "ordenes de " + cliente.get().getNombreCompleto() + "\n";
        int cantidad = 0;
        for (OrdenServicio orden : listaOrdenes) {
            if (orden.getCliente() == cliente.get()) {
                cantidad++;
                reporte += "orden " + orden.getCodigo() + " - fecha: " + orden.getFecha() + " - estado: " + orden.getEstadoDeOrden()
                        + " - total: " + orden.calcularValorTotal() + "\n";
                for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                    reporte += "   " + detalle.getCodigo() + " - equipo: " + detalle.getEquipo().getCodigoRegistro()
                            + " (serie " + detalle.getEquipo().getNumeroSerie() + ")"
                            + " - servicio: " + detalle.getServicio().getNombre()
                            + " - estado: " + detalle.getEstado()
                            + " - valor: " + detalle.calcularValor() + "\n";
                }
            }
        }
        if (cantidad == 0) {
            reporte += "no tiene ordenes\n";
        }
        return reporte;
    }

    // 14. trabajos de un tecnico separados por estado
    public String consultarTrabajosTecnico(String codigoTecnico) {
        Optional<Tecnico> tecnico = buscarTecnico(codigoTecnico);
        if (tecnico.isEmpty()) {
            return "ese tecnico no existe";
        }
        String reporte = "trabajos de " + tecnico.get().getNombre() + "\n";
        for (EstadoIntervencion estado : EstadoIntervencion.values()) {
            reporte += estado + ":\n";
            int cantidad = 0;
            for (OrdenServicio orden : listaOrdenes) {
                for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                    if (detalle.getTecnicoResponsable() == tecnico.get() && detalle.getEstado() == estado) {
                        cantidad++;
                        reporte += "   orden " + orden.getCodigo() + " - detalle " + detalle.getCodigo()
                                + " - equipo: " + detalle.getEquipo().getCodigoRegistro()
                                + " - servicio: " + detalle.getServicio().getNombre() + "\n";
                    }
                }
            }
            if (cantidad == 0) {
                reporte += "   nada\n";
            }
        }
        return reporte;
    }

    // 15. equipos que tienen detalles sin finalizar
    public ArrayList<EquipoTec> consultarEquiposPendientes() {
        ArrayList<EquipoTec> pendientes = new ArrayList<>();
        for (OrdenServicio orden : listaOrdenes) {
            if (orden.getEstadoDeOrden() == EstadoDeOrden.CANCELADA) {
                continue;
            }
            for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                if (detalle.getEstado() != EstadoIntervencion.FINALIZADO && !pendientes.contains(detalle.getEquipo())) {
                    pendientes.add(detalle.getEquipo());
                }
            }
        }
        return pendientes;
    }

    // 16. cuantos servicios hay de cada tipo y cuanto se facturo por tipo y por categoria de equipo
    public String generarReporteServicios() {
        String reporte = "servicios por tipo:\n";
        for (Servicio servicio : catalogoServicios) {
            int cantidad = 0;
            double total = 0;
            for (OrdenServicio orden : listaOrdenes) {
                if (orden.getEstadoDeOrden() == EstadoDeOrden.CANCELADA) {
                    continue;
                }
                for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                    if (detalle.getServicio() == servicio) {
                        cantidad++;
                        total += detalle.calcularValor();
                    }
                }
            }
            reporte += "   " + servicio.getNombre() + ": " + cantidad + " - total: " + total + "\n";
        }

        reporte += "total por categoria de equipo:\n";
        for (CategoriaCompatibilidad categoria : CategoriaCompatibilidad.values()) {
            double total = 0;
            for (OrdenServicio orden : listaOrdenes) {
                if (orden.getEstadoDeOrden() == EstadoDeOrden.CANCELADA) {
                    continue;
                }
                for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                    if (detalle.getEquipo().getCategoria() == categoria) {
                        total += detalle.calcularValor();
                    }
                }
            }
            reporte += "   " + categoria + ": " + total + "\n";
        }
        return reporte;
    }

    // 17. cuantos detalles finalizo cada tecnico y cuanto valen
    public String generarReporteTecnicos() {
        String reporte = "reporte de tecnicos:\n";
        for (Tecnico tecnico : listaTecnicos) {
            int finalizadas = 0;
            double valor = 0;
            for (OrdenServicio orden : listaOrdenes) {
                for (DetalleOrdenServicio detalle : orden.getDetalles()) {
                    if (detalle.getTecnicoResponsable() == tecnico && detalle.getEstado() == EstadoIntervencion.FINALIZADO) {
                        finalizadas++;
                        valor += detalle.calcularValor();
                    }
                }
            }
            reporte += "   " + tecnico.getNombre() + ": " + finalizadas + " finalizadas - valor: " + valor + "\n";
        }
        return reporte;
    }

    // 18. repuestos con menos de 5 unidades
    public ArrayList<Repuesto> consultarInventarioCritico() {
        ArrayList<Repuesto> criticos = new ArrayList<>();
        for (Repuesto repuesto : inventario.values()) {
            if (repuesto.esCritico()) {
                criticos.add(repuesto);
            }
        }
        return criticos;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public ArrayList<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }

    public Map<String, Repuesto> getInventario() {
        return inventario;
    }

    public ArrayList<OrdenServicio> getListaOrdenes() {
        return listaOrdenes;
    }

    public ArrayList<Servicio> getCatalogoServicios() {
        return catalogoServicios;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                ", clientes=" + listaClientes.size() +
                ", tecnicos=" + listaTecnicos.size() +
                ", repuestos=" + inventario.size() +
                ", ordenes=" + listaOrdenes.size() +
                '}';
    }
}
