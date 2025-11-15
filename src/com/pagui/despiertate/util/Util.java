package com.pagui.despiertate.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Util {

    public static void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static int mensajeConfirmacion(String mensaje, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
    }

    public static JFileChooser crearSelectorFichero(File rutaDefecto, String descripcion, String... extensiones) {
        JFileChooser selectorFichero = new JFileChooser();
        if (rutaDefecto != null) {
            selectorFichero.setCurrentDirectory(rutaDefecto);
        }
        if (extensiones != null && extensiones.length > 0) {
            if (extensiones.length == 1) {
                FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extensiones[0]);
                selectorFichero.setFileFilter(filtro);
            } else {
                for (String ext : extensiones) {
                    FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                            ext.toUpperCase() + " (*." + ext.toLowerCase() + ")", ext
                    );
                    selectorFichero.addChoosableFileFilter(filtro);
                }
                selectorFichero.setAcceptAllFileFilterUsed(true);
            }
        }
        return selectorFichero;
    }
}
