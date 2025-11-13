package com.pagui.despiertate.base;

import java.time.LocalDate;

public class Te extends Bebidas {

    private double temperaturaConsumo;
    private int minutosInf;

    public Te() {

    }

    public Te(String codigo, String nombre, String origen, double precioKg, LocalDate fechaConsPref, double temperaturaCons, int minutosInf) {
        super(codigo, nombre, origen, precioKg, fechaConsPref);
        this.temperaturaConsumo = temperaturaCons;
        this.minutosInf = minutosInf;
    }

    public double getTemperaturaConsumo() {
        return temperaturaConsumo;
    }

    public void setTemperaturaConsumo(double temperaturaConsumo) {
        this.temperaturaConsumo = temperaturaConsumo;
    }

    public int getMinutosInf() {
        return minutosInf;
    }

    public void setMinutosInf(int minutosInf) {
        this.minutosInf = minutosInf;
    }

    @Override
    public String toString() {
        return "Té: " + getCodigo() + " " + getNombre() + "\nOrigen: " + getOrigen() + "\nTª Recomendada: " + getTemperaturaConsumo() + "ºC" + "\nMin infusionado: " + getMinutosInf() +
                "\nEur/Kg " + getPrecioKg() + "\nConsumo preferente: " + getFechaConsPref();
    }
}
