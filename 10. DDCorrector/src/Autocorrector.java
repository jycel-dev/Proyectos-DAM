/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class Autocorrector extends javax.swing.JFrame {

    private String rutaImagenCargada = null;
    /**
     * Creates new form Autocorrector
     */
    public Autocorrector() {
        initComponents();
        
        setTitle("Auto corrector"); 
        setSize(700, 700); 
        setResizable(false); 
        setAlwaysOnTop(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
    CargarFoto.addActionListener((ActionEvent evt) -> {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar imagen del examen");
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivo = selector.getSelectedFile();
        rutaImagenCargada = archivo.getAbsolutePath();
        
    // Mostrar imagen escalada en el JLabel
        ImageIcon imagenEscalada = ImageUtils.cargarImagenEscalada(rutaImagenCargada, imageLabel.getWidth(), imageLabel.getHeight());
        imageLabel.setIcon(imagenEscalada);
        imageLabel.setText("");
    }
});
    
    //ComboBox
    String[] codigos = {"EXAM001", "EXAM002", "EXAM003", "EXAM004"};
    DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(codigos);
    comboCodigos.setModel(modelo);
    comboCodigos.setSelectedIndex(-1);

    comboCodigos.addActionListener((ActionEvent e) -> {
    String codigo = comboCodigos.getSelectedItem().toString();
    if (codigo.isEmpty()) return;

    String nombreArchivo = switch (codigo) {
        case "EXAM001" -> "img-respuestas-examen-1.png";
        case "EXAM002" -> "img-respuestas-examen-2.png";
        case "EXAM003" -> "img-respuestas-examen-3.png";
        case "EXAM004" -> "img-respuestas-examen-4.png";
        default -> null;
    };

    if (nombreArchivo != null) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/" + nombreArchivo));
        referenciaLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(
                referenciaLabel.getWidth(), referenciaLabel.getHeight(), Image.SCALE_SMOOTH)));
    }
});
    
    
//Corregir     
     Corregir.addActionListener((ActionEvent evt) -> {
    String codigo = comboCodigos.getSelectedItem().toString();
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, ingrese un código de plantilla.");
        return;
    }
    
    if (rutaImagenCargada == null || rutaImagenCargada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, cargue una imagen primero.");
                return;
            }
 
    
    List<String> respuestas = JsonUtils.obtenerRespuestasCorrectas(codigo);
    if (respuestas == null) {
        resultadoArea.setText("No se encontró el código de plantilla: " + codigo);
    } else {
        resultadoArea.setText("Respuestas correctas:\n" + respuestas.toString());
    }
    
 
  OpenCVProcessor processor = new OpenCVProcessor();
List<String> respuestasDetectadas = processor.detectarRespuestas(rutaImagenCargada);
ResultadoDeteccion resultadoErrores = processor.detectarErrores(rutaImagenCargada);

//Mostrar advertencias 
if (!resultadoErrores.mensaje().isEmpty()) {
    comentariosLabel.setText("<html>" + resultadoErrores.mensaje().replace("\n", "<br>") + "</html>");
} else {
    comentariosLabel.setText("");
}

StringBuilder resultado = new StringBuilder();
int aciertos = 0;

for (int i = 0; i < respuestasDetectadas.size(); i++) {
    String correcta = respuestas.get(i);
    String alumno = respuestasDetectadas.get(i);

    if (alumno.equalsIgnoreCase("Doble")) {
    resultado.append("Pregunta ").append(i + 1).append(": Doble marca (Correcta: ").append(correcta).append(")\n");
        } else if (alumno.isEmpty()) {
            resultado.append("Pregunta ").append(i + 1).append(": Vacía (Correcta: ").append(correcta).append(")\n");
        } else if (alumno.equals(correcta)) {
            resultado.append("Pregunta ").append(i + 1).append(": Correcta (").append(alumno).append(")\n");
            aciertos++;
        } else {
            resultado.append("Pregunta ").append(i + 1).append(": Incorrecta (").append(alumno)
            .append(" | Correcta: ").append(correcta).append(")\n");
        }
    }

resultadoArea.setText(resultado.toString());
 

// Calificación
double nota = (aciertos / 40.0) * 100;

String mensajeFinal; 
if (nota >=50) {
   mensajeFinal = String.format("<html><b style='color:green;'>APROBADO</b><br>Aciertos: %d / 40<br>Nota final: %.2f %%</html>", aciertos, nota);
} else {
    mensajeFinal = String.format("<html><b style='color:red;'>REPROBADO</b><br>Aciertos: %d / 40<br>Nota final: %.2f %%</html>", aciertos, nota); 
}
resultadoFinal.setText(mensajeFinal);
  


    });   
   
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
        panelIzquierdo = new javax.swing.JPanel();
        ImagenExamen = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        CargarFoto = new javax.swing.JButton();
        Corregir = new javax.swing.JButton();
        resultadoFinal = new javax.swing.JLabel();
        comentariosLabel = new javax.swing.JLabel();
        panelDerecho = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboCodigos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultadoArea = new javax.swing.JTextArea();
        referenciaLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelIzquierdo.setBackground(new java.awt.Color(255, 255, 255));

        ImagenExamen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ImagenExamen.setText("Imagen del Examen ");

        CargarFoto.setBackground(new java.awt.Color(204, 204, 204));
        CargarFoto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CargarFoto.setText("Cargar Foto");
        CargarFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Corregir.setBackground(new java.awt.Color(204, 204, 204));
        Corregir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Corregir.setText("Corregir");
        Corregir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        resultadoFinal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        resultadoFinal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comentariosLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        comentariosLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelIzquierdoLayout = new javax.swing.GroupLayout(panelIzquierdo);
        panelIzquierdo.setLayout(panelIzquierdoLayout);
        panelIzquierdoLayout.setHorizontalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                        .addComponent(ImagenExamen)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIzquierdoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resultadoFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelIzquierdoLayout.createSequentialGroup()
                        .addComponent(CargarFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Corregir)))
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIzquierdoLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(comentariosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        panelIzquierdoLayout.setVerticalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIzquierdoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ImagenExamen)
                .addGap(18, 18, 18)
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CargarFoto)
                    .addComponent(Corregir))
                .addGap(18, 18, 18)
                .addComponent(resultadoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comentariosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDerecho.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código de la Plantilla");

        comboCodigos.setBackground(new java.awt.Color(204, 204, 204));
        comboCodigos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Resultado");

        resultadoArea.setEditable(false);
        resultadoArea.setBackground(new java.awt.Color(255, 255, 255));
        resultadoArea.setColumns(20);
        resultadoArea.setRows(5);
        resultadoArea.setBorder(null);
        jScrollPane1.setViewportView(resultadoArea);

        javax.swing.GroupLayout panelDerechoLayout = new javax.swing.GroupLayout(panelDerecho);
        panelDerecho.setLayout(panelDerechoLayout);
        panelDerechoLayout.setHorizontalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDerechoLayout.createSequentialGroup()
                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelDerechoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(referenciaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelDerechoLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(comboCodigos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDerechoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelDerechoLayout.setVerticalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDerechoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(comboCodigos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(referenciaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelIzquierdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDerecho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelDerecho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelIzquierdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(Autocorrector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Autocorrector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Autocorrector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Autocorrector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Autocorrector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CargarFoto;
    private javax.swing.JButton Corregir;
    private javax.swing.JLabel ImagenExamen;
    private javax.swing.JComboBox<String> comboCodigos;
    private javax.swing.JLabel comentariosLabel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelDerecho;
    private javax.swing.JPanel panelIzquierdo;
    private javax.swing.JLabel referenciaLabel;
    private javax.swing.JTextArea resultadoArea;
    private javax.swing.JLabel resultadoFinal;
    // End of variables declaration//GEN-END:variables
}
