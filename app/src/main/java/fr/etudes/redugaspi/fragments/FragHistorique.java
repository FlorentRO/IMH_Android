package fr.etudes.redugaspi.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.etudes.redugaspi.R;

public class FragHistorique extends Fragment {

    public static FragHistorique newInstance() {
        return (new FragHistorique());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_historique, container, false);

        //code here

        return view;
    }
}
