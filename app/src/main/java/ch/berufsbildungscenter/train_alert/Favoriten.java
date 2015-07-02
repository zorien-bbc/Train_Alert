package ch.berufsbildungscenter.train_alert;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by zorien on 02.07.2015.
 */
public class Favoriten {

    //Here are the Values which can be set.
    private long id;
    private double longitude;
    private double latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public Favoriten(){
    }


    public Favoriten(LatLng latLong, String name){
        setLatitude(latLong.latitude);
        setLongitude(latLong.longitude);
        setName(name);
    }


    //Getter and Setters for all Values
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return longitude+", "+latitude;
    }
}