package com.pagui.despiertate.base;

import java.time.LocalDate;

public class Bebidas {

    private String codigo;
    private String nombre;
    private String origen;
    private double precioKg;
    private LocalDate fechaConsPref;

    public Bebidas() {

    }

    public Bebidas(String codigo, String nombre, String origen, double precioKg, LocalDate fechaConsPref) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.origen = origen;
        this.precioKg = precioKg;
        this.fechaConsPref = fechaConsPref;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public double getPrecioKg() {
        return precioKg;
    }

    public void setPrecioKg(double precioKg) {
        this.precioKg = precioKg;
    }

    public LocalDate getFechaConsPref() {
        return fechaConsPref;
    }

    public void setFechaConsPref(LocalDate fechaConsPref) {
        this.fechaConsPref = fechaConsPref;
    }
}
