/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*; 
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.json.JSONObject;
/**
 *
 * @author Usuario
 */
public class Utilidades {

    
    private Utilidades(){}
    
    public static void cambiarPanel(JPanel contentPanel, JPanel instancePanel) {
        instancePanel.setSize(contentPanel.getWidth(), contentPanel.getHeight());
        instancePanel.setLocation(0, 0);

        contentPanel.removeAll();
        contentPanel.add(instancePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
 
    public static void SetImageLabel(JLabel label, String imagePath) {
    ImageIcon icon = new ImageIcon(imagePath);
    if (icon.getIconWidth() == -1) {
        System.out.println("No se pudo cargar la imagen " + imagePath);
        label.setText("Imagen no disponible");
        return;
    }
    label.setIcon(icon);
    }
    
    
    public static void SetImageLabel(JLabel label, String imagePath, Dimension dimension) {
    ImageIcon icon = new ImageIcon(imagePath);
    if (icon.getIconWidth() == -1) {
        System.out.println("No se pudo cargar la imagen " + imagePath);
        return;
    }
    Image resizedImage = icon.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(resizedImage));
    }
    
    //Que se adapte el escalado al label 
 public static void addHoverEffect(JLabel label, String imagePath, Dimension defaultSize) {
    SetImageLabel(label, imagePath, defaultSize);

    label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
           SetImageLabel(label, imagePath, new Dimension(label.getWidth() + 3, label.getHeight() + 3));   
        }

        @Override
        public void mouseExited(MouseEvent e) {
            SetImageLabel(label, imagePath, defaultSize);
        }
    });
}
    
    public static void setBackgroundImage(JPanel panel, String imagePath) {
        // Cargar la imagen de fondo
        Image backgroundImage = new ImageIcon(Utilidades.class.getResource(imagePath)).getImage();

        if (backgroundImage == null) {
            System.err.println("No se encontró la imagen " + imagePath);
        }

        // Establecer el fondo personalizado en el panel
        panel.setOpaque(false);

        // Sobrescribir el método paintComponent del panel para dibujar la imagen de fondo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Establecer el tamaño y ubicación del panel de fondo
        backgroundPanel.setSize(panel.getWidth(), panel.getHeight());
        backgroundPanel.setLocation(0, 0);

        // Añadir el panel de fondo al panel principal
        panel.add(backgroundPanel);
    }
    
    public static void addClickNavigation(JLabel label, JPanel contentPanel, JPanel targetPanel) {
    label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            cambiarPanel(contentPanel, targetPanel);
        }
    });
}
    
    //Info del juego en la consola 
    public static void mostrarInformacionJuego(int gradoIndex, int juegoIndex) {
        if (LeerJSON.jsonCargado()) {
            System.out.println("El JSON no ha sido cargado correctamente.");
        return;
    }       
        JSONObject juego = LeerJSON.getJuego(gradoIndex, juegoIndex);
                if (juego != null) {
            System.out.println("Grado: " + gradoIndex);
            System.out.println("Juego: " + juegoIndex);
            System.out.println("Título: " + juego.getString("título"));
            System.out.println("Imagen: " + juego.getString("imagen"));
            System.out.println("Ruta: " + juego.getString("ruta"));
            System.out.println("Descripción: " + juego.getString("descripcion"));
        } else {
            System.out.println("No se encontró el juego con índices: " + gradoIndex + ", " + juegoIndex);
        }
    }
   
}