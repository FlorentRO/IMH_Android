package fr.etudes.redugaspi.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.fragments.IListenItem;
import fr.etudes.redugaspi.models.Advert;
import fr.etudes.redugaspi.services.DownloadManager;

public class AdvertAdapter extends BaseAdapter {

    private List listView;
    private LayoutInflater mInflater;
    private IListenItem listViewListen;

    public AdvertAdapter(Context context, List listView) {
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
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.advert_row_layout, parent, false);

        //TextView name = layoutItem.findViewById(R.id.name_product);
        TextView quantity = layoutItem.findViewById(R.id.price_product);
        //TextView dateP = layoutItem.findViewById(R.id.desc_product);
        TextView nameShop = layoutItem.findViewById(R.id.name_shop);

        Advert advert = (Advert) listView.get(position);
        //JSONObject json = DownloadManager.getProductData(parent.getContext(), advert.getProduct().getBarCode());

        /*try {
            name.setText(json.getJSONObject("product").getString("product_name"));
            Bitmap bitmap = DownloadManager.getImage(parent.getContext(), advert.getProduct().getBarCode());
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
        }

        name.setTag(position);*/

        quantity.setText("Gâteau au chocolat - x" + advert.getProduct().getQuantity() + " - périme le "
                + advert.getProduct().getDate());
        quantity.setTag(position);

        //dateP.setText(" périme dans "+ advert.getProduct().getDate() +" jour(s)");
        //dateP.setTag(position);

        nameShop.setText("Casino");
        nameShop.setTag(position);


        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}
