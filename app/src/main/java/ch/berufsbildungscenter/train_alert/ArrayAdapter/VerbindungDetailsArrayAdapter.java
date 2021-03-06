package ch.berufsbildungscenter.train_alert.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ch.berufsbildungscenter.train_alert.JSON.Fahrt;
import ch.berufsbildungscenter.train_alert.R;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class VerbindungDetailsArrayAdapter extends ArrayAdapter<Fahrt> {

    LayoutInflater mInflater;
    private List<Fahrt> mItems;

    public VerbindungDetailsArrayAdapter(Context context, List<Fahrt> items, LayoutInflater inflater) {
        super(context, -1, items);
        this.mItems = items;
        this.mInflater = inflater;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.verbindung_details_adapter, null);
        final Fahrt fahrt = (Fahrt) mItems.get(pos);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ((TextView) convertView.findViewById(R.id.vonLabel)).setText(fahrt.getVonHaltestelle());
        ((TextView) convertView.findViewById(R.id.nachLabel)).setText(fahrt.getBisHaltestelle());
        ((TextView) convertView.findViewById(R.id.vonZeitLabel)).setText(formatter.format(fahrt.getAbfahrt()));
        ((TextView) convertView.findViewById(R.id.bisZeitLabel)).setText(formatter.format(fahrt.getAnkunft()));
        ((TextView) convertView.findViewById(R.id.vonGleisLabel)).setText("Gl. " + fahrt.getVonGleis());
        ((TextView) convertView.findViewById(R.id.bisGleisLabel)).setText("Gl. " + fahrt.getBisGleis());
        ((TextView) convertView.findViewById(R.id.bahnLabel)).setText(fahrt.getTransportmittel());

        return convertView;
    }
}
