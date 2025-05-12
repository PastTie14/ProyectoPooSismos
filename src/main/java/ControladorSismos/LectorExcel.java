/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorSismos;

import Alertas.Usuario.AdmUsuarios;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Modelos.Localizacion;
import Modelos.OrigenFalla;
import Modelos.Provincias;
import Modelos.Sismo;
import java.util.ArrayList;
import java.util.List;
import Alertas.Usuario.UsuarioSuscrito;
import java.io.FileOutputStream;

/**
 *
 * @author Sebas Masís
 * Clase LectorExcel, que permite abrir y manipular distintos tipos de archivos
 * utilizando la biblioteca APACHE POI, entre ellos, archivos .xlsx
 */
public class LectorExcel {    
    
    
    /**
    * Carga una lista de sismos desde un archivo Excel (.xlsx) ubicado en la ruta proporcionada.
    * Cada fila del archivo representa un sismo con información como magnitud, profundidad, ubicación,
    * fecha, hora, tipo de origen, entre otros. Los datos válidos son transformados en objetos Sismo
    * y añadidos al administrador de sismos proporcionado.
    *
    * @param ruta Ruta del archivo Excel desde el cual se leerán los sismos.
    * @param admin Objeto de tipo AdmSismos que gestionará los sismos leídos.
    */
    public void CargarSismos(String ruta, AdmSismos admin) {
        try (FileInputStream f = new FileInputStream(ruta);
                Workbook workbook = new XSSFWorkbook(f)) {
            
            org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
            SimpleDateFormat fechaBonita = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  // Un SDF para que se imprima más bonito
            
            for (org.apache.poi.ss.usermodel.Row fila : hoja) {
                if (fila.getRowNum() == 0)
                    continue;
                
                // Verificamos que las celdas no sean nulas antes de leerlas
                Cell magnitudCell = fila.getCell(0);
                Cell profundidadCell = fila.getCell(1);
                Cell latitudCell = fila.getCell(7);
                Cell longitudCell = fila.getCell(8);
                Cell maritimoCell = fila.getCell(3);
                Cell fechaCell = fila.getCell(4);
                Cell tiempoCell = fila.getCell(5);
                Cell tipoOrigenCell = fila.getCell(6);
                Cell provinciaCell = fila.getCell(2);
                Cell descripcionCell = fila.getCell(9);
                
                // Si alguna de las celdas es nula, saltamos esta fila
                if (magnitudCell == null || profundidadCell == null || latitudCell == null || 
                    longitudCell == null || maritimoCell == null || fechaCell == null || 
                    tiempoCell == null || tipoOrigenCell == null || provinciaCell == null ||
                    descripcionCell == null) {
                    System.out.println("Fila " + fila.getRowNum() + " contiene celdas nulas, se omite.");
                    continue;
                }
                
                double magnitud = magnitudCell.getNumericCellValue();
                double profundidad = profundidadCell.getNumericCellValue();
                double latitud = latitudCell.getNumericCellValue();
                double longitud = longitudCell.getNumericCellValue();
                boolean maritimo = Boolean.valueOf(maritimoCell.getStringCellValue());
                
                Date fechaHora;
                if (fechaCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(fechaCell)) {
                    fechaHora = fechaCell.getDateCellValue();
                } else {
                    System.out.println("Error: La fecha en la fila " + fila.getRowNum() + " no está en formato de fecha");
                    continue;
                }
                
                // Para el tiempo, validamos el tipo
                LocalDateTime ldt;
                if (tiempoCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(tiempoCell)) {
                    // Si es un valor numérico con formato de fecha/hora
                    ldt = tiempoCell.getLocalDateTimeCellValue();
                } else if (tiempoCell.getCellType() == CellType.STRING) {
                    // Si es un string, intentamos convertirlo a LocalDateTime
                    try {
                        String timeStr = tiempoCell.getStringCellValue();
                        // Asumiendo un formato común, ajusta según tus necesidades
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        ldt = LocalDateTime.of(1900, 1, 1, 0, 0, 0); // Fecha ficticia
                        ldt = ldt.withHour(Integer.parseInt(timeStr.substring(0, 2)))
                               .withMinute(Integer.parseInt(timeStr.substring(3, 5)))
                               .withSecond(timeStr.length() >= 8 ? Integer.parseInt(timeStr.substring(6, 8)) : 0);
                    } catch (DateTimeParseException | NumberFormatException | StringIndexOutOfBoundsException e) {
                        System.out.println("Error al parsear el tiempo en la fila " + fila.getRowNum() + ": " + e.getMessage());
                        continue;
                    }
                } else {
                    System.out.println("Error: El tiempo en la fila " + fila.getRowNum() + " no está en un formato reconocible");
                    continue;
                }
                
                // Actualizamos la hora en fechaHora
                fechaHora.setHours(ldt.getHour());
                fechaHora.setMinutes(ldt.getMinute());
                fechaHora.setSeconds(ldt.getSecond());
                
                String tipoOrigenString = tipoOrigenCell.getStringCellValue();
                String provinciaOrigenString = provinciaCell.getStringCellValue();
                String descripcion = descripcionCell.getStringCellValue();
               
                OrigenFalla tipoOrigen;
                try {
                    tipoOrigen = OrigenFalla.valueOf(tipoOrigenString);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Tipo de origen '" + tipoOrigenString + "' no válido en la fila " + fila.getRowNum());
                    continue;
                }
                
                Provincias provinciaOrigen;
                try {
                    provinciaOrigen = Provincias.valueOf(provinciaOrigenString);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Provincia '" + provinciaOrigenString + "' no válida en la fila " + fila.getRowNum());
                    continue;
                }
                
                Calendar momento = Calendar.getInstance();
                momento.setTime(fechaHora);
                
                Localizacion ubi = new Localizacion(latitud, longitud, maritimo, provinciaOrigen, descripcion);
                Sismo NuevoSismo = new Sismo(magnitud, profundidad, momento, tipoOrigen, ubi);
                
                admin.agregarSismo(NuevoSismo);
            }
        }
        catch(IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    
    /**
    * Escribe los datos de un sismo en un archivo Excel existente.
    * Si el archivo no existe, crea uno nuevo con los encabezados correspondientes.
    * 
    * @param ruta Ruta del archivo Excel donde se guardarán los datos
    * @param sismo Objeto Sismo que contiene los datos a escribir
    * @return boolean Indica si la operación fue exitosa
    */
   public boolean escribirSismo(String ruta, Sismo sismo) {
       try {
           Workbook workbook;
           org.apache.poi.ss.usermodel.Sheet hoja;
           boolean archivoExistente = false;

           // Verificar si el archivo existe
           try (FileInputStream fis = new FileInputStream(ruta)) {
               workbook = new XSSFWorkbook(fis);
               hoja = workbook.getSheetAt(0);
               archivoExistente = true;
           } catch (IOException e) {
               // Si el archivo no existe, crear uno nuevo
               workbook = new XSSFWorkbook();
               hoja = workbook.createSheet("Sismos");

               // Crear encabezados
               Row filaEncabezado = hoja.createRow(0);
               String[] encabezados = {"Magnitud", "Profundidad", "Provincia", "Maritimo", "Fecha", 
                                      "Hora", "TipoOrigen", "Latitud", "Longitud", "Descripcion"};

               for (int i = 0; i < encabezados.length; i++) {
                   Cell celda = filaEncabezado.createCell(i);
                   celda.setCellValue(encabezados[i]);
               }
           }

           // Determinar la última fila con datos
           int ultimaFila = archivoExistente ? hoja.getLastRowNum() + 1 : 1;

           // Crear una nueva fila para el sismo
           Row fila = hoja.createRow(ultimaFila);

           // Crear celdas y establecer sus valores
           fila.createCell(0).setCellValue(sismo.getMagnitud());
           fila.createCell(1).setCellValue(sismo.getProfundidad());
           fila.createCell(2).setCellValue(sismo.getubicacion().getProvinciaOrigen().toString());
           fila.createCell(3).setCellValue(String.valueOf(sismo.getubicacion().isMaritimo()));

           // Crear estilos para fecha y hora
           CellStyle fechaEstilo = workbook.createCellStyle();
           CellStyle horaEstilo = workbook.createCellStyle();
           CreationHelper creationHelper = workbook.getCreationHelper();

           // Formato numérico de tipo fecha (yyyy-mm-dd)
           fechaEstilo.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));

           // Formato personalizado para hora (HH:mm:ss)
           horaEstilo.setDataFormat(creationHelper.createDataFormat().getFormat("hh:mm:ss"));

           // Obtener la fecha y hora del sismo
           Date fechaHora = sismo.getMomento().getTime();

           // Celda 4: Fecha en formato numérico
           Cell celdaFecha = fila.createCell(4);
           celdaFecha.setCellValue(fechaHora);
           celdaFecha.setCellStyle(fechaEstilo);

           // Celda 5: Hora en formato personalizado HH:mm:ss
           Cell celdaHora = fila.createCell(5);
           celdaHora.setCellValue(fechaHora);
           celdaHora.setCellStyle(horaEstilo);
           fila.createCell(6).setCellValue(sismo.getTipoOrigen().toString());
           fila.createCell(7).setCellValue(sismo.getubicacion().getLatitud());
           fila.createCell(8).setCellValue(sismo.getubicacion().getLongitud());
           fila.createCell(9).setCellValue(sismo.getubicacion().getDescripcion());

           // Guardar los cambios en el archivo
           try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(ruta)) {
               workbook.write(fileOut);
           }

           // Cerrar el libro de trabajo
           workbook.close();

           return true;
       } catch (Exception e) {
           System.out.println("Error al escribir en el archivo Excel: " + e.getMessage());
           e.printStackTrace();
           return false;
       }
   }
   
   /**
    * Reescribe completamente un archivo Excel con los datos de un ArrayList de sismos.
    * Si el archivo existe, será sobrescrito. Si no existe, se creará uno nuevo.
    * 
    * @param ruta Ruta del archivo Excel donde se guardarán los datos
    * @param sismos ArrayList de objetos Sismo que contiene los datos a escribir
    * @return boolean Indica si la operación fue exitosa
    */
   public boolean escribirListaSismos(String ruta, ArrayList<Sismo> sismos) {
        try {
            Workbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Sismos");

            // Crear encabezados
            Row filaEncabezado = hoja.createRow(0);
            String[] encabezados = {
                "Magnitud", "Profundidad", "Provincia", "Maritimo",
                "Fecha", "Hora", "TipoOrigen", "Latitud", "Longitud", "Descripcion"
            };

            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados[i]);
            }

            // Crear estilos para fecha y hora
            CellStyle fechaEstilo = workbook.createCellStyle();
            CellStyle horaEstilo = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();

            fechaEstilo.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            horaEstilo.setDataFormat(creationHelper.createDataFormat().getFormat("hh:mm:ss"));

            // Escribir los sismos
            int numeroFila = 1;
            for (Sismo sismo : sismos) {
                Row fila = hoja.createRow(numeroFila++);

                fila.createCell(0).setCellValue(sismo.getMagnitud());
                fila.createCell(1).setCellValue(sismo.getProfundidad());
                fila.createCell(2).setCellValue(sismo.getubicacion().getProvinciaOrigen().toString());
                fila.createCell(3).setCellValue(String.valueOf(sismo.getubicacion().isMaritimo()));

                // Fecha y hora con estilo
                Date fechaHora = sismo.getMomento().getTime();

                Cell celdaFecha = fila.createCell(4);
                celdaFecha.setCellValue(fechaHora);
                celdaFecha.setCellStyle(fechaEstilo);

                Cell celdaHora = fila.createCell(5);
                celdaHora.setCellValue(fechaHora);
                celdaHora.setCellStyle(horaEstilo);

                fila.createCell(6).setCellValue(sismo.getTipoOrigen().toString());
                fila.createCell(7).setCellValue(sismo.getubicacion().getLatitud());
                fila.createCell(8).setCellValue(sismo.getubicacion().getLongitud());
                fila.createCell(9).setCellValue(sismo.getubicacion().getDescripcion());
            }

            // Guardar el archivo
            try (FileOutputStream fileOut = new FileOutputStream(ruta)) {
                workbook.write(fileOut);
            }

            workbook.close();

            return true;

        } catch (Exception e) {
            System.out.println("Error al escribir la lista de sismos en el archivo Excel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }




//USUARIOS
    
   /**
    * Carga una lista de usuarios suscritos desde un archivo Excel (.xlsx) ubicado en la ruta proporcionada.
    * Cada fila del archivo debe contener el nombre, correo electrónico, número de teléfono y provincias de interés separadas por comas.
    * Los usuarios válidos son transformados en objetos UsuarioSuscrito y añadidos
    * al administrador de usuarios proporcionado.
    * @param ruta Ruta del archivo Excel desde el cual se leerán los datos de los usuarios.
    * @param adm Objeto de tipo AdmUsuarios que gestionará los usuarios leídos.
    */
    public void CargarUsuarios(String ruta, AdmUsuarios adm) {
        try (FileInputStream f = new FileInputStream(ruta);
                Workbook workbook = new XSSFWorkbook(f)) {
            
            org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(0);
            
            for (org.apache.poi.ss.usermodel.Row fila : hoja) {
                if (fila.getRowNum() == 0)
                    continue;
                
                Cell nombreCell = fila.getCell(0);
                Cell correoCell = fila.getCell(1);
                Cell numTelCell = fila.getCell(2);
                Cell provinciasCell = fila.getCell(3);
                
                // Verificar celdas nulas
                if (nombreCell == null || correoCell == null || numTelCell == null || provinciasCell == null) {
                    System.out.println("Fila " + fila.getRowNum() + " contiene celdas nulas, se omite.");
                    continue;
                }
                
                String nombre = nombreCell.getStringCellValue();
                String correo = correoCell.getStringCellValue();
                
                String numTel;
                if (numTelCell.getCellType() == CellType.NUMERIC) {
                    numTel = String.valueOf((long)numTelCell.getNumericCellValue());
                } else if (numTelCell.getCellType() == CellType.STRING) {
                    numTel = numTelCell.getStringCellValue();
                } else {
                    numTel = numTelCell.getStringCellValue();
                    System.out.println("Error: Número de teléfono en formato no válido en la fila " + fila.getRowNum());
                    continue;
                }
                
                // Crear la lista de provincias de interés
                String provinciasInteres = provinciasCell.getStringCellValue();
                String[] provinciasInte = provinciasInteres.split(",");
                List<Provincias> provinciasDeInteres = new ArrayList<>(provinciasInte.length);
                
                for (String provi : provinciasInte) {
                    try {
                        Provincias provincia = Provincias.valueOf(provi.trim());
                        provinciasDeInteres.add(provincia);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Provincia '" + provi + "' No válida");
                    }
                }
                
                UsuarioSuscrito NuevoUsuario = new UsuarioSuscrito(nombre, correo, numTel, provinciasDeInteres);
                adm.agregarUsuario(NuevoUsuario);
            }
        }
        catch(IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
