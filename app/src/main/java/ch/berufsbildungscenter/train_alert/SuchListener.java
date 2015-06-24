package ch.berufsbildungscenter.train_alert;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Nils on 19.06.2015.
 */
public class SuchListener implements Button.OnClickListener{
    private Activity activity;
    private EditText editText;

    public SuchListener(Activity activity,EditText editText) {
        this.editText = editText;
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {
        if(this.editText.getId()== R.id.editVon){
            Intent intent = new Intent(this.activity.getApplicationContext(),SuchHilfe.class);
            this.activity.startActivity(intent);
        }else if(this.editText.getId()== R.id.editNach){
            Intent intent = new Intent(this.activity.getApplicationContext(),SuchHilfe.class);
            this.activity.startActivity(intent);
        }
    }
}