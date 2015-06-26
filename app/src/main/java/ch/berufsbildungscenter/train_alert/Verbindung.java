package ch.berufsbildungscenter.train_alert;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by zfehrn on 17.06.2015.
 */
public class Verbindung implements Serializable {


    public ArrayList<Fahrt> getVerbindungen() {
        return verbindungen;
    }

    public void setVerbindungen(ArrayList<Fahrt> verbindungen) {
        this.verbindungen = verbindungen;
    }


    public Timestamp getZeit() {
        return zeit;
    }

    public void setZeit(Timestamp zeit) {
        this.zeit = zeit;
    }

    public String getTransportmittel() {
        return transportmittel;
    }

    public void setTransportmittel(String transportmittel) {
        this.transportmittel = transportmittel;
    }

    public String getGleis() {
        return gleis;
    }

    public void setGleis(String gleis) {
        this.gleis = gleis;
    }

    public Ort getNachOrt() {
        return nachOrt;
    }

    public void setNachOrt(Ort nachOrt) {
        this.nachOrt = nachOrt;
    }

    public Ort getVonOrt() {
        return vonOrt;
    }

    public void setVonOrt(Ort vonOrt) {
        this.vonOrt = vonOrt;
    }

    private Ort vonOrt;
    private Ort nachOrt;

    private String gleis;
    private String transportmittel;
    private java.sql.Timestamp zeit;
    private java.sql.Timestamp zeitAn;

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    private String dauer;

    private ArrayList<Fahrt> verbindungen;

    public Timestamp getZeitAn() {
        return zeitAn;
    }

    public void setZeitAn(Timestamp zeitAn) {
        this.zeitAn = zeitAn;
    }
}
