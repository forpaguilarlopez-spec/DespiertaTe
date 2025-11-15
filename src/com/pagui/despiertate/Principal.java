package com.pagui.despiertate;

import com.pagui.despiertate.base.Cafe;
import com.pagui.despiertate.base.Te;
import com.pagui.despiertate.gui.BebidasControlador;
import com.pagui.despiertate.gui.BebidasModelo;
import com.pagui.despiertate.gui.Ventana;

import java.time.LocalDate;

/**
 * Punto de entrada de la aplicación DespiertaTe.
 * Crea la vista, el modelo y el controlador.
 */

public class Principal {
    public static void main(String[] args) {

        Ventana vista = new Ventana();
        BebidasModelo modelo = new BebidasModelo();
        new BebidasControlador(vista, modelo);

    }
}
