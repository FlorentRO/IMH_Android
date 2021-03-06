package fr.etudes.redugaspi.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.fragments.FragAdverts;
import fr.etudes.redugaspi.fragments.FragCourses;
import fr.etudes.redugaspi.fragments.FragFriends;
import fr.etudes.redugaspi.fragments.FragHistorique;
import fr.etudes.redugaspi.fragments.FragProducts;
import fr.etudes.redugaspi.settings.Utils;


public class ManagementActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment FragProduits;
    private Fragment Fraghistorique;
    private Fragment Fragcourses;
    private Fragment FragAds;
    private Fragment FragAmis;

    private static final int FRAGMENT_PRODUCT = 0;
    private static final int FRAGMENT_HISTORIQUE = 1;
    private static final int FRAGMENT_COURSES = 2;
    private static final int FRAGMENT_ADS = 3;
    private static final int FRAGMENT_FRIENDS = 4;



    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (!Utils.allPermissionsGranted(getApplicationContext())) {
            Utils.requestRuntimePermissions(ManagementActivity.this);
        }
        // 6 - Configure all views

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.showFirstFragment();

    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    // call barcode scanner camera
    public void barcodeReco(View view) {
        Intent intent = new Intent(getApplicationContext(), LiveBarcodeScanningActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Show fragment after user clicked on a menu item
        switch (id){
            case R.id.activity_menu_produit:
                this.showFragment(FRAGMENT_PRODUCT);
                break;
            case R.id.activity_menu_historique:
                this.showFragment(FRAGMENT_HISTORIQUE);
                break;
            case R.id.activity_menu_courses:
                this.showFragment(FRAGMENT_COURSES);
                break;
            case R.id.activity_menu_annonces:
                this.showFragment(FRAGMENT_ADS);
                break;
            case R.id.activity_menu_amis:
                this.showFragment(FRAGMENT_FRIENDS);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // FRAGMENTS
    // ---------------------

    // Show fragment according an Identifier
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // Show News Fragment
            this.showFragment(FRAGMENT_PRODUCT);
            // Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_PRODUCT :
                this.showProductFragment();
                break;
            case FRAGMENT_HISTORIQUE :
                this.showHistoriqueFragment();
                break;
            case FRAGMENT_COURSES :
                this.showCoursesFragment();
                break;
            case FRAGMENT_ADS:
                this.showAdsFragment();
                break;
            case FRAGMENT_FRIENDS:
                this.showFriendsFragment();
                break;
            default:
                break;
        }
    }

    // ---

    // Create each fragment page and show it

    private void showProductFragment(){
        if (this.FragProduits == null) this.FragProduits = FragProducts.newInstance();
        setTitle("Mes Produits");
        this.startTransactionFragment(this.FragProduits);
    }
    private void showHistoriqueFragment(){
        if (this.Fraghistorique == null) this.Fraghistorique = FragHistorique.newInstance();
        setTitle("Historique");
        this.startTransactionFragment(this.Fraghistorique);
    }
    private void showCoursesFragment(){
        if (this.Fragcourses == null) this.Fragcourses = FragCourses.newInstance();
        setTitle("Liste de courses");
        this.startTransactionFragment(this.Fragcourses);
    }
    private void showAdsFragment(){
        if (this.FragAds == null) this.FragAds = FragAdverts.newInstance();
        setTitle("Annonces");
        this.startTransactionFragment(this.FragAds);
    }

    private void showFriendsFragment(){
        if (this.FragAmis == null) this.FragAmis = FragFriends.newInstance();
        setTitle("Mes Amis");
        this.startTransactionFragment(this.FragAmis);
    }


    // ---

    // Generic method that will replace and show a fragment inside the MainActivity Frame Layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();

        }
    }


}
