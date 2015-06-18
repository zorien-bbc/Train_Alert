package ch.berufsbildungscenter.train_alert;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class VerbindungDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindung_details);

        ListView list = (ListView) findViewById(R.id.listView);
        ArrayList<Verbindung> verbindungen = new ArrayList<Verbindung>();
        verbindungen.add(new Verbindung("Uster, Nossikon", "9:15", "15min"));
        verbindungen.add(new Verbindung("Bern, Langenstrassenhaus", "9:16", "30min"));

        list.setAdapter(new VerbindungDetailsArrayAdapter(this.getApplicationContext(), verbindungen, this.getLayoutInflater()));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
