/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author Usuario
 */
public class QuizGenerador extends javax.swing.JFrame {
    private Point lastPoint;
    private boolean infoExpandida = false;
    private JPanel panelInfo;
    private static QuizGenerador instancia;
    /**
     * Creates new form QuizGenerador
     */
    public QuizGenerador() {
        setLayout(null);
        initComponents();
        instancia = this;
        
        setTitle("Crea tu Simulador"); 
        setSize(430, 932); 
        setResizable(false); 
        setAlwaysOnTop(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
       
       String imagePath = "Fondo.png";
       Utilidades.setBackgroundImage(jPanel1, imagePath);
       
       
       //Título y subtitulo 
       titulo.setFont(Utilidades.getRalewayFont(Font.BOLD, 24));
       titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
       TipodeSimulador.setFont(Utilidades.getRalewayFont(Font.BOLD, 14));
       TipodeSimulador.setAlignmentX(Component.CENTER_ALIGNMENT);
       

       //PanelOpciones 
        panelOpciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLabels.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSeleccionarSimulador.setFont(Utilidades.getRalewayFont(Font.PLAIN, 16));
        lblSeleccionarSimulador.setText("Ahora Aprendo");
        lblSeleccionarSimulador.setHorizontalTextPosition(SwingConstants.CENTER);
        Utilidades.SetImageLabel(lblSeleccionarSimulador, "src/resources/Desplegable_Off.png", new Dimension(360, 35));
       

        String[] opciones = { "Ahora Aprendo", "El Cazador", "Atrapa los Univercoins", "BAAM", "PiensoPalabra" };
        java.util.List<JLabel> listaOpciones = new ArrayList<>();
        
        lblSeleccionarSimulador.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSeleccionarSimulador.addMouseListener(new MouseAdapter() {
        private boolean desplegado = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        desplegado = !desplegado;
        String imagen = desplegado ? "Desplegable_On.png" : "Desplegable_Off.png";
        Utilidades.SetImageLabel(lblSeleccionarSimulador, "src/resources/" + imagen, new Dimension(360, 35));
        for (JLabel l : listaOpciones) {
            l.setVisible(desplegado);
        }
        panelOpciones.revalidate();
        panelOpciones.repaint();
    }
});
        for (String opcion : opciones) {
    JLabel lblOpcion = new JLabel(opcion);
    lblOpcion.setVisible(false); // oculto por defecto
    lblOpcion.setFont(Utilidades.getRalewayFont(Font.PLAIN, 14));
    lblOpcion.setForeground(Color.WHITE);
    lblOpcion.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    lblOpcion.setCursor(new Cursor(Cursor.HAND_CURSOR));

    lblOpcion.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            lblSeleccionarSimulador.setText(opcion);
            cargarPreguntasDesdeCSV(opcion + ".csv", panelPreguntas); // tu función
            for (JLabel l : listaOpciones)  l.setVisible(false); // ocultar opciones al seleccionar
                Utilidades.SetImageLabel(lblSeleccionarSimulador, "src/resources/Desplegable_Off.png", new Dimension(330, 35));
            panelOpciones.revalidate();
            panelOpciones.repaint();
        }
    });

    listaOpciones.add(lblOpcion);
    panelOpciones.add(lblOpcion); 
     
    }       
    
    //Texto añadir una pregunta    
     jLabel1.setFont(Utilidades.getRalewayFont(Font.BOLD, 14)); 
     
    //LabelInfo 
    Utilidades.SetImageLabel(lblInformacion, "src/resources/Info_Off.png", new Dimension(20, 20));
    lblInformacion.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        alternarInfo();
    }
});

    // ScrollPane para desplazarse si hay muchas preguntas
       jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

   
    // Carga preguntas desde CSV después de inicializar todo
     cargarPreguntasDesdeCSV("preguntas.csv", panelPreguntas);
   
    jScrollPane1.getViewport().addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = e.getPoint();
    }
});
    jScrollPane1.getViewport().addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastPoint != null) {
        JViewport viewPort = jScrollPane1.getViewport();
        Point currentPoint = e.getPoint();
        int deltaY = lastPoint.y - currentPoint.y;
        Rectangle view = viewPort.getViewRect();
        view.y += deltaY;
       
        panelPreguntas.scrollRectToVisible(view);
        lastPoint = currentPoint;
        }
    }
});  
    
    //Añadir una Pregunta
    Utilidades.SetImageLabel(lblAñadirunaPregunta, "src/resources/Mas_Off.png", new Dimension(20, 20));
    lblAñadirunaPregunta.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        Utilidades.SetImageLabel(lblAñadirunaPregunta, "src/resources/Mas_On.png", new Dimension(20, 20));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Utilidades.SetImageLabel(lblAñadirunaPregunta, "src/resources/Mas_Off.png", new Dimension(20, 20));
        PreguntaPanel nuevaPregunta = new PreguntaPanel();
        panelPreguntas.add(nuevaPregunta);
        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    
        int totalPreguntas = panelPreguntas.getComponentCount();
    actualizarEstadoBotonCrear();
    
    QuizGenerador.getInstancia().mostrarRegistro("Pregunta añadida (actualmente hay " + totalPreguntas + " preguntas )");
    }
});

    //Crear 
    Utilidades.SetImageLabel(lblCrear, "src/resources/Boton_Off.png", new Dimension (340, 45));
        lblCrear.setFont(Utilidades.getRalewayFont(Font.BOLD, 16));
        lblCrear.setForeground(Color.WHITE);
        lblCrear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCrear.setHorizontalTextPosition(SwingConstants.CENTER);
        lblCrear.setVerticalTextPosition(SwingConstants.CENTER); 
        lblCrear.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        
        lblCrear.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        guardarPreguntasEnCSV("preguntas.csv", panelPreguntas);
        comprimirCSVaZIP("preguntas.csv", "simulador.zip");
        // Refrescar interfaz
        cargarPreguntasDesdeCSV("preguntas.csv", panelPreguntas);
        actualizarEstadoBotonCrear();   
    }
});
    
    //Debug
    lblDebug.setHorizontalAlignment(SwingConstants.CENTER);
    lblDebug.setFont(Utilidades.getRalewayFont(Font.PLAIN, 14));    
}
    
    private void alternarInfo() {
    if (infoExpandida) {
        // Ocultar el panel info
        if (panelInfo != null) {
            jPanel2.remove(panelInfo);
        }
        Utilidades.SetImageLabel(lblInformacion, "src/resources/Info_Off.png", new Dimension(20, 20));
    } else {
        if (panelInfo == null) {
            panelInfo = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon icon = new ImageIcon("src/resources/Panel_Info.png");
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            panelInfo.setOpaque(false);
            panelInfo.setLayout(new BorderLayout());
            panelInfo.setMaximumSize(new Dimension(380, 100));
            panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lblTextoInfo = new JLabel("<html><div style='text-align: left; width: 330px; padding: 10px; color: white;'>" +
                "Añade las preguntas que quieras que aparezcan durante <br> " +
                "la simulación. Luego, pulsa el botón <b>Crear</b> para exportar <br>" +
                "el archivo ZIP que deberás subir a SharePoint.</div></html>");
            lblTextoInfo.setForeground(Color.WHITE);
            lblTextoInfo.setFont(Utilidades.getRalewayFont(Font.PLAIN, 12));
            panelInfo.add(lblTextoInfo, BorderLayout.CENTER);
        }

        // Insertar justo debajo de panelLabels
        int index = jPanel2.getComponentZOrder(panelLabels) + 1;
        jPanel2.add(panelInfo, index);
        Utilidades.SetImageLabel(lblInformacion, "src/resources/Info_On.png", new Dimension(20, 20));
    }

    infoExpandida = !infoExpandida;
    jPanel2.revalidate();
    jPanel2.repaint();
}
    
    // Método para actualizar la imagen del botón "Crear"
private void actualizarEstadoBotonCrear() {
    if (panelPreguntas.getComponentCount() > 0) {
        Utilidades.SetImageLabel(lblCrear, "src/resources/Boton_On.png", new Dimension(340, 45));
        lblCrear.setForeground(Color.BLACK);
        lblCrear.setText("Crear");
    } else {
        Utilidades.SetImageLabel(lblCrear, "src/resources/Boton_Off.png", new Dimension(340, 45));
        lblCrear.setForeground(Color.WHITE);
        lblCrear.setText("Crear");
    }
    lblCrear.setHorizontalTextPosition(SwingConstants.CENTER);
    lblCrear.setVerticalTextPosition(SwingConstants.CENTER);
    lblCrear.setFont(Utilidades.getRalewayFont(Font.BOLD, 16));
}
 

public static void cargarPreguntasDesdeCSV(String filePath, JPanel panelPreguntas) {
    panelPreguntas.removeAll();
    int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";"); // Dividir la línea por el separador ";"
                if (data.length == 5) {
                    // Crear una instancia de PreguntaPanel para mostrar la pregunta y respuestas
                    PreguntaPanel preguntaPanel = new PreguntaPanel();
                    
                    // Establecer los valores en el PreguntaPanel
                    preguntaPanel.setPregunta(data[0]);
                    preguntaPanel.setRespuestaCorrecta(data[1]);
                    preguntaPanel.setRespuestaIncorrecta1(data[2]);
                    preguntaPanel.setRespuestaIncorrecta2(data[3]);
                    preguntaPanel.setRespuestaIncorrecta3(data[4]);

                    // Agregar el panel de la pregunta al contenedor de preguntas
                    panelPreguntas.add(preguntaPanel);
                    count++;
                }
            }
        if (count > 0) {
            QuizGenerador.getInstancia().mostrarConfirmacion("Preguntas cargadas: " + count + " encontradas");
        } else {
            QuizGenerador.getInstancia().mostrarAdvertencia("El archivo no contiene preguntas válidas");
        }
    } catch (FileNotFoundException e) {
        QuizGenerador.getInstancia().mostrarError("Archivo no encontrado: " + filePath);
    } catch (IOException e) {
        QuizGenerador.getInstancia().mostrarError("Error al leer el archivo: " + e.getMessage());
    }

    panelPreguntas.revalidate();
    panelPreguntas.repaint();
    }
       
       
      public static void guardarPreguntasEnCSV(String filePath, JPanel panelPreguntas) {
    if (panelPreguntas.getComponentCount() == 0) {
        QuizGenerador.getInstancia().mostrarAdvertencia("No hay preguntas para guardar.");
        return;
    }

    int count = 0; // Inicializar el contador

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        for (Component comp : panelPreguntas.getComponents()) {
            if (comp instanceof PreguntaPanel pregunta) {
                String preguntaTexto = pregunta.getPregunta();
                String respuestaCorrecta = pregunta.getRespuestaCorrecta();
                String respuestaIncorrecta1 = pregunta.getRespuestaIncorrecta1();
                String respuestaIncorrecta2 = pregunta.getRespuestaIncorrecta2();
                String respuestaIncorrecta3 = pregunta.getRespuestaIncorrecta3();

                // Verificar que no haya preguntas vacías
                if (preguntaTexto.isEmpty() || respuestaCorrecta.isEmpty() || 
                    respuestaIncorrecta1.isEmpty() || respuestaIncorrecta2.isEmpty() || respuestaIncorrecta3.isEmpty()) {
                   QuizGenerador.getInstancia().mostrarError("Algunas preguntas están vacías. No se guardará el archivo.");
                    continue;
                }

                // Escribir la pregunta en el archivo CSV
                bw.write(String.join(";", preguntaTexto, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3));
                bw.newLine();
                count++;
            }
        }
        QuizGenerador.getInstancia().mostrarConfirmacion("Preguntas guardadas: " + count);
    } catch (IOException e) {
        QuizGenerador.getInstancia().mostrarError("No se pudo crear el archivo: " + filePath);
    }      
      }  
       
     public static void comprimirCSVaZIP(String csvFilePath, String zipFilePath) {
            try (FileOutputStream fos = new FileOutputStream(zipFilePath);
         ZipOutputStream zipOut = new ZipOutputStream(fos)) {
        
        // Agregar el archivo CSV al ZIP
        boolean csvAñadido = agregarArchivoAZIP(csvFilePath, zipOut);
        boolean exeAñadido = agregarArchivoAZIP("UnityProject/Build.exe", zipOut);
        boolean jsonAñadido = agregarArchivoAZIP("UnityProject/Config.json", zipOut);

        // Mensajes según el resultado
        if (!csvAñadido || !exeAñadido || !jsonAñadido) {
            QuizGenerador.getInstancia().mostrarAdvertencia("No todos los archivos fueron añadidos al ZIP.");
        } else {
            QuizGenerador.getInstancia().mostrarConfirmacion("Archivo ZIP generado con éxito.");
            System.out.println("Archivo ZIP generado en: " + new File(zipFilePath).getAbsolutePath());
        }

    } catch (IOException e) {
       QuizGenerador.getInstancia().mostrarError("Error al comprimir archivos: " + e.getMessage());
    }
     }  
    
     // Método auxiliar para agregar archivos al ZIP
private static boolean agregarArchivoAZIP(String filePath, ZipOutputStream zipOut) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
        QuizGenerador.getInstancia().mostrarAdvertencia("No se encontró: " + filePath);
        return false;
    } // Evita errores si el archivo no existe

    try (FileInputStream fis = new FileInputStream(file)) {
        zipOut.putNextEntry(new ZipEntry(file.getName()));

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.closeEntry();
        return true;
     
    } catch (IOException e) {
        QuizGenerador.getInstancia().mostrarError("Error al añadir archivo: " + filePath);
        return false;
    }
}

        public static QuizGenerador getInstancia() {
    return instancia;
}
 
    private void mostrarMensajeDebug(String mensaje, Color color, int tiempo) {
    // Configurar el mensaje y color 
    lblDebug.setText(mensaje);
    lblDebug.setForeground(color);
    lblDebug.setOpaque(true);
    lblDebug.setHorizontalAlignment(SwingConstants.CENTER); 
    lblDebug.setFont(Utilidades.getRalewayFont(Font.PLAIN, 14));
    lblDebug.revalidate();
    lblDebug.repaint();
    
    // Temporizador para ocultar el mensaje
    new Timer(tiempo, e -> {
        lblDebug.setText("");
        lblDebug.setOpaque(false);
        lblDebug.revalidate();
        lblDebug.repaint();
        ((Timer)e.getSource()).stop(); // Detener el timer
    }).start();
}
    // Para errores (rojo, 3 segundos)
private  void mostrarError(String mensaje) {
    mostrarMensajeDebug(mensaje, new Color(235, 65, 81), 3000);
}

// Para advertencias (amarillo, 2 segundos)
void mostrarAdvertencia(String mensaje) {
    mostrarMensajeDebug(mensaje, new Color(255, 156, 0), 2000);
}

// Para confirmaciones (verde, 2 segundos)
private void mostrarConfirmacion(String mensaje) {
    mostrarMensajeDebug(mensaje, new Color(134, 210, 149), 2000);
}

//Para Registros (Blanco, 1 segundo)
void mostrarRegistro(String mensaje) {
    mostrarMensajeDebug(mensaje, new Color(247, 247, 247), 1000);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        TipodeSimulador = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelOpciones = new javax.swing.JPanel();
        lblSeleccionarSimulador = new javax.swing.JLabel();
        panelLabels = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblAñadirunaPregunta = new javax.swing.JLabel();
        lblInformacion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPreguntas = new javax.swing.JPanel();
        lblCrear = new javax.swing.JLabel();
        lblDebug = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setText("Crea tu simulador Teórico");

        TipodeSimulador.setForeground(new java.awt.Color(255, 255, 255));
        TipodeSimulador.setText("Tipo de simulador ");

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        panelOpciones.setAlignmentY(0.0F);
        panelOpciones.setOpaque(false);
        panelOpciones.setLayout(new javax.swing.BoxLayout(panelOpciones, javax.swing.BoxLayout.Y_AXIS));

        lblSeleccionarSimulador.setForeground(new java.awt.Color(255, 255, 255));
        lblSeleccionarSimulador.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSeleccionarSimulador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelOpciones.add(lblSeleccionarSimulador);

        jPanel2.add(panelOpciones);

        panelLabels.setAlignmentX(0.0F);
        panelLabels.setAlignmentY(0.0F);
        panelLabels.setOpaque(false);
        panelLabels.setPreferredSize(new java.awt.Dimension(384, 35));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Añadir una Pregunta");

        lblAñadirunaPregunta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAñadirunaPregunta.setPreferredSize(new java.awt.Dimension(20, 20));

        lblInformacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblInformacion.setPreferredSize(new java.awt.Dimension(20, 20));

        javax.swing.GroupLayout panelLabelsLayout = new javax.swing.GroupLayout(panelLabels);
        panelLabels.setLayout(panelLabelsLayout);
        panelLabelsLayout.setHorizontalGroup(
            panelLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLabelsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                .addComponent(lblAñadirunaPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        panelLabelsLayout.setVerticalGroup(
            panelLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLabelsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelLabelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAñadirunaPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(panelLabels);

        jScrollPane1.setBackground(new java.awt.Color(5, 19, 36));
        jScrollPane1.setBorder(null);

        panelPreguntas.setBackground(new java.awt.Color(5, 19, 36));
        panelPreguntas.setLayout(new javax.swing.BoxLayout(panelPreguntas, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelPreguntas);

        lblCrear.setForeground(new java.awt.Color(255, 255, 255));
        lblCrear.setText("Crear");

        lblDebug.setBackground(new java.awt.Color(5, 19, 36));
        lblDebug.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(TipodeSimulador)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDebug, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(titulo)
                .addGap(12, 12, 12)
                .addComponent(TipodeSimulador)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDebug, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizGenerador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizGenerador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizGenerador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuizGenerador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizGenerador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TipodeSimulador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAñadirunaPregunta;
    private javax.swing.JLabel lblCrear;
    private javax.swing.JLabel lblDebug;
    private javax.swing.JLabel lblInformacion;
    private javax.swing.JLabel lblSeleccionarSimulador;
    private javax.swing.JPanel panelLabels;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelPreguntas;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
