package ch.berufsbildungscenter.train_alert;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by zorien on 19.06.2015.
 */
public class AlarmManagerActivity extends Activity {
    public static final String TAG = "SampleActivityBase";
    public static final int REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                    intent, 0);

            int alarmType = AlarmManager.ELAPSED_REALTIME;
            final int FIFTEEN_SEC_MILLIS = 10000;

            // The AlarmManager, like most system services, isn't created by application code, but
            // requested from the system.
            AlarmManager alarmManager = (AlarmManager)
                    this.getSystemService(this.ALARM_SERVICE);

        // setRepeating takes a start delay and period between alarms as arguments.
            // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
            // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
            alarmManager.set(alarmType,SystemClock.elapsedRealtime()+ FIFTEEN_SEC_MILLIS,pendingIntent);
            Log.i("RepeatingAlarmFragment", "Alarm set.");

        return true;
    }
}



