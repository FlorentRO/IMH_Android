package fr.etudes.redugaspi.adapters;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.fragments.FragProducts;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductName;
import fr.etudes.redugaspi.services.DownloadManager;

public class ProductAdapter extends BaseAdapter {
    private List listView;
    private LayoutInflater mInflater;
    private FragProducts listViewListen;

    public ProductAdapter(Context context, List listView) {
        this.listView = listView;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (listView!=null)
            return listView.size();
        return 0;
    }

    public Object getItem(int position) {
        return listView.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.product_row_layout, parent, false);

        ImageView image = layoutItem.findViewById(R.id.prd_image);
        TextView name = layoutItem.findViewById(R.id.prd_name);
        TextView quantity = layoutItem.findViewById(R.id.prd_quantity);
        TextView date = layoutItem.findViewById(R.id.prd_date);

        Product product = (Product) listView.get(position);

        try {
            DownloadManager.getProductData(parent.getContext(), product.getBarCode());
            ProductName productName = Database.getNames().getFirst(x -> x.getBarcode().equals(product.getBarCode()));
            if (productName != null)
                name.setText(productName.getName());

            Bitmap bitmap = DownloadManager.getImage(parent.getContext(), product.getBarCode());
            image.setImageBitmap(bitmap);


            quantity.setText(String.format("x%s", product.getQuantity()));
            name.setOnClickListener(v -> {
                if (this.listViewListen != null && productName != null)
                    listViewListen.onClickProduct(product);
            });

            date.setText(product.getDate());
            name.setTag(position);
            quantity.setTag(position);
            date.setTag(position);

            long productMilis = product.getDateMillis();
            long currentTime = System.currentTimeMillis();
            long compared = productMilis - currentTime;
            if (compared <= (AlarmManager.INTERVAL_DAY * 2)) {
                date.setBackgroundColor(Color.parseColor("#FFFF6B"));
                if (compared <= AlarmManager.INTERVAL_DAY) {
                    date.setBackgroundColor(Color.parseColor("#FFA500"));
                    if (compared <= 0) {
                        date.setBackgroundColor(Color.parseColor("#FF0033"));
                        if (compared <= -AlarmManager.INTERVAL_DAY){
                            layoutItem.setBackgroundColor(Color.parseColor("#CBBEB5"));
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Database.getProducts().remove(product);
        }
        return layoutItem;
    }

    public void addListener(FragProducts itemToListen) {
        listViewListen = itemToListen;
    }
}


