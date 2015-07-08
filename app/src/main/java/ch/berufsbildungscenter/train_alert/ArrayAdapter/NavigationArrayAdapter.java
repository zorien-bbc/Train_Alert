package ch.berufsbildungscenter.train_alert.ArrayAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ch.berufsbildungscenter.train_alert.R;

/**
 * Created by zorien on 08.07.2015.
 */
public class NavigationArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] fragmentName;
    private final int[] imageId;
    public NavigationArrayAdapter(Activity context,
                      String[] fragmentName, int[] imageId) {
        super(context, R.layout.navigation_adapter, fragmentName);
        this.context = context;
        this.fragmentName = fragmentName;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.navigation_adapter, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtSecond);
        txtTitle.setTextColor(Color.WHITE);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(fragmentName[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
