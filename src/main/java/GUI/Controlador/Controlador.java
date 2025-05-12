/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Controlador;

import Alertas.Usuario.AdmUsuarios;
import ControladorSismos.AdmSismos;
import ControladorSismos.LectorExcel;
import GUI.Vista.*;
import Modelos.Sismo;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Ian
 */
public class Controlador {
    private GUI vista;
    private AdmSismos adminSismos;
    private AdmUsuarios adminUsuarios;
    private LectorExcel controladorExcel;
    private String rutaExcelSismos = "src\\archivos\\pruebaSismos2.xlsx";
    private String rutaExcelUsuarios = "src\\archivos\\usuarios.xlsx";
    private Sismo sismoSeleccionado;
    
    //Datos para poder enviar correos
    private String usuario = "smasiscr@gmail.com";
    private String contraseña = "nkxg udku ihrf oxxo";
    private String servidor = "smtp.gmail.com";
    private int puerto = 587;
    
    public Controlador(){
        vista = new GUI(this);
        adminSismos = new AdmSismos();
        adminUsuarios = new AdmUsuarios(usuario, contraseña, servidor, puerto);
        controladorExcel = new LectorExcel();
        actualizarSismos();
        cargarUsuarios();
    }
    
    /**
    * Carga los usuarios desde un archivo Excel usando el controlador de Excel y los agrega al administrador de usuarios.
    */
    public void cargarUsuarios(){
        controladorExcel.CargarUsuarios(rutaExcelUsuarios, adminUsuarios);
    }
    
    /**
    * Muestra una ventana emergente que funciona como menu para seleccionar las estadisticas que se desean ver.
    */
    public void GUIEstadisticas(){
        GUIPopupEstadisticas estadisticas = new GUIPopupEstadisticas(vista, this);
        estadisticas.setVisible(true);
    }
    
    
    
    /**
     * Obtiene la cantidad de sismos por provincia.
     *
     * @return Un arreglo de enteros con la cantidad de sismos por cada provincia.
     */
    public int[] getCantidadSimosProvincia(){
        return adminSismos.obtenerSismosPorProvincia();
    }
    
    
    /**
    * Obtiene la cantidad de sismos por mes en un año específico.
    *
    * @param annio Año para el cual se desea obtener la información.
    * @return Un arreglo de enteros con la cantidad de sismos por mes.
    */
    public int[] getCantidadSismosMesAnnio(int annio){
        return adminSismos.obtenerSismosPorMesEnUnAnnio(annio);
    }
    
    /**
    * Obtiene la cantidad de sismos clasificados por tipo de origen.
    *
    * @return Un arreglo de enteros que representa la cantidad de sismos por tipo de origen.
    */
    public int[] getCantidadSismosPorOrigen(){
        return adminSismos.obtenerSismosPorTipoDeOrigen();
    }
    
    /**
    * Obtiene una lista de sismos registrados en un rango de años.
    *
    * @param min Año mínimo del rango.
    * @param max Año máximo del rango.
    * @return Una lista de objetos Sismo dentro del rango especificado.
    */
    public ArrayList<Sismo> getSismosEnRangoAnnios(int min, int max){
        return adminSismos.obtenerSismosPorRangoDeAños(min,max);
    }
    
    /**
    * Obtiene una lista de sismos clasificados por su magnitud.
    *
    * @return Una lista de sismos ordenados o clasificados por magnitud.
    */
    public ArrayList<Sismo> getSismosPorMagnitud(){
        return adminSismos.obtenerClasificacionPorMagnitud();
    }
    
    /**
    * Envía correos a los usuarios interesados en un sismo específico.
    *
    * @param sismo Objeto Sismo del cual se desea notificar a los usuarios.
    */
    public void enviarCorreos(Sismo sismo){
        adminUsuarios.validarInteresUsuarios(sismo);
    }
    
    /**
    * Envía correos a los usuarios interesados en el sismo actualmente seleccionado.
    */
    public void enviarCorreos(){
        adminUsuarios.validarInteresUsuarios(sismoSeleccionado);
    }
    
    /**
    * Carga los sismos desde el archivo Excel y los reinicia en la lista de sismos.
    */
    public void cargarSismos(){
        adminSismos.resetListaSismos();
        controladorExcel.CargarSismos(rutaExcelSismos, adminSismos);
    }
    
    /**
    * Actualiza la lista de sismos desde el archivo y los muestra en la vista.
    */
    public void actualizarSismos(){
        cargarSismos();
        vista.mostrarSismos((ArrayList<Sismo>) adminSismos.getListaSismos());
    }
    
    /**
     * Muestra la interfaz de acciones relacionada con los sismos.
     *
     * @param padre Ventana padre desde la cual se mostrará el popup.
     */
    public void GUIAcciones(JFrame padre){
        GUIPopupSismos popup = new GUIPopupSismos(padre,this);
        popup.setVisible(true);
    }
    
    /**
    * Muestra el formulario para agregar o modificar un sismo.
    *
    * @param modificar Indica si se desea modificar (true) o agregar (false) un sismo.
    * @return true si se muestra el formulario correctamente, false si no hay un sismo seleccionado para modificar.
    */
    public boolean GUISismoAddMod(boolean modificar){
        if(modificar){
            if (sismoSeleccionado != null){
                GUIFormularioSismoDialog addMod = new GUIFormularioSismoDialog(vista,sismoSeleccionado, this);
                addMod.setVisible(true);
                return true;
            }else{
                JOptionPane.showMessageDialog(vista, "No se ha seleccionado ningun sismo para modificar");
                return false;
            }
        }else{
            GUIFormularioSismoDialog addMod = new GUIFormularioSismoDialog(vista,null, this);
            addMod.setVisible(true);
            return true;
        }
    }
    
    /**
    * Guarda un sismo en el archivo Excel y actualiza la lista de sismos, además de notificar a los usuarios.
    *
    * @param sismo Sismo a guardar.
    * @return true si el sismo fue guardado correctamente, false en caso contrario.
    */
    public boolean guardarSismo(Sismo sismo){
        boolean resultado =  controladorExcel.escribirSismo(rutaExcelSismos, sismo);
        actualizarSismos();
        enviarCorreos(sismo);
        return resultado;
    }
    
    /**
    * Reescribe la lista completa de sismos en el archivo Excel y actualiza la visualización.
    *
    * @return true si la escritura fue exitosa, false en caso contrario.
    */
    public boolean actualizarExcelSismos(){
        boolean resultado =  controladorExcel.escribirListaSismos(rutaExcelSismos, (ArrayList<Sismo>) adminSismos.getListaSismos());
        actualizarSismos();
        return resultado;
    }
    
    /**
    * Establece el sismo actualmente seleccionado en la GUI.
    *
    * @param sismo Sismo que será marcado como seleccionado.
    */
    public void setSismoSeleccionado(Sismo sismo){
        this.sismoSeleccionado = sismo;
    }
    
    
}
