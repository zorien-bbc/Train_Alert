package ch.berufsbildungscenter.train_alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import ch.berufsbildungscenter.train_alert.JSON.JSONAsyncTask;


public class VerbindungenActivity extends ActionBarActivity {
    private String von;
    private String nach;
    private String via;
    private String time;
    private String date;
    private String aban;
    private EditText textFeld;
    public static ProgressDialog progressDialog;
    Timestamp timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindungen);

        Intent intent = getIntent();
        von = intent.getStringExtra("von");
        nach = intent.getStringExtra("nach");
        via = intent.getStringExtra("via");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        aban = intent.getStringExtra("wann");

        progressDialog = ProgressDialog.show(this, "Lade Verbindung", "Bitte warten...");
        JSONAsyncTask jsonAsyncTask = new JSONAsyncTask(this, progressDialog);
        jsonAsyncTask.execute(von, nach, via, time, date, aban);
    }

    public void setData(List<Verbindung> result) {
        final List<Verbindung> resultset = result;

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

        ((TextView) findViewById(R.id.vonOrtLabel)).setText(result.get(0).getVonOrt().getName());
        ((TextView) findViewById(R.id.nachOrtLabel)).setText(result.get(0).getNachOrt().getName());
        ((TextView) findViewById(R.id.datumLabel)).setText(dateFormatter.format(result.get(0).getZeit()).toString());
        ((TextView) findViewById(R.id.zeitLabel)).setText(timeFormatter.format(result.get(0).getZeit()).toString());
        timestamp = result.get(0).getZeit();
        ListView verbListView = (ListView) findViewById(R.id.verbListView);
        verbListView.setAdapter(new VerbindungenArrayAdapter(this.getApplicationContext(), resultset, this.getLayoutInflater()));

        verbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), VerbindungDetailsActivity.class);
                intent.putExtra("vonOrt", (Serializable) resultset.get(position).getVonOrt());
                intent.putExtra("nachOrt", (Serializable) resultset.get(position).getNachOrt());
                intent.putParcelableArrayListExtra("fahrten", resultset.get(position).getVerbindungen());
                intent.putParcelableArrayListExtra("stationen", resultset.get(position).getStations());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verbindungen, menu);
        return true;
    }

    public void setAlert() {
        Intent intent = new Intent(this, Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp.getTime(), pendingIntent);

        Toast.makeText(this.getApplicationContext(), "Alarm wurde gesetzt!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.alarm) {
            // setAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
