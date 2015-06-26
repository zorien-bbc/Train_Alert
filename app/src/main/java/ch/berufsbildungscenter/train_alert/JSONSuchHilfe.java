package ch.berufsbildungscenter.train_alert;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zorien on 18.06.2015.
 */
public class JSONSuchHilfe extends AsyncTask<String, Void, List<String>> {

    private static final String LOG_TAG = JSONSuchHilfe.class.getCanonicalName();

    private static final String API_URL = "http://transport.opendata.ch/v1/locations?query=";

    private SuchHilfe activity;

    public JSONSuchHilfe(SuchHilfe activity) {
        this.activity = activity;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> result = null;
        String stationVon = params[0].toString();


        if (isNetworkConnectionAvailable()) {
            try {
                URL url = new URL(API_URL + stationVon.replaceAll("\\s+", "%20"));

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (HttpURLConnection.HTTP_OK == responseCode) {
                    result = parseData(connection.getInputStream());

                } else {
                    Log.e(LOG_TAG, String.format("An error occurred while loading the data in the background. HTTP status: %d", responseCode));
                }

                connection.disconnect();

            } catch (Exception e) {
                Log.e(LOG_TAG, "An error occurred while loading the data in the background", e);
            }
        }

        return result;
    }

    private boolean isNetworkConnectionAvailable() {
        ConnectivityManager connectivityService = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityService.getActiveNetworkInfo();

        return null != networkInfo && networkInfo.isConnected();
    }

    private List<String> parseData(InputStream inputStream) throws IOException, JSONException {

        List<String> result = new ArrayList<String>();

        String input = readInput(inputStream);
        JSONObject data = new JSONObject(input);

        JSONArray verbindungenJSON = data.getJSONArray("stations");

        for (int i = 0; i < verbindungenJSON.length(); i++) {
            JSONObject verbindungJSON = verbindungenJSON.getJSONObject(i);
                result.add(verbindungJSON.getString("name"));
        }

        return result;
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
    @Override
    protected void onPostExecute(List<String> result) {
        if (null == result) {
            Log.e(LOG_TAG, "Daten nicht geladen");
        } else {
            activity.setData(result);
        }
    }


}
