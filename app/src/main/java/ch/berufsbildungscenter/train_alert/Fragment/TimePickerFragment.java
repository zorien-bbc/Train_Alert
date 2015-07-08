package ch.berufsbildungscenter.train_alert.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.berufsbildungscenter.train_alert.R;

/**
 * Created by zorien on 17.06.2015.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Button button;
    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");

    public TimePickerFragment() {
        super();
    }

    //public TimePickerFragment(Button button) {
    //    this.button = button;
    //}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date zeit = new Date(0, 0, 0, hourOfDay, minute);
        ((Button) this.getActivity().findViewById(R.id.buttonTime)).setText(time.format(zeit));
        //this.button.setText(time.format(zeit));

    }

}
