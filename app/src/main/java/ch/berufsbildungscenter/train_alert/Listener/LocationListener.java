package ch.berufsbildungscenter.train_alert.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import ch.berufsbildungscenter.train_alert.Location.StationenLocation;

/**
 * Created by zorien on 26.06.2015.
 */
public class LocationListener implements View.OnClickListener {
    ImageButton imageButton;
    Activity activity;
    public LocationListener(Activity activity, ImageButton imageButton) {
        this.imageButton = imageButton;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.activity.getApplicationContext(),StationenLocation.class);
        intent.putExtra("button",imageButton.getId());
        this.activity.startActivity(intent);
    }
}
