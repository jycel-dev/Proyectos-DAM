/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
/**
 *
 * @author Usuario
 */
public class ReinoAnimal extends Reino {
    public ReinoAnimal(JPanel panelCategorias, JTextPane txtInfo) {
        super(panelCategorias, txtInfo);
    }

   @Override
    protected void crearCategorias() {
  
        JButton btnMamiferos = new JButton("Mamíferos");
        JButton btnAves = new JButton("Aves");
        JButton btnReptiles = new JButton("Reptiles");
        JButton btnInsectos = new JButton("Insectos");

        btnMamiferos.addActionListener(e -> {mostrarInformacion("Mamiferos", "Los mamiferos son  animales vertebrados y de sangre caliente pertenecientes a la clase mammalia, cuya característica esencial es que las hembras poseen glándulas mamarias que sirven para generar leche con que alimentar a sus crías", "mamiferos.jpg"); mostrarMamiferos(); });
        btnAves.addActionListener(e -> { mostrarInformacion("Aves", "Las aves son animales vertebrados que, entre otras características distintivas, tienen el cuerpo cubierto de plumas, alas como extremidades delanteras y un pico", "aves.jpg"); mostrarAves(); });
        btnReptiles.addActionListener(e -> { mostrarInformacion("Reptiles", "conjunto de animales cuadrúpedos y vertebrados, de sangre fría, cuya principal característica es poseer una piel cubierta de escamas de queratina", "reptiles.jpeg"); mostrarReptiles();  });
        btnInsectos.addActionListener(e -> { mostrarInformacion("Insectos", "son una clase de animales invertebrados del filo de los artrópodos, caracterizados por presentar un par de antenas, tres pares de patas y dos pares de alas que pueden reducirse o faltar", "insectos.png"); mostrarInsectos(); });

        panelCategorias.add(btnMamiferos);
        panelCategorias.add(btnAves);
        panelCategorias.add(btnReptiles);
        panelCategorias.add(btnInsectos);
    }

    private void mostrarMamiferos() {
        String[][] mamiferos = {
            {"Elefante", "son una familia de mamíferos cuadrúpedos de gran tamaño, famosos por sus grandes orejas y trompa prensil, así como sus colmillos blancos y largos de marfil, además de por ser los animales terrestres más voluminosos que existen hoy en día en el mundo", "elefante.jpg"},
            {"Jirafa", "es un mamífero herbívoro propio de África. Es la especie de animal terrestre más alta del mundo, pudiendo alcanzar una altura de 5,8 m y un peso que varía entre 750 y 1600 kg. Las jirafas habitan en el sur del Sahara y norte de Botsuana en espacios abiertos, pastizales y sabanas", "jirafa.jpeg"},
            {"León", "es un felino que habita en África y la India de gran tamaño que constituye el mayor depredador de la cadena trófica", "leon.jpg"}
        };
        mostrarSubcategoria(mamiferos);
    }

    private void mostrarAves() {
        String[][] aves = {
            {"Cotorra", "es un animal pequeño, pariente del loro, y oriundo del Centro y Sur de América. Tienen las mejillas cubiertas de plumas, alas y cola largas y puntiagudas y plumaje de colores varios en que domina el verde", "cotorra.jpg"},
            {"Gorrión", "es un ave conocida por todo el mundo por ser domésticos. Es una especie originaria de Europa, África y Asia. Esta ave ha conquistado todo el planeta y se distingue por ser el único grupo de vertebrados que pueden vivir en todos los ambientes, desde el nivel del mar hasta las montañas más altas, es lista y versatil que lleva miles de años conviviendo con los humanos", "gorrion.jpeg"},
            {"Urraca", "(también conocida como Pica pica) es un ave paseriforme que reside en Eurasia. Es una de las aves más frecuentes en Europa y se adapta a casi todos los ecosistemas", "urraca.jpeg"}
        };
        mostrarSubcategoria(aves);

    }

    private void mostrarReptiles() {
        String[][] reptiles = {
            {"Tortuga", " son un grupo de reptiles de hábitat acuático y terrestre. Su principal característica es poseer un fuerte caparazón que les protege los órganos internos y que abarca todo el torso del animal", "tortuga.jpeg"},
            {"Iguana", "es un reptil escamoso nativo de zonas tropicales, tienen escamas en forma de picos, su sentido de la vista esta muy desarrollado y su piel les permite camuflarse en su entorno", "iguana.jpeg"},
            {"Caimán", "es un género de cocodrilos. Se distribuyen en las regiones subtropicales y tropicales de América, desde Centroamérica hasta el sur de Sudamérica, poseen una cola muy poderosa que utilizan para impulsarse en el agua", "caiman.jpeg"}
        };
        mostrarSubcategoria(reptiles);
    }

    private void mostrarInsectos() {
        String[][] insectos = {
            {"Mariposa", "es un insecto que cuenta con cuatro alas, por lo general de tonalidades brillantes, son un orden de insectos holometábolos, es decir, su tipo de desarrollo va desde el embrión, pasando por larva, pupa y finalmente adulto", "mariposa.jpeg"},
            {"Abeja", "son insectos sociales y colaboradores que viven en las colmenas formando grandes colonias, lo que ha proporcionado a las sociedades humanas miel y cera de abeja desde hace miles de años", "abeja.jpeg"},
            {"Mantis", "es un insecto depredador fuerte, pero no es venenoso, son animales solitarios excepto en época de reproducción, pueden ser de color verde o pardo", "mantis.jpg"}
        };
        mostrarSubcategoria(insectos);
    }
}

