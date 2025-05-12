/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorSismos;

import Modelos.Meses;
import Modelos.OrigenFalla;
import Modelos.Provincias;
import java.util.ArrayList;
import java.util.List;
import Modelos.Sismo;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Sebas Masís
 */
public class AdmSismos {
    
    // Atributos privados
    private final List<Sismo> listaSismos;
    private int contador = 1;
    
    //Métodos públicos
    
    /**
    * Constructor de la clase AdmSismos. Inicializa la lista que almacenará los sismos.
    */
    public AdmSismos() {listaSismos = new ArrayList<>();}
    
    public List<Sismo> getListaSismos() {return listaSismos;}
    
    /**
    * Agrega un nuevo sismo a la lista de sismos registrados.
    *
    * @param nuevoSismo Objeto Sismo que se desea agregar.
    */
    public void agregarSismo(Sismo nuevoSismo) {listaSismos.add(nuevoSismo);}
    
    /**
    * Elimina un sismo de la lista de sismos registrados.
    *
    * @param S Objeto Sismo que se desea eliminar.
    * @return true si el sismo fue eliminado; false si no se encontraba en la lista.
    */
    public boolean eliminarSismo(Sismo S) {return listaSismos.remove(S);}
    
    public void print() {
        for (Sismo Sismo : listaSismos) {
            System.out.println("Sismo registrado #" + contador);
            System.out.println("Fecha: " + Sismo.printMomento()); 
            System.out.println("Magnitud: " + Sismo.getMagnitud());
            System.out.println("Tipo de origen: " + Sismo.getTipoOrigen());
            System.out.println("Profundidad en Km: " + Sismo.getProfundidad());
            System.out.println("Localización: " + Sismo.getubicacion()); 
            System.out.println("\n");
            contador += 1;
        }
    }
    
    /**
    * Elimina todos los sismos de la lista.
    */
    public void resetListaSismos(){
        listaSismos.clear();
    }
    
    /**
    * Retorna una copia de la lista de sismos ordenada por magnitud de mayor a menor.
    *
    * @return Lista de sismos ordenada por magnitud.
    */
    public ArrayList<Sismo> obtenerClasificacionPorMagnitud(){
        ArrayList<Sismo> copia = new ArrayList<>(listaSismos); 
        copia.sort(new Comparator<Sismo>() {
            @Override
            public int compare(Sismo s1, Sismo s2) {
                return Double.compare(s2.getMagnitud(), s1.getMagnitud());
            }
        });
        return copia;
    }
    
    /**
    * Retorna una lista de sismos ocurridos entre los años especificados (inclusive).
    *
    * @param min Año mínimo (inclusive).
    * @param max Año máximo (inclusive).
    * @return Lista de sismos dentro del rango de años indicado.
    */
    public ArrayList<Sismo> obtenerSismosPorRangoDeAños(int min, int max){
        ArrayList<Sismo> datos = new ArrayList<Sismo>();
        for (Sismo sismo : listaSismos) {
            if (sismo.getMomento().get(Calendar.YEAR) >= min && sismo.getMomento().get(Calendar.YEAR) <= max)
                datos.add(sismo);
        }     
        return datos;
    }
    
    /**
    * Obtiene la cantidad de sismos registrados por provincia.
    *
    * @return Arreglo de enteros donde cada posición representa la cantidad de sismos por provincia.
    */

    public int[] obtenerSismosPorProvincia(){
        int[] arrCant = new int[7];
        for (int i = 0; i < Provincias.values().length - 1 ;i++) {
            for (Sismo sismo : listaSismos) {
                if (sismo.getubicacion().getProvinciaOrigen() == Provincias.values()[i])
                    arrCant[i]++;
            }     
        }
        return arrCant;
    }
    
    /**
    * Obtiene la cantidad de sismos registrados por provincia.
    *
    * @return Arreglo de enteros donde cada posición representa la cantidad de sismos por provincia.
    */
    public int[] obtenerSismosPorTipoDeOrigen(){
        int[] arrCant = new int[5];
        for (int i = 0; i < OrigenFalla.values().length - 1 ;i++) {
            for (Sismo sismo : listaSismos) {
                if (sismo.getTipoOrigen() == OrigenFalla.values()[i])
                    arrCant[i]++;
            }     
        }
        return arrCant;
    }
    
    /**
    * Obtiene la cantidad de sismos registrados por mes en un año específico.
    *
    * @param annio Año para el cual se desea obtener el conteo mensual de sismos.
    * @return Arreglo de 12 posiciones con la cantidad de sismos por mes.
    */
    public int[] obtenerSismosPorMesEnUnAnnio(int annio){
        int[] arrCant = new int[12];
        for (int i = 0; i < Meses.values().length - 1 ;i++) {
            for (Sismo sismo : listaSismos) {
                if (sismo.getMomento().get(Calendar.MONTH) == Meses.values()[i].getMonthOrder() && sismo.getMomento().get(Calendar.YEAR) == annio)
                    arrCant[i]++;
            }     
        }
        return arrCant;
    }

    @Override
    public String toString() {
        return "AdmSismos \nLista de sismos registrados: \n" + listaSismos;
    }
    
}