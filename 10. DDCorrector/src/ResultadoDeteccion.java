
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Map;
/**
 *
 * @author Usuario
 */
public class ResultadoDeteccion {
    private final String mensaje;
    private final Map<Integer, Integer> erroresPorColumna;

    public ResultadoDeteccion(String mensaje, Map<Integer, Integer> erroresPorColumna) {
        this.mensaje = mensaje;
        this.erroresPorColumna = erroresPorColumna;
    }

    public String mensaje() {
        return mensaje;
    }

    public Map<Integer, Integer> getErroresPorColumna() {
        return erroresPorColumna;
    }
}
