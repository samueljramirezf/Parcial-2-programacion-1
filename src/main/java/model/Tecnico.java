package model;

public class Tecnico {
    private String codigo;
    private String nombre;
    private String ID;
    private boolean estado;
    private Especialidad especialidad;
    Empresa ownedByEmpresa;

    public Tecnico(String codigo, String ID, Especialidad especialidad, Empresa ownedByEmpresa, boolean estado, String nombre) {
        this.codigo = codigo;
        this.ID = ID;
        this.especialidad = especialidad;
        this.ownedByEmpresa = ownedByEmpresa;
        this.estado = estado;
        this.nombre = nombre;
    }

    public boolean estaAutorizado(EquipoTec equipo) {
        if (!estado) {
            return false;
        }
        switch (especialidad) {
            case SOPORTE_GENERAL:
                return true;
            case COMPUTADORES:
                return equipo instanceof PCPortatil || equipo instanceof PCEscritorio;
            case DISPOSITIVOS_MOVILES:
                return equipo instanceof CelularInteligente;
            default:
                return false;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Empresa getOwnedByEmpresa() {
        return ownedByEmpresa;
    }

    public void setOwnedByEmpresa(Empresa ownedByEmpresa) {
        this.ownedByEmpresa = ownedByEmpresa;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
