package ch.berufsbildungscenter.train_alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class VerbindungMap extends ActionBarActivity implements OnMapReadyCallback {

    List<Station> stations;
    int laenge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindung_map);
        Intent intent = getIntent();
        stations = intent.getParcelableArrayListExtra("station");
        laenge  = this.stations.size() - 1;
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapVerbindung);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(this.stations.get(0).getX(), this.stations.get(0).getY()), 13));
        LatLng latLng = new LatLng(this.stations.get(0).getX(), this.stations.get(0).getY());
        PolylineOptions polyline = new PolylineOptions().geodesic(true);
        for (int i = 0; i < this.stations.size(); i++) {
            polyline.add(new LatLng(this.stations.get(i).getX(), this.stations.get(i).getY()));

        }

        map.addMarker(new MarkerOptions()
                .title(this.stations.get(0).getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(new LatLng(this.stations.get(0).getX(), this.stations.get(0).getY())));

        map.addMarker(new MarkerOptions()
                .title(this.stations.get(laenge).getName())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .position(new LatLng(this.stations.get(laenge).getX(), this.stations.get(laenge).getY())));

        map.addPolyline(polyline);

    }


}
