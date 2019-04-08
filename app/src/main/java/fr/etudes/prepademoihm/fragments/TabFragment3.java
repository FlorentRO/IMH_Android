package fr.etudes.prepademoihm.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.etudes.prepademoihm.R;
import fr.etudes.prepademoihm.adapters.IndianAdapter;
import fr.etudes.prepademoihm.models.Kindergarten;


public class TabFragment3 extends Fragment implements IListenItem {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment_tab1
        final View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        //get valid button from fragment_tab1.xml
        ((TextView) view.findViewById(R.id.nbChildren)).setText(getString(R.string.quantity) + Kindergarten.size());

        //create and arrayAdapter with a list "indians" and "cowboys"
        ArrayAdapter cowboysAdapter = new ArrayAdapter(    getContext(),
                                                            android.R.layout.simple_list_item_1,
                                                            Kindergarten.getListOne() );
/*--> change :
        ArrayAdapter indiansAdapter = new ArrayAdapter(    getContext(),
                                                            android.R.layout.simple_list_item_1,
                                                            Kindergarten.getListTwo() );
*/
        IndianAdapter indiansAdapter = new IndianAdapter(getContext(), Kindergarten.getListTwo());


        //get ListView indians and cowboys
        ListView cowboysListView = view.findViewById(R.id.listViewCowboys);
        ListView indiansListView = view.findViewById(R.id.listViewIndians);

        //adapt the ListView with data adapters
        cowboysListView.setAdapter(cowboysAdapter);
        indiansListView.setAdapter(indiansAdapter);

        //--> ADD : listen events on the adapter
        indiansAdapter.addListener(this);
        return view;
    }


    /**
     * because this TabFragment implements IListenItem...
     * @param name item clicked
     */
    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("INFORMATION");
        builder.setMessage("You have selected " + name + "'s indian");
        builder.setNeutralButton("ON", null);
        builder.show();
    }
}
