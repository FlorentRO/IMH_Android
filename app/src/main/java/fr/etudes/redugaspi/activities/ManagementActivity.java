package fr.etudes.redugaspi.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import fr.etudes.redugaspi.adapters.PagerAdapter;
import fr.etudes.redugaspi.R;


public class ManagementActivity extends AppCompatActivity {
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        TabLayout tabLayout = findViewById(R.id.menu);
        tabLayout.addTab(tabLayout.newTab().setText("Produits"));
        tabLayout.addTab(tabLayout.newTab().setText("Annonces"));
        tabLayout.addTab(tabLayout.newTab().setText("Amis"));

        adapter = new PagerAdapter( getSupportFragmentManager(), tabLayout.getTabCount() );

        final ViewPager viewPager = findViewById(R.id.main_pager);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

}
