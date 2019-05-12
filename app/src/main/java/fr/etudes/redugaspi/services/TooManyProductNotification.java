package fr.etudes.redugaspi.services;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;
import fr.etudes.redugaspi.models.ProductCourses;

public class TooManyProductNotification extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CourseChannelId";
    private static final String CHANNEL_NAME = "Courses notification";
    private static final String CHANNEL_DESC = "This notification is sent every too many product";
    private static final int NOTIFICATION_ID = 88883;

    @Override
    public void onReceive(Context context, Intent intent) {
        CustomNotificationHelper.createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME, CHANNEL_DESC);

        long now = System.currentTimeMillis();
        List<ProductCourses> products = Database.getCourses().get(x-> x.getQuantity() >= 7);
        int n = products.size();
        if (n>=1) {
            String titre;
            String message;
            if (n==1) {
                String name = Database.getCourses().getFirst(x->x.getproductName().equals(products.get(0).getproductName())).getproductName();
                titre = "Attention !";
                message = "Vous prévoyez d'acheter une grande quantité de  : " + name + ", pensez à ne pas gaspiller!";
            } else  {
                titre = "Urgent !";
                message = n + " produits sont en trop grande quantité dans votre liste de courses";
            }
            NotificationCompat.Builder builder = CustomNotificationHelper.getNotificationBuilder(context, CHANNEL_ID);
            builder.setContentTitle(titre);
            builder.setContentText(message);
            CustomNotificationHelper.sendNotification(context, builder, NOTIFICATION_ID);
        }

    }
}
