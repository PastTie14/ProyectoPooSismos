/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelos;

/**
 *
 * @author Ian
 */
public enum Meses {
    Eneron(1), Febrero(2), Marzo(3), Abril(4), Mayo(5), Junio(6), Julio(7), Agosto(8), Septiembre(9), Octubre(10), Noviembre(11), Diciembre(12);
    
    private int ordenMeses;
    
    Meses (int ordenMeses) {
        this.ordenMeses = ordenMeses;
    }
    
    public int getMonthOrder() {
        return this.ordenMeses;
    }
}
