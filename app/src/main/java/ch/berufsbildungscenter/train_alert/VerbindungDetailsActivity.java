package ch.berufsbildungscenter.train_alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.berufsbildungscenter.train_alert.ArrayAdapter.VerbindungDetailsArrayAdapter;
import ch.berufsbildungscenter.train_alert.Database.Alarm;
import ch.berufsbildungscenter.train_alert.Database.AlarmDAO;
import ch.berufsbildungscenter.train_alert.Database.Favoriten;
import ch.berufsbildungscenter.train_alert.Database.FavoritenDAO;
import ch.berufsbildungscenter.train_alert.JSON.Fahrt;
import ch.berufsbildungscenter.train_alert.JSON.Ort;
import ch.berufsbildungscenter.train_alert.JSON.Station;
import ch.berufsbildungscenter.train_alert.Listener.FavoritenListener;
import ch.berufsbildungscenter.train_alert.Location.VerbindungMap;


public class VerbindungDetailsActivity extends ActionBarActivity {
    Timestamp timestamp;
    ArrayList<Station> stations;
    ArrayList<Fahrt> fahrten;
    Ort vonOrt;
    Ort nachOrt;

    Button buttonVon;
    Button buttonNach;
    ImageButton imageButtonVon;
    ImageButton imageButtonNach;
    int zeit;
    AlarmDAO alarmDatabase;
    FavoritenDAO favoritenDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String style = sprefs.getString("tastyle", "default");
        if(style.equals("holo")) {
            setTheme(R.style.holo);
        } else if(style.equals("iap")) {
            setTheme(R.style.iap);
        } else if(style.equals("default")) {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_verbindung_details);


        SharedPreferences savedState = getPreferences(MODE_PRIVATE);

        Intent intent = getIntent();
        vonOrt = (Ort) intent.getSerializableExtra("vonOrt");
        nachOrt = (Ort) intent.getSerializableExtra("nachOrt");
        fahrten = intent.getExtras().getParcelableArrayList("fahrten");
        stations = intent.getExtras().getParcelableArrayList("stationen");
        alarmDatabase = new AlarmDAO(this.getApplicationContext());
        favoritenDatabase = new FavoritenDAO(getApplicationContext());
        buttonVon = (Button) findViewById(R.id.imageButton3);
        buttonNach = (Button) findViewById(R.id.imageButton4);
        imageButtonVon = (ImageButton) findViewById(R.id.imageButton2);
        imageButtonNach = (ImageButton) findViewById(R.id.imageButton5);
        setFavoritenIcon();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm / dd.MM.yyyy");
        timestamp = fahrten.get(0).getAbfahrt();
        ((TextView) findViewById(R.id.textView13)).setText(formatter.format(timestamp));
        Drawable image = getApplicationContext().getResources().getDrawable( R.mipmap.star_favorite);
        buttonVon.setCompoundDrawables(image, null, null, null);
        buttonVon.setText(vonOrt.getName());
        buttonNach.setText(nachOrt.getName());

        imageButtonVon.setOnClickListener(new FavoritenListener(this, vonOrt));
        imageButtonNach.setOnClickListener(new FavoritenListener(this, nachOrt));

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new VerbindungDetailsArrayAdapter(this.getApplicationContext(), fahrten, this.getLayoutInflater()));
    }

    public void setAlert() {

        List<Alarm> alarms = alarmDatabase.getAllAlarme();
        boolean exist = false;
        for(int i=0; i<alarms.size();i++){
            Alarm alarm = alarms.get(i);
            if(alarm.getVonOrt().equals(vonOrt.getName()) && alarm.getNachOrt().equals(nachOrt.getName()) && alarm.getTime() == timestamp.getTime()){
                Toast.makeText(this.getApplicationContext(), "Alarm ist bereits gesetzt!", Toast.LENGTH_SHORT).show();
                exist = true;

            }
        }if(!exist) {
            int _id = (int) System.currentTimeMillis();
            Intent intent = new Intent(this, Notification.class);
            intent.putExtra("id", _id);
            intent.putExtra("nach", nachOrt.getName());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), _id, intent, 0);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp.getTime() - zeit, pendingIntent);

            Alarm alarm = new Alarm(timestamp.getTime(), buttonVon.getText().toString(), buttonNach.getText().toString(), 0, _id);
            alarmDatabase.createAlarm(alarm);
            Toast.makeText(this.getApplicationContext(), "Alarm wurde gesetzt!", Toast.LENGTH_SHORT).show();
        }

    }
    public void setFavoritenIcon(){
        if(favoritenDatabase.getAllFavoriten().size()>0) {
            List<Favoriten> favoritens = favoritenDatabase.getAllFavoriten();

            for(int i = 0;i<favoritens.size(); i++) {
                if (vonOrt.getName().equals(favoritens.get(i).getName())){
                    imageButtonVon.setImageResource(R.mipmap.star_favorite);
                }else if(nachOrt.getName().equals(favoritens.get(i).getName())){
                    imageButtonNach.setImageResource(R.mipmap.star_favorite);
                }else if(!vonOrt.getName().equals(favoritens.get(i).getName())){
                    imageButtonVon.setImageResource(R.mipmap.star_nofavorite);
                }else if(!nachOrt.getName().equals(favoritens.get(i).getName())){
                    imageButtonNach.setImageResource(R.mipmap.star_nofavorite);
                }
            }
        }else{
            imageButtonVon.setImageResource(R.mipmap.star_nofavorite);
            imageButtonNach.setImageResource(R.mipmap.star_nofavorite);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verbindung_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.alarm) {
            setAlert();
            return true;
        } else if (id == R.id.mapAction) {
            Intent intent = new Intent(getApplicationContext(), VerbindungMap.class);
            intent.putParcelableArrayListExtra("station", stations);
            intent.putExtra("verbindung", vonOrt.getName() + " - " + nachOrt.getName());
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}