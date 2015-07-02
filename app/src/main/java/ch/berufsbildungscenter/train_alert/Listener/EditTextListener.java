package ch.berufsbildungscenter.train_alert.Listener;

import android.view.View;
import android.widget.EditText;

/**
 * Created by zorien on 26.06.2015.
 */
public class EditTextListener implements View.OnClickListener {
    EditText editText;

    public EditTextListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
            this.editText.setText("");
        }

}
