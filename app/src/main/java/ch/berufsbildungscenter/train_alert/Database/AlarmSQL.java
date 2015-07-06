package ch.berufsbildungscenter.train_alert.Database;

/**
 * Created by zorien on 02.07.2015.
 */
public class AlarmSQL {


    public static final String TABLE_ALARM = "alarm";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_VON = "von";
    public static final String COLUMN_NACH = "nach";
    public static final String COLUMN_AKTIV = "aktiv";
    public static final String COLUMN_ALARMNR = "alarmnr";


    public static String createTableAlarm() {
        return "CREATE TABLE " + TABLE_ALARM + " ("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_TIME + " INTEGER, "
                + COLUMN_VON + " TEXT, "
                + COLUMN_NACH + " TEXT,"
                + COLUMN_AKTIV + " INTEGER,"
                + COLUMN_ALARMNR + " INTEGER);";
    }

}