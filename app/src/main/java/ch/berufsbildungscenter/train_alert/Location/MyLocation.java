package ch.berufsbildungscenter.train_alert.Location;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;


public class MyLocation {
    Activity activity;
    double latitude;

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    double longtitude;
    Location location = null;

    public MyLocation(Activity activity) {
        this.activity = activity;
        LocationManager locationManager = (LocationManager) this.activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        if (locationManager.getLastKnownLocation(provider) == null) {
            String toastText = null;
            if (locationManager.isProviderEnabled(provider)) {
                toastText = "Fehler! Position konnte nicht abgerufen werden";
            } else {
                toastText = "GPS einschalten!";
            }
            Toast toast = Toast.makeText(this.activity.getApplicationContext(), toastText, Toast.LENGTH_LONG);
            toast.show();
        } else {
            this.location = locationManager.getLastKnownLocation(provider);
            latitude = location.getLatitude();
            longtitude = location.getLongitude();
        }
    }
}
