package com.pagui.despiertate.gui;

import com.pagui.despiertate.base.Bebidas;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

/**
 * Vista principal de la aplicación DespiertaTe.
 * Define y organiza los componentes Swing de la interfaz gráfica.
 */

public class Ventana {

    private JPanel panelMain;
    public JRadioButton cafeRadioButton;
    public JRadioButton teRadioButton;
    public JTextField codigoTxt;
    public JTextField nombreTxt;
    public JTextField origenTxt;
    public JTextField precioTxt;
    public JLabel campoExtraLbl;
    public JTextField campoExtraTxt;
    public JCheckBox descafeinadoCheckBox;
    public JTextField minutosInfusionTxt;
    public JButton nuevoButton;
    public JButton exportarButton;
    public JButton importarButton;
    public JList listaBebidas;
    public DatePicker fechaConsPrefPicker;
    public JPanel tipoPanel;
    public JPanel botonesPanel;
    public JButton modificarButton;
    public JTextArea infoBebidaTxt;
    public JButton borrarButton;
    public JLabel minutosInfusionLbl;

    public JFrame frame;
    public DefaultListModel<Bebidas> dlmBebidas;

    public Ventana() {
        frame = new JFrame("DespiertaTé - CafesTería");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initComponents();
    }

    private void initComponents() {

        dlmBebidas = new DefaultListModel<>();
        listaBebidas.setModel(dlmBebidas);

    }
}



