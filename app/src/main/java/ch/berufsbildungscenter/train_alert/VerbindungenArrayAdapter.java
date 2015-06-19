package ch.berufsbildungscenter.train_alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zfehrn on 17.06.2015.
 */
public class VerbindungenArrayAdapter extends ArrayAdapter<Verbindung> {

    LayoutInflater mInflater;
    private List<Verbindung> mItems;

    public VerbindungenArrayAdapter(Context context, List<Verbindung> items, LayoutInflater inflater) {
        super(context, -1, items);
        this.mItems = items;
        this.mInflater = inflater;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.verbindungen_adapter, null);
        final Verbindung verbindung = (Verbindung) mItems.get(pos);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        ((TextView) convertView.findViewById(R.id.zeit)).setText(formatter.format(verbindung.getZeit()).toString());
        ((TextView) convertView.findViewById(R.id.dauer)).setText(verbindung.getDauer().substring(3, 8));
        ((TextView) convertView.findViewById(R.id.gleis)).setText(verbindung.getGleis());
        ((TextView) convertView.findViewById(R.id.beschreibung)).setText(verbindung.getTransportmittel());
        return convertView;
    }
}
