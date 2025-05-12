/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 *
 * @author Sebas Masís
 * Clase Sismo que representa un sismo con sus atributos:
 * -magnitud
 * -momento
 * -ubicación
 * -profundidad
 * -tipoOrigen
 */
public class Sismo {
    private Calendar momento;
    private Localizacion ubicacion;
    private double magnitud;
    private double profundidad;
    private OrigenFalla tipoOrigen;
    
    
    /**
    * Crea una nueva instancia de Sismo con los datos proporcionados.
    *
    * @param magnitud     La magnitud del sismo en la escala correspondiente.
    * @param profundidad  La profundidad del sismo en kilómetros.
    * @param momento      El momento en que ocurrió el sismo (como objeto {@code Calendar}).
    * @param tipoOrigen   El tipo de origen de la falla que causó el sismo (valor del enum {@code OrigenFalla}).
    * @param ubicacion    La localización geográfica del sismo (objeto {@code Localizacion}).
    */
    public Sismo(double magnitud, double profundidad, Calendar momento,
            OrigenFalla tipoOrigen, Localizacion ubicacion) {
        this.magnitud = magnitud;
        this.ubicacion = ubicacion;
        this.momento = momento;
        this.profundidad = profundidad;
        this.tipoOrigen = tipoOrigen;
    }

    public void setProfundidad(double profundidad) {this.profundidad = profundidad;}

    public void setTipoOrigen(OrigenFalla tipoOrigen) {this.tipoOrigen = tipoOrigen;}
    
    public void setMomento(Calendar momento) {this.momento = momento;}
    
    public void setubicacion(Localizacion ubicacion) {this.ubicacion = ubicacion;}

    public void setMagnitud(double magnitud) {this.magnitud = magnitud;}

    public Localizacion getubicacion() {return ubicacion;}

    public double getMagnitud() {return magnitud;}
    
    public Calendar getMomento() {return momento;}

    public double getProfundidad() {return profundidad;}

    public OrigenFalla getTipoOrigen() {return tipoOrigen;}
    
    /**
    * Devuelve una representación en cadena del momento en que ocurrió el sismo,
    * formateada como "yyyy-MM-dd HH:mm:ss".
    *
    * @return Una cadena con la fecha y hora del sismo.
    */
    public String printMomento() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(momento.getTime());
    }
    
    
        
    public void printUbi() {ubicacion.print();}
    
    @Override
    public String toString() {
        SimpleDateFormat sdfmomento = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        return "sismo: " + "Fecha = " + sdfmomento.format(momento.getTime()) + "\nMagnitud = " + magnitud +
                "\nTipo de origen = " + tipoOrigen + "\nProfundidad en km = " + profundidad +  
                "\nUbicacion = " + ubicacion.toString();
    } 
}
