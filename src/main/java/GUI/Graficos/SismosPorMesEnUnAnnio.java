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

public class SismosPorMesEnUnAnnio extends JDialog {

    private int[] cantSismos;
    private int annio;

    public SismosPorMesEnUnAnnio(JFrame parent, int[] cantSismos, int annio) {
        super(parent, "Sismos por Mes en una año", true); // Modal
        this.cantSismos = cantSismos;
        this.annio = annio;

        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        generarGrafico();
    }

    private void generarGrafico() {
        DefaultCategoryDataset datos = new DefaultCategoryDataset();
        for (int i = 0; i < Provincias.values().length - 1; i++) {
            datos.addValue(cantSismos[i], "Sismos", Meses.values()[i].toString());
        }

        JFreeChart grafico = ChartFactory.createBarChart(
            "Sismos por mes en el año " + annio,
            "Meses",
            "Cantidad de Sismos",
            datos,
            PlotOrientation.VERTICAL,
            false, true, false
        );

        ChartPanel panel = new ChartPanel(grafico);
        add(panel, BorderLayout.CENTER);
    }
}

