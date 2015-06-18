package ch.berufsbildungscenter.train_alert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    final Calendar c = Calendar.getInstance();
    Button buttonDate;
    Button buttonTime;
    EditText textVon;
    EditText textNach;
    MainActivity main = this;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textVon = (EditText) findViewById(R.id.editVon);
        textNach = (EditText) findViewById(R.id.editNach);

        final Intent intent = new Intent(this, VerbindungenActivity.class);

        Button button = (Button) findViewById(R
                .id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(main, "Lade Verbindung", "Bitte warten...");
                JSONAsyncTask jsonAsyncTask = new JSONAsyncTask(main,progressDialog);
                jsonAsyncTask.execute(textVon.getText().toString(), textNach.getText().toString());
                startActivity(intent);
            }
        });

        /*buttonDate = (Button) findViewById(R.id.button2);
        buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonTime.setText(c.get(Calendar.HOUR_OF_DAY)+" : " + c.get(Calendar.MINUTE));
        buttonDate.setText(c.get(Calendar.DAY_OF_MONTH)+"."+c.get(Calendar.MONTH)+"."+c.get(Calendar.YEAR));
        buttonTime.setOnClickListener(new VerbindungenListener(this, buttonTime));
        buttonDate.setOnClickListener(new VerbindungenListener(this, buttonDate));*/
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void setData(List<Verbindung> result) {

        StringBuilder sb = new StringBuilder();
        for(Verbindung verbindung : result){
            sb.append(verbindung.toString());
            sb.append("\n\n");
        }


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
