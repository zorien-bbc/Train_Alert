package ch.berufsbildungscenter.train_alert.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.berufsbildungscenter.train_alert.MainActivity;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Button button;
    MainActivity mainActivity;
    final Calendar c = Calendar.getInstance();

    private SimpleDateFormat date = new SimpleDateFormat("dd.MM." + c.get(Calendar.YEAR));

    public DatePickerFragment(Button button, MainActivity mainActivity) {
        this.button = button;
        this.mainActivity = mainActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Date datum = new Date(year, month, day);
        mainActivity.getDate(year,month,day);
        this.button.setText(date.format(datum));

    }
}