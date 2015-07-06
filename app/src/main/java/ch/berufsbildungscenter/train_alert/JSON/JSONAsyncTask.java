package ch.berufsbildungscenter.train_alert.JSON;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ch.berufsbildungscenter.train_alert.MainActivity;
import ch.berufsbildungscenter.train_alert.R;
import ch.berufsbildungscenter.train_alert.Verbindung;
import ch.berufsbildungscenter.train_alert.VerbindungenActivity;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONAsyncTask extends AsyncTask<String, Void, List<Verbindung>> {

    private static final String LOG_TAG = JSONAsyncTask.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/connections?from=";

    private VerbindungenActivity activity;
    private ProgressDialog progressDialog;
    private HttpURLConnection connection;

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

        SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.activity.getApplicationContext());
        String limit = sprefs.getString("anzVerbindungen", "4");

        if (isNetworkConnectionAvailable(this.activity)) {
            try {
                URL url = new URL(API_URL + stationVon.replaceAll("\\s+", "%20") + "&to=" + stationNach.replaceAll("\\s+", "%20") + "&via=" +
                        stationVia.replaceAll("\\s+", "%20") + "&time=" + time + "&date=" + date + "&isArrivalTime=" + aban + "&limit=" + limit);


                Log.v("URLJSON", url.toString());

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = JSONParser.parseConnections(connection.getInputStream());
                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                    return result;
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
                return result;
            } finally {
            }
        }
        return result;
    }

    public static boolean isNetworkConnectionAvailable(ActionBarActivity activity) {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }

    public static void noConnectionAlert(final ActionBarActivity activity) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.nocon_text));
        alert.setNeutralButton(activity.getString(R.string.ok_text), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
            }
        });
        alert.show();
    }

    @Override
    protected void onPostExecute(List<Verbindung> result) {
        if (result == null) {
            noConnectionAlert(this.activity);
        } else {
            this.progressDialog.dismiss();
            activity.setData(result);
        }
    }
}
