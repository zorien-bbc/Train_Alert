package ch.berufsbildungscenter.train_alert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by zorien on 19.06.2015.
 */
public class MyNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context k1, Intent k2) {
        // TODO Auto-generated method stub
        createNotification(k1, "Alarm alarm alarm!", "Dein Zug f\u00e4hrt in f\u00fcnf minuten", "ALARM");
    }

    public void createNotification(Context context, String msg, String msgtext, String msgalert){
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(msg)
                .setTicker(msgalert)
                .setContentText(msgtext)
                .setSound(Uri.parse("android.resource://ch.berufsbildungscenter.train_alert/"+R.raw.train_whistle));

        mbuilder.setContentIntent(notificIntent);
        //mbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mbuilder.setAutoCancel(true);

        NotificationManager mnotificationManager         = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mnotificationManager.notify(1,mbuilder.build());

    }
}

