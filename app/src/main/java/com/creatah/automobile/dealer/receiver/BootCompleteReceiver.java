package com.creatah.automobile.dealer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.creatah.automobile.dealer.utils.AppUtils;


/**
 * Created by sathish on 31-03-2016.
 */
public class BootCompleteReceiver extends BroadcastReceiver
{
    private static final String TAG = "MyBootReceiver";

    public void onReceive(Context context, Intent intent) {
        AppUtils.setAppInfoAlarmManager(context);
        Log.d(TAG, "onReceive");
    }

}