package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.HistoryPagerAdapter;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 04-05-2017.
 */

public class UserBookingActivity extends BaseActivity {

    private TabLayout tabs;

    private ViewPager pager;

    private HistoryPagerAdapter pagerAdapter;

    CharSequence Titles[] = {"General Service", "Instant  Service", "Service Enquiries"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookings);
        setTitle(getString(R.string.userBookingEnquiries));

        tabs = (TabLayout) findViewById(R.id.tabs);

        pager = (ViewPager) findViewById(R.id.profileViewPager);

        setUpTabs();

    }

    void setUpTabs() {
        pagerAdapter = new HistoryPagerAdapter(getSupportFragmentManager(), Titles, Titles.length);
        pager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(pager);
        setupTabIcons();
    }


    private void setupTabIcons() {
        // tabs.getTabAt(0).setIcon(R.mipmap.ic_launcher);
        // tabs.getTabAt(1).setIcon(R.mipmap.ic_launcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
