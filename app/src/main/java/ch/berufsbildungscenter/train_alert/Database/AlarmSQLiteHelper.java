package ch.berufsbildungscenter.train_alert.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by zorien on 02.07.2015.
 *
 */
public class AlarmSQLiteHelper extends SQLiteOpenHelper {

    //Default Settings to work with the DB
    public static final String TABLE_ALARM = "alarm";
    //Collumns of the DB
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME= "time";
    public static final String COLUMN_VON = "von";
    public static final String COLUMN_NACH = "nach";
    public static final String COLUMN_AKTIV = "aktiv";


    //DB Version
    private static final int DATABASE_VERSION = 1;

    //SQL Query to create a Table in the DB
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ALARM + " ("
                    +COLUMN_ID + " integer primary key autoincrement, "
                    +COLUMN_TIME + " INTEGER, "
                    +COLUMN_VON + " TEXT, "
                    +COLUMN_NACH + " TEXT,"
                    +COLUMN_AKTIV +" INTEGER);";

    //Default Constructor which calls the real SQP Helper (with super(...))
    AlarmSQLiteHelper(Context context) {
        super(context, "alarm", null, DATABASE_VERSION);
    }

    /*
    If the DB must be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DICTIONARY_TABLE_CREATE);
    }

    /*
    If the DB needs an upgrade (called when the DATABASE_VERSION ist changed)
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
