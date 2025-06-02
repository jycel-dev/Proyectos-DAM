/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
/**
 *
 * @author Usuario
 */
public class ImageUtils {
    
     public static ImageIcon cargarImagenEscalada(String ruta, int ancho, int alto) {
        ImageIcon original = new ImageIcon(ruta);
        Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
    
}
