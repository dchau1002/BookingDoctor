package com.example.app_book.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.app_book.ChatActivity;
import com.example.app_book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Random;

public class FirebaseMessaging extends FirebaseMessagingService {

    private static final String ADMIN_CHANNEL_ID = "admin_channel";

    public void onMessageReceived(RemoteMessage remoteMessage){
         super.onMessageReceived(remoteMessage);

        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        String savedCurrentUser = sp.getString("Current_USERID", "None");

        String notificationType = remoteMessage.getData().get("notificationType");
        if (notificationType.equals("PostNotification")){

//            String sender = remoteMessage.getData().get("sender");
//            String pId = remoteMessage.getData().get("pId");
//            String pTitle = remoteMessage.getData().get("pTitle");
//            String pDesc = remoteMessage.getData().get("pDesc");
//            if (!sender.equals(savedCurrentUser)){
//                ShowPostNotification(""+pId, ""+pTitle, ""+pDesc);
//            }

        }else if (notificationType.equals("ChatNotification")){
            String sent = remoteMessage.getData().get("sent");
            String user = remoteMessage.getData().get("user");
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            if (fUser != null && sent.equals(fUser.getUid())){
                if (!savedCurrentUser.equals(user)){
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                        sendOAndAboveNotication(remoteMessage);
                    }else{
                        sendNormalNotification(remoteMessage);
                    }
                }

            }
        }

    }

//    private void ShowPostNotification(String pid, String ptitle, String pdesc) {
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//        int notificationID = new Random().nextInt(3000);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            setupPostNotificationChannel(notificationManager);
//        }
//
//       Intent intent = new Intent( this, DetailsPostActivity.class);
////
//
//
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_ONE_SHOT);
//        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.hi);
//
//        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ""+ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.hi)
//                .setLargeIcon(largeIcon)
//                .setContentTitle(ptitle)
//                .setContentText(pdesc)
//                .setSound(notificationSoundUri)
//                .setContentIntent(pendingIntent);
//
//        notificationManager.notify(notificationID, notificationBuilder.build());
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupPostNotificationChannel(NotificationManager notificationManager) {
        CharSequence charSequenceName = "Thông báo mới";
        String channelDesc = "Thiết bị đăng thông báo bài viết";

         NotificationChannel adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, charSequenceName, NotificationManager.IMPORTANCE_HIGH);
         adminChannel.setDescription(channelDesc);
         adminChannel.enableLights(true);
         adminChannel.setLightColor(Color.RED);
         adminChannel.enableVibration(true);
         if (notificationManager != null){
             notificationManager.createNotificationChannel(adminChannel);
         }

    }

    private void sendNormalNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hisUid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, i,intent, PendingIntent.FLAG_ONE_SHOT);


        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defSoundUri)
                .setContentIntent(pIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

          int j = 0;
          if (i>0){
              j=i;
          }
          notificationManager.notify(j, builder.build());

    }

    private void sendOAndAboveNotication(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hisUid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, i,intent, PendingIntent.FLAG_ONE_SHOT);


        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        oreoAndAboveNotification notification1 = new oreoAndAboveNotification(this);
        Notification.Builder builder = notification1.getOnotification(title, body, pIntent, defSoundUri, icon);

        int j = 0;
        if (i>0){
            j=i;
        }
        notification1.getManager().notify(j, builder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            UpdateToken(s);
        }
    }

    private void UpdateToken(String s) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(s);
        ref.child(user.getUid()).setValue(token);
    }
}
