package ch.berufsbildungscenter.train_alert;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class Fahrt implements Serializable {

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
}
