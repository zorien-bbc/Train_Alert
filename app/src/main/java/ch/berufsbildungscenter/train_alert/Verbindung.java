package ch.berufsbildungscenter.train_alert;

/**
 * Created by zfehrn on 17.06.2015.
 */
public class Verbindung {

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    private String beschreibung;
    private String zeit;
    private String dauer;


    public Verbindung(String beschreibung, String zeit, String dauer) {
        this.beschreibung = beschreibung;
        this.zeit = zeit;
        this.dauer = dauer;
    }
}
