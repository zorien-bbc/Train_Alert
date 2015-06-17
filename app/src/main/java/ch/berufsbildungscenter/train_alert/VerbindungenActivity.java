package ch.berufsbildungscenter.train_alert;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class VerbindungenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindungen);

        ListView verbListView = (ListView) findViewById(R.id.verbListView);
        ArrayList<Verbindung> verbindungen = new ArrayList<Verbindung>();
        verbindungen.add(new Verbindung("Hallo", "9:15", "15min"));
        verbindungen.add(new Verbindung("Hallo2", "032094", "30min"));

        verbListView.setAdapter(new VerbindungenArrayAdapter(this.getApplicationContext(), verbindungen, this.getLayoutInflater()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verbindungen, menu);
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
}