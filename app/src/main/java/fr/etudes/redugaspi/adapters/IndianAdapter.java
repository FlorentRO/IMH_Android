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

/**
 * Modify by F. Rallo on 11/02/2018.
 */
public class IndianAdapter extends BaseAdapter {
    private List listView;
    private LayoutInflater mInflater; //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private IListenItem listViewListen;

    /**
     * normal contructor
     * @param context le contexte de l'activité
     */
    public IndianAdapter(Context context, List listView) {
        this.listView = listView;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * needed override
     * @return nb of items to be adapted
     **/
    public int getCount() {
        return listView.size();
    }

    /**
     * surcharge obligatoire de BaseAdapter
     */
    public Object getItem(int position) {
        return listView.get(position);
    }

    /**
     * surcharge obligatoire de BaseAdapter
     */
    public long getItemId(int position) {
        return position;
    }


//--------------------------------------------------------------


    public View getView(int position, View convertView, ViewGroup parent) {
        //(1) : inflate layout
        ConstraintLayout layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.indian_layout, parent, false);


        //(2) : get widgets
        TextView name = layoutItem.findViewById(R.id.name);


        //(3) : set values to widget
        name.setText(listView.get(position)+"");
        Log.d("ADAPTER","position="+position);
        Log.d("ADAPTER","name="+listView.get(position));

        //(4) : set tag as index
        name.setTag(position);

        //(5) : set listener on selected item
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                //TODO vérifier si unécouteur==null ou si maListeDeDiplome==null
                listViewListen.onClickName(listView.get(position)+"");
            }
        });

        return layoutItem;
    }






    public void addListener(IListenItem itemToListen) {
        listViewListen = itemToListen;
    }
}


