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
        Database.setContextAll(this);
        //MOCK_DATABASE();
        setupNotifications();
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), ManagementActivity.class);
        startActivity(intent);
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
        super.onDestroy();
    }

    private void MOCK_DATABASE() /* TODO remove, this is just a mock */ {
        Database.getNames().add(new ProductName("4002359009471", "miracoli"));
        Database.getNames().add(new ProductName("3564700332856", "fromage"));
        Database.getNames().add(new ProductName("26017969", "escalope"));
        Database.getNames().add(new ProductName("3250390000853", "haricot"));
        Database.getProducts().add(new Product("26017969", 1, 10,6,2019));
        Database.getProducts().add(new Product("3564700332856", 1, 10,7,2018));
        Database.getProducts().add(new Product("4002359009471", 1, 10,5,2019));
        Database.getProducts().add(new Product("26017969", 1, 10,5,2019));
        Database.getProducts().add(new Product("4002359009471", 1, 10,5,2020));
        Database.getProducts().add(new Product("3564700332856", 1, 10,5,2020));
        Database.getProducts().add(new Product("3564700332856", 1, 10,4,2019));
        Database.getProducts().add(new Product("26017969", 1, 10,5,2019));
        Database.getProducts().add(new Product("4002359009471", 1, 10,5,2015));
        Database.getProducts().add(new Product("4002359009471", 1, 10,5,2019));
        Database.getProducts().add(new Product("3564700332856", 1, 10,5,2015));
        Database.getProducts().add(new Product("4002359009471", 1, 10,5,2019));
        Database.getProducts().add(new Product("3564700332856", 1, 10,5,2019));
        Database.getProducts().add(new Product("3250390000853", 1, 10,1,2019));
        Database.getProducts().add(new Product("3564700332856", 1, 10,5,2019));
        Database.getHistory().add(new Product("26017969", 1, 6,5,2019));
        Database.getHistory().add(new Product("3564700332856", 1, 6,5,2019));
        Database.getHistory().add(new Product("4002359009471", 1, 8,6,2020));
        Database.getHistory().add(new Product("26017969", 1, 6,5,2019));
        Database.getHistory().add(new Product("4002359009471", 1, 6,5,2019));
        Database.getHistory().add(new Product("3564700332856", 1, 6,5,2019));
        Database.getHistory().add(new Product("3564700332856", 1, 6,5,2019));
        Database.getHistory().add(new Product("26017969", 1, 6,5,2019));
        Database.getHistory().add(new Product("4002359009471", 1, 6,5,2019));
        Database.getHistory().add(new Product("4002359009471", 1, 6,5,2019));
        Database.getHistory().add(new Product("3564700332856", 1, 6,5,2019));
        Database.getHistory().add(new Product("4002359009471", 1, 6,5,2019));
        Database.getHistory().add(new Product("3564700332856", 1, 6,5,2019));
    }
}
