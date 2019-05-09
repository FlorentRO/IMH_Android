package fr.etudes.redugaspi.services;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;

public class DailyNotification extends BroadcastReceiver {
    private static final String CHANNEL_ID = "DailyChannelId";
    private static final String CHANNEL_NAME = "Daily notification";
    private static final String CHANNEL_DESC = "This notification is sent every day";
    private static final int NOTIFICATION_ID = 88882;

    private static final long interval = AlarmManager.INTERVAL_DAY;

    @Override
    public void onReceive(Context context, Intent intent) {
        CustomNotificationHelper.createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME, CHANNEL_DESC);

        long now = System.currentTimeMillis();
        List<Product> products = Database.getProducts().get( x-> x.getDateMillis()>=now && x.getDateMillis()<=now+interval );
        int n = products.stream().mapToInt(Product::getQuantity).sum();
        String titre;
        String message;
        if (n<=0) {
            titre = "Rien à signaler !";
            message = "Il n'y a pas de produits à consommer rapidement.";
        } else if (n==1) {
            String name = Database.getNames().getFirst(x->x.getBarcode().equals(products.get(0).getBarCode())).getName();
            titre = "Attention !";
            message = "Dépéchez vous de manger : " + name + " !";
        } else {
            titre = "Urgent !";
            message = n + " produits expirent aujourd'hui, ne les gaspillez pas !";
        }

        NotificationCompat.Builder builder = CustomNotificationHelper.getNotificationBuilder(context, CHANNEL_ID);
        builder.setContentTitle(titre);
        builder.setContentText(message);
        CustomNotificationHelper.sendNotification(context, builder, NOTIFICATION_ID);
        CustomNotificationHelper.schedule(context, this.getClass(), now + interval);
    }
}
