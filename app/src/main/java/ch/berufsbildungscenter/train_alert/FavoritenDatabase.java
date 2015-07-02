package ch.berufsbildungscenter.train_alert;

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
public class FavoritenDatabase {
    private SQLiteDatabase database;
    private FavoritenSQLiteHelper dbHelper;
    private String[] allColumns = {
            FavoritenSQLiteHelper.COLUMN_ID,
            FavoritenSQLiteHelper.COLUMN_NAME,
            FavoritenSQLiteHelper.COLUMN_LATITUDE,
            FavoritenSQLiteHelper.COLUMN_LONGTITUDE};

    public FavoritenDatabase(Context context){
        dbHelper = new FavoritenSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    /*
     */
    public Favoriten createFavoriten(Favoriten favoriten){
        ContentValues values = new ContentValues();
        values.put(FavoritenSQLiteHelper.COLUMN_NAME, favoriten.getName());
        values.put(FavoritenSQLiteHelper.COLUMN_LATITUDE, favoriten.getLatitude());
        values.put(FavoritenSQLiteHelper.COLUMN_LONGTITUDE, favoriten.getLongitude());
        long insertId = database.insert(FavoritenSQLiteHelper.TABLE_FAVORITEN, null, values);
        Cursor cursor = database.query(FavoritenSQLiteHelper.TABLE_FAVORITEN, allColumns, FavoritenSQLiteHelper.COLUMN_ID + " = " + insertId, null,null,null,null);
        cursor.moveToFirst();
        Favoriten fav = (Favoriten) cursorToFavoriten(cursor);
        cursor.close();
        return favoriten;
    }

    /*
     */
    public void deleteFavoriten(Favoriten favoriten) {
        long id = favoriten.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(FavoritenSQLiteHelper.TABLE_FAVORITEN, FavoritenSQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /*
     */
    public List<Favoriten> getAllFavoriten() {
        List<Favoriten> favoritens = new ArrayList<Favoriten>();
        Cursor cursor = database.query(FavoritenSQLiteHelper.TABLE_FAVORITEN,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Favoriten favoriten = cursorToFavoriten(cursor);
            favoritens.add(favoriten);
            cursor.moveToNext();
        }
        cursor.close();
        return favoritens;
    }

    public void removeAllFavoriten(){
        database.delete(FavoritenSQLiteHelper.TABLE_FAVORITEN,null,null);

    }

    /*
    Converts a DB Value to a real WayPoint Object
     */
    private Favoriten cursorToFavoriten(Cursor cursor) {
        Favoriten favoriten = new Favoriten();
        favoriten.setId(cursor.getLong(0));
        favoriten.setName(cursor.getString(1));
        favoriten.setLongitude(cursor.getDouble(2));
        favoriten.setLatitude(cursor.getDouble(3));
        return favoriten;
    }
}