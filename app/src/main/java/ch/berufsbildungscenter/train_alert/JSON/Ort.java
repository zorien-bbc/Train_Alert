package ch.berufsbildungscenter.train_alert.JSON;

import java.io.Serializable;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class Ort implements Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private String id;
    private String name;
    private double x;
    private double y;

    public Ort(String id, String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.id = id;
    }
}
