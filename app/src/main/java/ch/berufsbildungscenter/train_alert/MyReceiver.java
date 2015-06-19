package ch.berufsbildungscenter.train_alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by zorien on 19.06.2015.
 */

public class MyReceiver  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Hier käme die Logik um z.B. auf den Broadcast zu reagieren.
            Toast toast = Toast.makeText(context, "Broadcast empfangen!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


