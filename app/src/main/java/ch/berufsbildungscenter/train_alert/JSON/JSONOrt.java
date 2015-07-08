package ch.berufsbildungscenter.train_alert.JSON;

import android.app.ProgressDialog;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Location.StationenLocation;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONOrt extends JSONConnection {


    private StationenLocation activity;
    private ProgressDialog progressDialog;

    public JSONOrt(StationenLocation activity, ProgressDialog progressDialog) {
        super(activity);
        this.activity = activity;
        this.progressDialog = progressDialog;
        apiUrl ="http://transport.opendata.ch/v1/locations?x=";
    }


    @Override
    protected void onCostumePostExecute(String jsonString) {
        List<String> result = null;
        try {
            result = jsonParser.parseOrt(jsonString);
            this.progressDialog.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.setData(result);
        this.progressDialog.dismiss();
    }
}
