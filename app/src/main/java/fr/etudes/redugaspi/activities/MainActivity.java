package fr.etudes.redugaspi.activities;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.util.TimeUtils;
import android.widget.Button;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductName;
import fr.etudes.redugaspi.services.CustomNotificationHelper;
import fr.etudes.redugaspi.services.DailyNotification;
import fr.etudes.redugaspi.services.WeeklyNotification;

import fr.etudes.redugaspi.settings.Utils;


public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Database.LoadAll(this);
        MOCK_DATABASE();
        setupNotifications();

        setContentView(R.layout.activity_main);
        Button valid = findViewById(R.id.connect);
        valid.setOnClickListener(v -> {
            
            Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
            startActivity(intent);
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void setupNotifications() {
        Calendar cal=Calendar.getInstance();
        cal.add( Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK))+7); // sunday
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);
        long start = cal.getTimeInMillis();
        Log.e("ERROR", ""+(start-System.currentTimeMillis()));
        CustomNotificationHelper.schedule(this, DailyNotification.class, start);
        CustomNotificationHelper.schedule(this, WeeklyNotification.class, start);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Utils.allPermissionsGranted(this)) {
            Utils.requestRuntimePermissions(this);
        }
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
        Database.SaveAll(this);
        super.onDestroy();
    }

    private void MOCK_DATABASE() /* TODO remove, this is just a mock */ {
//        Database.ClearAll(this);

        //Database.getNames().add(this, new ProductName("3116430210371", "biscuit"));
        //Database.getProducts().add(this, new Product("3116430210371", 1, 10,5,2019));
        //Database.getHistory().add(this, new Product("3116430210371", 1, 6,5,2019));

//        Database.SaveAll(this);
    }
}
