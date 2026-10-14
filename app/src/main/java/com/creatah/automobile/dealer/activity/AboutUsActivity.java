package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.framework.utils.Constants;
import com.android.framework.utils.Utils;
import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.BuildConfig;
import com.creatah.automobile.dealer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 01-02-2018.
 */

public class AboutUsActivity extends BaseActivity {
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle(getString(R.string.aboutUS));
        TextView aboutTextView = (TextView) findViewById(R.id.versionTextview);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        String s = getString(R.string.version_name) + Utils.getAppVersionname(this);

        if (BuildConfig.SERVER_MODE == Constants.SERVICE_LOCAL) {
            s = getString(R.string.version_name) + Utils.getAppVersionname(this) + " " + getString(R.string.local_server);
        } else if (BuildConfig.SERVER_MODE == Constants.SERVICE_DEMO) {
            s = getString(R.string.version_name) + Utils.getAppVersionname(this) + " " + getString(R.string.demo_server);

        }
        aboutTextView.setText(s);

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
