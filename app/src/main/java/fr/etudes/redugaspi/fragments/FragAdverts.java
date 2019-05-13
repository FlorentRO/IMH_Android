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
import fr.etudes.redugaspi.services.DownloadManager;

import java.util.ArrayList;
import java.util.List;

public class FragAdverts extends Fragment implements IListenItem, OnMapReadyCallback {
    private View view;
    ListView mListView;
    ArrayList<Advert> advert;
    GoogleMap googleMap;

    public static FragAdverts newInstance() {
        return (new FragAdverts());
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_adverts, container, false);

        DownloadManager.getProductData(getContext(),"3116430210371");
        DownloadManager.getProductData(getContext(),"3564700332856");
        DownloadManager.getProductData(getContext(),"4002359009471");
        DownloadManager.getProductData(getContext(),"3250390000853");

        ArrayList<Advert> adverts = new ArrayList<>();
        adverts.add(new Advert(new Product("3116430210371", 1, 1, 1, 1975), "2.5 € / kg", "Casino Supermarché", 43.617996, 7.075185, "20% sur les Cordons bleus Père Dodu !"));
        adverts.add(new Advert(new Product("3564700332856", 2, 2, 2, 1970),"2.5 € / kg", "Carrefour Antibes", 43.604116, 7.089257, "2 paquets de jambon achetés 1 offert !"));
        adverts.add(new Advert(new Product("4002359009471", 3, 3, 3, 1970),"2.5 € / kg", "Lidle Antibes", 43.599428, 7.104236, "25% sur le saucisson Justin Bridou !"));
        adverts.add(new Advert(new Product("3250390000853", 4, 4, 4, 1970),"2.5 € / kg", "Leclerc", 43.592544, 7.057965, "15% sur la pastaBox Bolognaise !"));
        adverts.add(new Advert(new Product("3116430210371", 5, 5, 5, 1970),"2.5 € / kg", "E.Leclerc Antibes les pins", 43.574443, 7.091099, "1 paquet de yaourts aux fruitx acheté 1 autre offert !"));


        advert = adverts;

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
        this.googleMap = googleMap;
        for(Advert ad : advert){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(ad.getLatitude(), ad.getLongitude())).title(ad.getShop()).snippet(ad.getDescAdd()));
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(advert.get(0).getLatitude(), advert.get(0).getLongitude()), 10));

    }
    public void onClickAdvert(Advert ad) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ad.getLatitude(), ad.getLongitude()), 17));
    }

    @Override
    public void onClickName(String name) {
    }
}




