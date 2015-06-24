package ch.berufsbildungscenter.train_alert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class SuchHilfe extends ActionBarActivity {
    final SuchHilfe suchHilfe = this;
    String textFeld;

    public List<String> getVorschlaege() {
        return vorschlaege;
    }

    public void setVorschlaege(List<String> vorschlaege) {
        this.vorschlaege = vorschlaege;
    }

    private List<String> vorschlaege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_such_hilfe);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("HALLO");
        textFeld = intent.getStringExtra("text");
        final EditText myTextBox = (EditText) findViewById(R.id.editText);
        myTextBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                JSONSuchHilfe jsonSuchHilfe = new JSONSuchHilfe(suchHilfe);
                jsonSuchHilfe.execute(myTextBox.getText().toString());

            }
        });


        // jsonSuchHilfe.execute("B");
    }

    public void setData(List<String> result) {
        final List<String> resultset = result;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView verbListView = (ListView) findViewById(R.id.listView2);
        for (int i = 0; i < resultset.size(); i++) {
            arrayAdapter.add(resultset.get(i));
        }
        verbListView.setAdapter(arrayAdapter);
        verbListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ort", resultset.get(position));
                intent.putExtra("vonWo", textFeld);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_such_hilfe, menu);
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