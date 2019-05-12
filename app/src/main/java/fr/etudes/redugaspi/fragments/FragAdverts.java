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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.AdvertAdapter;
import fr.etudes.redugaspi.models.Advert;
import fr.etudes.redugaspi.models.Product;

import java.util.ArrayList;
import java.util.List;

public class FragAdverts extends Fragment implements IListenItem, OnMapReadyCallback {
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
        final MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(43.617996, 7.075185)).title("Casino Supermarché").snippet("20% sur les Cordons bleus Père Dodu !"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(43.604116, 7.089257)).title("Carrefour Antibes").snippet("2 paquets de jambon achetés 1 offert !"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(43.599428, 7.104236)).title("Lidle Antibes").snippet("25% sur le saucisson Justin Bridou !"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(43.592544, 7.057965)).title("Leclerc").snippet("15% sur la pastaBox Bolognaise !"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(43.574443, 7.091099)).title("E.Leclerc Antibes les pins").snippet("1 paquet de yaourts aux fruitx acheté 1 autre offert !"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.615781, 7.071805), 10));

    }
    @Override
    public void onClickName(String name) {
        Toast.makeText(getContext(), "...", Toast.LENGTH_LONG).show();
    }
}




