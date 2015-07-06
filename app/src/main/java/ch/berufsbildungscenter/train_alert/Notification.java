package ch.berufsbildungscenter.train_alert;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDAO;

/**
 * Created by zorien on 19.06.2015.
 */
public class Notification extends BroadcastReceiver {
    AlarmDAO alarmDatabase;
    int alarmNummer;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        alarmDatabase = new AlarmDAO(context);
        alarmNummer = intent.getExtras().getInt("id");
        getAlarme();
    }

    public void getAlarme() {
        List<Alarm> alarms = alarmDatabase.getAllAlarme();
        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).getAlarmNummer() == alarmNummer && alarms.get(i).getAktiviert() == 0) {
                createNotification(this.context, "Alarm alarm alarm!", "Dein Zug f\u00e4hrt in f\u00fcnf minuten", "ALARM");
                alarmDatabase.deleteAlarm(alarms.get(i));
            } else if(alarms.get(i).getAlarmNummer() == alarmNummer && alarms.get(i).getAktiviert() == 1) {
                alarmDatabase.deleteAlarm(alarms.get(i));
            }
        }


    }

    public void createNotification(Context context, String msg, String msgtext, String msgalert) {
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(msg)
                .setTicker(msgalert)
                .setContentText(msgtext)
                .setSound(Uri.parse("android.resource://ch.berufsbildungscenter.train_alert/" + R.raw.train_whistle));

        mbuilder.setContentIntent(notificIntent);
        //mbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mbuilder.setAutoCancel(true);

        NotificationManager mnotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mnotificationManager.notify(1, mbuilder.build());

    }
}

