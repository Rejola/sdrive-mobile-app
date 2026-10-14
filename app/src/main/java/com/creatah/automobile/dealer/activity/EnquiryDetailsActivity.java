package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.CustomerEnquiry;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 18-05-2017.
 */

public class EnquiryDetailsActivity extends BaseActivity {

    private TextView nameTextView;
    private TextView noTextView;
    private TextView email;
    private TextView address;
    private TextView date;
    private TextView brand;
    private TextView model;
    private TextView vehicleNo;
    private TextView serviceType;
    private TextView descTextView;
    private TextView kmsDriven;
    private TextView fuelType;
    private TextView variant;
    private TextView mfgYear;

    private LinearLayout addressLayout;
    private LinearLayout descLayout;
    private LinearLayout fuelLayout;
    private LinearLayout variantLayout;
    private LinearLayout yearLayout;
    private LinearLayout enquiryLayout;

    private CustomerEnquiry enquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_details);
        setTitle(getString(R.string.details));
        initializeViews();
        getBundle();

        if (enquiry != null) {
            enquiryLayout.setVisibility(View.VISIBLE);
            nameTextView.setText(enquiry.getCustomerName());
            noTextView.setText(enquiry.getCusPhoneNo());
            date.setText(AppUtils.convertDateTimeStringTodate(enquiry.getDate()));
            brand.setText(enquiry.getMake());
            model.setText(enquiry.getModel());
            vehicleNo.setText(enquiry.getVehicleNo());
            descTextView.setText(enquiry.getRequirement());
            email.setText(enquiry.getCusEmail());
            kmsDriven.setText(enquiry.getKmsDriven());
            fuelType.setText(enquiry.getFuelType());
            variant.setText(enquiry.getVehicleVariant());
            mfgYear.setText(enquiry.getManufactureYear());

            fuelLayout.setVisibility((enquiry.getFuelType() == null || TextUtils.isEmpty(enquiry.getFuelType())) ? View.GONE : View.VISIBLE);
            variantLayout.setVisibility((enquiry.getVehicleVariant() == null || TextUtils.isEmpty(enquiry.getVehicleVariant())) ? View.GONE : View.VISIBLE);
            descLayout.setVisibility((enquiry.getRequirement() == null || TextUtils.isEmpty(enquiry.getRequirement())) ? View.GONE : View.VISIBLE);
            yearLayout.setVisibility((enquiry.getManufactureYear() == null || TextUtils.isEmpty(enquiry.getManufactureYear())) ? View.GONE : View.VISIBLE);

        }
        else {
            enquiryLayout.setVisibility(View.GONE);
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ENQUIRY_DETAILS_EXTRA)) {
            enquiry = (CustomerEnquiry) bundle.getSerializable(AppConstants.ENQUIRY_DETAILS_EXTRA);
        }
    }


    private void initializeViews() {
        enquiryLayout=(LinearLayout) findViewById(R.id.enquiryLayout);
        nameTextView = (TextView) findViewById(R.id.customerName);
        noTextView = (TextView) findViewById(R.id.customerNo);
        date = (TextView) findViewById(R.id.dateTextView);
        brand = (TextView) findViewById(R.id.brand);
        model = (TextView) findViewById(R.id.model);
        vehicleNo = (TextView) findViewById(R.id.vehicleNo);
        serviceType = (TextView) findViewById(R.id.serviceType);
        descTextView = (TextView) findViewById(R.id.description);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.address);
        kmsDriven = (TextView) findViewById(R.id.kmsDriven);
        fuelType = (TextView) findViewById(R.id.fuelType);
        variant = (TextView) findViewById(R.id.vehicleVarient);
        mfgYear = (TextView) findViewById(R.id.mfgYear);

        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);
        descLayout = (LinearLayout) findViewById(R.id.descLayout);
        fuelLayout = (LinearLayout) findViewById(R.id.fuelLayout);
        variantLayout = (LinearLayout) findViewById(R.id.variantLayout);
        yearLayout = (LinearLayout) findViewById(R.id.yearLayout);

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
