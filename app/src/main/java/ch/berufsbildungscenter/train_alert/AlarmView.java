package ch.berufsbildungscenter.train_alert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDAO;


public class AlarmView extends ActionBarActivity implements ActionBar.TabListener {
    AlarmDAO alarmDatabase;

    Activity activity = this;
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(getString(R.string.favoriten)).setTabListener(this), false);
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.route)).setTabListener(this), false
        );
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.alarme)).setTabListener(this), true);
        actionBar.setHomeButtonEnabled(false);
        fillList();

    }

    public void fillList() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView alrListView = (ListView) findViewById(R.id.listView4);

        alarmDatabase = new AlarmDAO(getApplicationContext());
        final List<Alarm> alarms = alarmDatabase.getAllAlarme();
        final Calendar calendar = Calendar.getInstance();
        if(alarms.size() !=0) {
         //   AlarmArrayAdapter alarmArrayAdapter = new AlarmArrayAdapter(this.getApplicationContext(), alarms, this.getLayoutInflater(), this);
            for(int i =0;i<alarms.size();i++){

                Log.v(alarms.get(i).getNachOrt(),alarms.get(i).getAktiviert()+"superyolo");
            }

            //alrListView.setAdapter(alarmArrayAdapter);
        }else {
            arrayAdapter.add(getString(R.string.keinAlarm));
            alrListView.setAdapter(arrayAdapter);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {


        if (tab.getPosition() == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else if(tab.getPosition()==0){
            Intent intent = new Intent(getApplicationContext(), FavoritenView.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
