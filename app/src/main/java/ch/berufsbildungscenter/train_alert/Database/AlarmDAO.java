package ch.berufsbildungscenter.train_alert.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorien on 06.07.2015.
 */
public class AlarmDAO extends DatabaseDAO {
    public AlarmDAO(Context context) {
        super(context);
    }

    private String[] allColumns = {
            AlarmSQL.COLUMN_ID,
            AlarmSQL.COLUMN_TIME,
            AlarmSQL.COLUMN_VON,
            AlarmSQL.COLUMN_NACH,
            AlarmSQL.COLUMN_AKTIV,
            AlarmSQL.COLUMN_ALARMNR};

    public Alarm createAlarm(Alarm alarm) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(AlarmSQL.COLUMN_TIME, alarm.getTime());
        values.put(AlarmSQL.COLUMN_VON, alarm.getVonOrt());
        values.put(AlarmSQL.COLUMN_NACH, alarm.getNachOrt());
        values.put(AlarmSQL.COLUMN_AKTIV, alarm.getAktiviert());
        values.put(AlarmSQL.COLUMN_ALARMNR, alarm.getAlarmNummer());

        long insertId = db.insert(AlarmSQL.TABLE_ALARM, null, values);
        Cursor cursor = db.query(AlarmSQL.TABLE_ALARM, allColumns, AlarmSQL.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Alarm alr = (Alarm) cursorToAlarm(cursor);
        cursor.close();
        close();
        return alarm;
    }

    public void deleteAlarm(Alarm alarm) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long id = alarm.getId();
        db.delete(AlarmSQL.TABLE_ALARM, AlarmSQL.COLUMN_ID
                + " = " + id, null);
        close();
    }
    public void updateAlarm(Alarm alarm){
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long id = alarm.getId();
        ContentValues values = new ContentValues();
        if(alarm.getAktiviert()==0) {
            values.put(AlarmSQL.COLUMN_AKTIV, 1);
        }else{
            values.put(AlarmSQL.COLUMN_AKTIV, 0);
        }
        db.update(AlarmSQL.TABLE_ALARM, values, AlarmSQL.COLUMN_ID + "=" + id, null);
        close();
    }

    public void removeAllAlarme() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.delete(AlarmSQL.TABLE_ALARM, null, null);
        close();
    }

    public List<Alarm> getAllAlarme() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = db.query(AlarmSQL.TABLE_ALARM,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Alarm alarm = cursorToAlarm(cursor);
            alarms.add(alarm);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return alarms;
    }


    private Alarm cursorToAlarm(Cursor cursor) {
        Alarm alarm = new Alarm();
        alarm.setId(cursor.getLong(0));
        alarm.setTime(cursor.getLong(1));
        alarm.setVonOrt(cursor.getString(2));
        alarm.setNachOrt(cursor.getString(3));
        alarm.setAktiviert(cursor.getInt(4));
        alarm.setAlarmNummer(cursor.getInt(5));
        return alarm;
    }
}
