package ch.berufsbildungscenter.train_alert.Database;

/**
 * Created by zorien on 03.07.2015.
 */

public class Alarm {


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    long time;
    String vonOrt;
    String nachOrt;

    public int getAlarmNummer() {
        return alarmNummer;
    }

    public void setAlarmNummer(int alarmNummer) {
        this.alarmNummer = alarmNummer;
    }

    int alarmNummer;

    public int getAktiviert() {
        return aktiviert;
    }

    public void setAktiviert(int aktiviert) {
        this.aktiviert = aktiviert;
    }

    int aktiviert;

    public long getId() {
        return id;
    }

    public void setId(long  id) {
        this.id = id;
    }

    long id;

    public Alarm() {
    }

    public Alarm(long time, String von,String nach, int aktiviert, int alarmNummer) {
        setTime(time);
        setVonOrt(von);
        setNachOrt(nach);
        setAktiviert(aktiviert);
        setAlarmNummer(alarmNummer);
    }



    public String getVonOrt() {
        return vonOrt;
    }

    public void setVonOrt(String vonOrt) {
        this.vonOrt = vonOrt;
    }

    public String getNachOrt() {
        return nachOrt;
    }

    public void setNachOrt(String nachOrt) {
        this.nachOrt = nachOrt;
    }
}
