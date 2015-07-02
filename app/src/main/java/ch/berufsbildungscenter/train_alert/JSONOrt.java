package ch.berufsbildungscenter.train_alert;

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

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONOrt extends AsyncTask<String, Void, List<String>> {

    private static final String LOG_TAG = JSONAsyncTask.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/locations?x=";

    private StationenLocation activity;

    public JSONOrt(StationenLocation activity) {
        this.activity = activity;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> result = null;
        String xCor = params[0];
        String yCor = params[1];


        if (isNetworkConnectionAvailable()) {
            try {
                URL url = new URL(String.format(API_URL + xCor)+"&y="+yCor);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = ch.berufsbildungscenter.train_alert.JSONParser.parseOrt(connection.getInputStream());

                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                }

                connection.disconnect();

            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
            }
        }else{

        }

        return result;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();

        return null != networkInfo && networkInfo.isConnected();
    }



    @Override
    protected void onPostExecute(List<String> result) {
        if (!isNetworkConnectionAvailable()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Keine Internet Verbindung");
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        } else {
            if (null == result) {
                Log.e("Daten nicht geladen", "EBOLA");
            } else {
                this.activity.setData(result);
            }
        }
    }


}
