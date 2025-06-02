/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
/**
 *
 * @author Usuario
 */
public class ReinoFungi extends Reino {
    public ReinoFungi(JPanel panelCategorias, JTextPane txtInfo) {
        super(panelCategorias, txtInfo);
    }

 @Override
    protected void crearCategorias() {
        
        JButton btnAscomicetos = new JButton("Ascomicetos");
        JButton btnBasidiomicetos = new JButton("Basidiomicetos");

        btnAscomicetos.addActionListener(e -> mostrarInformacion("Ascomicetos", "son uno de los grupos de hongos más antiguos que existen, utilizados desde siempre por los humanos como la levadura y son capaces de realizar fermentación alcohólica ", "ascomicetos.jpg"));
        btnBasidiomicetos.addActionListener(e -> mostrarInformacion("Basidiomicetos", " son na division del reino Fungi que incluyen las clasicos hongos comestibles y su nombre deriva de quealojan a sus esporas en estructuras en forma de basidios ", "basidiomicetos.jpeg"));
       
        panelCategorias.add(btnAscomicetos);
        panelCategorias.add(btnBasidiomicetos);
    }
}

