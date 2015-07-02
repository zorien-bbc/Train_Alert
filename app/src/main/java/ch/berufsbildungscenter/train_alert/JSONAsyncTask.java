package ch.berufsbildungscenter.train_alert;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONAsyncTask extends AsyncTask<String, Void, List<Verbindung>> {

    private static final String LOG_TAG = JSONAsyncTask.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/connections?from=";

    private VerbindungenActivity activity;
    private ProgressDialog progressDialog;

    public JSONAsyncTask(VerbindungenActivity activity, ProgressDialog progressDialog) {
        this.activity = activity;
        this.progressDialog = progressDialog;
    }

    @Override
    protected List<Verbindung> doInBackground(String... params) {
        List<Verbindung> result = null;
        String stationVon = params[0];
        String stationNach = params[1];
        String stationVia = params[2];
        String time = params[3];
        String date = params[4];
        String aban = params[5];

        if (isNetworkConnectionAvailable()) {
            try {
                URL url = new URL(API_URL + stationVon.replaceAll("\\s+", "%20") + "&to=" + stationNach.replaceAll("\\s+", "%20") + "&via=" +
                        stationVia.replaceAll("\\s+", "%20") + "&time=" + time + "&date=" + date + "&isArrivalTime=" + aban);


                Log.v("URLJSON", url.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = JSONParser.parseConnections(connection.getInputStream());

                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                }

                connection.disconnect();

            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
            }//finally
        }

        return result;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();

        return null != networkInfo && networkInfo.isConnected();
    }



    @Override
    protected void onPostExecute(List<Verbindung> result) {
        if (result == null) {

        } else {
            this.progressDialog.dismiss();
            activity.setData(result);
        }
    }
}
