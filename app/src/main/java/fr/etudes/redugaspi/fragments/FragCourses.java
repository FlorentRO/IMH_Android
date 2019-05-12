package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.activities.LiveBarcodeScanningActivity;
import fr.etudes.redugaspi.adapters.CoursesAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.ProductCourses;

public class FragCourses extends Fragment implements IListenItem {
    List<ProductCourses> products;
    EditText searchText;
    ListView productListView;
    Button addProduct;
    CoursesAdapter adapter;


    public static FragCourses newInstance() {
        return (new FragCourses());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_courses, container, false);

        products = Database.getCourses().getAll();

        adapter = new CoursesAdapter(getContext(), products);
        searchText = view.findViewById(R.id.prd_search);
        productListView = view.findViewById(R.id.lst_product);
        addProduct = view.findViewById(R.id.AjoutProduit);
        adapter.addListener(this);
        productListView.setAdapter(adapter);
        productListView.setTextFilterEnabled(true);

        addProduct.setOnClickListener(v -> onClickAdd(null));

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().toLowerCase();

                products = Database.getCourses().get(p -> p.getproductName().toLowerCase().contains(text));
                products.sort((p1, p2)->p2.getQuantity()-p1.getQuantity());
                adapter = new CoursesAdapter(getContext(), products);
                adapter.addListener(FragCourses.this);
                productListView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        products = Database.getCourses().getAll();
        products.sort((p1, p2)->p2.getQuantity()-p1.getQuantity());
        adapter = new CoursesAdapter(getContext(), products);
        adapter.addListener(this);

        productListView.setAdapter(adapter);

        searchText.setText("");
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

        if(name != null) {
            productname.setText(name);
            int p = Database.getCourses().getFirst(x->x.getproductName().equals(name)).getQuantity();
            productquantity.setText(String.valueOf(p));
        }
        builder.setPositiveButton("Ajout", (dialog, which) -> {
            int quantity=Integer.parseInt(productquantity.getText().toString());
            if (!productname.getText().toString().equals("") && quantity >= 0) {
                ProductCourses newProduct = new ProductCourses(productname.getText().toString(), quantity);
                ProductCourses match = Database.getCourses().getFirst(x->x.getproductName().equals(newProduct.getproductName()));
                if (match != null) {
                    Database.getCourses().remove(match);
                    if (name != null){newProduct.setQuantity(quantity);}
                    else {newProduct.setQuantity(match.getQuantity()+quantity);}
                }
                if (newProduct.getQuantity() <=0) {Database.getCourses().remove(newProduct);}
                else { Database.getCourses().add(newProduct); }
                if (newProduct.getQuantity() >=10) { tooMany(name);}
                onResume();
            } else {
                onClickAdd(name);
            }
        });
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }

    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Modification - "+name);
        builder.setMessage("Que voulez-vous faire ?");
        ProductCourses currentproduct = new ProductCourses(name, -1);
        ProductCourses match = Database.getCourses().getFirst(x->x.equals(currentproduct));

        builder.setPositiveButton("Modifier", (dialog, which) -> {
            onClickAdd(name);
        });
        builder.setNegativeButton("Supprimer", (dialog, which) -> {
            Database.getCourses().remove(match);
            onResume();
        });
        builder.setNeutralButton("Scanner", (dialog, which) -> {
            Database.getCourses().remove(match);
            askDatePopup(getContext());
        });
        builder.show();
    }


    private void askDatePopup(Context context) {
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

    private void tooMany(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ATTENTION");
        builder.setMessage("Vous avez beaucoup de " + name + " dans votre liste de courses");
        builder.setPositiveButton("En Supprimer", (dialog, which) -> {
            onClickAdd(name);
        });
        builder.setNegativeButton("Continuer", null);
        builder.show();
    }
}
