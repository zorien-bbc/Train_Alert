package ch.berufsbildungscenter.train_alert.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ch.berufsbildungscenter.train_alert.R;

public class ZugPositionFragment extends Fragment {
    private WebView webView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.activity_zug_position_fragment, container, false);

        webView = (WebView) view.findViewById(R.id.zugposition);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://maps.vasile.ch/transit-sbb/");


        return view;
    }
}
