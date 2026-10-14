/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.creatah.automobile.dealer.listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.framework.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.HomeActivity;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.google.android.gms.gcm.GcmListenerService;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String title = data.getString("title");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message,title);

        sendUpdateSuccessBroadcast();

        // [END_EXCLUDE]
    }

    public void sendUpdateSuccessBroadcast() {
        Intent intent = new Intent(Constants.SEND_BROADCAST);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */

    public void sendNotification(String message, String title) {
       if(AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            int icon = R.drawable.appicon;
            String timeStamp = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
            Notification ss;
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(this, HomeActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Notification notif;
                Notification.Builder notifBuilder = new Notification.Builder(getApplicationContext());
                notifBuilder.setContentIntent(contentIntent);
                notifBuilder.setContentTitle(title);
                notifBuilder.setSmallIcon(R.drawable.notification_status_icon_white);
                notifBuilder.setContentText(message);
                notifBuilder.setDefaults(Notification.DEFAULT_ALL);
                notifBuilder.setAutoCancel(true);
                notifBuilder.setColor(getResources().getColor(R.color.colorAccent));
                //notifBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.appicon));
                notifBuilder.setWhen(System.currentTimeMillis());
                notif = notifBuilder.build();
                mNotificationManager.notify(2, notif);
            } else {
                ss = new Notification(R.drawable.notification_status_icon, "Custom Notification", System.currentTimeMillis());
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
                contentView.setImageViewResource(R.id.image, icon);
                contentView.setTextViewText(R.id.title, title);
                contentView.setTextViewText(R.id.text, message);
                contentView.setTextViewText(R.id.time, timeStamp);
                ss.contentView = contentView;
                ss.contentIntent = contentIntent;
                ss.flags |= Notification.FLAG_AUTO_CANCEL; //Do not clear the notification
                ss.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                ss.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
                mNotificationManager.notify(2, ss);
            }
        }
    }

}
