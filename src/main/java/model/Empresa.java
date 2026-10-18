package model;

import java.util.*;

public class Empresa {
    private final String nombre;
    private final String localidad;
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    private ArrayList<Tecnico> listaTecnicos = new ArrayList<>();
    private Map<Repuesto, Integer> listarepuestos = new HashMap<>();
    private ArrayList<OrdenServicio> listaOrdenesServico = new ArrayList<>();
    private ArrayList<Servicio> listaServicio= new ArrayList<>();

    public Empresa(String nombre, String localidad) {
        this.nombre = nombre;
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                ", listaClientes=" + listaClientes +
                ", listaTecnicos=" + listaTecnicos +
                ", listarepuestos=" + listarepuestos +
                ", listaOrdenesServico=" + listaOrdenesServico +
                ", listaServicio=" + listaServicio +
                '}';
    }
}