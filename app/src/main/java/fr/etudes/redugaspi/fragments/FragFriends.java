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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.adapters.CoursesAdapter;
import fr.etudes.redugaspi.adapters.FriendAdapter;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;
import fr.etudes.redugaspi.models.ProductName;
import fr.etudes.redugaspi.models.User;

public class FragFriends extends Fragment implements IListenItem {
    List<User> fullUsers = new ArrayList<>();
    List<User> users = new ArrayList<>();
    FriendAdapter friendAdapter;
    ListView friendsListView;
    EditText search;


    public static FragFriends newInstance() { return (new FragFriends()); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_friends, container, false);
        fullUsers = getContactList();
        friendsListView = view.findViewById(R.id.lst_friends);
        friendsListView.setTextFilterEnabled(true);
        search = view.findViewById(R.id.search_friend);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().toLowerCase();

                users = fullUsers.stream().filter(u -> u.getPseudo().toLowerCase().contains(text)).collect(Collectors.toList());
                friendAdapter = new FriendAdapter(getContext(), users.stream().map(User::getPseudo).collect(Collectors.toList()));
                friendAdapter.addListener(FragFriends.this);
                friendsListView.setAdapter(friendAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContactList();
        friendAdapter = new FriendAdapter(getContext(), users.stream().map(User::getPseudo).collect(Collectors.toList()));
        friendsListView.setAdapter(friendAdapter);
        search.setText("");
        friendAdapter.addListener(this);
    }

    private List<User> getContactList() {
        List<User> result = new ArrayList<>();
        ContentResolver cr = Objects.requireNonNull(getActivity()).getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
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
                    while (pCur != null && pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if (!haveDup(name)){
                            result.add(new User(name,phoneNo));
                        }
                    }
                    if (pCur != null) {
                        pCur.close();
                    }
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return result;
    }

    @Override
    public void onClickName(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("INFORMATION - "+name);
        builder.setMessage("Que voulez-vous faire ?");
        String getNumber = "0";
        for(User d : users){
            if(d.getPseudo().equals(name)){
                getNumber = d.getNum();
            }
        }
        String finalGetNumber = getNumber;
        builder.setNeutralButton("Appel",
                (dialog, which) -> {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", finalGetNumber, null));
                    startActivity(callIntent);
                });
        builder.setPositiveButton("Envoyer la liste de courses",
                (dialog, which) -> {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", finalGetNumber, null));
                    String msg = "Liste de courses :\n";
                    List<Product> products;
                    for (ProductCourses p:Database.getCourses().getAll()) {
                        String pname = p.getproductName();
                        int qtt = p.getQuantity();
                        String msgC = pname+":"+qtt+"\n";
                        msg = msg.concat(msgC);
                    }
                    smsIntent.putExtra("sms_body", msg);
                    startActivity(smsIntent);
                });
        builder.setNegativeButton("SMS",
                (dialog, which) -> {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", finalGetNumber, null));
                    smsIntent.putExtra("sms_body", "Je t’envoie ce message depuis l’application RéduGaspi, rejoins moi pour moins gaspiller ! Lien de téléchargement");
                    startActivity(smsIntent);
                });
        builder.show();
    }

    public boolean haveDup(String name) {
        for (User d : users) {
            if (d.getPseudo().equals(name)) {
                return true;
            }
        }
        return false;
    }

}

