package com.creatah.automobile.dealer.manager;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.io.Serializable;

public final class SharedPreferenceManager  extends SecurePreferences{

	public SharedPreferenceManager(Context context) {
		super(context);
	}

	public interface Keys {
		public static final String IS_ALREADY_LAUNCHED="is_already_launched";
		public static final String IS_LOC_CONFIGURED = "is_app_launched";
		public static final String PREFERRED_BRANCHES="preferred_branches";
		public static final String PREFERRED_BRANCG_ID ="preffered_location_id";
		public static final String ANDROID_DEVICE_ID="android_device_id";
		public static final String UPDATE_TYPE="is_force_update";
		public static final String LOGINDETAILS="LOGINDETAILS";
		public static final String APPINFO = "APPINFO";
		public static final String INSTALL_TRACKING = "INSTALL_TRACKING";
		public static final String USERINFO = "USERINFO";
		public static final String DEALERINFO = "DEALERINFO";
		public static final String IS_RECHARGE_OFFER_USED="is_recharge_offer_used";
		public static final String SENT_TOKEN_TO_SERVER="sentTokenToServer";
		public static final String IS_ALARM_SET ="is_alarm_set";
	}

	
	private static final Object syncObj = new Object();

	public static void savePreferences(Context context, String key, int value) {

		new SecurePreferences(context).edit().putInt(key, value).commit();
	}

	public static int getPreferenceValue(Context context, String key) {
		return new SecurePreferences(context).getInt(key, 0);
	}

	public static void savePreferences(Context context, String key, String value) {
		new SecurePreferences(context).edit().putString(key, value).commit();
	}
	
	public static void savePreferences(Context context, String key, boolean value) {

		new SecurePreferences(context).edit().putBoolean(key, value).commit();
	}
	
	public static boolean getPreferenceBooleanValue(Context context, String key) {
		return new SecurePreferences(context).getBoolean(key, false);
	}

	public static String getPreferenceStringValue(Context context, String key) {
		return new SecurePreferences(context).getString(key, null);
	}

	public static <T extends Serializable> void setPreferenceObject(
			Context context, String key, T dataObj) {
			synchronized (syncObj) {
			try {
				new SecurePreferences(context).edit().putString(key, ObjectSerializer.serialize(dataObj)).commit();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPreferenceObject(Application application, String key) {
		synchronized (syncObj) {
			Object dataObj = null;
			try {
				dataObj = (T) ObjectSerializer.deserialize(new SecurePreferences(application).getString(key,
						null));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return (T) dataObj;
		}
		
	}
}