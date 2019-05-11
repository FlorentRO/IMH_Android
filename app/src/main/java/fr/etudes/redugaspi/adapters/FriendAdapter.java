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

public class FriendAdapter extends BaseAdapter {
    private List listView;
    private LayoutInflater mInflater;
    private IListenItem listViewListen;

    public FriendAdapter(Context context, List listView) {
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
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.friend_row_layout, parent, false);
        TextView name = layoutItem.findViewById(R.id.prd_date);

        name.setText(String.format("%s", listView.get(position)));
        Log.d("ADAPTER","position="+position);
        Log.d("ADAPTER","name="+listView.get(position));

        name.setTag(position);

        name.setOnClickListener(v -> {
            Integer position1 = (Integer)v.getTag();
            listViewListen.onClickName(listView.get(position1).toString());
        });
        return layoutItem;
    }

    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}


