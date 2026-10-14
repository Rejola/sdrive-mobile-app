package com.creatah.automobile.dealer.listener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;


/**
 * Created by karthikeyan on 13-10-2016.
 */

public class PackageReplacedReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null && intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
           /* // invalidate your current GCM registration id, and re-register with GCM server using an IntentService
            Intent serviceintent = new Intent(context, RegistrationIntentService.class);
            context.startService(serviceintent);*/

          /*  Intent appConfigIntent = new Intent(context, AppInfoUpdateService.class);
            context.startService(appConfigIntent);*/
        }
    }
}
