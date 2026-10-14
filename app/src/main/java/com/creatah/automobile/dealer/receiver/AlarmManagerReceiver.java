package com.creatah.automobile.dealer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.creatah.automobile.dealer.service.AppInfoUpdateService;


/**
 * Created by karthikeyan on 29-03-2016.
 */
public class AlarmManagerReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        Intent dailyUpdater = new Intent(context, AppInfoUpdateService.class);
        context.startService(dailyUpdater);
    }


}
