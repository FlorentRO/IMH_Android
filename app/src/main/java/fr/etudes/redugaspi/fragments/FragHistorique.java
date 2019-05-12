package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.activities.LiveBarcodeScanningActivity;
import fr.etudes.redugaspi.adapters.HistoriqueAdapter;
import fr.etudes.redugaspi.adapters.ProductAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;
import fr.etudes.redugaspi.models.ProductName;

public class FragHistorique extends Fragment implements IListenItem {
    List<Product> products;
    HistoriqueAdapter adapter;
    EditText searchText;
    ListView productListView;




    public static FragHistorique newInstance() {
        return (new FragHistorique());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_historique, container, false);

        products = Database.getHistory().getAll();

        adapter = new HistoriqueAdapter(getContext(), products);
        searchText = view.findViewById(R.id.prd_search);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().toLowerCase();

                products = Database.getHistory().get(p -> {
                    ProductName pname = Database.getNames().getFirst(pn -> pn.getBarcode().equals(p.getBarCode()));
                    String name;
                    if (pname != null)
                        name = pname.getName();
                    else
                        name = "";
                    return name.toLowerCase().contains(text);
                });
                products.sort((p1, p2)->Product.compareDates(p2, p1));
                adapter = new HistoriqueAdapter(getContext(), products);
                productListView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        productListView = view.findViewById(R.id.lst_product);
        productListView.setTextFilterEnabled(true);


        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        products = Database.getHistory().getAll();
        products.sort((p1, p2)->Product.compareDates(p2, p1));
        adapter = new HistoriqueAdapter(getContext(), products);
        adapter.addListener(this);

        productListView.setAdapter(adapter);

        searchText.setText("");
    }

    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ajout - "+ name);
        builder.setMessage("Ajouter le produit dans votre liste de courses");
        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            if (name != null) {
                ProductCourses newProduct = new ProductCourses(name, 1);
                ProductCourses match = Database.getCourses().getFirst(x->x.equals(newProduct));
                if (match != null) {
                    newProduct.setQuantity(match.getQuantity()+1);
                    Database.getCourses().remove(match);
                    Database.getCourses().add(newProduct);
                } else {
                    Database.getCourses().add(newProduct);
                }
            }
        });
        builder.show();
    }
}
