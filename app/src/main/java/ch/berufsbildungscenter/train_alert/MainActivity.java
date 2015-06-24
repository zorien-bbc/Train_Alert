package ch.berufsbildungscenter.train_alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {
    final Calendar c = Calendar.getInstance();
    Button buttonDate;
    Button buttonTime;
    EditText textVon,textNach;
    MainActivity main = this;
    Date date;
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM."+c.get(Calendar.YEAR));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R
                .id.button);
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textVon = (EditText) findViewById(R.id.editVon);
                textNach = (EditText) findViewById(R.id.editNach);
                String von = textVon.getText().toString();
                String nach = textNach.getText().toString();
                String time = buttonTime.getText().toString();

                SimpleDateFormat intentDate = new SimpleDateFormat(c.get(Calendar.YEAR)+"-MM-dd");

                String datum = intentDate.format(date);
                Intent intent = new Intent(main.getApplicationContext(), VerbindungenActivity.class);
                intent.putExtra("von", von);
                intent.putExtra("nach", nach);
                intent.putExtra("time",time);
                intent.putExtra("date",datum);
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



    public void setAlert(){
        Intent intent = new Intent(this, MyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (5 * 1000), pendingIntent);

        Toast.makeText(this.getApplicationContext(), "Alarm was setted", Toast.LENGTH_SHORT);
    }

    public void getDate(int year,int month,int day){
        date = new Date(year,month,day);
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
            setAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
