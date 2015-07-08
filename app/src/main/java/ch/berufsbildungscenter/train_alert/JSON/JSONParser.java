package ch.berufsbildungscenter.train_alert.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Verbindung;

/**
 * Created by zorien on 26.06.2015.
 */
public class JSONParser {
    public static List<Verbindung> parseConnections(String jsonString) throws IOException, JSONException {

        List<Verbindung> result = new ArrayList<Verbindung>();


        JSONObject data = new JSONObject(jsonString);

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
            ArrayList<Station> stationen = new ArrayList<Station>();
            for (int y = 0; y < reiseAbschnitte.length(); y++) {
                JSONObject abschnitt = reiseAbschnitte.getJSONObject(y);

                if (!abschnitt.isNull("journey")) {
                    JSONObject reise = abschnitt.getJSONObject("journey");
                    JSONArray passList = reise.getJSONArray("passList");
                    for (int zaehler = 0; zaehler < passList.length(); zaehler++) {

                        JSONObject stations = passList.getJSONObject(zaehler);
                        JSONObject passStation = stations.getJSONObject("station");
                        JSONObject coordinate = passStation.getJSONObject("coordinate");
                        Station station = new Station(passStation.getString("id"), passStation.getString("name"), coordinate.getDouble("x"), coordinate.getDouble("y"));
                        stationen.add(station);
                    }
                    Fahrt fahrt = new Fahrt();
                    fahrt.setTransportmittel(reise.getString("name"));

                    JSONObject reiseDeparture = abschnitt.getJSONObject("departure");
                    JSONObject reiseArrival = abschnitt.getJSONObject("arrival");

                    JSONObject prognosisDeparture = reiseDeparture.getJSONObject("prognosis");
                    JSONObject prognosisArrival = reiseArrival.getJSONObject("prognosis");

                    String platformDeparture;
                    String platformArrival;
                    if(!prognosisDeparture.isNull("platform")) {
                        platformDeparture = prognosisDeparture.getString("platform");
                    } else {
                        platformDeparture = reiseDeparture.getString("platform");
                    }

                    if(!prognosisArrival.isNull("platform")) {
                        platformArrival = prognosisArrival.getString("platform");
                    } else {
                        platformArrival = reiseArrival.getString("platform");
                    }

                    fahrt.setAbfahrt(new java.sql.Timestamp(reiseDeparture.getLong("departureTimestamp") * 1000));
                    fahrt.setAnkunft(new java.sql.Timestamp(reiseArrival.getLong("arrivalTimestamp") * 1000));
                    fahrt.setVonGleis(platformDeparture);
                    fahrt.setBisGleis(platformArrival);
                    fahrt.setVonHaltestelle(reiseDeparture.getJSONObject("station").getString("name"));
                    fahrt.setBisHaltestelle(reiseArrival.getJSONObject("station").getString("name"));


                    fahrtAbschnitte.add(fahrt);
                }
            }
            verbindung.setVonOrt(new Ort(vonStation.getString("id"), vonStation.getString("name"), vonCoordinatJSON.getDouble("x"), vonCoordinatJSON.getDouble("y")));
            verbindung.setNachOrt(new Ort(nachJSON.getString("id"), nachJSON.getString("name"), nachCoordinateJSON.getDouble("x"), nachCoordinateJSON.getDouble("y")));
            verbindung.setGleis(fahrtAbschnitte.get(0).getVonGleis());
            verbindung.setZeit(new java.sql.Timestamp(vonVerbindungJSON.getLong("departureTimestamp") * 1000));
            verbindung.setZeitAn(new java.sql.Timestamp(nachVerbindungJSON.getLong("arrivalTimestamp") * 1000));
            verbindung.setDauer(verbindungJSON.getString("duration"));
            verbindung.setTransportmittel(fahrtAbschnitte.get(0).getTransportmittel());
            verbindung.setVerbindungen(fahrtAbschnitte);
            verbindung.setStations(stationen);


            result.add(verbindung);
        }
        return result;
    }


    public static List<String> parseSearch(String jsonString) throws IOException, JSONException {

        List<String> result = new ArrayList<String>();

        JSONObject data = new JSONObject(jsonString);

        JSONArray verbindungenJSON = data.getJSONArray("stations");
        Verbindung verbindung = new Verbindung();

        for (int i = 0; i < verbindungenJSON.length(); i++) {
            JSONObject verbindungJSON = verbindungenJSON.getJSONObject(i);
            result.add(verbindungJSON.getString("name"));
        }

        return result;
    }

    public static List<String> parseOrt(String jsonString) throws IOException, JSONException {

        List<String> result = new ArrayList<String>();

        JSONObject data = new JSONObject(jsonString);

        JSONArray verbindungenJSON = data.getJSONArray("stations");

        for (int i = 0; i < verbindungenJSON.length(); i++) {
            JSONObject verbindungJSON = verbindungenJSON.getJSONObject(i);
            result.add(verbindungJSON.getString("name"));
        }

        return result;
    }

    private static String readInput(InputStream inputStream) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String line;
        while (null != (line = bufferedReader.readLine())) {
            resultBuilder.append(line);
        }

        return resultBuilder.toString();
    }

}

