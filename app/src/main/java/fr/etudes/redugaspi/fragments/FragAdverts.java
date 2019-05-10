package fr.etudes.redugaspi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.AdvertAdapter;
import fr.etudes.redugaspi.models.Advert;
import fr.etudes.redugaspi.models.Product;

import java.util.ArrayList;
import java.util.List;

public class FragAdverts extends Fragment implements IListenItem {
    private View view;
    ListView mListView;

    public static FragAdverts newInstance() {
        return (new FragAdverts());
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_adverts, container, false);

        ArrayList<Advert> adverts = new ArrayList<>();
        adverts.add(new Advert(new Product("3116430210371", 1, 1, 1, 1975), "2.5 € / kg"));
        adverts.add(new Advert(new Product("3116430210371", 2, 2, 2, 1970),"2.5 € / kg"));
        adverts.add(new Advert(new Product("3116430210371", 3, 3, 3, 1970),"2.5 € / kg"));
        adverts.add(new Advert(new Product("3116430210371", 4, 4, 4, 1970),"2.5 € / kg"));

        AdvertAdapter adapter = new AdvertAdapter(getContext(), adverts);

        ListView advertListView = view.findViewById(R.id.lst_advert);

        advertListView.setAdapter(adapter);
        advertListView.setTextFilterEnabled(true);

        adapter.addListener(this);

        return view;
    }
    @Override
    public void onClickName(String name) {
        Toast.makeText(getContext(), "...", Toast.LENGTH_LONG).show();
    }
}

