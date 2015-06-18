package ch.berufsbildungscenter.train_alert;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class Ort {

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

}
