package ch.berufsbildungscenter.train_alert.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 06.07.2015.
 */
public class FavoritenDAO extends DatabaseDAO {
    public FavoritenDAO(Context context) {
        super(context);
    }
    private String[] allColumns = {
            FavoritenSQL.COLUMN_ID,
            FavoritenSQL.COLUMN_NAME,
            FavoritenSQL.COLUMN_LATITUDE,
            FavoritenSQL.COLUMN_LONGTITUDE};

    public Favoriten createFavoriten(Favoriten favoriten) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(FavoritenSQL.COLUMN_NAME, favoriten.getName());
        values.put(FavoritenSQL.COLUMN_LATITUDE, favoriten.getLatitude());
        values.put(FavoritenSQL.COLUMN_LONGTITUDE, favoriten.getLongitude());
        long insertId = db.insert(FavoritenSQL.TABLE_FAVORITEN, null, values);
        Cursor cursor = db.query(FavoritenSQL.TABLE_FAVORITEN, allColumns, FavoritenSQL.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Favoriten fav = (Favoriten) cursorToFavoriten(cursor);
        cursor.close();
        close();
        return favoriten;
    }


    public void deleteFavoriten(Favoriten favoriten) {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long id = favoriten.getId();
        db.delete(FavoritenSQL.TABLE_FAVORITEN, FavoritenSQL.COLUMN_ID
                + " = " + id, null);
        close();
    }

    public List<Favoriten> getAllFavoriten() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Favoriten> favoritens = new ArrayList<Favoriten>();
        Cursor cursor = db.query(FavoritenSQL.TABLE_FAVORITEN,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Favoriten favoriten = cursorToFavoriten(cursor);
            favoritens.add(favoriten);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return favoritens;
    }

    public void removeAllFavoriten() {
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.delete(FavoritenSQL.TABLE_FAVORITEN, null, null);
        close();
    }

    private Favoriten cursorToFavoriten(Cursor cursor) {
        Favoriten favoriten = new Favoriten();
        favoriten.setId(cursor.getLong(0));
        favoriten.setName(cursor.getString(1));
        favoriten.setLongitude(cursor.getDouble(2));
        favoriten.setLatitude(cursor.getDouble(3));
        return favoriten;
    }
}
