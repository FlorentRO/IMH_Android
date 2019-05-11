package fr.etudes.redugaspi.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.fragments.IListenItem;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;
import fr.etudes.redugaspi.models.ProductName;
import fr.etudes.redugaspi.services.DownloadManager;

public class CoursesAdapter extends BaseAdapter {

    private List listView;
    private LayoutInflater mInflater;
    private IListenItem listViewListen;

    public CoursesAdapter(Context context, List listView) {
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
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.product_courses_row_layout, parent, false);

        TextView name = layoutItem.findViewById(R.id.prd_name);
        TextView quantity = layoutItem.findViewById(R.id.prd_quantity);

        ProductCourses product = (ProductCourses) listView.get(position);

        name.setText(product.getproductName());
        quantity.setText(String.format("x%s", product.getQuantity()));

        name.setTag(position);
        quantity.setTag(position);

        name.setOnClickListener(v -> listViewListen.onClickName(name.getText().toString()));


        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) { listViewListen = itemToListen;
    }
}
