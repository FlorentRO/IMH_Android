package fr.etudes.redugaspi.activities;

import android.content.Intent;
import android.os.Bundle;
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

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(id.getText());
                System.out.println(mdp.getText());
                System.out.println(id.getText().equals("mdp"));
                if ( (id.getText().toString().equals("corinne") || id.getText().toString().equals("bertrand") )
                        && mdp.getText().toString().equals("mdp") ) {
                    error.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
                    startActivity(intent);
                } else {
                    error.setVisibility(View.VISIBLE);
                }
            }
        });
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
