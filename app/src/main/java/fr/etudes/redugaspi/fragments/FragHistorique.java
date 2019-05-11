package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;

public class FragHistorique extends Fragment implements IListenItem {

    public static FragHistorique newInstance() {
        return (new FragHistorique());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_historique, container, false);

        List<Product> products = Database.getHistory().getAll();

        HistoriqueAdapter adapter = new HistoriqueAdapter(getContext(), products);
        EditText searchText = view.findViewById(R.id.prd_search);
        ListView productListView = view.findViewById(R.id.lst_product);

        productListView.setAdapter(adapter);
        productListView.setTextFilterEnabled(true);
        adapter.addListener(this);

        return view;
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
