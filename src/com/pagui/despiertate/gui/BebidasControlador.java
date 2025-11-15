package com.pagui.despiertate.gui;

import com.pagui.despiertate.base.Bebidas;
import com.pagui.despiertate.base.Cafe;
import com.pagui.despiertate.base.Te;
import com.pagui.despiertate.util.Util;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Controlador MVC de la aplicación DespiertaTe.
 * Atiende los eventos de la vista, actualiza el modelo y
 * sincroniza la interfaz con los datos.
 */
public class BebidasControlador implements ActionListener, ListSelectionListener, WindowListener {

    private Ventana vista;
    private BebidasModelo modelo;
    private File ultimaRutaExportada;

    /**
     * Construye el controlador y registra los listeners necesarios.
     *
     * @param vista  vista principal
     * @param modelo modelo de datos
     */
    public BebidasControlador(Ventana vista, BebidasModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        try {
            cargarDatosConfiguracion();
        } catch (IOException e) {
            System.out.println("No existe el fichero de configuracion " + e.getMessage());
        }

        addActionListener(this);
        addWindowListener(this);
        addListSelectionListener(this);
    }

    private void addActionListener(ActionListener listener) {
        vista.nuevoButton.addActionListener(listener);
        vista.exportarButton.addActionListener(listener);
        vista.importarButton.addActionListener(listener);
        vista.cafeRadioButton.addActionListener(listener);
        vista.teRadioButton.addActionListener(listener);
        vista.borrarButton.addActionListener(listener);
        vista.modificarButton.addActionListener(listener);
    }

    private void addWindowListener(WindowListener listener) {
        vista.frame.addWindowListener(listener);
    }

    private void addListSelectionListener(ListSelectionListener listener) {
        vista.listaBebidas.addListSelectionListener(listener);
    }

    private boolean hayCamposVacios() {
        if (vista.codigoTxt.getText().isEmpty() ||
                vista.nombreTxt.getText().isEmpty() ||
                vista.origenTxt.getText().isEmpty() ||
                vista.precioTxt.getText().isEmpty() ||
                vista.fechaConsPrefPicker.getText().isEmpty() ||
                vista.campoExtraTxt.getText().isEmpty()) {
            return true;
        }
        if (vista.teRadioButton.isSelected() && vista.minutosInfusionTxt.getText().isEmpty()) {
            return true;
        }
        return false;
    }

    private void limpiarCampos() {
        vista.codigoTxt.setText(null);
        vista.nombreTxt.setText(null);
        vista.origenTxt.setText(null);
        vista.precioTxt.setText(null);
        vista.fechaConsPrefPicker.clear();
        vista.campoExtraTxt.setText(null);
        vista.descafeinadoCheckBox.setSelected(false);
        vista.minutosInfusionTxt.setText(null);
        if (vista.infoBebidaTxt != null) {
            vista.infoBebidaTxt.setText("");
        }
        vista.codigoTxt.requestFocus();
    }

    private void refrescar() {
        vista.dlmBebidas.clear();
        for (Bebidas bebida : modelo.getBebidas()) {
            vista.dlmBebidas.addElement(bebida);
        }
    }

    /**
     * Carga la última ruta utilizada desde el fichero de configuración.
     */
    private void cargarDatosConfiguracion() throws IOException {
        File conf = new File("despiertate.conf");
        if (!conf.exists()) {
            return;
        }
        Properties configuracion = new Properties();
        configuracion.load(new java.io.FileReader(conf));
        String ruta = configuracion.getProperty("ultimaRutaExportada");
        if (ruta != null && !ruta.isEmpty()) {
            ultimaRutaExportada = new File(ruta);
        }
    }

    /**
     * Actualiza la ruta a guardar en el fichero de configuración.
     *
     * @param directorio directorio que se guardará como última ruta usada
     */
    private void actualizarDatosConfiguracion(File directorio) {
        this.ultimaRutaExportada = directorio;
    }

    /**
     * Guarda la configuración actual en un fichero de propiedades.
     */
    private void guardarConfiguracion() throws IOException {
        Properties configuracion = new Properties();
        if (ultimaRutaExportada != null) {
            configuracion.setProperty("ultimaRutaExportada", ultimaRutaExportada.getAbsolutePath());
        }
        java.io.PrintWriter pw = new java.io.PrintWriter("despiertate.conf");
        configuracion.store(pw, "Datos configuracion DespiertaTe");
        pw.close();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int resp = Util.mensajeConfirmacion("¿Desea cerrar la ventana?", "Salir");
        if (resp == JOptionPane.YES_OPTION) {
            try {
                guardarConfiguracion();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Cuando se selecciona una bebida en la lista, se rellenan los campos
     * del formulario con sus datos.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Bebidas bebidaSeleccionada = (Bebidas) vista.listaBebidas.getSelectedValue();
            if (bebidaSeleccionada == null) {
                return;
            }
            vista.codigoTxt.setText(bebidaSeleccionada.getCodigo());
            vista.nombreTxt.setText(bebidaSeleccionada.getNombre());
            vista.origenTxt.setText(bebidaSeleccionada.getOrigen());
            vista.precioTxt.setText(String.valueOf(bebidaSeleccionada.getPrecioKg()));
            vista.fechaConsPrefPicker.setDate(bebidaSeleccionada.getFechaConsPref());
            if (bebidaSeleccionada instanceof Cafe) {
                Cafe cafe = (Cafe) bebidaSeleccionada;
                vista.cafeRadioButton.setSelected(true);
                vista.campoExtraLbl.setText("Intensidad:");
                vista.campoExtraTxt.setText(String.valueOf(cafe.getIntensidad()));
                vista.descafeinadoCheckBox.setEnabled(true);
                vista.descafeinadoCheckBox.setSelected(cafe.isDescafeinado());
                vista.minutosInfusionTxt.setEnabled(false);
                vista.minutosInfusionLbl.setEnabled(false);
                vista.minutosInfusionTxt.setText("");
            } else if (bebidaSeleccionada instanceof Te) {
                Te te = (Te) bebidaSeleccionada;
                vista.teRadioButton.setSelected(true);
                vista.campoExtraLbl.setText("Temp. consumo:");
                vista.campoExtraTxt.setText(String.valueOf(te.getTemperaturaConsumo()));
                vista.descafeinadoCheckBox.setEnabled(false);
                vista.descafeinadoCheckBox.setSelected(false);
                vista.minutosInfusionTxt.setEnabled(true);
                vista.minutosInfusionLbl.setEnabled(true);
                vista.minutosInfusionTxt.setText(String.valueOf(te.getMinutosInf()));
            }
            if (vista.infoBebidaTxt != null) {
                vista.infoBebidaTxt.setText(bebidaSeleccionada.toString());
            }
        }
    }

    /**
     * Gestiona los eventos de los botones y radioButtons de la vista.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Nuevo":
                if (hayCamposVacios()) {
                    Util.mensajeError("Los siguientes campos no pueden estar vacíos:\nCódigo\nNombre\nOrigen\nPrecio\nFecha Cons. Pref.\n" + vista.campoExtraLbl.getText());
                    break;
                }
                if (modelo.existeCodigo(vista.codigoTxt.getText())) {
                    Util.mensajeError("Ya existe una bebida con ese código:\n" + vista.codigoTxt.getText());
                    break;
                }
                try {
                    if (vista.cafeRadioButton.isSelected()) {
                        modelo.altaCafe(
                                vista.codigoTxt.getText(),
                                vista.nombreTxt.getText(),
                                vista.origenTxt.getText(),
                                Double.parseDouble(vista.precioTxt.getText().replace(",", ".")),
                                vista.fechaConsPrefPicker.getDate(),
                                Integer.parseInt(vista.campoExtraTxt.getText().trim()),
                                vista.descafeinadoCheckBox.isSelected()
                        );
                    } else if (vista.teRadioButton.isSelected()) {
                        modelo.altaTe(
                                vista.codigoTxt.getText(),
                                vista.nombreTxt.getText(),
                                vista.origenTxt.getText(),
                                Double.parseDouble(vista.precioTxt.getText().replace(",", ".")),
                                vista.fechaConsPrefPicker.getDate(),
                                Double.parseDouble(vista.campoExtraTxt.getText().replace(",", ".")),
                                Integer.parseInt(vista.minutosInfusionTxt.getText().trim())
                        );
                    } else {
                        Util.mensajeError("Selecciona un tipo de bebida.");
                        break;
                    }
                    limpiarCampos();
                    refrescar();
                } catch (NumberFormatException ex) {
                    Util.mensajeError("Error en formato numérico. Revisa los campos.");
                }
                break;

            case "Importar":
                JFileChooser selectorImport = Util.crearSelectorFichero(ultimaRutaExportada, "Archivos de bebida", "xml", "txt");
                int optImport = selectorImport.showOpenDialog(null);
                if (optImport == JFileChooser.APPROVE_OPTION) {
                    File ficheroImport = selectorImport.getSelectedFile();
                    String nombreImport = ficheroImport.getName().toLowerCase();
                    try {
                        if (nombreImport.endsWith(".xml")) {
                            modelo.importarXML(ficheroImport);
                        } else if (nombreImport.endsWith(".txt")) {
                            modelo.importarTXT(ficheroImport);
                        } else {
                            Util.mensajeError("El fichero debe ser .xml o .txt");
                            break;
                        }
                        actualizarDatosConfiguracion(ficheroImport.getParentFile());
                        if (modelo.getBebidas().isEmpty()) {
                            Util.mensajeError("El fichero no contiene bebidas válidas o el formato es incorrecto.");
                        } else {
                            refrescar();
                        }
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SAXException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case "Exportar":
                JFileChooser selectorExport = Util.crearSelectorFichero(ultimaRutaExportada, "Archivos de bebida", "xml", "txt");
                int optExport = selectorExport.showSaveDialog(null);
                if (optExport == JFileChooser.APPROVE_OPTION) {
                    File ficheroExport = selectorExport.getSelectedFile();
                    FileFilter filtro = selectorExport.getFileFilter();
                    boolean filtroTxt = filtro != null && filtro.getDescription().toLowerCase().contains("txt");
                    String nombreExport = ficheroExport.getName().toLowerCase();

                    if (!nombreExport.contains(".")) {
                        if (filtroTxt) {
                            ficheroExport = new File(ficheroExport.getAbsolutePath() + ".txt");
                        } else {
                            ficheroExport = new File(ficheroExport.getAbsolutePath() + ".xml");
                        }
                        nombreExport = ficheroExport.getName().toLowerCase();
                    }

                    try {
                        if (nombreExport.endsWith(".xml")) {
                            modelo.exportarXML(ficheroExport);
                        } else if (nombreExport.endsWith(".txt")) {
                            modelo.exportarTXT(ficheroExport);
                        } else {
                            File ficheroXml = new File(ficheroExport.getAbsolutePath() + ".xml");
                            modelo.exportarXML(ficheroXml);
                            ficheroExport = ficheroXml;
                        }
                        actualizarDatosConfiguracion(ficheroExport.getParentFile());
                    } catch (ParserConfigurationException ex) {
                        ex.printStackTrace();
                    } catch (TransformerException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case "Borrar":
                int indice = vista.listaBebidas.getSelectedIndex();
                if (indice == -1) {
                    Util.mensajeError("Selecciona una bebida para borrar.");
                    break;
                }
                int resp = Util.mensajeConfirmacion("¿Desea borrar la bebida seleccionada?", "Borrar");
                if (resp == JOptionPane.YES_OPTION) {
                    modelo.getBebidas().remove(indice);
                    limpiarCampos();
                    refrescar();
                }
                break;

            case "Modificar":
                int index = vista.listaBebidas.getSelectedIndex();
                if (index == -1) {
                    Util.mensajeError("Selecciona una bebida para modificar.");
                    break;
                }
                if (hayCamposVacios()) {
                    Util.mensajeError("Los campos no pueden estar vacíos para modificar.");
                    break;
                }
                try {
                    Bebidas bebida = modelo.getBebidas().get(index);
                    bebida.setCodigo(vista.codigoTxt.getText());
                    bebida.setNombre(vista.nombreTxt.getText());
                    bebida.setOrigen(vista.origenTxt.getText());
                    bebida.setPrecioKg(Double.parseDouble(vista.precioTxt.getText().replace(",", ".")));
                    bebida.setFechaConsPref(vista.fechaConsPrefPicker.getDate());
                    if (bebida instanceof Cafe && vista.cafeRadioButton.isSelected()) {
                        Cafe cafe = (Cafe) bebida;
                        cafe.setIntensidad(Integer.parseInt(vista.campoExtraTxt.getText().trim()));
                        cafe.setDescafeinado(vista.descafeinadoCheckBox.isSelected());
                    } else if (bebida instanceof Te && vista.teRadioButton.isSelected()) {
                        Te te = (Te) bebida;
                        te.setTemperaturaConsumo(Double.parseDouble(vista.campoExtraTxt.getText().replace(",", ".")));
                        te.setMinutosInf(Integer.parseInt(vista.minutosInfusionTxt.getText().trim()));
                    }
                    refrescar();
                } catch (NumberFormatException ex) {
                    Util.mensajeError("Error en formato numérico al modificar.");
                }
                break;

            case "Té":
                vista.campoExtraLbl.setText("Temp. consumo:");
                vista.descafeinadoCheckBox.setEnabled(false);
                vista.descafeinadoCheckBox.setSelected(false);
                vista.minutosInfusionTxt.setEnabled(true);
                vista.minutosInfusionLbl.setEnabled(true);
                vista.minutosInfusionTxt.setText("");
                break;

            case "Café":
                vista.campoExtraLbl.setText("Intensidad:");
                vista.descafeinadoCheckBox.setEnabled(true);
                vista.minutosInfusionTxt.setEnabled(false);
                vista.minutosInfusionLbl.setEnabled(false);
                vista.minutosInfusionTxt.setText("");
                break;
        }
    }
}
