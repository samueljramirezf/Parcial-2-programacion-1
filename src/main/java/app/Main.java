package app;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    static Empresa empresa = new Empresa("TechRepair", "Armenia");

    static void main() {
        cargarDatos();

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEntero("opcion: ");
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    registrarEquipo();
                    break;
                case 3:
                    registrarTecnico();
                    break;
                case 4:
                    registrarRepuesto();
                    break;
                case 5:
                    crearOrden();
                    break;
                case 6:
                    agregarDetalle();
                    break;
                case 7:
                    asignarTecnico();
                    break;
                case 8:
                    registrarRepuestoUtilizado();
                    break;
                case 9:
                    verValorDetalle();
                    break;
                case 10:
                    verValorOrden();
                    break;
                case 11:
                    actualizarEstadoDetalle();
                    break;
                case 12:
                    IO.println(empresa.finalizarOrden(IO.readln("codigo de la orden: ")));
                    break;
                case 13:
                    IO.println(empresa.consultarOrdenesCliente(IO.readln("documento del cliente: ")));
                    break;
                case 14:
                    IO.println(empresa.consultarTrabajosTecnico(IO.readln("codigo del tecnico: ")));
                    break;
                case 15:
                    verEquiposPendientes();
                    break;
                case 16:
                    IO.println(empresa.generarReporteServicios());
                    break;
                case 17:
                    IO.println(empresa.generarReporteTecnicos());
                    break;
                case 18:
                    verInventarioCritico();
                    break;
                case 19:
                    IO.println(empresa.entregarOrden(IO.readln("codigo de la orden: ")));
                    break;
                case 20:
                    IO.println(empresa.cancelarOrden(IO.readln("codigo de la orden: ")));
                    break;
                case 0:
                    IO.println("chao");
                    break;
                default:
                    IO.println("esa opcion no existe");
            }
        }
    }

    static void mostrarMenu() {
        IO.println("");
        IO.println("------ menu techrepair ------");
        IO.println("1. registrar cliente");
        IO.println("2. registrar equipo");
        IO.println("3. registrar tecnico");
        IO.println("4. registrar repuesto");
        IO.println("5. crear orden");
        IO.println("6. agregar detalle a una orden");
        IO.println("7. asignar tecnico");
        IO.println("8. agregar repuesto a una reparacion");
        IO.println("9. ver valor de un detalle");
        IO.println("10. ver total de una orden");
        IO.println("11. cambiar estado de un detalle");
        IO.println("12. finalizar orden");
        IO.println("13. ver ordenes de un cliente");
        IO.println("14. ver trabajos de un tecnico");
        IO.println("15. ver equipos pendientes");
        IO.println("16. reporte de servicios");
        IO.println("17. reporte de tecnicos");
        IO.println("18. ver inventario critico");
        IO.println("19. entregar orden");
        IO.println("20. cancelar orden");
        IO.println("0. salir");
    }

    static void registrarCliente() {
        String documento = IO.readln("documento: ");
        String nombre = IO.readln("nombre: ");
        String telefono = IO.readln("telefono: ");
        String correo = IO.readln("correo: ");
        String direccion = IO.readln("direccion: ");
        Cliente cliente = new Cliente(empresa, direccion, correo, telefono, nombre, documento);
        IO.println(empresa.registrarCliente(cliente));
    }

    static void registrarEquipo() {
        Optional<Cliente> cliente = empresa.buscarCliente(IO.readln("documento del cliente: "));
        if (cliente.isEmpty()) {
            IO.println("ese cliente no existe");
            return;
        }
        IO.println("1. portatil  2. escritorio  3. celular");
        int tipo = leerEntero("tipo de equipo: ");
        String codigo = IO.readln("codigo: ");
        String serie = IO.readln("numero de serie: ");
        String marca = IO.readln("marca: ");
        String modelo = IO.readln("modelo: ");
        int anio = leerEntero("año: ");
        String estadoFisico = IO.readln("estado fisico: ");

        EquipoTec equipo;
        switch (tipo) {
            case 1:
                float pantalla = Float.parseFloat(IO.readln("pulgadas de pantalla: "));
                int ramPortatil = leerEntero("ram: ");
                int almacenamientoPortatil = leerEntero("almacenamiento: ");
                boolean bateriaExtraible = leerSiNo("bateria extraible (s/n): ");
                equipo = new PCPortatil(cliente.get(), codigo, serie, marca, modelo, anio, estadoFisico,
                        pantalla, ramPortatil, almacenamientoPortatil, bateriaExtraible);
                break;
            case 2:
                String procesador = IO.readln("procesador: ");
                int ramEscritorio = leerEntero("ram: ");
                int almacenamientoEscritorio = leerEntero("almacenamiento: ");
                boolean tarjetaGrafica = leerSiNo("tiene grafica dedicada (s/n): ");
                equipo = new PCEscritorio(cliente.get(), codigo, serie, marca, modelo, anio, estadoFisico,
                        procesador, ramEscritorio, almacenamientoEscritorio, tarjetaGrafica);
                break;
            case 3:
                String sistema = IO.readln("sistema operativo: ");
                int almacenamientoCelular = leerEntero("almacenamiento: ");
                int bateria = leerEntero("bateria (mah): ");
                boolean compatible5G = leerSiNo("es 5g (s/n): ");
                equipo = new CelularInteligente(cliente.get(), codigo, serie, marca, modelo, anio, estadoFisico,
                        sistema, almacenamientoCelular, bateria, compatible5G);
                break;
            default:
                IO.println("tipo no valido");
                return;
        }
        IO.println(empresa.registrarEquipo(equipo));
    }

    static void registrarTecnico() {
        String codigo = IO.readln("codigo: ");
        String nombre = IO.readln("nombre: ");
        String documento = IO.readln("documento: ");
        IO.println("1. computadores  2. moviles  3. soporte general");
        Especialidad especialidad;
        switch (leerEntero("especialidad: ")) {
            case 1:
                especialidad = Especialidad.COMPUTADORES;
                break;
            case 2:
                especialidad = Especialidad.DISPOSITIVOS_MOVILES;
                break;
            case 3:
                especialidad = Especialidad.SOPORTE_GENERAL;
                break;
            default:
                IO.println("especialidad no valida");
                return;
        }
        boolean activo = leerSiNo("esta activo (s/n): ");
        Tecnico tecnico = new Tecnico(codigo, documento, especialidad, empresa, activo, nombre);
        IO.println(empresa.registrarTecnico(tecnico));
    }

    static void registrarRepuesto() {
        String codigo = IO.readln("codigo: ");
        String nombre = IO.readln("nombre: ");
        String descripcion = IO.readln("descripcion: ");
        double precio = Double.parseDouble(IO.readln("precio: "));
        int cantidad = leerEntero("cantidad: ");
        IO.println("1. portatil  2. escritorio  3. celular");
        CategoriaCompatibilidad categoria;
        switch (leerEntero("categoria: ")) {
            case 1:
                categoria = CategoriaCompatibilidad.PC_PORTATIL;
                break;
            case 2:
                categoria = CategoriaCompatibilidad.PC_ESCRITORIO;
                break;
            case 3:
                categoria = CategoriaCompatibilidad.CELULAR_INTELIGENTE;
                break;
            default:
                IO.println("categoria no valida");
                return;
        }
        Repuesto repuesto = new Repuesto(empresa, categoria, cantidad, descripcion, codigo, nombre, precio);
        IO.println(empresa.registrarRepuesto(repuesto));
    }

    static void crearOrden() {
        String codigo = IO.readln("codigo de la orden: ");
        String documento = IO.readln("documento del cliente: ");
        String observaciones = IO.readln("observaciones: ");
        IO.println(empresa.crearOrdenServicio(codigo, documento, LocalDate.now(), observaciones));
    }

    static void agregarDetalle() {
        String codigoOrden = IO.readln("codigo de la orden: ");
        String codigoDetalle = IO.readln("codigo del detalle: ");
        String codigoEquipo = IO.readln("codigo del equipo: ");
        IO.println("servicios: diag = diagnostico, mant = mantenimiento, rep = reparacion");
        String codigoServicio = IO.readln("servicio: ");
        String falla = IO.readln("falla: ");
        double horas = Double.parseDouble(IO.readln("horas (0 si no es reparacion): "));
        IO.println(empresa.agregarDetalleOrden(codigoOrden, codigoDetalle, codigoEquipo, codigoServicio, falla, horas));
    }

    static void asignarTecnico() {
        String codigoOrden = IO.readln("codigo de la orden: ");
        String codigoDetalle = IO.readln("codigo del detalle: ");
        String codigoTecnico = IO.readln("codigo del tecnico: ");
        IO.println(empresa.asignarTecnico(codigoOrden, codigoDetalle, codigoTecnico));
    }

    static void registrarRepuestoUtilizado() {
        String codigoOrden = IO.readln("codigo de la orden: ");
        String codigoDetalle = IO.readln("codigo del detalle: ");
        String codigoRepuesto = IO.readln("codigo del repuesto: ");
        int cantidad = leerEntero("cantidad: ");
        IO.println(empresa.registrarRepuestoUtilizado(codigoOrden, codigoDetalle, codigoRepuesto, cantidad));
    }

    static void verValorDetalle() {
        String codigoOrden = IO.readln("codigo de la orden: ");
        String codigoDetalle = IO.readln("codigo del detalle: ");
        Optional<DetalleOrdenServicio> detalle = empresa.buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle.isEmpty()) {
            IO.println("no se encontro");
        } else {
            IO.println("valor: " + detalle.get().calcularValor());
        }
    }

    static void verValorOrden() {
        Optional<OrdenServicio> orden = empresa.buscarOrden(IO.readln("codigo de la orden: "));
        if (orden.isEmpty()) {
            IO.println("esa orden no existe");
        } else {
            IO.println("total: " + orden.get().calcularValorTotal());
        }
    }

    static void actualizarEstadoDetalle() {
        String codigoOrden = IO.readln("codigo de la orden: ");
        String codigoDetalle = IO.readln("codigo del detalle: ");
        IO.println("1. pendiente  2. en proceso  3. finalizado");
        EstadoIntervencion estado;
        switch (leerEntero("estado: ")) {
            case 1:
                estado = EstadoIntervencion.PENDIENTE;
                break;
            case 2:
                estado = EstadoIntervencion.EN_PROCESO;
                break;
            case 3:
                estado = EstadoIntervencion.FINALIZADO;
                break;
            default:
                IO.println("estado no valido");
                return;
        }
        IO.println(empresa.actualizarEstadoDetalle(codigoOrden, codigoDetalle, estado));
    }

    static void verEquiposPendientes() {
        ArrayList<EquipoTec> pendientes = empresa.consultarEquiposPendientes();
        if (pendientes.isEmpty()) {
            IO.println("no hay equipos pendientes");
        }
        for (EquipoTec equipo : pendientes) {
            IO.println(equipo.getCodigoRegistro() + " - " + equipo.getMarca() + " " + equipo.getModelo()
                    + " - cliente: " + equipo.getOwnedByCliente().getNombreCompleto());
        }
    }

    static void verInventarioCritico() {
        ArrayList<Repuesto> criticos = empresa.consultarInventarioCritico();
        if (criticos.isEmpty()) {
            IO.println("no hay repuestos criticos");
        }
        for (Repuesto repuesto : criticos) {
            IO.println(repuesto.getCodigo() + " - " + repuesto.getNombre() + " - quedan " + repuesto.getCantidadDisponible());
        }
    }

    static int leerEntero(String mensaje) {
        return Integer.parseInt(IO.readln(mensaje));
    }

    static boolean leerSiNo(String mensaje) {
        return IO.readln(mensaje).equalsIgnoreCase("s");
    }

    // datos para probar rapido
    static void cargarDatos() {
        Cliente juan = new Cliente(empresa, "calle 1", "juan@gmail.com", "300123", "juan", "123");
        empresa.registrarCliente(juan);
        empresa.registrarEquipo(new PCPortatil(juan, "e1", "s1", "lenovo", "ideapad", 2022, "bien", 15f, 8, 512, false));
        empresa.registrarEquipo(new CelularInteligente(juan, "e2", "s2", "samsung", "a54", 2023, "pantalla rayada", "android", 128, 5000, true));

        empresa.registrarTecnico(new Tecnico("t1", "111", Especialidad.COMPUTADORES, empresa, true, "pedro"));
        empresa.registrarTecnico(new Tecnico("t2", "222", Especialidad.DISPOSITIVOS_MOVILES, empresa, true, "maria"));

        empresa.registrarRepuesto(new Repuesto(empresa, CategoriaCompatibilidad.PC_PORTATIL, 6, "pantalla de portatil", "r1", "pantalla", 250000));
        empresa.registrarRepuesto(new Repuesto(empresa, CategoriaCompatibilidad.CELULAR_INTELIGENTE, 3, "bateria de celular", "r2", "bateria", 90000));
    }
}
