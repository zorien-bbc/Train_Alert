package ch.berufsbildungscenter.train_alert.Database;

/**
 * Created by zorien on 02.07.2015.
 */
public class FavoritenSQL {

    public static final String TABLE_FAVORITEN = "favoriten";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGTITUDE = "longtitude";


    public static  String createTableFavoriten(){
            return "CREATE TABLE " + TABLE_FAVORITEN + " ("
                    +COLUMN_ID + " integer primary key autoincrement, "
                    +COLUMN_NAME + " TEXT, "
                    +COLUMN_LATITUDE + " TEXT, "
                    +COLUMN_LONGTITUDE + " TEXT);";
    }

}