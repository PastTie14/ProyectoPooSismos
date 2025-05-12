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
import GUI.Graficos.ClasificacionSismosMagnitud;
import GUI.Graficos.SismosEnUnRangoDeAnnios;
import GUI.Graficos.SismosPorMesEnUnAnnio;
import GUI.Graficos.SismosPorProvincia;
import GUI.Graficos.SismosPorTipoOrigen;
import javax.swing.*;
import java.awt.*;

public class GUIPopupEstadisticas extends JDialog {

    private Controlador controlador;
    
    public GUIPopupEstadisticas(JFrame parent, Controlador controlador) {
        super(parent, "Estadísticas de Sismos", true);
        this.controlador = controlador;
        
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnPorProvincia = new JButton("Cantidad de sismos por provincia");
        JButton btnPorOrigen = new JButton("Cantidad de sismos por tipo de origen");
        JButton btnPorRangoFechas = new JButton("Sismos ocurridos en un rango de fechas");
        JButton btnPorMes = new JButton("Cantidad de sismos por mes en un año");
        JButton btnPorMagnitud = new JButton("Clasificación de sismos por magnitud");

        add(btnPorProvincia);
        add(btnPorOrigen);
        add(btnPorRangoFechas);
        add(btnPorMes);
        add(btnPorMagnitud);

        // Reservar zonas para acciones
        btnPorProvincia.addActionListener(e -> {
            SismosPorProvincia grafico = new SismosPorProvincia(parent,controlador.getCantidadSimosProvincia());
            grafico.setVisible(true);
            dispose();
        });

        btnPorOrigen.addActionListener(e -> {
            SismosPorTipoOrigen grafico = new SismosPorTipoOrigen(parent, controlador.getCantidadSismosPorOrigen());
            grafico.setVisible(true);
            dispose();
        });

        btnPorRangoFechas.addActionListener(e -> {
            String strAnnioMin = JOptionPane.showInputDialog("Digite el año que desea el minimo para filtrar los sismos.");
            String strAnnioMax = JOptionPane.showInputDialog("Digite el año que desea el maximo para filtrar los sismos.");
            if(strAnnioMin.matches("\\d+") && strAnnioMax.matches("\\d+")){
                int annioMin = Integer.parseInt(strAnnioMin);
                int annioMax = Integer.parseInt(strAnnioMax);
                SismosEnUnRangoDeAnnios grafico = new SismosEnUnRangoDeAnnios(parent, controlador.getSismosEnRangoAnnios(annioMin, annioMax));
                grafico.setVisible(true);
            }else
                JOptionPane.showMessageDialog(parent, "Por favor ingrese caracteres numericos.");
            
            dispose();
        });

        btnPorMes.addActionListener(e -> {
            String strAnnio = JOptionPane.showInputDialog("Digite el año del cual desea obtener el grafico.");
            
            if(strAnnio.matches("\\d+")){
                int annio = Integer.parseInt(strAnnio);
                SismosPorMesEnUnAnnio grafico = new SismosPorMesEnUnAnnio(parent, controlador.getCantidadSismosMesAnnio(annio), annio);
                grafico.setVisible(true);
            }else
                JOptionPane.showMessageDialog(parent, "Por favor ingrese caracteres numericos.");
            
            dispose();
        });

        btnPorMagnitud.addActionListener(e -> {
            ClasificacionSismosMagnitud grafico = new ClasificacionSismosMagnitud(parent, controlador.getSismosPorMagnitud());
            grafico.setVisible(true);
            dispose();
        });
    }
}
