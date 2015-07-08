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

import ch.berufsbildungscenter.train_alert.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Button button;
    HomeFragment homeFragment;
    final Calendar c = Calendar.getInstance();
    Bundle bundle = null;

    private SimpleDateFormat date = new SimpleDateFormat("dd.MM." + c.get(Calendar.YEAR));

    public DatePickerFragment() {
        super();
    }


//
//    public DatePickerFragment(Button button, HomeFragment homeFragment) {
//        this.button = button;
//        this.homeFragment = homeFragment;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //this.setArguments(savedInstanceState);
        this.bundle = savedInstanceState;



        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Date datum = new Date(year, month, day);
        //homeFragment.getDate(year, month, day);
        //((HomeFragment) ).getDate(year, month, day);

       // HomeFragment articleFrag = (HomeFragment) getActivity().getFragmentManager().getFragment(this.bundle, "HomeFragment");
        HomeFragment.setDate(year,month,day);
                ((Button) this.getActivity().findViewById(R.id.buttonDate)).setText(date.format(datum));

    }
}