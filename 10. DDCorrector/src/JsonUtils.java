/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
public class JsonUtils {
    public static List<String> obtenerRespuestasCorrectas(String codigo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("src/data/respuestas_correctas.json")));
            JSONObject json = new JSONObject(contenido);
            if (json.has(codigo)) {
                List<String> respuestas = new ArrayList<>();
                for (Object obj : json.getJSONArray(codigo)) {
                    respuestas.add(obj.toString());
                }
                return respuestas;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
