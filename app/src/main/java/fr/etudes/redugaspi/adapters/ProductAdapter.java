package fr.etudes.redugaspi.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.fragments.IListenItem;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.services.DownloadManager;

public class ProductAdapter extends BaseAdapter {
    private List listView;
    private LayoutInflater mInflater;
    private IListenItem listViewListen;

    public ProductAdapter(Context context, List listView) {
        this.listView = listView;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listView.size();
    }

    public Object getItem(int position) {
        return listView.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.product_row_layout, parent, false);

        ImageView image = layoutItem.findViewById(R.id.prd_image);
        TextView name = layoutItem.findViewById(R.id.prd_name);
        TextView quantity = layoutItem.findViewById(R.id.prd_quantity);
        TextView date = layoutItem.findViewById(R.id.prd_date);

        Product product = (Product) listView.get(position);

        JSONObject json = DownloadManager.getProductData(parent.getContext(), product.getBarCode());

        try {
            name.setText(json.getJSONObject("product").getString("product_name"));
            Bitmap bitmap = DownloadManager.getImage(parent.getContext(), product.getBarCode());
            image.setImageBitmap(bitmap);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }
        name.setTag(position);

        quantity.setText("x"+product.getQuantity());
        quantity.setTag(position);

        date.setText(product.getDate());
        date.setTag(position);

        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}


