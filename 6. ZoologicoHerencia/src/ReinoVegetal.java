/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
/**
 *
 * @author Usuario
 */
public class ReinoVegetal extends Reino {
    public ReinoVegetal(JPanel panelCategorias, JTextPane txtInfo) {
        super(panelCategorias, txtInfo);
    }

    @Override
    protected void crearCategorias() {
    
        JButton btnLicopodios = new JButton("Licopodios");
        JButton btnMusgos = new JButton("Musgos");
        JButton btnAngiospermas = new JButton("Angiospermas");

        btnLicopodios.addActionListener(e -> mostrarInformacion("Licopodios", "son plantas vasculares que se caracterizan por tener hojas con una estructura de espora, su reproducción depende del agua y llegan a tener mas d 40 especies ", "licopodios.jpg"));
        btnMusgos.addActionListener(e -> mostrarInformacion("Musgos", "son plantas pequeñas, no vasculares, que pertenecen al grupo de las briofitas. Se caracterizan por su capacidad para habitar en ambientes húmedos y sombríos, aunque algunas especies pueden encontrarse en zonas secas y desérticas", "musgos.jpeg"));
        btnAngiospermas.addActionListener(e -> mostrarInformacion("Angiospermas", "son un grupo diversos de plantas vasculares con órganos vegetativos bien diferenciados (raíz, tallo y hojas) que producen flores, frutos y semillas", "angiospermas.jpeg"));
        
        panelCategorias.add(btnLicopodios);
        panelCategorias.add(btnMusgos);
        panelCategorias.add(btnAngiospermas);
    }
}
