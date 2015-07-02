package ch.berufsbildungscenter.train_alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    final Calendar c = Calendar.getInstance();
    Button buttonDate;
    Button buttonTime;
    static EditText textVon, textNach, textVia;
    MainActivity main = this;
    Date date;
    Date startDate = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

    FavoritenDatabase favoritenDatabase;

    ImageButton imageButtonVon;
    ImageButton imageButtonNach;
    ImageButton imageButtonVia;

    ImageButton deleteVon;
    ImageButton deleteNach;
    ImageButton deleteVia;


    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM." + c.get(Calendar.YEAR));
    boolean isDateChanged = false;
    private String textVonInhalt, textNachInhalt, textViaInhalt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R
                .id.button);
        textVon = (EditText) findViewById(R.id.editVon);
        textNach = (EditText) findViewById(R.id.editNach);
        textVia = (EditText) findViewById(R.id.editVia);
        favoritenDatabase = new FavoritenDatabase(this);


        try {
            //Try to open the DB connection
            favoritenDatabase.open();
        } catch (SQLException e) {
            Log.v("DATABASETEST", e.toString());
        }
        LatLng latLng = new LatLng(55,33);
        Favoriten favoriten = new Favoriten(latLng,"Uster");
        favoritenDatabase.createFavoriten(favoriten);
        List<Favoriten> favoritens = favoritenDatabase.getAllFavoriten();
        imageButtonVon = (ImageButton) findViewById(R.id.imageButtonVon);
        imageButtonNach = (ImageButton) findViewById(R.id.imageButtonNach);
        imageButtonVia = (ImageButton) findViewById(R.id.imageButtonVia);

        deleteVon = (ImageButton) findViewById(R.id.deleteVon);
        deleteNach = (ImageButton) findViewById(R.id.deleteNach);
        deleteVia = (ImageButton) findViewById(R.id.deleteVia);

        imageButtonVon.setOnClickListener(new LocationListener(this,imageButtonVon));
        imageButtonNach.setOnClickListener(new LocationListener(this,imageButtonNach));
        imageButtonVia.setOnClickListener(new LocationListener(this, imageButtonVia));

        deleteVon.setOnClickListener(new EditTextListener(textVon));
        deleteNach.setOnClickListener(new EditTextListener(textNach));
        deleteVia.setOnClickListener(new EditTextListener(textVia));

        textVon.setFocusable(false);
        textNach.setFocusable(false);
        textVia.setFocusable(false);

        textVon.setOnClickListener(new SuchListener(this, textVon));
        textNach.setOnClickListener(new SuchListener(this, textNach));
        textVia.setOnClickListener(new SuchListener(this, textVia));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String von = textVon.getText().toString();
                String nach = textNach.getText().toString();
                String via = textVia.getText().toString();
                String time = buttonTime.getText().toString();

                SimpleDateFormat intentDate = new SimpleDateFormat(c.get(Calendar.YEAR) + "-MM-dd");

                String startDatum = intentDate.format(startDate);
                Intent intent = new Intent(main.getApplicationContext(), VerbindungenActivity.class);
                intent.putExtra("von", von);
                intent.putExtra("nach", nach);
                intent.putExtra("via", via);
                intent.putExtra("time", time);
                if (isDateChanged) {
                    String datum = intentDate.format(date);
                    intent.putExtra("date", datum);
                } else {
                    intent.putExtra("date", startDatum);
                }
                startActivity(intent);
            }
        });

        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonTime.setText(time.format(c.getTime()));
        Date datum = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        buttonDate.setText(dateFormat.format(datum));
        buttonTime.setOnClickListener(new VerbindungenListener(this, buttonTime));
        buttonDate.setOnClickListener(new VerbindungenListener(this, buttonDate));
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }


    public void getDate(int year, int month, int day) {
        date = new Date(year, month, day);
        isDateChanged = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
