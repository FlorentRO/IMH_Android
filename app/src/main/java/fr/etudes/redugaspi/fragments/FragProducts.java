package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.activities.LiveBarcodeScanningActivity;
import fr.etudes.redugaspi.adapters.ProductAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;
import fr.etudes.redugaspi.models.ProductName;

public class FragProducts extends Fragment implements IListenItem {
    ProductAdapter adapter;
    ListView productListView;
    EditText search;
    Button button;
    List<Product> products = Database.getProducts().getAll().stream().sorted(Product::compareDates).collect(Collectors.toList());

    public static FragProducts newInstance() {
        return (new FragProducts());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_products, container, false);

        search = view.findViewById(R.id.prd_search);
        productListView = view.findViewById(R.id.lst_product);
        productListView.setTextFilterEnabled(true);
        button = view.findViewById(R.id.AjoutProduit);
        button.setOnClickListener(v -> askDatePopup(getContext()));
        adapter = new ProductAdapter(getContext(), products);
        adapter.addListener(this);
        productListView.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().toLowerCase();

                products = Database.getProducts().get(p -> {
                    ProductName pname = Database.getNames().getFirst(pn -> pn.getBarcode().equals(p.getBarCode()));
                    String name;
                    if (pname != null)
                        name = pname.getName();
                    else
                        name = "";
                    return name.toLowerCase().contains(text);
                });
                products.sort(Product::compareDates);
                adapter = new ProductAdapter(getContext(), products);
                adapter.addListener(FragProducts.this);
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

        products = Database.getProducts().getAll();
        products.sort(Product::compareDates);
        adapter = new ProductAdapter(getContext(), products);
        adapter.addListener(this);

        productListView.setAdapter(adapter);

        search.setText("");
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

    @Override
    public void onClickName(String name) {}

    public void onClickProduct(Product product) {
        String name = Database.getNames().getFirst(x->x.getBarcode().equals(product.getBarCode())).getName();

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(product.getQuantity() + " x " + name);
        builder.setMessage("Que faire:");
        View view = inflater.inflate(R.layout.product_add_layout, null);
        builder.setView(view);
        EditText productquantity = view.findViewById(R.id.product_quantity);
        productquantity.setTransformationMethod(null);
        productquantity.setText("1");
        builder.setPositiveButton("Consommer", (dialog, which) -> {
            if (!productquantity.getText().toString().equals("")) {
                int quantity = Integer.parseInt(productquantity.getText().toString());
                if (quantity > 0) {
                    Product match = Database.getProducts().getFirst(x -> x.equals(product));
                    if (match != null) {
                        Database.getProducts().remove(match);
                        match.setQuantity(match.getQuantity() - quantity);
                        if (match.getQuantity() > 0) {
                            Database.getProducts().add(match);
                        }
                    }
                    onResume();
                }
            }
        });

        builder.setNegativeButton("Jeter", (dialog, which) -> {
            if (!productquantity.getText().toString().equals("")) {
                int quantity = Integer.parseInt(productquantity.getText().toString());
                if (quantity > 0) {
                    Product match = Database.getProducts().getFirst(x -> x.equals(product));
                    if (match != null) {
                        Database.getProducts().remove(match);
                        match.setQuantity(match.getQuantity() - quantity);
                        if (match.getQuantity() > 0) {
                            Database.getProducts().add(match);
                        }
                        Calendar c = Calendar.getInstance();
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        int month = c.get(Calendar.MONTH)+1;
                        int year = c.get(Calendar.YEAR);
                        Product newProduct = new Product(match.getBarCode(), quantity, day, month, year);
                        Product match2 = Database.getHistory().getFirst(x -> x.getBarCode().equals(newProduct.getBarCode()));
                        if (match2 != null) {
                            Database.getHistory().remove(match2);
                            newProduct.setQuantity(match2.getQuantity() + quantity);
                            Database.getHistory().add(newProduct);
                        } else {
                            Database.getHistory().add(newProduct);
                        }
                    }
                    onResume();
                }
            }
        });

        builder.setNeutralButton("Annuler", null);
        builder.show();
    }
}
