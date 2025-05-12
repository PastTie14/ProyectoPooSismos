/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Alertas.Usuario;
import Alertas.Correos.EnviadorCorreo;
import java.util.List;
import java.util.ArrayList;
import Alertas.Usuario.UsuarioSuscrito;
import Modelos.Sismo;

/**
 *
 * @author Sebas Masís
 */
public class AdmUsuarios {
    
    // Atributos privados
    private final List<UsuarioSuscrito> listaUsuarios;
    private EnviadorCorreo mailSender;
    
    
    
    // Métodos públicos
    /**
    * Constructor de la clase AdmUsuarios. Inicializa la lista de usuarios
    * y configura el enviador de correos con los datos del remitente.
    *
    * @param usuario Dirección de correo del remitente.
    * @param contrasennia Contraseña del remitente.
    * @param servidor Servidor SMTP a utilizar para el envío de correos.
    * @param puerto Puerto del servidor SMTP.
    */
    public AdmUsuarios(String usuario, String contrasennia, String servidor, int puerto) {
        listaUsuarios = new ArrayList<>();
        mailSender = new EnviadorCorreo(usuario, contrasennia, servidor, puerto);
    }

    
    public List<UsuarioSuscrito> getListaUsuarios() {return listaUsuarios;}
    
    /**
    * Agrega un nuevo usuario a la lista de usuarios suscritos.
    *
    * @param usuario UsuarioSuscrito que se desea agregar.
    */
    public void agregarUsuario(UsuarioSuscrito usuario) {listaUsuarios.add(usuario);}
    
    /**
    * Elimina un usuario existente de la lista de usuarios suscritos.
    *
    * @param user UsuarioSuscrito que se desea eliminar.
    */
    public void eliminarUsuario(UsuarioSuscrito user) {listaUsuarios.remove(user);}
    
    /**
    * Valida si los usuarios están interesados en la ubicación del sismo
    * y, de ser así, les envía una notificación por correo.
    *
    * @param sismo Objeto Sismo que contiene la información del evento.
    */
    public void validarInteresUsuarios(Sismo sismo){
        for (UsuarioSuscrito usuario : listaUsuarios) {
            if(usuario.validarUsuario(sismo.getubicacion().getProvinciaOrigen())){
                mailSender.enviarCorreo(usuario.getCorreo(), "Sismo de interes.", sismo.toString());
            }
        }
    }
    
    
    public void print() {
        int contador = 1;
        for (UsuarioSuscrito user : listaUsuarios) {
            System.out.println("Usuario #" + contador);
            System.out.println("Nombre: " + user.getNombre());
            System.out.println("Correo: " + user.getCorreo());
            System.out.println("Número de teléfono: " + user.getNumTel());
            System.out.println("Provincias de interés: " + user.getProvinciasInteresadas());
            contador += 1;
        }
    }   
}
