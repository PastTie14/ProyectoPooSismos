/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Alertas.Usuario;

/**
 *
 * @author jmari
 */
import Modelos.Provincias;
import java.util.List;

public class UsuarioSuscrito {
    private String nombre;
    private String correo;
    private String numTel;
    private List<Provincias> provinciasInteresadas;

    
    /**
    * Constructor de la clase UsuarioSuscrito. Inicializa un usuario con su nombre,
    * correo electrónico, número de teléfono y lista de provincias de interés.
    *
    * @param nombre Nombre del usuario suscrito.
    * @param correo Dirección de correo electrónico del usuario.
    * @param numTel Número de teléfono del usuario.
    * @param provinciasInteresadas Lista de provincias en las que el usuario está interesado.
    */
    public UsuarioSuscrito(String nombre, String correo, String numTel, List<Provincias> provinciasInteresadas) {
        this.nombre = nombre;
        this.correo = correo;
        this.numTel = numTel;
        this.provinciasInteresadas = provinciasInteresadas;
    }

    // Getter y Setter para nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter y Setter para correo
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public boolean validarUsuario(Provincias provincia){
        if (provinciasInteresadas.contains(provincia))
            return true;
        return false;
    }

    // Getter y Setter para provinciasInteresadas
    public List<Provincias> getProvinciasInteresadas() {
        return provinciasInteresadas;
    }

    public void setProvinciasInteresadas(List<Provincias> provinciasInteresadas) {
        this.provinciasInteresadas = provinciasInteresadas;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
    
    
}
