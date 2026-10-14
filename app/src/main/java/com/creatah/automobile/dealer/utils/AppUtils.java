package com.creatah.automobile.dealer.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.framework.utils.Constants;
import com.creatah.automobile.dealer.listener.RegistrationIntentService;
import com.creatah.automobile.dealer.manager.LoginManager;
import com.creatah.automobile.dealer.manager.SharedPreferenceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.WorkshopInfo;
import com.creatah.automobile.dealer.receiver.AlarmManagerReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class AppUtils {

    public static AppInfo getAppInfo(Application application) {

        if (LoginManager.getInstance() != null && LoginManager.getInstance().getAppInfo(application) != null) {
            return LoginManager.getInstance().getAppInfo(application);
        }

        return null;
    }

    public static void setAppInfo(AppInfo appInfo, Application application) {
        LoginManager.getInstance().setAppInfo(appInfo, application);
    }


    public static boolean isAuthenticatedAppInfo(Application application) {
        if (LoginManager.getInstance() != null && LoginManager.getInstance().getAppInfo(application) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void clearAppInfo(Application application) {
        LoginManager.getInstance().clearAppInfo(application);
    }

    public static void setLogoutDeatails(Application application) {
        LoginManager.getInstance().setLogoutDetails(application);
    }

    public static WorkshopInfo getWorkshopInfo(Application application) {

        if (LoginManager.getInstance() != null && LoginManager.getInstance().getWorkshopInfo(application) != null) {
            return LoginManager.getInstance().getWorkshopInfo(application);
        }

        return null;
    }

    public static void setWorkshopInfo(WorkshopInfo workshopInfo, Application application) {
        LoginManager.getInstance().setUserInfo(workshopInfo, application);
    }


    public static boolean isAuthenticatedDealerInfo(Application application) {
        if (LoginManager.getInstance() != null && LoginManager.getInstance().getWorkshopInfo(application) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertDateTimeStringTodate(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd" + " " + "yyyy");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateStringTodate(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd" + " " + "yyyy");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String convertstringTodate1(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd" + ", " + "yyyy");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertstringToTime(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("HH:mm");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";


    }

    public static String convertstringToTime1(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("hh:mm a");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";


    }


    public static String convertstringToAMPM(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("hh:mm a");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateTimeToDate(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertstringTodate2(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String convertstringTodate3(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(c.getTime());
    }

    public static String getRequiredPickUpTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 10);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(c.getTime());
    }

    public static String getRequiredApproximateTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 10);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(c.getTime());
    }


    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    public static String responseTime(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("hh:mm a");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("hh:mm a");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeliveryDate(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd " + "yyyy" + " KK:mm a");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getApproximateDate(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM dd " + "yyyy");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getDeliveryTime(String date_String) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("KK:mm a");
        try {
            Date date = inputFormat24.parse(date_String);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static Date getDateTimeFormat1(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(time);
            return convertedDate;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    public static void registerNotification(Context context) {
        //registering gcm
        if (checkPlayServices(context)) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(context, RegistrationIntentService.class);
            context.startService(intent);
        }
    }

    public static boolean checkPlayServices(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    public static boolean isCurentDate(Date requiredDateTime) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Date currentDateTime = AppUtils.getDateTime(dateFormat.format(c.getTime()));
        if (currentDateTime.equals(requiredDateTime)) {
            return true;
        } else {
            return false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setAppInfoAlarmManager(Context context) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerReceiver.class);

        PendingIntent morningAlarmIntent = PendingIntent.getBroadcast(context, AppConstants.MORNING_ALARM_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();

        Calendar morningAlarmStartTime = Calendar.getInstance();
        morningAlarmStartTime.set(Calendar.HOUR_OF_DAY, 9);
        morningAlarmStartTime.set(Calendar.MINUTE, 00);
        morningAlarmStartTime.set(Calendar.SECOND, 00);

        if (now.after(morningAlarmStartTime)) {
            morningAlarmStartTime.add(Calendar.DATE, 1);
        }

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, morningAlarmStartTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, morningAlarmIntent);

        SharedPreferenceManager.savePreferences(context, SharedPreferenceManager.Keys.IS_ALARM_SET, true);

    }

    public static Date getDateFormat(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(time);
            return convertedDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateTimeFormat(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(time);
            return convertedDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date getTimeFormatNew(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(time);
            return convertedDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static Date getDateTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(time);
            return convertedDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String convert12To24StringFormat(String time) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("hh:mm aa");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = inputFormat24.parse(time);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        return "";
    }


    public static String convert24T12StringFormat(String time) {
        SimpleDateFormat inputFormat24 = new SimpleDateFormat("HH:mm");
        //SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("MMM'\n'dd'\n'EEEE'\n'KK:mm:ss a");
        SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        try {
            date = inputFormat24.parse(time);
            return outputFormatAmPm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        return "";
    }


    public static File getRotatedPictureFile(Uri uri) {
        File tempFile = null;
        tempFile = new File(uri.toString());
        return tempFile;
    }


    public static Bitmap getRotatedBitmap(String imagePath) {

        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

    }

    public static File getRotatedPictureFile(Context context, Bitmap bitmap) {
        File tempFile = null;
        try {
            tempFile = new File(context.getCacheDir(), "temp" + ".jpg");
            tempFile.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static String getImagePath(Activity activity, Uri uri) {
        String imagePath = Constants.EMPTY;
        Uri selectedImage = uri;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }

    public static void makeCall(Activity activity, String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNo));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        activity.startActivity(intent);
    }


    public static void ShareIntentMessage(Activity context, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(sendIntent, "Share"));
    }


}
