package ch.berufsbildungscenter.train_alert;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zorien on 18.06.2015.
 */
public class Station implements Serializable, Parcelable {

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

    public Station(String id, String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public static final Parcelable.Creator<Station> CREATOR = new Parcelable.Creator<Station>() {
        public Station createFromParcel(Parcel pc) {
            return new Station(pc);
        }

        public Station[] newArray(int size) {
            return new Station[size];
        }
    };
    public Station(Parcel pc) {
        name = pc.readString();
        id = pc.readString();

        x = (Double)pc.readSerializable();
        y = (Double)pc.readSerializable();

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);

        dest.writeSerializable(x);
        dest.writeSerializable(y);
    }
}
