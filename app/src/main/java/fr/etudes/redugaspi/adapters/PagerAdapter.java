package fr.etudes.redugaspi.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fr.etudes.redugaspi.fragments.FragAdverts;
import fr.etudes.redugaspi.fragments.FragFriends;
import fr.etudes.redugaspi.fragments.FragProducts;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int nbTab;

    public PagerAdapter(FragmentManager fm, int numTab) {
        super(fm);
        this.nbTab = numTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0 : return new FragProducts();
            case 1 : return new FragAdverts();
            case 2 : return new FragFriends();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return nbTab;
    }

}
