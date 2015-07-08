package ch.berufsbildungscenter.train_alert.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import ch.berufsbildungscenter.train_alert.ArrayAdapter.AlarmArrayAdapter;
import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDAO;
import ch.berufsbildungscenter.train_alert.R;

/**
 * Created by zorien on 07.07.2015.
 */
public class AlarmFragment extends Fragment {
    AlarmDAO alarmDatabase;
    LayoutInflater inflater;
    ListView arlListView;

    final Calendar cal = Calendar.getInstance();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(R.layout.alarm_fragment, container, false);
        this.inflater = inflater;
        arlListView = (ListView) view.findViewById(R.id.listViewAlarm);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        alarmDatabase = new AlarmDAO(getActivity().getApplicationContext());
        final List<Alarm> alarms = alarmDatabase.getAllAlarme();
        final Calendar calendar = Calendar.getInstance();
        if (alarms.size() != 0) {
            AlarmArrayAdapter alarmArrayAdapter = new AlarmArrayAdapter(getActivity().getApplicationContext(), alarms, this.inflater, getActivity(), this);
            arlListView.setAdapter(alarmArrayAdapter);
        } else {
            arrayAdapter.add(getString(R.string.keinAlarm));
            arlListView.setAdapter(arrayAdapter);
        }

        return view;
    }

}
