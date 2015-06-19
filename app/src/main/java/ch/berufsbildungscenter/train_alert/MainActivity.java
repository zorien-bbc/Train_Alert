package ch.berufsbildungscenter.train_alert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
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
    EditText textVon;
    EditText textNach;
    MainActivity main = this;
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat date = new SimpleDateFormat("dd.MM."+c.get(Calendar.YEAR));


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

                Intent intent = new Intent(main.getApplicationContext(), VerbindungenActivity.class);
                intent.putExtra("von", von);
                intent.putExtra("nach", nach);
                startActivity(intent);
            }
        });

        buttonDate = (Button) findViewById(R.id.buttonDate);
        buttonTime = (Button) findViewById(R.id.buttonTime);
        buttonTime.setText(time.format(c.getTime()));
        Date datum = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        buttonDate.setText(date.format(datum));
        buttonTime.setOnClickListener(new VerbindungenListener(this, buttonTime));
        buttonDate.setOnClickListener(new VerbindungenListener(this, buttonDate));
        //sendNotification();
       Alarm alarm = new Alarm(this);
        setAlarm();
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    public void sendNotification() {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setContentTitle("Train Alert")
                .setContentText("Ihr Zug fährt in 5 Minuten!")
                .setSmallIcon(R.drawable.logo)
                .setSound(sound);
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
public void setAlarm(){
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
