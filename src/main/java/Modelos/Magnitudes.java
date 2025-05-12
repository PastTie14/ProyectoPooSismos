/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelos;

/**
 *
 * @author Ian
 */
public enum Magnitudes {
    Micro, Menor,Ligero,Moderado,Fuerte,Mayor,Gran,Épico;
    
    /**
    * Clasifica una magnitud sísmica en una de las categorías del enum {@code Magnitudes}.
    *
    * @param magnitud El valor numérico de la magnitud del sismo.
    * @return La categoría de magnitud correspondiente al valor proporcionado:
    */
    public static Magnitudes clasificarMagnitud(double magnitud){
        if (magnitud < 2) {
            return Micro;
        } else if (magnitud <= 3.9) {
            return Menor;
        } else if (magnitud <= 4.9) {
            return Ligero;
        } else if (magnitud <= 5.9) {
            return Moderado;
        } else if (magnitud <= 6.9) {
            return Fuerte;
        } else if (magnitud <= 7.9) {
            return Mayor;
        } else if (magnitud <= 9.9) {
            return Gran;
        } else {
            return Épico;
        }        
    }
}
