package ch.berufsbildungscenter.train_alert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;


public class VerbindungenActivity extends ActionBarActivity {
    private String von;
    private String nach;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbindungen);

        Intent intent = getIntent();
        von = intent.getStringExtra("von");
        nach = intent.getStringExtra("nach");
        progressDialog = ProgressDialog.show(this, "Lade Verbindung", "Bitte warten...");
        JSONAsyncTask jsonAsyncTask = new JSONAsyncTask(this, progressDialog);
        jsonAsyncTask.execute(von,nach);

    }

    public void setData(List<Verbindung> result) {
        final List<Verbindung> resultset = result;

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

        ((TextView) findViewById(R.id.vonOrtLabel)).setText(result.get(0).getVonOrt().getName());
        ((TextView) findViewById(R.id.nachOrtLabel)).setText(result.get(0).getNachOrt().getName());
        ((TextView) findViewById(R.id.datumLabel)).setText(dateFormatter.format(result.get(0).getZeit()).toString());
        ((TextView) findViewById(R.id.zeitLabel)).setText(timeFormatter.format(result.get(0).getZeit()).toString());

        ListView verbListView = (ListView) findViewById(R.id.verbListView);
        verbListView.setAdapter(new VerbindungenArrayAdapter(this.getApplicationContext(), resultset, this.getLayoutInflater()));

        verbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), VerbindungDetailsActivity.class);
                intent.putExtra("vonOrt", (Ort) resultset.get(position).getVonOrt());
                intent.putExtra("nachOrt", (Ort) resultset.get(position).getNachOrt());
                intent.putExtra("fahrten", resultset.get(position).getVerbindungen().toArray());
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
