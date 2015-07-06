package ch.berufsbildungscenter.train_alert.JSON;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ch.berufsbildungscenter.train_alert.SuchHilfe;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONSuchHilfe extends AsyncTask<String, Void, List<String>> {

    private static final String LOG_TAG = JSONSuchHilfe.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/locations?query=";

    private SuchHilfe activity;
    private HttpURLConnection connection;

    public JSONSuchHilfe(SuchHilfe activity) {
        this.activity = activity;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> result = null;
        String stationVon = params[0].toString();


        if (JSONAsyncTask.isNetworkConnectionAvailable(this.activity)) {
            try {
                URL url = new URL(API_URL + stationVon.replaceAll("\\s+", "%20"));

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = JSONParser.parseSearch(connection.getInputStream());

                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
            } finally {
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (null == result) {
            JSONAsyncTask.noConnectionAlert(this.activity);
        } else {
            activity.setData(result);
        }
    }
}
