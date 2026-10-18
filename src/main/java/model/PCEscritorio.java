package model;

public class PCEscritorio extends EquipoTec {
    private String tipoProcesador;
    private int memoriaRam;
    private int almacenamiento;
    private boolean tarjetaGraficaDedicada;

    public PCEscritorio(Cliente ownedByCliente, String codigoRegistro, String numeroSerie, String marca, String modelo, int añoFabricacion, String estadoFisico,
                        String tipoProcesador, int memoriaRam, int almacenamiento, boolean tarjetaGraficaDedicada) {
        super(ownedByCliente, codigoRegistro, numeroSerie, marca, modelo, añoFabricacion, estadoFisico);
        this.tipoProcesador = tipoProcesador;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.tarjetaGraficaDedicada = tarjetaGraficaDedicada;
    }

    @Override
    public double getTarifaMantenimiento() {
        return 60000;
    }

    @Override
    public CategoriaCompatibilidad getCategoria() {
        return CategoriaCompatibilidad.PC_ESCRITORIO;
    }

    public String getTipoProcesador() {
        return tipoProcesador;
    }

    public void setTipoProcesador(String tipoProcesador) {
        this.tipoProcesador = tipoProcesador;
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

    public boolean isTarjetaGraficaDedicada() {
        return tarjetaGraficaDedicada;
    }

    public void setTarjetaGraficaDedicada(boolean tarjetaGraficaDedicada) {
        this.tarjetaGraficaDedicada = tarjetaGraficaDedicada;
    }
}
