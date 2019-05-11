package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.activities.LiveBarcodeScanningActivity;
import fr.etudes.redugaspi.activities.ManagementActivity;
import fr.etudes.redugaspi.adapters.CoursesAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;

public class FragCourses extends Fragment implements IListenItem {

    public static FragCourses newInstance() {
        return (new FragCourses());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_courses, container, false);

        List<ProductCourses> products = Database.getCourses().getAll();

        CoursesAdapter adapter = new CoursesAdapter(getContext(), products);
        EditText searchText = view.findViewById(R.id.prd_search);
        ListView productListView = view.findViewById(R.id.lst_product);
        Button addProduct = view.findViewById(R.id.AjoutProduit);

        productListView.setAdapter(adapter);
        productListView.setTextFilterEnabled(true);
        adapter.addListener(this);

        addProduct.setOnClickListener(v -> onClickAdd(null));

        return view;
    }
    public void onClickAdd (String name){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ajout d'un produit");
        builder.setMessage("Renseigner votre produit");
        View view = inflater.inflate(R.layout.product_courses_add_layout, null);
        builder.setView(view);
        EditText productname = view.findViewById(R.id.product_name);
        EditText productquantity = view.findViewById(R.id.product_quantity);
        productquantity.setTransformationMethod(null);

        if(name != null){
            productname.setText(name);
            productquantity.setText("0");
        }
        builder.setPositiveButton("Ajout", (dialog, which) -> {

            if (productname != null && productquantity != null ) {
                int quantity=Integer.parseInt(productquantity.getText().toString());

                ProductCourses newProduct = new ProductCourses(productname.getText().toString(), quantity);
                ProductCourses match = Database.getCourses().getFirst(x->x.equals(newProduct));
                if (match != null) {
                    newProduct.setQuantity(match.getQuantity()+quantity);
                    Database.getCourses().remove(match);
                    Database.getCourses().add(newProduct);
                } else {
                    Database.getCourses().add(newProduct);
                }
            }else {
                productname.setBackgroundColor(Color.RED);
                productquantity.setBackgroundColor(Color.RED);
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Modification - "+name);
        builder.setMessage("Modifier ou Scanner le produit");
        ProductCourses currentproduct = new ProductCourses(name, 6);
        ProductCourses match = Database.getCourses().getFirst(x->x.equals(currentproduct));

        builder.setPositiveButton("Modifier", (dialog, which) -> {
            onClickAdd(name);
        });
        builder.setNegativeButton("Supprimer", (dialog, which) -> {
            Database.getCourses().remove(match);
        });
        builder.setNeutralButton("Scanner", (dialog, which) -> {
            Database.getCourses().remove(match);
            askDatePopup(getContext());
        });
        builder.show();
    }


    private void askDatePopup(Context context) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context);
        DatePicker picker = dialog.getDatePicker();
        dialog.setTitle("Date de péremption");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Valider", (d, w) -> {
            Intent intent = new Intent(context, LiveBarcodeScanningActivity.class);
            Bundle extra = new Bundle();
            extra.putInt("day", picker.getDayOfMonth());
            extra.putInt("month", picker.getMonth()+1);
            extra.putInt("year", picker.getYear());
            intent.putExtras(extra);
            startActivity(intent);
        });
        dialog.show();
    }
}
