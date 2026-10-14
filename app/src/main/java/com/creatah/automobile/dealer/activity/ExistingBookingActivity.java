package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 05-04-2018.
 */

public class ExistingBookingActivity extends BaseActivity {
    private Context context;
   @BindView(R.id.mobileNoTextView)
    TextView mobileNoTextView;

    @BindView(R.id.nameTextView)
    TextView nameTextView;

    @BindView(R.id.emailTextView)
    TextView emailTextView;

    @BindView(R.id.mobileNumberTextView)
    TextView addressTextView;

    @BindView(R.id.addressTextView)
    TextView vehicleNoTextView;

    @BindView(R.id.MakeTextView)
    TextView MakeTextView;

    @BindView(R.id.ModelTextView)
    TextView ModelTextView;

    @BindView(R.id.serviceSpinner)
    Spinner serviceSpinner;

    @BindView(R.id.serviceDate)
    EditText serviceDate;

    @BindView(R.id.descriptionEditText)
    EditText descriptionEditText;

    @BindView(R.id.addMoreButton)
    Button addMoreButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_booking);
        setTitle(getString(R.string.existingBooking));
        ButterKnife.bind(this);
        context= this;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
