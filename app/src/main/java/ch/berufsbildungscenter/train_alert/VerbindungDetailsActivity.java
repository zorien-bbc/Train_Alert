package ch.berufsbildungscenter.train_alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class VerbindungDetailsActivity extends ActionBarActivity {
    Timestamp timestamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindung_details);

        Intent intent = getIntent();
        Ort vonOrt = (Ort) intent.getSerializableExtra("vonOrt");
        Ort nachOrt = (Ort) intent.getSerializableExtra("nachOrt");
        ArrayList<Fahrt> fahrten = intent.getExtras().getParcelableArrayList("fahrten");
        timestamp = fahrten.get(0).getAbfahrt();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ((TextView) findViewById(R.id.textView13)).setText(formatter.format(timestamp));
        ((Button) findViewById(R.id.imageButton3)).setText(vonOrt.getName());
        ((Button) findViewById(R.id.imageButton4)).setText(nachOrt.getName());

        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new VerbindungDetailsArrayAdapter(this.getApplicationContext(), fahrten, this.getLayoutInflater()));
    }

    public void setAlert() {
        Intent intent = new Intent(this, MyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp.getTime(), pendingIntent);

        Toast.makeText(this.getApplicationContext(), "Alarm wurde gesetzt!", Toast.LENGTH_SHORT).show();

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
        }

        return super.onOptionsItemSelected(item);
    }
}