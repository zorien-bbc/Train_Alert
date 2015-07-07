package ch.berufsbildungscenter.train_alert.JSON;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;

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
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String info;

    // Parcelling part
    public Fahrt() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(vonHaltestelle);
        pc.writeString(bisHaltestelle);
        pc.writeString(transportmittel);

        pc.writeSerializable(abfahrt);
        pc.writeSerializable(ankunft);

        pc.writeString(vonGleis);
        pc.writeString(bisGleis);
    }

    public static final Parcelable.Creator<Fahrt> CREATOR = new Parcelable.Creator<Fahrt>() {
        public Fahrt createFromParcel(Parcel pc) {
            return new Fahrt(pc);
        }

        public Fahrt[] newArray(int size) {
            return new Fahrt[size];
        }
    };

    public Fahrt(Parcel pc) {
        vonHaltestelle = pc.readString();
        bisHaltestelle = pc.readString();
        transportmittel = pc.readString();

        abfahrt = (Timestamp) pc.readSerializable();
        ankunft = (Timestamp) pc.readSerializable();

        vonGleis = pc.readString();
        bisGleis = pc.readString();
    }
}