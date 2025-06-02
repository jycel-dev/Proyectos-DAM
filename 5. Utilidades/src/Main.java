/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Main {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Prueba de Imágenes");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2, 10, 10));
        
        String imgPath1 = "src/resources/imagen1.jpg";
        String imgPath2 = "src/resources/imagen2.jpeg";
        String imgPath3 = "src/resources/imagen3.jpg";
        String imgPath4 = "src/resources/imagen4.jpg";
        
        frame.add(createImageLabel(imgPath1, "Escala automática", null, false));
        frame.add(createImageLabel(imgPath2, "Escala fija (200x200)", new Dimension(200, 200), false));
        frame.add(createImageLabel(imgPath3, "Escala con proporciones", new Dimension(300, 200), true));
        frame.add(createImageLabel(imgPath4, "Escala sin proporciones", new Dimension(300, 200), false));

        frame.setVisible(true);
        
        //ReadTextFile
        String filePath = "src/resources/miArchivo.txt";
        if (new File(filePath).exists()) {
            System.out.println("Contenido del archivo:");
            System.out.println(Utilidades.ReadTextFile(filePath));
        } else {
            System.out.println("El archivo " + filePath + " no existe.");
        }
        
        //CreateStringList
         System.out.println("\nPrueba de CreateStringList:");
        ArrayList<String> files = Utilidades.CreateStringList("C:/Images/", "Foto", ".jpg", 5);
        for (String file : files) {
            System.out.println(file);
        }
        
        //Async
        System.out.println("\nPrueba de Async:");
        Utilidades.Async(3000, () -> System.out.println("Ejecutado después de 3 segundos"));
        System.out.println("Antes de la espera");
        
        
     java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });    
    }
    
    private static JPanel createImageLabel(String imagePath, String title, Dimension size, boolean keepProportions) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY); // Para visualizar el tamaño del JLabel

        if (size == null) {
            Utilidades.SetImageLabel(label, imagePath);
        } else {
            Utilidades.SetImageLabel(label, imagePath, size, keepProportions);
        }

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
