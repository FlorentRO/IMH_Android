package fr.etudes.prepademoihm.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.etudes.prepademoihm.R;
import fr.etudes.prepademoihm.models.Kindergarten;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment1 extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment_tab1
        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        //get valid button from fragment_tab1.xml
        Button valid = view.findViewById(R.id.valid);

        //update nbCowboys TextView (present in fragment_tab1.xml
        ((TextView) view.findViewById(R.id.nbCowboys)).setText(getString(R.string.quantity) + Kindergarten.sizeOfTwo());


        //set OnClick Listener on valid button
                //add an "indian" to the Kindergarten
                //update TextView nbIndians
                //empty EditText editTextName
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kindergarten.add(Kindergarten.TEAM2, ((EditText) view.findViewById(R.id.editTextName)).getText().toString());
                ((TextView) view.findViewById(R.id.nbCowboys)).setText(getString(R.string.quantity) + Kindergarten.sizeOfTwo());
                ((EditText) view.findViewById(R.id.editTextName)).setText("");
            }
        });
        return view;
    }
}
