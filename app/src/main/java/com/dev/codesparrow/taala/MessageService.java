package com.dev.codesparrow.taala;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {
    public MessageService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);

      
        Intent loginIntent = new Intent(MessageService.this,CheckActivity.class);
        startActivity(loginIntent);

        Toast.makeText(this, "Message Recieved FCM", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("FCMToken", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }

}
