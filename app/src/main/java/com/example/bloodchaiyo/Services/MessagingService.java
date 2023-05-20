package com.example.bloodchaiyo.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.example.bloodchaiyo.Main.ConnectUsers;
import com.example.bloodchaiyo.Main.MainActivity;
import com.example.bloodchaiyo.R;

import static android.media.RingtoneManager.getDefaultUri;

import androidx.core.app.NotificationCompat;

/**
 *
 */
public class MessagingService extends FirebaseMessagingService {


    SharedPreferences.Editor edit;
    SharedPreferences spf;

    private static final String TAG = "FirebaseMessageService";
    public static final String ACTION_CONNECT_USERS = "ieee.donn.CONNECT_USERS";
    public Bundle messageBody;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            messageBody = new Bundle();
            Map<String,String> map  = remoteMessage.getData();
            messageBody.putString("name",map.get("name"));
            messageBody.putString("blood",map.get("blood"));
            messageBody.putString("email",map.get("email"));
            messageBody.putString("phone",map.get("phone"));
            messageBody.putString("facebook",map.get("facebook"));
            messageBody.putString("country",map.get("country"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        String message = remoteMessage.getData().get("message");
        Log.d(TAG,"Message: "+message);

        save("message", message);

        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        String bla = remoteMessage.getNotification().getBody();

        sendNotification(messageBody, bla);

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(Bundle messageBody, String bla) {
        int requestID = (int) System.currentTimeMillis();
        int NOTIFICATION_ID = createID();

        Intent intent = new Intent(this, ConnectUsers.class);
        intent.setAction(ACTION_CONNECT_USERS);
        intent.putExtra("data",messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //String channelId = getString(R.string.default_notification_channel_id);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentTitle("Blood Needed")
                        .setContentText(bla)
                        .setAutoCancel(true)
                        .setSound(alarmSound)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(100 /* ID of notification */, notificationBuilder.build());

    }


    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(this);
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);

        // send the refreshed token to your server
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // implement your code to send the token to your server
    }
}

