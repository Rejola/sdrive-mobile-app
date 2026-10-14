package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.utils.AppConstants;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by karthikeyan on 16-02-2017.
 */

public class ChooseVehicleActivity extends BaseActivity implements View.OnClickListener {

    private static final int ADD_BOOKING_REQ_CODE = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_vehicle);
        setTitle(getString(R.string.addBooking));
        findViewById(R.id.carButton).setOnClickListener(this);
        findViewById(R.id.bikeButton).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent bookingIntent = new Intent(ChooseVehicleActivity.this, AddBookingActivity.class);
        switch (view.getId()) {

            case R.id.carButton:
                bookingIntent.putExtra(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA, getString(R.string.car));
                break;
            case R.id.bikeButton:
                bookingIntent.putExtra(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA, getString(R.string.car));
                break;
        }

        startActivity(bookingIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BOOKING_REQ_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
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
