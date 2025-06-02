
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
/**
 *
 * @author Usuario
 */
public class Utilidades {
    private Utilidades(){}
    
     private static Font ralewayFont;

    static {
        try {
            // Ruta del archivo de fuente
            File fontFile = new File("src/fonts/Raleway-Regular.ttf");

            // Cargar la fuente
            ralewayFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 14);

            // Registrar la fuente en el sistema gráfico
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(ralewayFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            ralewayFont = new Font("SansSerif", Font.PLAIN, 14); // Fuente de respaldo
        }
    }
    
     // Método para obtener la fuente en distintos tamaños y estilos
    public static Font getRalewayFont(int style, float size) {
        return ralewayFont.deriveFont(style, size);
    }

    // Método para aplicar la fuente Raleway globalmente
    public static void applyRalewayFont(float size) {
        Font font = getRalewayFont(Font.PLAIN, size);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
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
    
    
    
}
