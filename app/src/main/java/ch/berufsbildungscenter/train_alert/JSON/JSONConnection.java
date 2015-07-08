package ch.berufsbildungscenter.train_alert.JSON;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.berufsbildungscenter.train_alert.R;
import ch.berufsbildungscenter.train_alert.StartScreen;


/**
 * Created by zorien on 02.07.2015.
 */
public abstract class JSONConnection extends AsyncTask<String, Void, String> {

    private URL url;
    protected JSONParser jsonParser;
    private static final String LOG_TAG = JSONConnection.class.getCanonicalName();
    private Context mContext = null;
    private Activity activity;
    protected String apiUrl = "";
    private String stadt;

    public JSONConnection(Activity activity) {
        this.activity = activity;
        jsonParser = new JSONParser();
    }


    @Override
    protected String doInBackground(String... params) {
        String result = "";
        String jsonTyp = params[0];
        SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.activity.getApplicationContext());
        String limit = sprefs.getString("anzVerbindungen", "4");
        HttpURLConnection connection = null;

        if (isNetworkConnectionAvailable()) {
            try {
                if (jsonTyp.equals("verbindung")) {
                    String stationVon = params[1];
                    String stationNach = params[2];
                    String stationVia = params[3];
                    String time = params[4];
                    String date = params[5];
                    String aban = params[6];
                    url = new URL((apiUrl + stationVon.replaceAll("\\s+", "%20") + "&to=" + stationNach.replaceAll("\\s+", "%20") + "&via=" +
                            stationVia.replaceAll("\\s+", "%20") + "&time=" + time + "&date=" + date + "&isArrivalTime=" + aban + "&limit=" + limit));
                } else if (jsonTyp.equals("suche")) {
                    String stationVon = params[1];
                    url = new URL(apiUrl + stationVon.replaceAll("\\s+", "%20"));
                } else if (jsonTyp.equals("ort")) {
                    String xCor = params[1];
                    String yCor = params[2];
                    url = new URL(String.format(apiUrl + xCor) + "&y=" + yCor);
                }
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();

                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = readInput(connection.getInputStream());
                } else {

                    Log.e(LOG_TAG, String.format("Ein Fehler ist aufgetreten. Service nicht verfugbar.", responseCode));

                    noConnectionAlert(this.activity);
                }

            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
                return result;
            } finally {
                connection.disconnect();
            }
        }

        if (result == null) {
            //result can be null
            return null;
        } else {

            return result;
        }
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }


    private String readInput(InputStream inputStream) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line;
        while (null != (line = bufferedReader.readLine())) {
            resultBuilder.append(line);
        }
        return resultBuilder.toString();
    }

    public static void noConnectionAlert(final Activity activity) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle(activity.getString(R.string.nocon_text));
        alert.setNeutralButton(activity.getString(R.string.ok_text), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(activity.getApplicationContext(), StartScreen.class);
                activity.startActivity(intent);
            }
        });
        alert.show();
    }

    protected abstract void onCostumePostExecute(String jsonString);

    protected void onPostExecute(String jsonString) {
        if (null == jsonString) {
            //Exception already catched above
        } else {
            onCostumePostExecute(jsonString);
        }
    }


}