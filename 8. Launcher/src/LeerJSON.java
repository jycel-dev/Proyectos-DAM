/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Usuario
 */
public class LeerJSON {
    private static JSONObject jsonData;

   // Método para leer el JSON desde un archivo
    public static void cargarJSON(String rutaArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            StringBuilder jsonContent = new StringBuilder();
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                jsonContent.append(linea);
            }
            
              jsonData = new JSONObject(jsonContent.toString());
              System.out.println("JSON cargado correctamente.");
              
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
}        

    public static boolean jsonCargado() {
    return jsonData != null;
}
    
    public static JSONObject getJuego(int gradoIndex, int juegoIndex) {
    if (jsonData == null ) {
        System.err.println("JSON no cargado");
        return null;
    }
    try {
        JSONArray grados = jsonData.getJSONArray("grados");
        JSONObject grado = grados.getJSONObject(gradoIndex);
        return grado.getJSONArray("juegos").getJSONObject(juegoIndex);
    
    } catch (JSONException e) {
        System.err.println("Error al obtener el juego: " + e.getMessage());
        return null;
    }
 }
    
    // Método main para probar la lectura del archivo
    public static void main(String[] args) {
        String ruta = "C:/Users/Usuario/Downloads/BaseDeDatos.json";  
        cargarJSON(ruta); 

        if (jsonCargado()) {
            JSONObject juego = getJuego(0, 0);
            if (juego != null) {
                System.out.println("Juego obtenido:");
                System.out.println("Título: " + juego.getString("titulo"));
                System.out.println("Descripción: " + juego.getString("descripcion"));
                System.out.println("Imagen: " + juego.getString("imagen"));
                System.out.println("Ruta: " + juego.getString("ruta"));
            } else {
                System.out.println("No se pudo obtener el juego.");
            }
        } else {
            System.out.println("El JSON no se cargó correctamente.");
        }
    }    
}  
