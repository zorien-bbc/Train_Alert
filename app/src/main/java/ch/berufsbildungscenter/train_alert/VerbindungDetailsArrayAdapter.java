package ch.berufsbildungscenter.train_alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zfehrn on 18.06.2015.
 */
public class VerbindungDetailsArrayAdapter extends ArrayAdapter<Verbindung> {

    LayoutInflater mInflater;
    private List<Verbindung> mItems;

    public VerbindungDetailsArrayAdapter(Context context, List<Verbindung> items, LayoutInflater inflater) {
        super(context, -1, items);
        this.mItems = items;
        this.mInflater = inflater;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.verbindung_details_adapter, null);
        final Verbindung verbindung = (Verbindung) mItems.get(pos);

        ((TextView) convertView.findViewById(R.id.vonLabel)).setText(verbindung.getBeschreibung());
        ((TextView) convertView.findViewById(R.id.nachLabel)).setText(verbindung.getBeschreibung());
        ((TextView) convertView.findViewById(R.id.vonZeitLabel)).setText(verbindung.getZeit());
        ((TextView) convertView.findViewById(R.id.bisZeitLabel)).setText(verbindung.getZeit());
        ((TextView) convertView.findViewById(R.id.bahnLabel)).setText(verbindung.getBeschreibung());

        return convertView;
    }
}
