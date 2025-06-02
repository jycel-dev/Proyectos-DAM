/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*; 
import java.io.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
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
    
    
    public static void SetImageLabel(JLabel label, String imagePath, Dimension dimension, boolean keepProportions) {
    ImageIcon icon = new ImageIcon(imagePath);
    Image image = icon.getImage();
    if (keepProportions) {
        double ratio = (double) image.getWidth(null) / image.getHeight(null);
        int newWidth = (int) (dimension.height * ratio);
        int newHeight = (int) (dimension.width / ratio);
        Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(resizedImage));
    } else {
        Image resizedImage = image.getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(resizedImage));
    }
}
    
    public static ArrayList<String> CreateStringList(String root, String name, String filetype, int size) {
    ArrayList<String> fileList = new ArrayList<>();
    for (int i = 1; i <= size; i++) {
        String filePath = root + name + i + filetype;
        fileList.add(filePath);
    }
    return fileList;
}
    
//ReadTextFile
    public static String ReadTextFile(String filePath) {
    StringBuilder content = new StringBuilder();
    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
    } catch (IOException e) {
        return "Error al leer el archivo.";
    }

    return content.toString();
}
    //Async
    public static void Async(long waitTime, Runnable asyncFunction) {
    new Thread(() -> {
        try {
            Thread.sleep(waitTime);
            asyncFunction.run();
        } catch (InterruptedException e) {
            System.out.println("Hilo interrumpido.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error en la función asíncrona: " + e.getMessage());
        }
    }).start();
}
}
