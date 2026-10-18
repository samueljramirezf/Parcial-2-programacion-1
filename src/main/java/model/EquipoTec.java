package model;

import java.time.LocalDate;

public abstract class EquipoTec {
    private String codigoRegistro;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private int añoFabricacion;
    private String estadoFisico;
    private Cliente ownedByCliente;

    public EquipoTec(Cliente ownedByCliente, String codigoRegistro, String numeroSerie, String marca, String modelo, int anioFabricacion, String estadoFisico) {
        this.ownedByCliente = ownedByCliente;
        this.codigoRegistro = codigoRegistro;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.añoFabricacion = anioFabricacion;
        this.estadoFisico = estadoFisico;
    }

    public abstract double getTarifaMantenimiento();

    public abstract CategoriaCompatibilidad getCategoria();

    // valida los datos comunes; cada hija agrega los de su categoria
    public boolean datosValidos() {
        int anioActual = LocalDate.now().getYear();
        if (estaVacio(codigoRegistro) || estaVacio(numeroSerie) || estaVacio(marca) || estaVacio(modelo) || estaVacio(estadoFisico)) {
            return false;
        }
        return añoFabricacion >= 1990 && añoFabricacion <= anioActual;
    }

    protected boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAñoFabricacion() {
        return añoFabricacion;
    }

    public void setAnioFabricacion(int añoFabricacion) {
        this.añoFabricacion = añoFabricacion;
    }

    public String getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(String estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public Cliente getOwnedByCliente() {
        return ownedByCliente;
    }
}
