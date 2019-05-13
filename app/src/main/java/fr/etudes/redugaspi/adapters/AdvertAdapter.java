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
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.fragments.IListenItem;
import fr.etudes.redugaspi.models.Advert;
import fr.etudes.redugaspi.models.ProductName;
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

        TextView adTitle = layoutItem.findViewById(R.id.price_product);
        TextView nameShop = layoutItem.findViewById(R.id.name_shop);

        Advert advert = (Advert) listView.get(position);

        String name = "";
        ProductName product = Database.getNames().getFirst(x -> x.getBarcode().equals(advert.getProduct().getBarCode()));
        if(product != null){
            name = product.getName();
        }

        adTitle.setText(name +" quantité : " +advert.getProduct().getQuantity() + " - périme le "
                + advert.getProduct().getDate());
        adTitle.setTag(position);

        //dateP.setText(" périme dans "+ advert.getProduct().getDate() +" jour(s)");
        //dateP.setTag(position);

        nameShop.setText(advert.getShop());
        nameShop.setTag(position);


        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}
