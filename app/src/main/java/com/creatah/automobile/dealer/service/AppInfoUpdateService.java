package com.creatah.automobile.dealer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.framework.utils.Utils;

import com.android.network.listener.ServiceResponseListener;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

public class AppInfoUpdateService extends Service {

    Boolean internet_status = true;
    private static final String TAG_SUCCESS = "success";

    private boolean isService = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            getAppInfo();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void getAppInfo() {

        if (!isService) {

            RequestParams requestParams = new RequestParams();
            requestParams.put("version_code", String.valueOf(Utils.getAppVersion(getApplication())));
            requestParams.put("workshop_id", String.valueOf(AppUtils.getWorkshopInfo(getApplication()).getworkshopId()));
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

            ServiceManager.getAppInfo(getApplicationContext(), requestParams, new ServiceResponseListener<AppInfo>() {
                        @Override
                        public void onSuccess(AppInfo response) {
                            isService = false;

                            if (response != null && !response.isError()) {
                                AppInfo appInfo = response;
                                AppUtils.setAppInfo(appInfo, getApplication());
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                        }
                    }

            );
        }

    }
}