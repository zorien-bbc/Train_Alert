package ch.berufsbildungscenter.train_alert;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class Fahrt implements Serializable, Parcelable {

    public String getVonHaltestelle() {
        return vonHaltestelle;
    }

    public void setVonHaltestelle(String vonHaltestelle) {
        this.vonHaltestelle = vonHaltestelle;
    }

    public String getBisHaltestelle() {
        return bisHaltestelle;
    }

    public void setBisHaltestelle(String bisHaltestelle) {
        this.bisHaltestelle = bisHaltestelle;
    }

    public Timestamp getAbfahrt() {
        return abfahrt;
    }

    public void setAbfahrt(Timestamp abfahrt) {
        this.abfahrt = abfahrt;
    }

    public Timestamp getAnkunft() {
        return ankunft;
    }

    public void setAnkunft(Timestamp ankunft) {
        this.ankunft = ankunft;
    }

    public String getTransportmittel() {
        return transportmittel;
    }

    public void setTransportmittel(String transportmittel) {
        this.transportmittel = transportmittel;
    }

    public String getVonGleis() {
        return vonGleis;
    }

    public void setVonGleis(String vonGleis) {
        this.vonGleis = vonGleis;
    }

    public String getBisGleis() {
        return bisGleis;
    }

    public void setBisGleis(String bisGleis) {
        this.bisGleis = bisGleis;
    }

    private String vonHaltestelle;
    private String bisHaltestelle;

    private Timestamp abfahrt;
    private Timestamp ankunft;

    private String transportmittel;
    private String vonGleis;
    private String bisGleis;

    // Parcelling part
    public Fahrt(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        this.vonHaltestelle = data[0];
        this.bisHaltestelle = data[1];
        this.transportmittel = data[2];
        this.vonGleis = data[3];
        this.bisGleis = data[4];
    }

    @?verride
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id,
                this.name,
                this.grade});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
