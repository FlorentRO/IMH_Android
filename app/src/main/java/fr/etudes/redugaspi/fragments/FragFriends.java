package fr.etudes.redugaspi.fragments;

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
import java.util.stream.Collectors;
import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.FriendAdapter;
import fr.etudes.redugaspi.models.User;

public class FragFriends extends Fragment implements IListenItem {
    List<User> users = new ArrayList<User>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_friends, container, false);

        //Liste d'ami de base
        users.add(new User("Corinne Renardo",0606060600));
        users.add(new User("Bertrand Dupont",0706060600));
        users.add(new User("Florent Kinowa",0606060600));
        users.add(new User("Aldric Neogeekmo",0606060600));
        users.add(new User("Couette Inaccoutumé",0006060600));
        users.add(new User("William Daubard",0006060600));
        users.add(new User("Aelita Stone",0006060600));
        users.add(new User("Harry Potter",0006060600));
        users.add(new User("Voajaje Travajer",0006060600));
        users.add(new User("Jaiplus Didée",0006060600));
        users.add(new User("Encore Undernier",0006060600));

        //Add un ami
        Button btnAddFriend = view.findViewById(R.id.btn_add_friend);
        btnAddFriend.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FragFriends.this.getContext());
            final EditText input = new EditText(FragFriends.this.getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setTitle("Ajouter un ami");
            builder.setMessage("Qui voulez vous ajouter ?");
            builder.setNeutralButton("Annuler", null);
            builder.setPositiveButton("Ajouter", (dialog, id) -> {
                addAmi(input.toString());
            });
            builder.show();
        });

        //Liste ami dans la liste
        FriendAdapter friendAdapter= new FriendAdapter(getContext(), users.stream().map(User::getPseudo).collect(Collectors.toList()));
        EditText searchText = view.findViewById(R.id.txt_search_friend);
        ListView friendsListView = view.findViewById(R.id.lst_friends);
        friendsListView.setAdapter(friendAdapter);
        friendsListView.setTextFilterEnabled(true);
        friendAdapter.addListener(this);


        searchText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
            public void afterTextChanged(Editable arg0) { }
        });
        return view;
    }

    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("INFORMATION - "+name);
        builder.setMessage("Que voulez vous faire ?");
        builder.setNeutralButton("Rien", null);
        builder.setPositiveButton("Appeler",
                (dialog, which) -> {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0606060606", null));
                    startActivity(callIntent);
                });
        builder.setNegativeButton("SMS",
                (dialog, which) -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "0606060606", null))));
        builder.show();
    }

    public void addAmi(String input){
        users.add(new User(input,0606060600));
    }
}
