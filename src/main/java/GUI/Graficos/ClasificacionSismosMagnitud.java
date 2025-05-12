/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Graficos;

/**
 *
 * @author Ian
 */
import Modelos.Magnitudes;
import Modelos.Sismo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ClasificacionSismosMagnitud extends JDialog {

    String [] columnas = {"#","Fecha y Hora", "Provincia" , "Magnitud" , "Tipo de Origen"}; 
    public ClasificacionSismosMagnitud(JFrame parent, ArrayList<Sismo> datos) {
        super(parent, "Sismos en un Rango de Años", true); // Modal
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        DefaultTableModel modelo = new DefaultTableModel(FormatearObjSismo(datos), columnas);
        JTable tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(scrollPane, BorderLayout.CENTER);
    }
    
    private Object [][] FormatearObjSismo(ArrayList<Sismo> datos){
        Object[][] formatTable = new Object[datos.size()][columnas.length];
        for (int i = 0; i < datos.size(); i++) {
            formatTable[i][0] = i+1;
            formatTable[i][1] = datos.get(i).printMomento();
            formatTable[i][2] = datos.get(i).getubicacion().getProvinciaOrigen();
            formatTable[i][3] = datos.get(i).getMagnitud() + " - " + Magnitudes.clasificarMagnitud(datos.get(i).getMagnitud());
            formatTable[i][4] = datos.get(i).getTipoOrigen();
        }
        return formatTable;
    }
    
}

