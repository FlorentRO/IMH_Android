package fr.etudes.redugaspi.services;

import android.app.Application;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import fr.etudes.redugaspi.activities.MainActivity;

public class NotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("ERROR", "NOTIFICATION");
            /*NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle("my title")
                    .setContentText("my message")
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Intent i = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            i,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            builder.setContentIntent(pendingIntent);
            manager.notify(0, builder.build());
*/
    }
}
