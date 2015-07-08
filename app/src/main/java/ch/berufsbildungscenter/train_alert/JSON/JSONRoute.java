package ch.berufsbildungscenter.train_alert.JSON;

import android.app.ProgressDialog;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Verbindung;
import ch.berufsbildungscenter.train_alert.VerbindungenActivity;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONRoute extends JSONConnection {


    private VerbindungenActivity activity;
    private ProgressDialog progressDialog;

    public JSONRoute(VerbindungenActivity activity, ProgressDialog progressDialog) {
        super(activity);
        this.activity = activity;
        this.progressDialog = progressDialog;
        apiUrl =  "http://transport.opendata.ch/v1/connections?from=";
    }

    @Override
    protected void onCostumePostExecute(String jsonString) {
        List<Verbindung> result = null;
        try {
            result = jsonParser.parseConnections(jsonString);
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
