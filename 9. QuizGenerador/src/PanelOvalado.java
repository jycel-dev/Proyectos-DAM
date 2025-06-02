/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Usuario
 */
public class PanelOvalado extends JPanel {
     private int arcWidth = 30;
    private int arcHeight = 30;

    public PanelOvalado() {
        setOpaque(false);  
}
    @Override
    protected void paintComponent(Graphics g) {
        // Graphics2D para mejor control del renderizado
        Graphics2D g2 = (Graphics2D) g.create();

        // bordes suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //fondo redondeado con el color de fondo del panel
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

        g2.dispose(); 
        super.paintComponent(g); 
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
        repaint();
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
        repaint();
    }
}
