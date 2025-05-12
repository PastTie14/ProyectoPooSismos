/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Graficos;

/**
 *
 * @author Ian
 */
import ControladorSismos.AdmSismos;
import Modelos.Meses;
import Modelos.OrigenFalla;
import Modelos.Provincias;
import Modelos.Sismo;
import java.util.ArrayList;
import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JFrame;

import javax.swing.JDialog;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.BorderLayout;
import org.jfree.data.general.DefaultPieDataset;

public class SismosPorTipoOrigen extends JDialog {

    private int[] cantSismos;

    public SismosPorTipoOrigen(JFrame parent, int[] cantSismos) {
        super(parent, "Sismos por tipo de origen", true); // Modal
        this.cantSismos = cantSismos;

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        generarGrafico();
    }

    private void generarGrafico() {
        DefaultPieDataset datos = new DefaultPieDataset();
        for (int i = 0; i < OrigenFalla.values().length - 1; i++) {
            datos.setValue(OrigenFalla.values()[i],cantSismos[i]);
        }

        JFreeChart grafico = ChartFactory.createPieChart(
            "Sismos por tipo de origen ",
            datos,
            true, 
            true, 
            false
        );

        ChartPanel panel = new ChartPanel(grafico);
        add(panel, BorderLayout.CENTER);
    }
}

