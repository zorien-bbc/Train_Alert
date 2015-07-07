package ch.berufsbildungscenter.train_alert;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDAO;
import ch.berufsbildungscenter.train_alert.Fragment.AlarmFragment;

/**
 * Created by zorien on 03.07.2015.
 */
public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {
    LayoutInflater mInflater;
    private List<Alarm> mItems;
    Activity activity;
    AlarmDAO alarmDatabase;
    AlarmFragment alarmFragment;

    public AlarmArrayAdapter(Context context, List<Alarm> items, LayoutInflater inflater, Activity activity, AlarmFragment alarmFragment) {
        super(context, -1, items);
        this.mItems = items;
        this.mInflater = inflater;
        this.alarmFragment = alarmFragment;
        this.activity = activity;

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final int pos1 = pos;
        convertView = mInflater.inflate(R.layout.alarm_adapter, null);
        final Alarm alarm = mItems.get(pos);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Timestamp timestamp = new Timestamp(alarm.getTime());
        alarmDatabase = new AlarmDAO(getContext());

        final List<Alarm> alarms = mItems;
        ((TextView) convertView.findViewById(R.id.adapterTextVon)).setText("Von: " + alarm.getVonOrt());
        ((TextView) convertView.findViewById(R.id.adapterTextVia)).setText("Abfahrt: " + formatter.format(timestamp));
        ((TextView) convertView.findViewById(R.id.adapterTextNach)).setText("Nach: " + alarm.getNachOrt());

        Switch curSwitch = (Switch) convertView.findViewById(R.id.adapterSwitch);
        if (alarm.getAktiviert() == 0){
            curSwitch.setChecked(true);
    }else{
            curSwitch.setChecked(false);
        }

        curSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String alertText = null;

                if (!isChecked) {
                    alertText = "Stummschalten";

                } else if (isChecked) {
                    alertText = "aktivieren";
                }
                showAlertDialog(alertText, alarm);
            }
        });


        return convertView;
    }

    public void showAlertDialog(String text, final Alarm alarm) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        alert.setTitle("Wollen Sie den Alarm löschen oder " + text + "?");
        alert.setPositiveButton(text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmDatabase.updateAlarm(alarm);
                refreshFragment();
            }

        });

        alert.setNegativeButton("löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarmDatabase.deleteAlarm(alarm);
                refreshFragment();

            }
        });


        alert.show();

    }
    public void refreshFragment(){
        FragmentManager fragmentManager = alarmFragment.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, alarmFragment)
                .commit();
    }
}
