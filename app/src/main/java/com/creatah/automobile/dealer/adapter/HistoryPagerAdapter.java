package com.creatah.automobile.dealer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.creatah.automobile.dealer.fragment.InstantBookingFragment;
import com.creatah.automobile.dealer.fragment.UserBookingFragment;
import com.creatah.automobile.dealer.fragment.UserEnquiryFragment;

/**
 * Created by creatah1 on 04-05-2017.
 */

public class HistoryPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public HistoryPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        switch (position) {

            case 0:
                UserBookingFragment tab1 = new UserBookingFragment();
                return tab1;

            case 1:
                InstantBookingFragment tab2 = new InstantBookingFragment();
                return tab2;

            case 2:
                UserEnquiryFragment tab3 = new UserEnquiryFragment();
                return tab3;


            default:
                return new UserBookingFragment();

        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}