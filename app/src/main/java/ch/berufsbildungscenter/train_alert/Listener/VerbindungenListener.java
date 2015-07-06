package ch.berufsbildungscenter.train_alert.Listener;

import android.app.DialogFragment;
import android.location.Location;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

import ch.berufsbildungscenter.train_alert.Fragment.DatePickerFragment;
import ch.berufsbildungscenter.train_alert.MainActivity;
import ch.berufsbildungscenter.train_alert.R;
import ch.berufsbildungscenter.train_alert.Fragment.TimePickerFragment;

/**
 * Created by zorien on 17.06.2015.
 */
public class VerbindungenListener implements View.OnClickListener {
    final Calendar c = Calendar.getInstance();
    Button button;
    MainActivity activity;
    GoogleMap map;
    final int REQUEST_CODE = 0;

    public VerbindungenListener(MainActivity activity, Button button){
        this.button = button;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if(this.button.getId() == R.id.buttonTime){
            DialogFragment newFragment = new TimePickerFragment(this.button);
            newFragment.show(this.activity.getFragmentManager(), "Zeit auswaehlen");
        }else if(this.button.getId() == R.id.buttonDate){
            DialogFragment newFragment = new DatePickerFragment(this.button,this.activity);
            newFragment.show(this.activity.getFragmentManager(), "Datum auswaehlen");
        }else if(this.button.getId() == R.id.button){

        }
    }
    public LatLng myPostion(GoogleMap map){
        map.setMyLocationEnabled(true);
        Location location = map.getMyLocation();
        LatLng myPosition = new LatLng(location.getLatitude(),location.getLongitude());

        return myPosition;
    }
    private static final long REPEAT_TIME = 1000 * 30;






}
