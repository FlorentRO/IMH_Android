package fr.etudes.redugaspi.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.IndianAdapter;
import fr.etudes.redugaspi.models.Kindergarten;
import fr.etudes.redugaspi.models.Users;


public class TabFragment3 extends Fragment implements IListenItem {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        List<Users> users = new ArrayList<Users>();
                users.add(new Users("madame",0606060600));
                users.add(new Users("monsieur",0706060600));
                users.add(new Users("florent",0606060600));
                users.add(new Users("aldric",0606060600));
                users.add(new Users("couette",0006060600));

        IndianAdapter indiansAdapter = new IndianAdapter(getContext(), Kindergarten.getListTwo());

        Button addAmi = view.findViewById(R.id.addAmi);
        addAmi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setTitle("Ajouter un ami");
                builder.setMessage("Qui voulez vous ajouter ?");
                builder.setNeutralButton("Annuler", null);
                builder.setPositiveButton("Ajouter",null);
                builder.show();

            }});

        EditText recherche = view.findViewById(R.id.textView);

        //get ListView indians and cowboys

        ListView indiansListView = view.findViewById(R.id.listViewIndians);

        //adapt the ListView with data adapters
        indiansListView.setAdapter(indiansAdapter);
        indiansListView.setTextFilterEnabled(true);

        //--> ADD : listen events on the adapter
        indiansAdapter.addListener(this);

        recherche.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
                //activity.this.adapter.getFilter().filter(arg0);
            }
        });
        return view;
    }


    /**
     * because this TabFragment implements IListenItem...
     * @param name item clicked
     */
    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("INFORMATION - "+name);
        builder.setMessage("Que voulez vous faire ?");
        builder.setNeutralButton("Rien", null);
        builder.setPositiveButton("Appeler",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0606060606", null));
                        startActivity(callIntent);
                    }
                });
        builder.setNegativeButton("SMS",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "0606060606", null)));
                    }
                });
        builder.show();
    }
}
