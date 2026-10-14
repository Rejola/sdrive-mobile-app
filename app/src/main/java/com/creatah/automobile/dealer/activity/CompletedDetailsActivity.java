package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class CompletedDetailsActivity extends BaseActivity {

    private TextView nameTextView;
    private TextView noTextView;
    private TextView dateTextView;
    private TextView deliveryDate;
    //private TextView followUpDate;
    private TextView brand;
    private TextView model;
    private TextView vehicleNo;
    private TextView serviceType;
    private TextView finalBill;
    //private RatingBar ratingBar;
    private Booking booking;
    private boolean isService = false;
    private Context context;
    private int EDIT_REQ_CODE = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_details);
        setTitle(getString(R.string.completedTitle));
        initializeViews();

        getBundle();
        if (booking != null) {
            nameTextView.setText(booking.getCustomerName());
            noTextView.setText(booking.getCustomerPhoneNo());
            dateTextView.setText(AppUtils.convertDateTimeStringTodate(booking.getServiceDate()));
            deliveryDate.setText(AppUtils.convertDateTimeStringTodate(booking.getDeliveryDate()));
            //followUpDate.setText(AppUtils.convertDateTimeStringTodate(booking.getFollowUpDate()));
            brand.setText(booking.getBrand());
            model.setText(booking.getModal());
            vehicleNo.setText(booking.getVehicleNo());
            serviceType.setText(booking.getServiceType());
            finalBill.setText(booking.getFinalBill());
            //ratingBar.setRating(Float.valueOf(booking.getRatting()));
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            booking = (Booking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
        }
    }

    private void initializeViews() {
        nameTextView = (TextView) findViewById(R.id.customerName);
        noTextView = (TextView) findViewById(R.id.customerNo);
        dateTextView = (TextView) findViewById(R.id.serviceDate);
        deliveryDate = (TextView) findViewById(R.id.deliveryDate);
        //followUpDate = (TextView) findViewById(R.id.followUpDate);
        brand = (TextView) findViewById(R.id.brand);
        model = (TextView) findViewById(R.id.model);
        vehicleNo = (TextView) findViewById(R.id.vehicleNo);
        serviceType = (TextView) findViewById(R.id.serviceType);
        finalBill = (TextView) findViewById(R.id.finalBill);
        //ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supportFinishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
