package com.pagui.despiertate.base;

import java.time.LocalDate;

/**
 * Representa un café de DespiertaTe.
 * Añade intensidad y si es descafeinado a los datos comunes de Bebidas.
 */
public class Cafe extends Bebidas {

    private int intensidad;
    private boolean descafeinado;

    /**
     * Constructor por defecto.
     */
    public Cafe() {
        super();
    }

    /**
     * Constructor completo para crear un café.
     *
     * @param codigo        código identificador
     * @param nombre        nombre comercial
     * @param origen        origen o procedencia
     * @param precioKg      precio por kilogramo
     * @param fechaConsPref fecha de consumo preferente
     * @param intensidad    intensidad del café
     * @param descafeinado  true si es descafeinado
     */
    public Cafe(String codigo,
                String nombre,
                String origen,
                double precioKg,
                LocalDate fechaConsPref,
                int intensidad,
                boolean descafeinado) {
        super(codigo, nombre, origen, precioKg, fechaConsPref);
        this.intensidad = intensidad;
        this.descafeinado = descafeinado;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public boolean isDescafeinado() {
        return descafeinado;
    }

    public void setDescafeinado(boolean descafeinado) {
        this.descafeinado = descafeinado;
    }

    @Override
    public String toString() {
        return "Café - " + super.toString() +
                ", Intensidad: " + intensidad +
                ", Descafeinado: " + (descafeinado ? "Sí" : "No");
    }
}
