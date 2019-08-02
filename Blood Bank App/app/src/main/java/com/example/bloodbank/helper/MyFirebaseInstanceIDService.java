package com.example.bloodbank.helper;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.ClientModel;
import com.example.bloodbank.ui.activity.HomeNavgation;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID = "admin_channel";
    private NotificationManager notificationManager;
    private String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    private int notificationId;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        SharedPreferences preferences = getSharedPreferences("Blood", 0);
        preferences.edit().putString(FIREBASE_TOKEN, s).apply();
        Log.d("onNewToken",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

         notificationId = new Random().nextInt(60000);
        String massage = remoteMessage.getData().toString();
        ClientModel data = new ClientModel();



        String Content = getString(R.string.notification_content) + massage+" " + data.getName();
        sendNotification(massage);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(massage)
//                .setContentText(massage)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(massage))
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(massage).setSummaryText("#hashtag"))
//                .setShowWhen(true)
//                .setSound(defaultSoundUri)
//                .setAutoCancel(true);
//
//        notificationManager.notify(notificationId, notificationBuilder.build());


    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, HomeNavgation.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(ADMIN_CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }

}
