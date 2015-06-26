package ch.berufsbildungscenter.train_alert;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class StationenLocation extends ActionBarActivity {
    int imageButtonId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationen_location);
        MyLocation myLocation = new MyLocation(this);
        JSONOrt jsonOrt = new JSONOrt(this);
        jsonOrt.execute(myLocation.latitude + "", myLocation.longtitude + "");
        imageButtonId = getIntent().getIntExtra("button",R.id.imageButtonVon);
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
                    MainActivity.textVon.setText(resultset.get(position).toString());
                } else if(imageButtonId == R.id.imageButtonNach) {
                    MainActivity.textNach.setText(resultset.get(position).toString());
                } else if(imageButtonId == R.id.imageButtonVia) {
                    MainActivity.textVia.setText(resultset.get(position).toString());
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
