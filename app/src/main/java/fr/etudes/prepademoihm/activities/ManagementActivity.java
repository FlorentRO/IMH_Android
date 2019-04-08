package fr.etudes.prepademoihm.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import fr.etudes.prepademoihm.adapters.PagerAdapter;
import fr.etudes.prepademoihm.R;


public class ManagementActivity extends AppCompatActivity {
    private PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        //Récup item page
        final ViewPager viewPager = findViewById(R.id.myContainer);
        TabLayout tabLayout = findViewById(R.id.menu);

        //Creation de tabs (menus)
        tabLayout.addTab(tabLayout.newTab().setText("Produits"));
        tabLayout.addTab(tabLayout.newTab().setText("Annonces"));
        tabLayout.addTab(tabLayout.newTab().setText("Amis"));

        //Récup des tabs dans l'adapter
        adapter = new PagerAdapter( getSupportFragmentManager(), tabLayout.getTabCount() );

        //On met les tableau via l'adapter dans la page
        viewPager.setAdapter(adapter);

        //Changement du contenu de page si clic sur tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setAdapter(adapter);
                //TODO: setAdapter only when Kindergarten's listview changed (make it obervable)
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

}
