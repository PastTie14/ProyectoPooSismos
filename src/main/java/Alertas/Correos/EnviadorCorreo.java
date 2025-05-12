/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Alertas.Correos;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author jmarin
 */
public class EnviadorCorreo {
    
    private final String usuario;
    private final String contraseña;
    private final String servidor;
    private final int puerto;

    public EnviadorCorreo(String usuario, String contraseña, String servidor, int puerto) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.servidor = servidor;
        this.puerto = puerto;
    }
    
    /**
    * Envía un correo electrónico al destinatario especificado con el asunto y cuerpo dados.
    * Utiliza el protocolo SMTP con autenticación y STARTTLS.
    *
    * @param destinatario Dirección de correo electrónico del destinatario.
    * @param asunto Asunto del correo electrónico.
    * @param cuerpo Texto del mensaje del correo electrónico.
    */

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", servidor);
        propiedades.put("mail.smtp.port", String.valueOf(puerto));

        // Autenticación
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contraseña);
            }
        });

        try {
            // Crear el mensaje
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(usuario));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);

            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("Correo enviado exitosamente.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
