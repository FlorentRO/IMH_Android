package fr.etudes.redugaspi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.ProductAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;

public class FragProducts extends Fragment implements IListenItem {

    public static FragProducts newInstance() {
        return (new FragProducts());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_products, container, false);

        List<Product> products = Database.getProducts().getAll();

        ProductAdapter adapter = new ProductAdapter(getContext(), products);
        ListView productListView = view.findViewById(R.id.lst_product);

        productListView.setAdapter(adapter);
        productListView.setTextFilterEnabled(true);

        adapter.addListener(this);



        return view;
    }

    @Override
    public void onClickName(String name) {
        Toast.makeText(getContext(), "...", Toast.LENGTH_LONG).show();
    }
}
