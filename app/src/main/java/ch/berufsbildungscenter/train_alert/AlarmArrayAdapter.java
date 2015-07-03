package ch.berufsbildungscenter.train_alert;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDatabase;

/**
 * Created by zorien on 03.07.2015.
 */
public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {
    LayoutInflater mInflater;
    private List<Alarm> mItems;
    AlarmView activity;
    AlarmDatabase alarmDatabase;



    public AlarmArrayAdapter(Context context, List<Alarm> items, LayoutInflater inflater, AlarmView activity) {
        super(context, -1, items);
        this.mItems = items;
        this.mInflater = inflater;

        this.activity = activity;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final int pos1 = pos;
        convertView = mInflater.inflate(R.layout.alarm_adapter, null);
        final Alarm alarm = mItems.get(pos);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Timestamp timestamp = new Timestamp(alarm.getTime());
        alarmDatabase = new AlarmDatabase(getContext());

        final List<Alarm> alarms = mItems;
                ((TextView) convertView.findViewById(R.id.adapterTextVon)).setText("Von: " + alarm.getVonOrt());
        ((TextView) convertView.findViewById(R.id.adapterTextVia)).setText("Abfahrt: " + formatter.format(timestamp));
        ((TextView) convertView.findViewById(R.id.adapterTextNach)).setText("Nach: " + alarm.getNachOrt());
        Switch curSwitch = (Switch) convertView.findViewById(R.id.adapterSwitch);
        curSwitch.setChecked(true);
        curSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    //Try to open the DB connection
                    alarmDatabase.open();
                } catch (SQLException e) {
                    Log.v("DATABASETEST", e.toString());
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle("Wollen Sie den Alarm löschen oder Stummschalten?");
                alert.setNegativeButton("Löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmDatabase.deleteAlarm(alarm);

                        activity.fillList();
                    }
                });
                alert.setPositiveButton("Stummschalten", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarm.setAktiviert(0);

                        activity.fillList();

                    }
                });


                alert.show();
            }
        });



        return convertView;
    }
}
