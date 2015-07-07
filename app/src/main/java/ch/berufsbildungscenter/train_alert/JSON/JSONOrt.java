package ch.berufsbildungscenter.train_alert.JSON;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Location.StationenLocation;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONOrt extends AsyncTask<String, Void, List<String>> {

    private static final String LOG_TAG = JSONRoute.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/locations?x=";

    private StationenLocation activity;
    private HttpURLConnection connection;
    private ProgressDialog progressDialog;

    public JSONOrt(StationenLocation activity, ProgressDialog progressDialog) {
        this.activity = activity;
        this.progressDialog = progressDialog;
    }


    @Override
    protected List<String> doInBackground(String... params) {
        List<String> result = null;
        String xCor = params[0];
        String yCor = params[1];


        if (JSONRoute.isNetworkConnectionAvailable(this.activity)) {
            try {
                URL url = new URL(String.format(API_URL + xCor) + "&y=" + yCor);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = JSONParser.parseOrt(connection.getInputStream());

                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
            }

        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (null == result) {
            JSONRoute.noConnectionAlert(this.activity);
        } else {
            this.activity.setData(result);
            progressDialog.dismiss();
        }
    }
}
