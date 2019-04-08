package fr.etudes.redugaspi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.models.Kindergarten;


public class MainActivity extends AppCompatActivity{
    private static int step = 0;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LIFECYCLE", "step " + step++ + "  --> onCreate()");
        setContentView(R.layout.activity_main);

        //On récup les items
        //logo = findViewById(R.id.imageLogo);
        Button valid = findViewById(R.id.ok);

        //On donne une action on click au bouton
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On click, on start management activity
                Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                startActivity(intent);
            }
        });

        /*//Animation de rotation
        final Animation rotate = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.rotation);
        logo.startAnimation(rotate);

        //Animation en boucle
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LIFECYCLE", "step " + step++ + "  --> onStart()");

        //initilize app model (Kindergarten.java)
        Kindergarten.init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LIFECYCLE", "step " + step++ + "  --> onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LIFECYCLE", "step " + step++ + "  --> onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE", "step " + step++ + "  --> onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE", "step " + step++ + "  --> onDestroy()");
    }

}
