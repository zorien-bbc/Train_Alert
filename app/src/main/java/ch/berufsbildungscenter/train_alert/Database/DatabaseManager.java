package ch.berufsbildungscenter.train_alert.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zorien on 06.07.2015.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "TrainAlert.db";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(AlarmSQL.createTableAlarm());
        db.execSQL(FavoritenSQL.createTableFavoriten());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + AlarmSQL.TABLE_ALARM);
        db.execSQL("DROP TABLE IF EXISTS " + FavoritenSQL.TABLE_FAVORITEN);
        // create new tables
        onCreate(db);
    }
}
