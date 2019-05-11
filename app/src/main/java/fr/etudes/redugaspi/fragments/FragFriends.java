package fr.etudes.redugaspi.fragments;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    List<String> msgList = new ArrayList<String>();
    public static FragFriends newInstance() { return (new FragFriends()); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_friends, container, false);
        getContactList();

        //Liste ami dans la liste
        FriendAdapter friendAdapter= new FriendAdapter(getContext(), users.stream().map(User::getPseudo).collect(Collectors.toList()));

        ListView friendsListView = view.findViewById(R.id.lst_friends);
        friendsListView.setAdapter(friendAdapter);
        friendsListView.setTextFilterEnabled(true);
        friendAdapter.addListener(this);

        ListView msgListView = view.findViewById(R.id.lst_msg);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, msgList );
        msgListView.setAdapter(arrayAdapter);

        int size=users.size();
        if(size>=1) {String test1 = users.get(0).getPseudo()+" a partagé l'annonce : Gâteau au chocolat";msgList.add(test1);}
        if(size>=2) {String test2 = users.get(1).getPseudo()+" a commenté l'annonce : Gâteau au chocolat";msgList.add(test2);}
        if(size>=2) {String test3 = users.get(1).getPseudo()+" a acheté le produit : Haricot vert";msgList.add(test3);}
        if(size>=3) {String test3 = users.get(2).getPseudo()+" a acheté le produit : Haricot bleu";msgList.add(test3);}
        return view;
    }

    private void getContactList() {
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if (!haveDup(name,phoneNo)){
                            users.add(new User(name,phoneNo));
                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("INFORMATION - "+name);
        builder.setMessage("Que voulez vous faire ?");
        builder.setNeutralButton("Rien", null);
        String getNumber = "0";
        for(User d : users){
            if(d.getPseudo().equals(name)){
                getNumber = d.getNum();
            }
        }
        String finalGetNumber = getNumber;
        builder.setPositiveButton("Appeler",
                (dialog, which) -> {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", finalGetNumber, null));
                    startActivity(callIntent);
                });
        builder.setNegativeButton("SMS",
                (dialog, which) -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", finalGetNumber, null))));
        builder.show();
    }

    public boolean haveDup(String name, String phoneNo) {
        for (User d : users) {
            if (d.getPseudo().equals(name)) {
                return true;
            }
        }
        return false;
    }

}

