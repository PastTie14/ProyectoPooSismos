/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Vista;

/**
 *
 * @author Ian
 */
import GUI.Controlador.Controlador;
import Modelos.Localizacion;
import Modelos.OrigenFalla;
import Modelos.Provincias;
import Modelos.Sismo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GUIFormularioSismoDialog extends JDialog {

    private JTextField txtMagnitud, txtProfundidad;
    private JComboBox<OrigenFalla> cbOrigen;
    private JSpinner spinnerFecha;

    // Localizacion
    private JTextField txtLatitud, txtLongitud, txtDescripcion;
    private JCheckBox chkMaritimo;
    private JComboBox<Provincias> cbProvincia;

    private JButton btnGuardar, btnCancelar;
    private Sismo sismo;
    private Controlador controlador;

    public GUIFormularioSismoDialog(JFrame parent, Sismo sismo, Controlador controlador) {
        super(parent, (sismo == null ? "Nuevo Sismo" : "Modificar Sismo"), true);
        this.sismo = sismo;
        this.controlador = controlador;

        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 5));

        // Campos sismo
        txtMagnitud = new JTextField();
        txtProfundidad = new JTextField();
        cbOrigen = new JComboBox<>(OrigenFalla.values());

        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spinnerFecha = new JSpinner(model);
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd HH:mm:ss"));

        // Campos localización
        txtLatitud = new JTextField();
        txtLongitud = new JTextField();
        txtDescripcion = new JTextField();
        chkMaritimo = new JCheckBox("¿Marítimo?");
        cbProvincia = new JComboBox<>(Provincias.values());

        // Si estamos editando un sismo existente
        if (sismo != null) {
            txtMagnitud.setText(String.valueOf(sismo.getMagnitud()));
            txtProfundidad.setText(String.valueOf(sismo.getProfundidad()));
            cbOrigen.setSelectedItem(sismo.getTipoOrigen());
            spinnerFecha.setValue(sismo.getMomento().getTime());

            Localizacion loc = sismo.getubicacion();
            txtLatitud.setText(String.valueOf(loc.getLatitud()));
            txtLongitud.setText(String.valueOf(loc.getLongitud()));
            txtDescripcion.setText(loc.getDescripcion());
            chkMaritimo.setSelected(loc.isMaritimo());
            cbProvincia.setSelectedItem(loc.getProvinciaOrigen());
        }

        // Añadir campos
        panel.add(new JLabel("Magnitud:"));
        panel.add(txtMagnitud);

        panel.add(new JLabel("Profundidad (km):"));
        panel.add(txtProfundidad);

        panel.add(new JLabel("Tipo de Origen:"));
        panel.add(cbOrigen);

        panel.add(new JLabel("Fecha y Hora:"));
        panel.add(spinnerFecha);

        panel.add(new JLabel("Latitud:"));
        panel.add(txtLatitud);

        panel.add(new JLabel("Longitud:"));
        panel.add(txtLongitud);

        panel.add(new JLabel("¿Marítimo?"));
        panel.add(chkMaritimo);

        panel.add(new JLabel("Provincia de origen:"));
        panel.add(cbProvincia);

        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        // Botones
        btnGuardar = new JButton((sismo==null) ? "Guardar" : "Modificar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarSismo());
        btnCancelar.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        this.add(panel, BorderLayout.CENTER);
        this.add(panelBotones, BorderLayout.SOUTH);
        this.setSize(500, 400);
        this.setLocationRelativeTo(parent);
    }

    private void guardarSismo() {
        try {
            double magnitud = Double.parseDouble(txtMagnitud.getText());
            double profundidad = Double.parseDouble(txtProfundidad.getText());
            OrigenFalla origen = (OrigenFalla) cbOrigen.getSelectedItem();
            Date fecha = (Date) spinnerFecha.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);

            double lat = Double.parseDouble(txtLatitud.getText());
            double lon = Double.parseDouble(txtLongitud.getText());
            boolean mar = chkMaritimo.isSelected();
            Provincias prov = (Provincias) cbProvincia.getSelectedItem();
            String desc = txtDescripcion.getText();

            Localizacion localizacion = new Localizacion(lat, lon, mar, prov, desc);

            if (sismo == null) {
                sismo = new Sismo(magnitud, profundidad, cal, origen, localizacion);
                controlador.guardarSismo(sismo);
            } else {
                sismo.setMagnitud(magnitud);
                sismo.setProfundidad(profundidad);
                sismo.setTipoOrigen(origen);
                sismo.setMomento(cal);
                sismo.setubicacion(localizacion);
                controlador.actualizarExcelSismos();
            }            
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Sismo getSismo() {
        return sismo;
    }
}
