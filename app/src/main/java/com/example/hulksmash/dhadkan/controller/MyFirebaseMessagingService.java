package com.example.hulksmash.dhadkan.controller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
//import android.support.v7.app.NotificationCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.patientActivities.Entry;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by hulksmash on 9/9/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "TAG";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            sendNotification("" + remoteMessage.getData().get("message").toString());
            Log.d(TAG, "Message: " + remoteMessage.getData().get("message"));
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Entry.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle("Doctor Advice")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
