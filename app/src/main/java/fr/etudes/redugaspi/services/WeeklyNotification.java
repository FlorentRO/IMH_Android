package fr.etudes.redugaspi.services;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;

public class WeeklyNotification extends BroadcastReceiver {
    private static final String CHANNEL_ID = "WeeklyChannelId";
    private static final String CHANNEL_NAME = "Weekly notification";
    private static final String CHANNEL_DESC = "This notification is sent every week";
    private static final int NOTIFICATION_ID = 88881;

    private static final long interval = AlarmManager.INTERVAL_DAY * 7;

    @Override
    public void onReceive(Context context, Intent intent) {
        CustomNotificationHelper.createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME, CHANNEL_DESC);

        long now = System.currentTimeMillis();
        List<Product> products = Database.getHistory().get(x->x.getDateMillis()>=now-interval && x.getDateMillis()<=now);
        int n = products.stream().mapToInt(Product::getQuantity).sum();
        String titre;
        String message;
        if (n<=0) {
            titre = "Excellent !";
            message = "Vous avez réussi à ne rien gaspiller cette semaine !";
        } else if (n==1) {
            titre = "Très bien !";
            message = "Vous n'avez jeté qu'un seul produit cette semaine !";
        } else if (n<=5) {
            titre = "Continuez !";
            message = "vous n'avez gaspillé que " + n + " produits cette semaine.";
        } else {
            titre = "Courage !";
            message = "vous avez jeté " + n + " produits cette semaine.";
        }

        NotificationCompat.Builder builder = CustomNotificationHelper.getNotificationBuilder(context, CHANNEL_ID);
        builder.setContentTitle(titre);
        builder.setContentText(message);
        CustomNotificationHelper.sendNotification(context, builder, NOTIFICATION_ID);
        CustomNotificationHelper.schedule(context, this.getClass(), now + interval);
    }
}
