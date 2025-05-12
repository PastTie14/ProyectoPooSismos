/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Vista;

import GUI.Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Ian
 */
import javax.swing.*;
import java.awt.*;

public class GUIPopupSismos extends JDialog {
    
    Controlador controlador;

    public GUIPopupSismos(JFrame parent, Controlador controlador) {
        super(parent, "Opciones de Sismo", true); // true para modal
        this.controlador = controlador;

        // Crear botones
        JButton btnNuevo = new JButton("Nuevo Sismo");
        JButton btnActualizar = new JButton("Actualizar lista de Sismos");
        JButton btnModificar = new JButton("Modificar Sismo Seleccionado");

        // Agregar eventos a los botones
        btnActualizar.addActionListener(e -> {
            controlador.actualizarSismos();
            dispose();
        });
        
        btnNuevo.addActionListener(e -> {
            controlador.GUISismoAddMod(false);
            dispose();
        });

        btnModificar.addActionListener(e -> {
            controlador.GUISismoAddMod(true);
            dispose();
        });



        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnModificar);

        // Configuración de la ventana
        this.add(panelBotones, BorderLayout.CENTER);
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
    }
}

