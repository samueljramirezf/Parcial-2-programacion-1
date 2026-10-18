package model;

public class PCPortatil extends EquipoTec {
    private float tamañoPantalla;
    private int memoriaRam;
    private int almacenamiento;
    private boolean bateriaExtraible;

    public PCPortatil(Cliente ownedByCliente, String codigoRegistro, String numeroSerie, String marca, String modelo, int añoFabricacion, String estadoFisico,
                      float tamañoPantalla, int memoriaRam, int almacenamiento, boolean bateriaExtraible) {
        super(ownedByCliente, codigoRegistro, numeroSerie, marca, modelo, añoFabricacion, estadoFisico);
        this.tamañoPantalla = tamañoPantalla;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.bateriaExtraible = bateriaExtraible;
    }

    @Override
    public double getTarifaMantenimiento() {
        return 60000;
    }

    @Override
    public CategoriaCompatibilidad getCategoria() {
        return CategoriaCompatibilidad.PC_PORTATIL;
    }

    @Override
    public boolean datosValidos() {
        return super.datosValidos() && tamañoPantalla > 0 && memoriaRam > 0 && almacenamiento > 0;
    }

    public float getTamañoPantalla() {
        return tamañoPantalla;
    }

    public void setTamañoPantalla(float tamañoPantalla) {
        this.tamañoPantalla = tamañoPantalla;
    }

    public int getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(int memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public boolean isBateriaExtraible() {
        return bateriaExtraible;
    }

    public void setBateriaExtraible(boolean bateriaExtraible) {
        this.bateriaExtraible = bateriaExtraible;
    }
}
