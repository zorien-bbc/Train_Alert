package ch.berufsbildungscenter.train_alert;

import android.app.ProgressDialog;
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
        String time = params[2];
        String date = params[3];


        if (isNetworkConnectionAvailable()) {
            try {
                URL url = new URL(String.format(API_URL + stationVon + "&to=" + stationNach+"&time="+time+"&date="+date));

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

    private List<Verbindung> parseData(InputStream inputStream) throws IOException, JSONException {

        List<Verbindung> result = new ArrayList<Verbindung>();

        String input = readInput(inputStream);
        JSONObject data = new JSONObject(input);

        JSONArray verbindungenJSON = data.getJSONArray("connections");

        JSONObject vonJSON = data.getJSONObject("from");
        JSONObject nachJSON = data.getJSONObject("to");
        JSONObject vonCoordinateJSON = vonJSON.getJSONObject("coordinate");
        JSONObject nachCoordinateJSON = nachJSON.getJSONObject("coordinate");

        for (int i = 0; i < verbindungenJSON.length(); i++) {
            JSONObject verbindungJSON = verbindungenJSON.getJSONObject(i);
            JSONObject vonVerbindungJSON = verbindungJSON.getJSONObject("from");
            JSONObject vonStation = vonVerbindungJSON.getJSONObject("station");
            JSONObject vonCoordinatJSON = vonStation.getJSONObject("coordinate");


            JSONObject nachVerbindungJSON = verbindungJSON.getJSONObject("to");
            JSONObject nachStation = nachVerbindungJSON.getJSONObject("station");
            JSONObject nachCoordinatJSON = nachStation.getJSONObject("coordinate");
            JSONArray reiseAbschnitte = verbindungJSON.getJSONArray("sections");

            Verbindung verbindung = new Verbindung();

            ArrayList<Fahrt> fahrtAbschnitte = new ArrayList<Fahrt>();
            for (int y = 0; y < reiseAbschnitte.length(); y++) {
                JSONObject abschnitt = reiseAbschnitte.getJSONObject(y);

                if(!abschnitt.isNull("journey")) {
                    JSONObject reise = abschnitt.getJSONObject("journey");
                    Fahrt fahrt = new Fahrt();
                    fahrt.setTransportmittel(reise.getString("name"));

                    JSONObject reiseDeparture = abschnitt.getJSONObject("departure");
                    JSONObject reiseArrival = abschnitt.getJSONObject("arrival");

                    fahrt.setAbfahrt(new java.sql.Timestamp(reiseDeparture.getLong("departureTimestamp") * 1000));
                    fahrt.setAnkunft(new java.sql.Timestamp(reiseArrival.getLong("arrivalTimestamp") * 1000));
                    fahrt.setVonGleis(reiseDeparture.getString("platform"));
                    fahrt.setBisGleis(reiseArrival.getString("platform"));
                    fahrt.setVonHaltestelle(reiseDeparture.getJSONObject("station").getString("name"));
                    fahrt.setBisHaltestelle(reiseArrival.getJSONObject("station").getString("name"));
                    fahrtAbschnitte.add(fahrt);
                }
            }
            verbindung.setVonOrt(new Ort(vonStation.getString("id"), vonStation.getString("name"), vonCoordinatJSON.getDouble("x"), vonCoordinatJSON.getDouble("y")));
            verbindung.setNachOrt(new Ort(nachJSON.getString("id"), nachJSON.getString("name"), nachCoordinateJSON.getDouble("x"), nachCoordinateJSON.getDouble("y")));
            verbindung.setGleis(vonVerbindungJSON.getString("platform"));
            verbindung.setZeit(new java.sql.Timestamp(vonVerbindungJSON.getLong("departureTimestamp") * 1000));
            verbindung.setDauer(verbindungJSON.getString("duration"));
            verbindung.setVerbindungen(fahrtAbschnitte);
            verbindung.setTransportmittel(fahrtAbschnitte.get(0).getTransportmittel());
            Log.v(verbindung.getGleis(), verbindung.getZeit() + "EBOLA");

            result.add(verbindung);
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
    protected void onPostExecute(List<Verbindung> result) {
        if (null == result) {
            Log.e("Daten nicht geladen", "EBOLA");
        } else {
            this.progressDialog.dismiss();
            activity.setData(result);
        }
    }
}
