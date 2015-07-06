package ch.berufsbildungscenter.train_alert;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.berufsbildungscenter.train_alert.Database.Favoriten;
import ch.berufsbildungscenter.train_alert.Database.FavoritenDAO;


public class FavoritenView extends AppCompatActivity implements ActionBar.TabListener {

    private FavoritenDAO favoritenDatabase;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriten_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(getString(R.string.favoriten)).setTabListener(this), true);
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.route)).setTabListener(this), false
        );
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.alarme)).setTabListener(this), false);
        actionBar.setHomeButtonEnabled(false);
        fillList();
    }

    public void fillList() {


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView favListView = (ListView) findViewById(R.id.listViewOrte);
        favoritenDatabase = new FavoritenDAO(getApplicationContext());

        final List<Favoriten> favoritens = favoritenDatabase.getAllFavoriten();
        if (favoritens.size() != 0) {
            for (int i = 0; i < favoritens.size(); i++) {
                arrayAdapter.add(favoritens.get(i).getName());
            }

            favListView.setAdapter(arrayAdapter);
            favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int pos, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
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
                            fillList();
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
        favoritenDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), MainEinstellungen.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {


        if (tab.getPosition() == 1) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else if(tab.getPosition()==2){
            Intent intent = new Intent(getApplicationContext(), AlarmView.class);
            startActivity(intent);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
