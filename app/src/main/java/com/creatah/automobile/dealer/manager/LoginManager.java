package com.creatah.automobile.dealer.manager;

/**
 * Created by karthikeyan on 06-06-2016.
 */

import android.app.Application;


import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.WorkshopInfo;

import java.io.Serializable;


public class LoginManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static LoginManager loginManager;
    private WorkshopInfo dealerInfo;
    private AppInfo appInfo;

    public static LoginManager getInstance() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }
        return loginManager;
    }


    public boolean isAuthenticatedAppInfo(Application application) {
        appInfo = SharedPreferenceManager.getPreferenceObject(application, SharedPreferenceManager.Keys.APPINFO);
        if (appInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public AppInfo getAppInfo(Application application) {

        if (appInfo == null) {
            appInfo = SharedPreferenceManager.getPreferenceObject(application, SharedPreferenceManager.Keys.APPINFO);
        }

        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo, Application application) {
        SharedPreferenceManager.setPreferenceObject(application, SharedPreferenceManager.Keys.APPINFO, appInfo);
        this.appInfo = appInfo;
    }

    public void clearAppInfo(Application application) {
        SharedPreferenceManager.setPreferenceObject(application, SharedPreferenceManager.Keys.APPINFO, null);
        this.appInfo = null;
    }

    public void setLogoutDetails(Application application) {

        SharedPreferenceManager.setPreferenceObject(application, SharedPreferenceManager.Keys.DEALERINFO, null);
        this.dealerInfo = null;
    }


    public boolean isAuthenticated(Application application) {
        dealerInfo = SharedPreferenceManager.getPreferenceObject(application, SharedPreferenceManager.Keys.DEALERINFO);
        if (dealerInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public WorkshopInfo getWorkshopInfo(Application application) {

        if (dealerInfo == null) {
            dealerInfo = SharedPreferenceManager.getPreferenceObject(application, SharedPreferenceManager.Keys.DEALERINFO);
        }

        return dealerInfo;
    }

    public void setUserInfo(WorkshopInfo dealerInfo, Application application) {
        SharedPreferenceManager.setPreferenceObject(application, SharedPreferenceManager.Keys.DEALERINFO, dealerInfo);
        this.dealerInfo = dealerInfo;
    }

}
