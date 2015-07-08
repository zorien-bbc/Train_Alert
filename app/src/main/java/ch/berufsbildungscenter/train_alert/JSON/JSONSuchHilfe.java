package ch.berufsbildungscenter.train_alert.JSON;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import ch.berufsbildungscenter.train_alert.SuchHilfe;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONSuchHilfe extends JSONConnection {

    private SuchHilfe activity;


    public JSONSuchHilfe(SuchHilfe activity) {
        super(activity);
        this.activity = activity;
        apiUrl = "http://transport.opendata.ch/v1/locations?query=";
    }


    @Override
    protected void onCostumePostExecute(String jsonString) {
        List<String> result = null;
        try {
            result = jsonParser.parseSearch(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.setData(result);
    }
}
