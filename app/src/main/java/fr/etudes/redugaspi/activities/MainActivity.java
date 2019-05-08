package fr.etudes.redugaspi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.etudes.redugaspi.R;


public class MainActivity extends AppCompatActivity{
    private static int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button valid = findViewById(R.id.ok);
        final EditText id = findViewById(R.id.id);
        final EditText mdp = findViewById(R.id.mdp);
        final TextView error = findViewById(R.id.error);

        valid.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
            startActivity(intent);
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
