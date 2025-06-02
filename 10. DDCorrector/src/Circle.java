
import org.opencv.core.Point;
import org.opencv.core.Rect;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */
public class Circle {
    public Point centro;
    public int radio;

    public Circle(Point centro, int radio) {
        this.centro = centro;
        this.radio = radio;
    }
    
    public Rect toRect() {
        return new Rect((int)(centro.x - radio), (int)(centro.y - radio), radio * 2, radio * 2);
    }
}
