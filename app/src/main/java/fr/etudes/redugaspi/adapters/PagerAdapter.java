package fr.etudes.redugaspi.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import fr.etudes.redugaspi.fragments.TabFragment1;
import fr.etudes.redugaspi.fragments.TabFragment2;
import fr.etudes.redugaspi.fragments.TabFragment3;


public class PagerAdapter extends FragmentStatePagerAdapter {
    private int nbTab;

    public PagerAdapter(FragmentManager fm, int numTab) {
        super(fm);
        this.nbTab = numTab;
    }

    /**
     * automatically called by android when viewPager use setCurrentItem() method
     * @param i the num of tab selected
     * @return Fragment to be adapted
     */
    @Override
    public Fragment getItem(int i) {
        //create TabFragment1, TabFragment2 or TabFragment3
        Fragment fragment = null;
        switch (i){
            case 0 : fragment = new TabFragment1(); break;
            case 1 : fragment = new TabFragment2(); break;
            case 2 : fragment = new TabFragment3(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return nbTab;
    }
}
