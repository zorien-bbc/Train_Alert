package ch.berufsbildungscenter.train_alert;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zorien on 19.06.2015.
 */
public class AlarmManagerActivity extends AppCompatActivity implements View.OnClickListener {

    MyReceiver myReceiver;
    IntentFilter intentFilter;
    TextView textFiled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Clicklistener auf den Button setzen: Ruft unten onClick() auf
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        //Den Boradcast Receiver und den Intentfilter (auf was der Receiver reagiert) erstellen.
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter("ch.berufsbildungscenter.broadcastdemo.USER_ACTION");
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent("ch.berufsbildungscenter.broadcastdemo.USER_ACTION");
        sendBroadcast(i);
    }
}


