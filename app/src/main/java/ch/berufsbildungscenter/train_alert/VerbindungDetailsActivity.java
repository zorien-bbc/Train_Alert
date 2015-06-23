package ch.berufsbildungscenter.train_alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class VerbindungDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindung_details);

        Intent intent = getIntent();
        Ort vonOrt = (Ort) intent.getSerializableExtra("vonOrt");
        Ort nachOrt = (Ort) intent.getSerializableExtra("nachOrt");
        Fahrt[] fahrten = (Fahrt[]) intent.getParcelableArrayExtra("fahrten");

        ((Button) findViewById(R.id.imageButton3)).setText(vonOrt.getName());
        ((Button) findViewById(R.id.imageButton4)).setText(nachOrt.getName());

        ArrayList<Fahrt> alleFahrten = new ArrayList<Fahrt>();
        for(int i = 0; i == fahrten.length; i++) {
            Fahrt einzFahrt = fahrten[i];
            alleFahrten.add(einzFahrt);
        }
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new VerbindungDetailsArrayAdapter(this.getApplicationContext(), alleFahrten, this.getLayoutInflater()));
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
