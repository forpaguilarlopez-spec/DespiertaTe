package com.pagui.despiertate.base;

import java.time.LocalDate;

/**
 * Representa un té de DespiertaTe.
 * Añade temperatura de consumo y minutos de infusión a los datos comunes.
 */
public class Te extends Bebidas {

    private double temperaturaConsumo;
    private int minutosInf;

    /**
     * Constructor por defecto.
     */
    public Te() {
        super();
    }

    /**
     * Constructor completo para crear un té.
     *
     * @param codigo              código identificador
     * @param nombre              nombre comercial
     * @param origen              origen o procedencia
     * @param precioKg            precio por kilogramo
     * @param fechaConsPref       fecha de consumo preferente
     * @param temperaturaConsumo  temperatura recomendada de consumo
     * @param minutosInf          minutos de infusión
     */
    public Te(String codigo,
              String nombre,
              String origen,
              double precioKg,
              LocalDate fechaConsPref,
              double temperaturaConsumo,
              int minutosInf) {
        super(codigo, nombre, origen, precioKg, fechaConsPref);
        this.temperaturaConsumo = temperaturaConsumo;
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
        return "Té - " + super.toString() +
                ", Tª Consumo: " + temperaturaConsumo + "ºC" +
                ", Infusión: " + minutosInf + " min";
    }
}
