import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.util.*;
import java.util.List;

public class OpenCVProcessor {

    static {
        System.load("C:\\Users\\Usuario\\Downloads\\opencv\\build\\java\\x64\\opencv_java4110.dll");
    }
    
    private static final int OPCIONES = 4;
    private static final int NUM_PREGUNTAS = 40;
    private static final int MARGEN_COLUMNA = 350;
    private static final int MARGEN_Y_FILA = 75;

    public static Mat prepararImagen(Mat imagenColor) {
        Mat imagenGris = new Mat();
        Imgproc.cvtColor(imagenColor, imagenGris, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imagenGris, imagenGris, new Size(5, 5), 0);
        Core.normalize(imagenGris, imagenGris, 0, 255, Core.NORM_MINMAX);
        
        Mat imagenBinaria = new Mat();
        Imgproc.threshold(imagenGris, imagenBinaria, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
        return imagenBinaria;
    } 
    

    public static List<Circle> detectarBurbujas(Mat imagenOriginal) {
    // Convertir a escala de grises
    Mat gris = new Mat();
    Imgproc.cvtColor(imagenOriginal, gris, Imgproc.COLOR_BGR2GRAY);

    // desenfoque para reducir ruido y bordes falsos
    Imgproc.GaussianBlur(gris, gris, new Size(7, 7), 2);

    // histograma para mejorar el contraste 
    Imgproc.equalizeHist(gris, gris);

    // Detectar círculos con HoughCircles
    Mat circulos = new Mat();
    Imgproc.HoughCircles(
        gris,
        circulos,
        Imgproc.HOUGH_GRADIENT,
        1,             // dp (resolución)
        40,            // minDist: distancia mínima entre centros 
        55,            // param1: umbral alto del detector de bordes 
        26,            // param2: umbral de acumulador (cuanto más bajo, más círculos falsos)
        25,            // minRadius 
        40             // maxRadius
    );

    List<Circle> burbujas = new ArrayList<>();
    if (circulos.cols() > 0) {
        for (int i = 0; i < circulos.cols(); i++) {
            double[] c = circulos.get(0, i);
            if (c == null) continue;
            Point centro = new Point(Math.round(c[0]), Math.round(c[1]));
            int radio = (int) Math.round(c[2]);
            burbujas.add(new Circle(centro, radio));
        }
    }
    
    burbujas = eliminarDuplicadosCercanos(burbujas, 14.0);

    return burbujas;
  }
    
    public static List<Circle> eliminarDuplicadosCercanos(List<Circle> circulos, double distanciaMinima) {
    List<Circle> filtrados = new ArrayList<>();

    for (Circle actual : circulos) {
        boolean esDuplicado = false;

        for (Circle existente : filtrados) {
            double distancia = Math.hypot(actual.centro.x - existente.centro.x, actual.centro.y - existente.centro.y);
            if (distancia < distanciaMinima) {
                esDuplicado = true;
                break;
            }
        }

        if (!esDuplicado) {
            filtrados.add(actual);
        }
    }

    return filtrados;
}
    
    
//Verificacion Visual     
    public static void guardarBurbujasMarcadas(String rutaOriginal, List<Circle> burbujas, String rutaSalida) {
    Mat imagen = Imgcodecs.imread(rutaOriginal);
    for (Circle burbuja : burbujas) {
        Imgproc.circle(imagen, burbuja.centro, burbuja.radio, new Scalar(0, 255, 0), 2);
        Imgproc.circle(imagen, burbuja.centro, 2, new Scalar(0, 0, 255), 3);
    }
    Imgcodecs.imwrite(rutaSalida, imagen);
}
    


    public static List<List<Rect>> agruparPorFilas(List<Rect> columna) {
        columna.sort(Comparator.comparingInt(r -> r.y)); 

    List<List<Rect>> filas = new ArrayList<>(10);
    for (Rect burbuja : columna) {
            boolean asignado = false;
            for (List<Rect> fila : filas) {
                if (Math.abs(fila.get(0).y - burbuja.y) < MARGEN_Y_FILA) {
                    fila.add(burbuja);
                    asignado = true;
                    break;
                }
            }
            if (!asignado) {
                List<Rect> nuevaFila = new ArrayList<>();
                nuevaFila.add(burbuja);
                filas.add(nuevaFila);
            }
        }
    
    // información de burbujas por fila
    for (int i = 0; i < filas.size(); i++) {
        System.out.printf("  -> Fila %2d: %d burbujas detectadas.%n", i + 1, filas.get(i).size());
    }
        return filas;
    }
    
    

    public static String detectarRespuestaDeFila(Mat imagenBinaria, List<Rect> fila) {
    fila.sort(Comparator.comparingInt(r -> r.x));

    final int UMBRAL_NEGROS_ABSOLUTO = 900; // Más estricto
    final double UMBRAL_RELATIVO = 0.6;     // Menos sensible

    int[] valores = new int[fila.size()];
    int maxNegros = -1;

    // Contar píxeles blancos en la imagen binaria (porque está invertida)
    for (int i = 0; i < fila.size(); i++) {
        Mat roi = new Mat(imagenBinaria, fila.get(i));
        valores[i] = Core.countNonZero(roi);  // blancos = burbuja marcada en binaria invertida
        if (valores[i] > maxNegros) maxNegros = valores[i];
    }

    // Si la burbuja más negra no pasa cierto umbral → se considera vacía
    if (maxNegros < UMBRAL_NEGROS_ABSOLUTO) {
        return "";  // Vacía
    }

    // Revisar cuántas están por encima del 60% del máximo
    int opcionesMarcadas = 0;
    int respuestaIndex = -1;
    for (int i = 0; i < valores.length; i++) {
        if (valores[i] >= maxNegros * UMBRAL_RELATIVO) {
            opcionesMarcadas++;
            if (respuestaIndex == -1) respuestaIndex = i;
        }
    }

    // Si hay más de una por encima del umbral → doble
    if (opcionesMarcadas > 1) {
        return "Doble";
    }

    return respuestaIndex >= 0 && respuestaIndex < OPCIONES ? "ABCD".charAt(respuestaIndex) + "" : "";
}
    
        public static List<List<Rect>> agruparPorColumnas(List<Rect> burbujas) {
        burbujas.sort(Comparator.comparingInt(r -> r.x));
        List<List<Rect>> columnas = new ArrayList<>();
        List<Rect> columnaActual = new ArrayList<>();
        int xRef = burbujas.get(0).x;

        for (Rect burbuja : burbujas) {
            if (Math.abs(burbuja.x - xRef) > MARGEN_COLUMNA) {
                columnas.add(columnaActual);
                columnaActual = new ArrayList<>();
                xRef = burbuja.x;
            }
            columnaActual.add(burbuja);
        }

        if (!columnaActual.isEmpty()) columnas.add(columnaActual);
        for (List<Rect> columna : columnas) {
            columna.sort(Comparator.comparingInt(r -> r.y));
        }

        return columnas;
    }
    
    
    public static List<String> detectarRespuestas(String rutaImagen) {
        List<String> respuestas = new ArrayList<>(Collections.nCopies(NUM_PREGUNTAS, ""));
        Mat imagenColor = Imgcodecs.imread(rutaImagen);
        if (imagenColor.empty()) {
            System.err.println("No se pudo cargar la imagen.");
            return respuestas;
        }

        Mat imagenBinaria = prepararImagen(imagenColor);
        List<Circle> circulosDetectados = detectarBurbujas(imagenColor);
        
        
    // Guardar imagen con burbujas detectadas
        guardarBurbujasMarcadas(rutaImagen, circulosDetectados, "burbujas_detectadas.jpg");
        Imgcodecs.imwrite("debug_binaria.jpg", imagenBinaria);
        
        
        List<Rect> burbujas = new ArrayList<>(circulosDetectados.stream().map(Circle::toRect).toList());
        System.out.println(" Total de burbujas detectadas: " + burbujas.size());
        
        if (burbujas.isEmpty()) {
            System.err.println("No se detectaron burbujas.");
            return respuestas;
        }

        List<List<Rect>> columnas = agruparPorColumnas(burbujas);
        System.out.println(" Columnas detectadas: " + columnas.size()); // revisar columnas
        
        int totalFilas = 0;
        int totalBurbujasRellenas = 0;
        int preguntaActual = 0;

    for (int i = 0; i < columnas.size(); i++) {
        List<List<Rect>> filas = agruparPorFilas(columnas.get(i));
        System.out.println(" Columna " + (i + 1) + ": " + filas.size() + " filas detectadas.");
        totalFilas += filas.size();

        for (List<Rect> fila : filas) {
            if (preguntaActual >= NUM_PREGUNTAS) break;

            String respuesta = detectarRespuestaDeFila(imagenBinaria, fila);

            // Para contar cuántas están rellenas (no vacías)
            if (!respuesta.isEmpty()) {
                totalBurbujasRellenas++;
            }

            respuestas.set(preguntaActual, respuesta);
            preguntaActual++;
        }
    }

    System.out.println(" Total de filas (preguntas): " + totalFilas);
    System.out.println("️ Total de burbujas marcadas: " + totalBurbujasRellenas);
    System.out.println(" Respuestas detectadas: " + preguntaActual);

    return respuestas;

    }
    
    
    //Mostrar errores 
    public static ResultadoDeteccion detectarErrores(String rutaImagen) {
    Mat imagenColor = Imgcodecs.imread(rutaImagen);
    if (imagenColor.empty()) {
        return new ResultadoDeteccion("", new HashMap<>());
    }

    List<Circle> circulosDetectados = detectarBurbujas(imagenColor);
    List<Rect> burbujas = new ArrayList <> (circulosDetectados.stream().map(Circle::toRect).toList());
    List<List<Rect>> columnas = agruparPorColumnas(burbujas);

    int preguntaActual = 0;
    boolean hayFilaIncompleta = false;
    Map<Integer, Integer> erroresPorColumna = new HashMap<>();

    for (int i = 0; i < columnas.size(); i++) {
        List<List<Rect>> filas = agruparPorFilas(columnas.get(i));
        for (List<Rect> fila : filas) {
            if (preguntaActual >= NUM_PREGUNTAS) break;
            if (fila.size() != 4) {
                hayFilaIncompleta = true;
                erroresPorColumna.put(i + 1, erroresPorColumna.getOrDefault(i + 1, 0) + 1);
            }
            preguntaActual++;
        }
    }

    StringBuilder advertencias = new StringBuilder();
    if (preguntaActual < NUM_PREGUNTAS || hayFilaIncompleta) {
        advertencias.append("Atención: No se detectaron todas las preguntas.\n");
    }

    for (Map.Entry<Integer, Integer> entry : erroresPorColumna.entrySet()) {
        if (entry.getValue() >= 5) {
            advertencias.append("Error en la lectura de la columna ").append(entry.getKey()).append(".\n");
        }
    }

    return new ResultadoDeteccion(advertencias.toString(), erroresPorColumna);   
    }
    
}