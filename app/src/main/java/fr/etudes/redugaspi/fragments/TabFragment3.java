package fr.etudes.redugaspi.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.IndianAdapter;
import fr.etudes.redugaspi.models.Kindergarten;


public class TabFragment3 extends Fragment implements IListenItem {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment_tab1
        final View view = inflater.inflate(R.layout.fragment_tab3, container, false);

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
        Button callButton = view.findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0606060606", null));
                startActivity(callIntent);
            }});

        Button mailButton = view.findViewById(R.id.mailButton);
        mailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:" + "0606060606" + "?subject=" + "Salut" + "&body=" + "");
                emailIntent.setData(data);
                startActivity(emailIntent);
            }
        });

        //get ListView indians and cowboys

        ListView indiansListView = view.findViewById(R.id.listViewIndians);

        //adapt the ListView with data adapters
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
