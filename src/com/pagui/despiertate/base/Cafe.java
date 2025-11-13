package com.pagui.despiertate.base;

import java.time.LocalDate;

public class Cafe extends Bebidas {

    private int intensidad;

    public Cafe() {
        super();
    }

    public Cafe(String codigo, String nombre, String origen, double precioKg, LocalDate fechaConsPref, int intensidad) {
        super(codigo, nombre, origen, precioKg, fechaConsPref);
        this.intensidad = intensidad;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    @Override
    public String toString() {
        return "Cafe: " + getCodigo() + " " + getNombre() + "\nOrigen: " + getOrigen() + "\nIntensidad: " + getIntensidad() + "\nEur/Kg: " + getPrecioKg() + "\nConsumo preferente: " + getFechaConsPref();
    }
}
