package ch.berufsbildungscenter.train_alert.Listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Favoriten;
import ch.berufsbildungscenter.train_alert.Database.FavoritenDatabase;
import ch.berufsbildungscenter.train_alert.JSON.Ort;

/**
 * Created by zorien on 02.07.2015.
 */
public class FavoritenListener implements View.OnClickListener{
    FavoritenDatabase favoritenDatabase;
    Activity activity;
    Ort ort;
    Toast toast;
    boolean exist;
    public FavoritenListener(Activity activity, Ort ort){
        this.activity = activity;
        this.ort = ort;

    }

    @Override
    public void onClick(View view) {
        favoritenDatabase = new FavoritenDatabase(this.activity.getApplicationContext());
        try {
            //Try to open the DB connection
            favoritenDatabase.open();
        } catch (SQLException e) {
            Log.v("DATABASETEST", e.toString());
        }
        LatLng latLng = new LatLng(ort.getX(),ort.getY());
        Favoriten favoriten = new Favoriten(latLng,ort.getName());
        List<Favoriten> favoritens = favoritenDatabase.getAllFavoriten();
        for(int i =0;i< favoritens.size() ;i++) {
            if (favoritens.get(i).getName().equals(ort.getName())){
                toast.makeText(this.activity.getApplicationContext(), ort.getName() + " geh\u00f6rt bereits zu ihren Favoriten", Toast.LENGTH_SHORT).show();
                exist = true;
            }
        }if(exist==false) {
            favoritenDatabase.createFavoriten(favoriten);
            toast.makeText(this.activity.getApplicationContext(), ort.getName() + " wurde zu Favoriten hinzugef\u00fcgt", Toast.LENGTH_SHORT).show();
        }
        favoritenDatabase.close();

    }



}
