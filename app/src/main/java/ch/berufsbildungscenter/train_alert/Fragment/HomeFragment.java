package ch.berufsbildungscenter.train_alert.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.berufsbildungscenter.train_alert.Listener.EditTextListener;
import ch.berufsbildungscenter.train_alert.Listener.LocationListener;
import ch.berufsbildungscenter.train_alert.Listener.SuchListener;
import ch.berufsbildungscenter.train_alert.Listener.VerbindungenListener;
import ch.berufsbildungscenter.train_alert.R;
import ch.berufsbildungscenter.train_alert.VerbindungenActivity;

/**
 * Created by zorien on 07.07.2015.
 */
public class HomeFragment extends Fragment {
    final Calendar c = Calendar.getInstance();
    Button buttonDate;
    Button buttonTime;

    static EditText textVon, textNach, textVia;
    Date date;
    Date startDate = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

    ImageButton imageButtonVon;
    ImageButton imageButtonNach;
    ImageButton imageButtonVia;

    ImageButton deleteVon;
    ImageButton deleteNach;
    ImageButton deleteVia;

    RadioButton buttonAb;
    RadioButton buttonAn;

    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM." + c.get(Calendar.YEAR));
    boolean isDateChanged = false;
    private String textVonInhalt, textNachInhalt, textViaInhalt;
    private GestureDetectorCompat gDetector;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        Button button = (Button) view.findViewById(R
                .id.button);
        textVon = (EditText) view.findViewById(R.id.editVon);
        textNach = (EditText) view.findViewById(R.id.editNach);
        textVia = (EditText) view.findViewById(R.id.editVia);

        if(!getActivity().getPreferences(getActivity().MODE_PRIVATE).getAll().isEmpty()) {
            SharedPreferences savedState = getActivity().getPreferences(getActivity().MODE_PRIVATE);
            textVon.setText(savedState.getString("textVon", ""));
            textNach.setText(savedState.getString("textNach", ""));
            textVia.setText(savedState.getString("textVia", ""));
        }

        imageButtonVon = (ImageButton) view.findViewById(R.id.imageButtonVon);
        imageButtonNach = (ImageButton) view.findViewById(R.id.imageButtonNach);
        imageButtonVia = (ImageButton) view.findViewById(R.id.imageButtonVia);

        deleteVon = (ImageButton)view.findViewById(R.id.deleteVon);
        deleteNach = (ImageButton) view.findViewById(R.id.deleteNach);
        deleteVia = (ImageButton) view.findViewById(R.id.deleteVia);

        buttonAb = (RadioButton) view.findViewById(R.id.toggleButtonAb);
        buttonAn = (RadioButton) view.findViewById(R.id.toggleButtonAn);

        imageButtonVon.setOnClickListener(new LocationListener(getActivity(),imageButtonVon));
        imageButtonNach.setOnClickListener(new LocationListener(getActivity(), imageButtonNach));
        imageButtonVia.setOnClickListener(new LocationListener(getActivity(), imageButtonVia));

        deleteVon.setOnClickListener(new EditTextListener(textVon));
        deleteNach.setOnClickListener(new EditTextListener(textNach));
        deleteVia.setOnClickListener(new EditTextListener(textVia));

        textVon.setFocusable(false);
        textNach.setFocusable(false);
        textVia.setFocusable(false);

        textVon.setOnClickListener(new SuchListener(getActivity(), textVon, getString(R.string.hintEditVon)));
       textNach.setOnClickListener(new SuchListener(getActivity(), textNach, getString(R.string.hintEditNach)));
        textVia.setOnClickListener(new SuchListener(getActivity(), textVia, getString(R.string.hintEditVia)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String von = textVon.getText().toString();
                String nach = textNach.getText().toString();
                String via = textVia.getText().toString();
                String time = buttonTime.getText().toString();
                String aban = null;
                if (buttonAn.isChecked()) {
                    aban = "1";
                } else {
                    aban = "0";

                }

                if (von.equals("") || nach.equals("")) {
                    showErrorDialog("Start- und Zielort angeben!");
                } else if (von.equals(nach) || von.equals(via) || nach.equals(via)) {
                    showErrorDialog("Orte m\u00fcssen verschieden sein!");
                } else {
                    SimpleDateFormat intentDate = new SimpleDateFormat(c.get(Calendar.YEAR) + "-MM-dd");
                    String startDatum = intentDate.format(startDate);
                    Intent intent = new Intent(getActivity().getApplicationContext(), VerbindungenActivity.class);
                    intent.putExtra("von", von);
                    intent.putExtra("nach", nach);
                    intent.putExtra("via", via);
                    intent.putExtra("time", time);
                    intent.putExtra("wann", aban);
                    if (isDateChanged) {
                        String datum = intentDate.format(date);
                        intent.putExtra("date", datum);
                    } else {
                        intent.putExtra("date", startDatum);
                    }
                    startActivity(intent);
                }
            }
        });

        buttonDate = (Button) view.findViewById(R.id.buttonDate);
        buttonTime = (Button) view.findViewById(R.id.buttonTime);
        buttonTime.setText(time.format(c.getTime()));
        Date datum = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        buttonDate.setText(dateFormat.format(datum));
        buttonTime.setOnClickListener(new VerbindungenListener(getActivity(),this, buttonTime));
        buttonDate.setOnClickListener(new VerbindungenListener(getActivity(),this, buttonDate));
        return view;
    }
    private void showErrorDialog(String msg) {
        AlertDialog errorAlertDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
        errorAlertDialog.setTitle("Fehler");
        errorAlertDialog.setMessage(msg);
        errorAlertDialog.show();
    }

    public void displayLoadingDataFailedError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }


    public void getDate(int year, int month, int day) {
        date = new Date(year, month, day);
        isDateChanged = true;
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences savedState = getActivity().getPreferences(getActivity().MODE_PRIVATE);
       SharedPreferences.Editor editor = savedState.edit();
       editor.putString("textVon", textVon.getText().toString());
       editor.putString("textNach", textNach.getText().toString());
        editor.putString("textVia", textVia.getText().toString());
       editor.commit();
    }

    public static EditText getTextVia() {
        return textVia;
    }

    public static void setTextVia(EditText textVia) {
        HomeFragment.textVia = textVia;
    }

    public static EditText getTextNach() {
        return textNach;
    }

    public static void setTextNach(EditText textNach) {
        HomeFragment.textNach = textNach;
    }

    public static EditText getTextVon() {
        return textVon;
    }

    public static void setTextVon(EditText textVon) {
        HomeFragment.textVon = textVon;
    }
}
