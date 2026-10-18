package model;

public class CelularInteligente extends EquipoTec {
    private String sistemaOperativo;
    private int almacenamiento;
    private int capacidadBateria;
    private boolean compatible5G;

    public CelularInteligente(Cliente ownedByCliente, String codigoRegistro, String numeroSerie, String marca, String modelo, int añoFabricacion, String estadoFisico,
                              String sistemaOperativo, int almacenamiento, int capacidadBateria, boolean compatible5G) {
        super(ownedByCliente, codigoRegistro, numeroSerie, marca, modelo, añoFabricacion, estadoFisico);
        this.sistemaOperativo = sistemaOperativo;
        this.almacenamiento = almacenamiento;
        this.capacidadBateria = capacidadBateria;
        this.compatible5G = compatible5G;
    }

    @Override
    public double getTarifaMantenimiento() {
        return 40000;
    }

    @Override
    public CategoriaCompatibilidad getCategoria() {
        return CategoriaCompatibilidad.CELULAR_INTELIGENTE;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public int getCapacidadBateria() {
        return capacidadBateria;
    }

    public void setCapacidadBateria(int capacidadBateria) {
        this.capacidadBateria = capacidadBateria;
    }

    public boolean isCompatible5G() {
        return compatible5G;
    }

    public void setCompatible5G(boolean compatible5G) {
        this.compatible5G = compatible5G;
    }
}
