package fr.etudes.redugaspi.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.fragments.IListenItem;
import fr.etudes.redugaspi.models.Product;

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

        TextView name = layoutItem.findViewById(R.id.prd_name);
        TextView quantity = layoutItem.findViewById(R.id.prd_quantity);
        TextView date = layoutItem.findViewById(R.id.prd_date);

        name.setText(((Product)listView.get(position)).getName());
        name.setTag(position);

        quantity.setText("x"+((Product)listView.get(position)).getQuantity());
        quantity.setTag(position);

        date.setText(((Product)listView.get(position)).getDate());
        date.setTag(position);

        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}


