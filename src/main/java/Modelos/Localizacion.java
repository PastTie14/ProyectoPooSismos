/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;


/**
  
   @author Sebas Masís
   IMPORTANTE: El PDF de la profe dice :
    Localización, la cual se indica en latitud y longitud, acompañada de una
    descripción detallada de la zona del epicentro. Es importante anotar que la
    latitud y longitud se expresa en grados y es REQUERIDO que la descripción
    detallada incluya la provincia donde se originó el epicentro. Debe considerarse
    además un sismo pudo haber sido originado en zona terrestre o marítima, en
    cuyo caso no tiene una provincia registrada por lo que se debe poder
    diferenciar entre estas dos condiciones. 
    
 */
public class Localizacion {
    private double latitud;
    private double longitud;
    private boolean maritimo;
    private String descripcion;
    private Provincias provinciaOrigen;
    
    /**
    * Construye una nueva instancia de localizacion con los valores especificados.
    *
    * @param latitud La latitud geográfica de la localización.
    * @param longitud La longitud geográfica de la localización.
    * @param maritimo {@code true} si la localización es marítima, de lo contrario {@code false}.
    * @param provinciaOrigen La provincia de origen correspondiente a la localización.
    * @param descripcion Una descripción adicional sobre la localización.
    */
    public Localizacion(double latitud, double longitud, boolean maritimo, Provincias provinciaOrigen, String descripcion) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.maritimo = maritimo;
        this.provinciaOrigen = provinciaOrigen;
    }

    public double getLatitud() {return latitud;}

    public double getLongitud() {return longitud;}

    public boolean isMaritimo() {return maritimo;}

    public String getDescripcion() {return descripcion;}
    
    public Provincias getProvinciaOrigen() {return provinciaOrigen;}

    public void setLatitud(double latitud) {this.latitud = latitud;}

    public void setLongitud(double longitud) {this.longitud = longitud;}

    public void setMaritimo(boolean maritimo) {this.maritimo = maritimo;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    
    public void setProvinciaOrigen(Provincias provinciaOrigen) {this.provinciaOrigen = provinciaOrigen;}
    
    public void print() {
        System.out.println("Latitud: " + latitud + "\nLongitud: " + longitud);
        System.out.println("Marítimo: " + maritimo + "\nProvincia: " + provinciaOrigen);
        System.out.println("Descripción: " + descripcion);
    }

    @Override
    public String toString() {
        return "localizacion: latitud= " + latitud + "\nlongitud = " + longitud + "\nmaritimo = " + maritimo + "\nProvincia de Origen = " + provinciaOrigen + "\ndescripcion = " + descripcion;
    }
    
}
