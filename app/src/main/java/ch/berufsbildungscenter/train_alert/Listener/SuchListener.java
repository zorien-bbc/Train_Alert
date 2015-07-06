package ch.berufsbildungscenter.train_alert.Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.berufsbildungscenter.train_alert.SuchHilfe;

/**
 * Created by Nils on 19.06.2015.
 */
public class SuchListener implements Button.OnClickListener {
    private Activity activity;
    private EditText selectedEdit;
    private String weg;

    public SuchListener(Activity activity, EditText selectedEdit, String weg) {
        this.activity = activity;
        this.selectedEdit = selectedEdit;
        this.weg = weg;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.activity.getApplicationContext(), SuchHilfe.class);
        intent.putExtra("selectedEdit", selectedEdit.getId());
        intent.putExtra("ort", selectedEdit.getText().toString());
        intent.putExtra("weg", weg);
        this.activity.startActivity(intent);
    }
}