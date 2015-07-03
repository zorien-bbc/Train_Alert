package ch.berufsbildungscenter.train_alert.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorien on 02.07.2015.
 */
public class AlarmDatabase {
    private SQLiteDatabase database;
    private AlarmSQLiteHelper dbHelper;
    private String[] allColumns = {
            AlarmSQLiteHelper.COLUMN_ID,
            AlarmSQLiteHelper.COLUMN_TIME,
            AlarmSQLiteHelper.COLUMN_VON,
            AlarmSQLiteHelper.COLUMN_NACH,
            AlarmSQLiteHelper.COLUMN_AKTIV};

    public AlarmDatabase(Context context) {
        dbHelper = new AlarmSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /*
     */
    public Alarm createAlarm(Alarm alarm){
        ContentValues values = new ContentValues();
        values.put(AlarmSQLiteHelper.COLUMN_TIME, alarm.getTime());
        values.put(AlarmSQLiteHelper.COLUMN_VON, alarm.getVonOrt());
        values.put(AlarmSQLiteHelper.COLUMN_NACH, alarm.getNachOrt());
        values.put(AlarmSQLiteHelper.COLUMN_AKTIV, alarm.getAktiviert());
        long insertId = database.insert(AlarmSQLiteHelper.TABLE_ALARM, null, values);
        Cursor cursor = database.query(AlarmSQLiteHelper.TABLE_ALARM, allColumns, AlarmSQLiteHelper.COLUMN_ID + " = " + insertId, null,null,null,null);
        cursor.moveToFirst();
        Alarm alr = (Alarm) cursorToAlarm(cursor);
        cursor.close();
        return alarm;
    }
    /*
     */
    public void deleteAlarm(Alarm alarm) {
        long id = alarm.getId();
        database.delete(AlarmSQLiteHelper.TABLE_ALARM, AlarmSQLiteHelper.COLUMN_ID
                + " = " + id, null);

    }

    /*
     */
    public List<Alarm> getAllAlarme() {
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = database.query(AlarmSQLiteHelper.TABLE_ALARM,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Alarm alarm = cursorToAlarm(cursor);
            alarms.add(alarm);
            cursor.moveToNext();
        }
        cursor.close();
        return alarms;
    }

    public void removeAllAlarme() {
        database.delete(AlarmSQLiteHelper.TABLE_ALARM, null, null);

    }

    /*
     */
    private Alarm cursorToAlarm(Cursor cursor) {
        Alarm alarm = new Alarm();
        alarm.setId(cursor.getLong(0));
        alarm.setTime(cursor.getLong(1));
        alarm.setVonOrt(cursor.getString(2));
        alarm.setNachOrt(cursor.getString(3));
        alarm.setAktiviert(cursor.getInt(4));
        return alarm;
    }
}