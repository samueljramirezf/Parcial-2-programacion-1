package model;

import java.util.ArrayList;
import java.util.Optional;

public class Cliente {
private String ID;
private String nombreCompleto;
private String telefono;
private String correo;
private  String direccion;
Empresa ownedByEmpresa;
ArrayList<EquipoTec> listaEquipos= new ArrayList<>();
ArrayList<OrdenServicio> listaOrdenesDeServicio= new ArrayList<>();

    public Cliente(Empresa ownedByEmpresa, String direccion, String correo, String telefono, String nombreCompleto, String ID) {
        this.ownedByEmpresa = ownedByEmpresa;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.nombreCompleto = nombreCompleto;
        this.ID = ID;
    }

    public String registrarEquipo(EquipoTec equipo) {
        Optional<EquipoTec> equipoYaExiste = buscarEquipo(equipo.getCodigoRegistro());
        if (equipoYaExiste.isEmpty()) {
            listaEquipos.add(equipo);
            return "El equipo fue registrado de forma exitosa";
        } else return "El equipo no pudo ser registrado debido a que ya existe en TechRepair ";
    }

    public Optional<EquipoTec> buscarEquipo(String codigoRegistro) {
        for (EquipoTec equipo : listaEquipos) {
            if (equipo.getCodigoRegistro().equals(codigoRegistro)) {
                return Optional.of(equipo);
            }
        }
        return Optional.empty();
    }

    public ArrayList<EquipoTec> getListaEquipos() {
        return listaEquipos;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
