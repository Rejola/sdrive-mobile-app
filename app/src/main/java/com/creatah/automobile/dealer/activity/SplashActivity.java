package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.android.framework.utils.Constants;
import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DELAY = 4000;
    private static final int NEXT_ACTIVITY = 0;

    private Context context;

    private boolean isService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        hideToolbar();
        context = this;
        startHomeActivity();
    }


    private void startHomeActivity() {
        Message msg = new Message();
        msg.what = NEXT_ACTIVITY;
        splashHandler.sendMessageDelayed(msg, SPLASH_DELAY);
    }

    private final Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                //  Thread.sleep(2000);
                showHomeScreen();
            } catch (Exception ex) {
            }
        }
    };

    private void showHomeScreen() {

        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {

            if (AppUtils.isAuthenticatedAppInfo(getApplication())) {
               /* AppUtils.getAppInfo(getApplication()).getUpdateStatus();
                int updateStatus = 0;
                updateStatus = AppUtils.getAppInfo(getApplication()).getUpdateStatus();
                if (updateStatus == Constants.FORCE_UPDATE || updateStatus == Constants.SOFT_UPDATE) {
                    startActivity(new Intent(context, AppUpd    ateActivity.class));
                    finish();
                }else {*/


                    Intent homeActivity = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(homeActivity);
                    finish();
               // }
            } else {
                Intent homeActivity = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(homeActivity);
                finish();
            }
        } else {
            if (AppUtils.isAuthenticatedAppInfo(getApplication())) {
                /*AppUtils.getAppInfo(getApplication()).getUpdateStatus();
                int updateStatus = 0;
                updateStatus = AppUtils.getAppInfo(getApplication()).getUpdateStatus();
                if (updateStatus == Constants.FORCE_UPDATE || updateStatus == Constants.SOFT_UPDATE) {
                    startActivity(new Intent(context, AppUpdateActivity.class));
                    finish();
                }else {*/
                    Intent homeActivity = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(homeActivity);
                    finish();
               // }
            } else {
                Intent homeActivity = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(homeActivity);
                finish();
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
