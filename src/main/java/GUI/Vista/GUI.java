package GUI.Vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ian
 */
import GUI.Controlador.Controlador;
import Modelos.Sismo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

public class GUI extends JFrame {

    private int anchoPantalla;
    private int altoPantalla;
    private Controlador controlador;
    private JPanel panelMapa;
    private JPanel panelInfo;
    private JPanel panelBotones; // Panel para los botones
    private JPanel panelSismos;
    private JScrollPane scrollPanelSismos;
    
    private JLabel lblMomento;
    private JLabel lblUbicacion;
    private JLabel lblLatitud;
    private JLabel lblLongitud;
    private JLabel lblMagnitud;
    private JLabel lblProfundidad;
    private JLabel lblTipoOrigen;
    private JLabel lblMaritimo;
    private JLabel lblDescripcion;
    
    private JXMapViewer mapa;
    private JButton btnActualizar;
    private JButton btnEnviar;
    private JButton btnEstadisticas; // Nuevo botón para estadísticas
    private JButton btnAcercar;
    private JButton btnAlejar;
    
    private final GeoPosition CostaRica = new GeoPosition(9.9281, -84.0907);
    private ArrayList<Sismo> listaSismos;
    Set<Waypoint> waypoints = new HashSet<>();
    WaypointPainter<Waypoint> waypointPainter;
    
    public GUI() {        
        // Obtener el tamaño de la pantalla principal
        Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla = tamPantalla.width;
        altoPantalla = tamPantalla.height;
        waypointPainter = new WaypointPainter<>();

        // Configurar la ventana
        setTitle("Sismos Costa Rica");
        setSize(anchoPantalla, altoPantalla);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Establecer layout absoluto (si decides mantenerlo así)
        setLayout(null); 

        // Generar paneles y componentes
        generarPaneles();
        generarMapa();
        generarBotones();        

        // Mostrar ventana
        setVisible(true);
    }
    
    public GUI(Controlador controlador) {
        this.controlador = controlador;
        
        // Obtener el tamaño de la pantalla principal
        Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();
        anchoPantalla = tamPantalla.width;
        altoPantalla = tamPantalla.height;
        waypointPainter = new WaypointPainter<>();

        // Configurar la ventana
        setTitle("Sismos Costa Rica");
        setSize(anchoPantalla, altoPantalla);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Establecer layout absoluto (si decides mantenerlo así)
        setLayout(null); 

        // Generar paneles y componentes
        generarPaneles();
        generarMapa();
        generarBotones();
        
        

        // Mostrar ventana
        setVisible(true);
    }

    
    private void generarMapa() {
        panelMapa.setLayout(null); // Posicionamiento absoluto
        // Crear y posicionar el mapa a tamaño completo
        mapa = new JXMapViewer();
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapa.setTileFactory(tileFactory);
        mapa.setZoom(10);
        mapa.setAddressLocation(CostaRica);
        mapa.setBounds(0, 0, panelMapa.getWidth(), panelMapa.getHeight()); // mapa ocupa todo el panel
        panelMapa.add(mapa); // agregar primero para que quede al fondo
        // Crear botones
        btnAcercar = new JButton("+");
        btnAlejar = new JButton("-");
        int btnWidth = 50;
        int btnHeight = 30;
        int espacioEntre = 10;
        int totalAnchoBotones = btnWidth * 2 + espacioEntre;
        int xInicio = (panelMapa.getWidth() - totalAnchoBotones) / 2;
        int yBotones = 10;
        btnAcercar.setBounds(xInicio, yBotones, btnWidth, btnHeight);
        btnAlejar.setBounds(xInicio + btnWidth + espacioEntre, yBotones, btnWidth, btnHeight);
        // Agregar botones sobre el mapa
        mapa.add(btnAcercar);
        mapa.add(btnAlejar);
        mapa.setOverlayPainter(waypointPainter);
        // Listeners
        btnAcercar.addActionListener(this::btnAcercarActionPerformed);
        btnAlejar.addActionListener(this::btnAlejarActionPerformed);
        panelMapa.revalidate();
        panelMapa.repaint();
    }
    
    /**
    * Agrega un punto al mapa basado en las coordenadas de latitud y longitud especificadas.
    * Este método limpia los puntos existentes, añade el nuevo punto, y actualiza el panel del mapa.
    *
    * @param latitud  Latitud del punto a agregar.
    * @param longitud Longitud del punto a agregar.
    */
    public void agregarPuntoAMapaPorCoordenadas(double latitud, double longitud){
        waypoints.clear();
        waypoints.add(new DefaultWaypoint(latitud, longitud));
        waypointPainter.setWaypoints(waypoints);
        panelMapa.revalidate();
        panelMapa.repaint();
    }
    
    private void generarBotones(){
        // Crear botones
        btnActualizar = new JButton("Acciones");
        btnEnviar = new JButton("Enviar Correo");
        btnEstadisticas = new JButton("Estadísticas");
        
        // Modificar el layout del panelInfo para incluir los botones en la parte inferior
        panelInfo.setLayout(new BorderLayout());
        
        // Panel para la información en la parte superior con margen para evitar desbordamiento
        JPanel panelInfoLabels = new JPanel();
        panelInfoLabels.setLayout(new BoxLayout(panelInfoLabels, BoxLayout.Y_AXIS));
        // Añadir un margen al panel de información para mejor visualización
        panelInfoLabels.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblMomento = new JLabel("Momento: ");
        lblUbicacion = new JLabel("Ubicación: ");
        lblLatitud = new JLabel("Latitud: ");
        lblLongitud = new JLabel("Longitud: ");
        lblMagnitud = new JLabel("Magnitud: ");
        lblProfundidad = new JLabel("Profundidad: ");
        lblTipoOrigen = new JLabel("Tipo de Origen: ");
        lblMaritimo = new JLabel("Maritimo: ");
        lblDescripcion = new JLabel("Descripción: ");

        // Establecer un tamaño preferido para las etiquetas
        Dimension labelSize = new Dimension(panelInfo.getWidth() - 20, 30);
        lblMomento.setPreferredSize(labelSize);
        lblUbicacion.setPreferredSize(labelSize);
        lblLatitud.setPreferredSize(labelSize);
        lblLongitud.setPreferredSize(labelSize);
        lblMagnitud.setPreferredSize(labelSize);
        lblProfundidad.setPreferredSize(labelSize);
        lblTipoOrigen.setPreferredSize(labelSize);
        lblMaritimo.setPreferredSize(labelSize);
        lblDescripcion.setPreferredSize(labelSize);
        
        // Añadir las etiquetas al panel de información
        panelInfoLabels.add(lblMomento);
        panelInfoLabels.add(Box.createVerticalStrut(5)); // Espacio entre etiquetas
        panelInfoLabels.add(lblUbicacion);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblLatitud);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblLongitud);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblMagnitud);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblProfundidad);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblTipoOrigen);
        //panelInfoLabels.add(Box.createVerticalStrut(5));
        //panelInfoLabels.add(lblMaritimo);
        panelInfoLabels.add(Box.createVerticalStrut(5));
        panelInfoLabels.add(lblDescripcion);
        
        // Panel de desplazamiento para las etiquetas si hay muchas
        JScrollPane scrollInfoLabels = new JScrollPane(panelInfoLabels);
        scrollInfoLabels.setBorder(null); // Eliminar borde del scroll
        scrollInfoLabels.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Añadir el panel de etiquetas con scroll a la parte central del panelInfo
        panelInfo.add(scrollInfoLabels, BorderLayout.CENTER);
        
        // Crear panel para botones en la parte inferior del panelInfo
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 5, 0)); // 1 fila, 3 columnas, con espaciado horizontal
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen para los botones
        
        // Establecer un tamaño fijo para el panel de botones
        panelBotones.setPreferredSize(new Dimension(panelInfo.getWidth(), 60));
        
        // Añadir botones al panel
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEnviar);
        panelBotones.add(btnEstadisticas);
        
        // Añadir el panel de botones a la parte inferior del panelInfo
        panelInfo.add(panelBotones, BorderLayout.SOUTH);

        // Listeners para los botones
        btnActualizar.addActionListener(e -> {
            controlador.GUIAcciones(this);
        });

        btnEnviar.addActionListener(e -> {
            controlador.enviarCorreos();
        });
        
        btnEstadisticas.addActionListener(e -> {
            controlador.GUIEstadisticas();
        });
    }

    private void generarPaneles() {
        // Calcular la altura efectiva para evitar que los componentes se salgan de la pantalla
        // Considera dejar un margen inferior para la barra de tareas del sistema operativo
        int margenInferior = 40; // Margen para la barra de tareas
        int alturaEfectiva = altoPantalla - margenInferior;
        
        // Panel del mapa (75% del ancho, 50% de la altura)
        panelMapa = new JPanel();
        panelMapa.setSize(anchoPantalla / 4 * 3, alturaEfectiva / 2);
        panelMapa.setLocation(anchoPantalla / 4, 0);
        panelMapa.setLayout(null);
        panelMapa.setBackground(Color.red);
        add(panelMapa);

        // Panel de información (75% del ancho, 50% de la altura - margen)
        panelInfo = new JPanel();
        // Ajustar la altura para que no se salga de la pantalla
        panelInfo.setSize(anchoPantalla / 4 * 3, alturaEfectiva / 2 - margenInferior);
        panelInfo.setLocation(anchoPantalla / 4, alturaEfectiva / 2);
        panelInfo.setBackground(Color.LIGHT_GRAY);
        
        // El layout de panelInfo se configura en generarBotones()
        add(panelInfo);

        // Panel para tarjetas de sismos (25% del ancho, altura completa)
        panelSismos = new JPanel();
        panelSismos.setLayout(new BoxLayout(panelSismos, BoxLayout.Y_AXIS)); // apilar verticalmente
        panelSismos.setBackground(Color.WHITE);

        // Scroll pane para el panel de sismos
        scrollPanelSismos = new JScrollPane(panelSismos);
        scrollPanelSismos.setBounds(0, 0, anchoPantalla / 4, alturaEfectiva);
        scrollPanelSismos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanelSismos);
        repaint();
    }
    
    private void crearTarjetaSismo(Sismo sismo) {
        JButton tarjeta = new JButton("<html><b>" + sismo.getubicacion().getProvinciaOrigen()+ "</b><br/>" +
                                      "Magnitud: " + sismo.getMagnitud() + "<br/>" +
                                      "Profundidad: " + sismo.getProfundidad() + "</html>");
        tarjeta.setPreferredSize(new Dimension(panelSismos.getWidth() - 20, 80));
        tarjeta.addActionListener(e -> mostrarDetallesSismo(sismo));
        panelSismos.add(tarjeta);
        panelSismos.repaint();
        panelSismos.revalidate();
    }

    public void mostrarSismos(ArrayList<Sismo> listaSismos){
        this.listaSismos = listaSismos;
        panelSismos.removeAll();
        for (Sismo sismo : listaSismos) {
            crearTarjetaSismo(sismo);
        }
        panelSismos.repaint();
        panelSismos.revalidate();
        revalidate();
        repaint();
    }
    
    /**
    * Muestra los detalles de un sismo en la interfaz gráfica.
    * Actualiza las etiquetas de información con los datos del sismo proporcionado
    * y coloca un punto en el mapa correspondiente a su ubicación.
    *
    * @param sismo El objeto {@link Sismo} del cual se mostrarán los detalles.
    */
    public void mostrarDetallesSismo(Sismo sismo) {
        agregarPuntoAMapaPorCoordenadas(sismo.getubicacion().getLatitud(), sismo.getubicacion().getLongitud());
        lblMomento.setText("Momento: " + sismo.printMomento());
        lblUbicacion.setText("Ubicación: " + sismo.getubicacion().getProvinciaOrigen());
        lblLatitud.setText("Latitud: " + sismo.getubicacion().getLatitud());
        lblLongitud.setText("Longitud: " + sismo.getubicacion().getLongitud());
        lblMagnitud.setText("Magnitud: " + sismo.getMagnitud());
        lblProfundidad.setText("Profundidad: " + sismo.getProfundidad());
        lblTipoOrigen.setText("Tipo de Origen: " + sismo.getTipoOrigen());
        lblDescripcion.setText("Descripción: " + sismo.getubicacion().getDescripcion());
        controlador.setSismoSeleccionado(sismo);
    }
    
    //Eventos Botones
    private void btnAlejarActionPerformed(java.awt.event.ActionEvent evt){
        mapa.setZoom(Math.min(15, mapa.getZoom() + 1));
        panelMapa.revalidate();
        panelMapa.repaint();
    }
    
    private void btnAcercarActionPerformed(ActionEvent evt) {
        mapa.setZoom(Math.max(1, mapa.getZoom() - 1));
        panelMapa.revalidate();
        panelMapa.repaint();
    }
    
    public int getAnchoPantalla() {
        return anchoPantalla;
    }

    public int getAltoPantalla() {
        return altoPantalla;
    }

    // Método main para probar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI());
    }
}