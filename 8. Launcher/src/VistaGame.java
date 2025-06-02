/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Usuario
 */
public class VistaGame extends javax.swing.JPanel {
    private JSONObject juegoJSON;
    private String[] imagenes;
    private int indiceActual = 0;
    private JLabel[] circulos;
    /**
     * Creates new form VistaGame
     */
    public VistaGame(JSONObject juegoData) {
        this.juegoJSON = juegoData;
            if (this.juegoJSON == null) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar el juego.");
            return;
        }
            
        initComponents();
        cargarImagenesJuego();
        inicializarInformacion();
        inicializarEventos();
        actualizarCarrusel();
    }
            
    private void inicializarInformacion() {    
        String informacion = juegoJSON.optString("descripcion");
        informacion = informacion.replace("<html>", "").replace("</html>", "");
        informacion = informacion.replace("<br>", "\n");
        
        jTextArea1.setText(informacion);
        jTextArea1.setEditable(false);
        jTextArea1.setBorder(null);
        
        jLabel2.setText(juegoJSON.optString("titulo")); //Actualizo el titulo 
}
    
    private String obtenerNombreGrado() {
        int gradoIndex = 0;
        JSONObject grado = LeerJSON.getJuego(gradoIndex, 0);
         if (grado != null) {
        return "Grado" + (gradoIndex + 1);
        } else {
        return "Grado desconocido";
     }
    }
    
     private void cargarImagenesJuego() { // si juego.getImagen() es "Embarque" carga Embarque0.png, Embarque1.png y asi sucesivamente 
        String nombreBase = juegoJSON.optString("imagen", "default");
         this.imagenes = new String[5] ;
         
        for (int i = 0; i < 5; i++) {
        String ruta = "src/resources/" + nombreBase + i + ".png"; // Ej: "Embarque0.png"
        if (new File(ruta).exists()) {
            imagenes[i] = ruta;
        } else {
            imagenes[i] = "src/resources/placeholder.png";
        }    
    }
} 
     
    private void inicializarEventos() { 

        Utilidades.SetImageLabel(jLabel1, "src/resources/Cuadrado fondo enfocado.png", new Dimension(840, 310));
        Utilidades.SetImageLabel(Comenzar, "src/resources/Comenzar.png", new Dimension(180, 35));
        Utilidades.addHoverEffect(Comenzar, "src/resources/Comenzar.png" , new Dimension(180, 35) );
        Utilidades.SetImageLabel(jLabel2, "src/resources/Barrita aislada descripción.png");
        Utilidades.SetImageLabel(BotonIzquierda, "src/resources/Flecha izquierda.png", new Dimension(22, 35));
        Utilidades.SetImageLabel(BotonDerecha, "src/resources/Flecha derecha.png", new Dimension(22, 35));
        
       Dimension originalSize = new Dimension(22, 35);
       Utilidades.addHoverEffect(BotonIzquierda, "src/resources/Flecha izquierda.png", originalSize);
       Utilidades.addHoverEffect(BotonDerecha, "src/resources/Flecha derecha.png", originalSize);
       
       circulos = new JLabel[]{CirculoCarrusel1, CirculoCarrusel2, CirculoCarrusel3, CirculoCarrusel4, CirculoCarrusel5};

BotonDerecha.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        siguienteImagen();
    }
    });

BotonIzquierda.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        anteriorImagen();
    }
    });

Comenzar.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        ejecutarJuego();
    }
});

}


     private void actualizarCarrusel() {
        
        ImageIcon icono = new ImageIcon(imagenes[indiceActual]);
        imagenLabel.setIcon(new ImageIcon(icono.getImage().getScaledInstance(710, 370, Image.SCALE_SMOOTH)));

        // Actualizar círculos 
        for (int i = 0; i < circulos.length; i++) {
            if (i == indiceActual) {
                Utilidades.SetImageLabel(circulos[i], "src/resources/PuntoCarruselFilled.png");
            } else {
                Utilidades.SetImageLabel(circulos[i], "src/resources/PuntoCarruselEmpty.png");
            }
        }
    }
    
    private void siguienteImagen() {
    indiceActual = (indiceActual + 1) % imagenes.length;
    actualizarCarrusel();
    }

    private void anteriorImagen() {
    indiceActual = (indiceActual - 1 + imagenes.length) % imagenes.length;
    actualizarCarrusel();
    }

    private void ejecutarJuego() {
        String rutaEjecutable = juegoJSON.optString("ruta", "");
        if (rutaEjecutable.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró la ruta del juego.");
            return;
        }
       try {
           String rutaAbsoluta = System.getProperty("user.dir") + "/" + rutaEjecutable;
           File archivo = new File(rutaAbsoluta);
            if (!archivo.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo no existe: " + archivo.getAbsolutePath());
            return;
        }
 
            Runtime.getRuntime().exec(rutaAbsoluta); 
            System.out.println("Ejecutando: " + archivo.getAbsolutePath());
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error al ejecutar el juego: " + ex.getMessage());
    }
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
        imagenLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        BotonIzquierda = new javax.swing.JLabel();
        BotonDerecha = new javax.swing.JLabel();
        Comenzar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        Barradescripcion1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        CirculoCarrusel1 = new javax.swing.JLabel();
        CirculoCarrusel2 = new javax.swing.JLabel();
        CirculoCarrusel3 = new javax.swing.JLabel();
        CirculoCarrusel4 = new javax.swing.JLabel();
        CirculoCarrusel5 = new javax.swing.JLabel();

        setOpaque(false);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 700));
        jPanel1.setLayout(null);

        imagenLabel.setMaximumSize(new java.awt.Dimension(710, 350));
        imagenLabel.setMinimumSize(new java.awt.Dimension(710, 350));
        imagenLabel.setPreferredSize(new java.awt.Dimension(710, 350));
        jPanel1.add(imagenLabel);
        imagenLabel.setBounds(260, 0, 710, 370);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(190, 30, 840, 310);

        BotonIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Flecha izquierda.png"))); // NOI18N
        BotonIzquierda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(BotonIzquierda);
        BotonIzquierda.setBounds(160, 190, 30, 30);

        BotonDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Flecha derecha.png"))); // NOI18N
        BotonDerecha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(BotonDerecha);
        BotonDerecha.setBounds(1050, 190, 30, 30);

        Comenzar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Comenzar.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel1.add(Comenzar);
        Comenzar.setBounds(970, 640, 210, 40);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(10, 38, 72));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jTextArea1.setBorder(null);
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 490, 710, 130);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Embarque y desembarque en helicoptero");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 430, 670, 40);
        jPanel1.add(Barradescripcion1);
        Barradescripcion1.setBounds(10, 430, 20, 40);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 20));
        jPanel2.setLayout(new java.awt.GridLayout(1, 5));
        jPanel2.add(CirculoCarrusel1);
        jPanel2.add(CirculoCarrusel2);
        jPanel2.add(CirculoCarrusel3);
        jPanel2.add(CirculoCarrusel4);
        jPanel2.add(CirculoCarrusel5);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(560, 380, 100, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Barradescripcion1;
    private javax.swing.JLabel BotonDerecha;
    private javax.swing.JLabel BotonIzquierda;
    private javax.swing.JLabel CirculoCarrusel1;
    private javax.swing.JLabel CirculoCarrusel2;
    private javax.swing.JLabel CirculoCarrusel3;
    private javax.swing.JLabel CirculoCarrusel4;
    private javax.swing.JLabel CirculoCarrusel5;
    private javax.swing.JLabel Comenzar;
    private javax.swing.JLabel imagenLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
