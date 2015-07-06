package ch.berufsbildungscenter.train_alert.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by zorien on 06.07.2015.
 */
public class DatabaseDAO {

    protected DatabaseManager dbHelper;
    protected SQLiteDatabase db;

    public DatabaseDAO(Context context) {
        dbHelper = new DatabaseManager(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
