package ch.berufsbildungscenter.train_alert;

import java.security.Timestamp;
import java.sql.Time;
import java.util.List;

/**
 * Created by zfehrn on 17.06.2015.
 */
public class Verbindung {


    public Ort getVonOrt() {
        return vonOrt;
    }

    public void setVonOrt(Ort vonOrt) {
        this.vonOrt = vonOrt;
    }

    public Ort getNachOrt() {
        return nachOrt;
    }

    public void setNachOrt(Ort nachOrt) {
        this.nachOrt = nachOrt;
    }

    public String getGleis() {
        return gleis;
    }

    public void setGleis(String gleis) {
        this.gleis = gleis;
    }

    public String getTransportmittel() {
        return transportmittel;
    }

    public void setTransportmittel(String transportmittel) {
        this.transportmittel = transportmittel;
    }

    public Timestamp getZeit() {
        return zeit;
    }

    public void setZeit(Timestamp zeit) {
        this.zeit = zeit;
    }

    public Timestamp getDauer() {
        return dauer;
    }

    public void setDauer(Timestamp dauer) {
        this.dauer = dauer;
    }

    public List<Fahrt> getVerbindungen() {
        return verbindungen;
    }

    public void setVerbindungen(List<Fahrt> verbindungen) {
        this.verbindungen = verbindungen;
    }

    private Ort vonOrt;
    private Ort nachOrt;

    private String gleis;
    private String transportmittel;
    private Timestamp zeit;
    private Timestamp dauer;

    private List<Fahrt> verbindungen;

}
