/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.Image;
/**
 *
 * @author Usuario
 */
public abstract class Reino {
    protected JPanel panelCategorias;
    protected JTextPane txtInfo;

    public Reino(JPanel panelCategorias, JTextPane txtInfo) {
        this.panelCategorias = panelCategorias;
        this.txtInfo = txtInfo;
    }
    
    public JPanel getPanelCategorias(){
        return panelCategorias;
    }
    
    public void setPanelCategorias(JPanel panelCategorias){
        this.panelCategorias = panelCategorias;
    }
    
    public JTextPane getTxtInfo(){
        return txtInfo;
    }
    
    public void setTxtInfo (JTextPane txtInfo) {
        this.txtInfo = txtInfo;
    }
    
    protected abstract void crearCategorias();
    
    public void mostrarCategorias(){
        panelCategorias.removeAll();
        crearCategorias();
        
        panelCategorias.revalidate();
        panelCategorias.repaint();
    } 
    
    protected void mostrarInformacion(String categoria, String descripcion, String nombreImagen) {
        txtInfo.setText(categoria + "\n\n" + descripcion + "\n\n");
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/" + nombreImagen));
        
        if (icon.getImage() == null) {
            System.err.println("No se pudo cargar la imagen: " + nombreImagen);
        } else {
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
        }

        txtInfo.setCaretPosition(txtInfo.getDocument().getLength());
        txtInfo.insertIcon(icon);
    }  
    
    protected void mostrarSubcategoria(String[][] elementos) {
        panelCategorias.removeAll();
        for (String [] elemento : elementos) {
            String nombre = elemento [0];
            String descripcion = elemento [1];
            String imagen = elemento [2];
            
            JButton btn = new JButton(nombre);
            btn.addActionListener(e -> mostrarInformacion(nombre, descripcion, imagen));
            panelCategorias.add(btn);
    }

    panelCategorias.revalidate();
    panelCategorias.repaint();
    }
}

