package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.framework.utils.Constants;
import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.creatah.automobile.dealer.utils.AppConstants.APP_UPDATE_SKIP;

public class AppUpdateActivity extends BaseActivity {

    private String notificationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_update);
        Button updateButton = (Button) findViewById(R.id.btn_update);
        setTitle(getString(R.string.newVersion));
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
        TextView skipTextView = (TextView) findViewById(R.id.skip);
        int updateStatus = AppUtils.getAppInfo(getApplication()).getUpdateStatus();
        if (updateStatus == Constants.SOFT_UPDATE) {
            skipTextView.setVisibility(View.VISIBLE);
        } else {
            skipTextView.setVisibility(View.GONE);
        }

        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHomeScreen();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showHomeScreen() {


        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            Intent homeActivity = new Intent(AppUpdateActivity.this, HomeActivity.class);
            homeActivity.putExtra(AppConstants.APP_UPDATE_FLOW,APP_UPDATE_SKIP);
            startActivity(homeActivity);
            finish();
        } else {
            Intent homeActivity = new Intent(AppUpdateActivity.this, LoginActivity.class);
            startActivity(homeActivity);
            finish();
        }


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
