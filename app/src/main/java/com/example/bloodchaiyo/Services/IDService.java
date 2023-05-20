package com.example.bloodchaiyo.Services;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class IDService extends FirebaseMessagingService{
    private static final String TAG = "MyFirebaseMsgService";

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

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle the incoming message
    }
}