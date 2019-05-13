package fr.etudes.redugaspi.services;

import android.app.AlarmManager;
import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Objects;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.activities.MainActivity;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.INTERVAL_HALF_DAY;
import static android.support.v4.content.ContextCompat.getSystemService;

public class CustomNotificationHelper {

    public static void schedule(Context context, Class<? extends BroadcastReceiver> receiverClass, long triggerMillis) {
        AlarmManager alarmManager = getSystemService(context, AlarmManager.class);
        Intent intent = new Intent(context, receiverClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerMillis, pendingIntent);
        }
    }

    protected static void createNotificationChannel(Context context, String channelId, String name, String description) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(context, NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }

    protected static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId) {
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.logo_round)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }

    protected static void sendNotification(Context context, NotificationCompat.Builder builder, int id) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, builder.build());
    }
}
