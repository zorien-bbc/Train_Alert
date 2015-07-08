package ch.berufsbildungscenter.train_alert.Location;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.berufsbildungscenter.train_alert.Fragment.HomeFragment;
import ch.berufsbildungscenter.train_alert.JSON.JSONOrt;
import ch.berufsbildungscenter.train_alert.R;


public class StationenLocation extends ActionBarActivity {
    int imageButtonId;
    public static ProgressDialog progressDialog;
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

        setContentView(R.layout.activity_stationen_location);

        MyLocation myLocation = new MyLocation(this);
        Log.v("derultimativetest",myLocation.getLatitude()+"/"+myLocation.getLongtitude());
        progressDialog = ProgressDialog.show(this, "Lade Verbindung", "Bitte warten...");
        String jsonTyp = "ort";
        JSONOrt jsonOrt = new JSONOrt(this,progressDialog);
        jsonOrt.execute(jsonTyp,myLocation.latitude + "", myLocation.longtitude + "");
        imageButtonId = getIntent().getExtras().getInt("button");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stationen_location, menu);
        return true;
    }
    public void setData(List<String> result) {
        final List<String> resultset = result;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listView3);
        for(String s : resultset){
            arrayAdapter.add(s);
        }
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(imageButtonId == R.id.imageButtonVon) {
                    HomeFragment.getTextVon().setText(resultset.get(position).toString());
                } else if(imageButtonId == R.id.imageButtonNach) {
                    HomeFragment.getTextNach().setText(resultset.get(position).toString());
                } else if(imageButtonId == R.id.imageButtonVia) {
                    HomeFragment.getTextVia().setText(resultset.get(position).toString());
                }
                finish();
            }
        });

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