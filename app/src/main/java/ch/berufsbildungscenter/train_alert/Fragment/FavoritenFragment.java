package ch.berufsbildungscenter.train_alert.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Favoriten;
import ch.berufsbildungscenter.train_alert.Database.FavoritenDAO;
import ch.berufsbildungscenter.train_alert.R;

/**
 * Created by zorien on 07.07.2015.
 */
public class FavoritenFragment extends Fragment{
    FavoritenDAO favoritenDatabase;
    ListView favListView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.favoriten_fragment, container, false);
        favListView = (ListView) view.findViewById(R.id.listViewFavoriten);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        favoritenDatabase = new FavoritenDAO(getActivity().getApplicationContext());
        final List<Favoriten> favoritens = favoritenDatabase.getAllFavoriten();
        if (favoritens.size() != 0) {
            for (int i = 0; i < favoritens.size(); i++) {
                arrayAdapter.add(favoritens.get(i).getName());
            }
            favListView.setAdapter(arrayAdapter);
            favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int pos, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(favoritens.get(pos).getName() + " wirklich aus den Favoriten l\u00f6schen?");
                    alert.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            favoritenDatabase.deleteFavoriten(favoritens.get(pos));
                            refreshFragment();
                        }
                    });


                    alert.show();

                    Log.v("long clicked", "pos: " + pos);

                    return true;
                }
            });
        } else {
            arrayAdapter.add(getString(R.string.keineFavoriten));
            favListView.setAdapter(arrayAdapter);
        }

        return view;
    }
    public void refreshFragment(){

        Fragment frg = null;
        frg = this;
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}
