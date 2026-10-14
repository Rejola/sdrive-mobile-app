package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 04-04-2018.
 */

public class NewBookingActivity extends BaseActivity {
    private Context context;
    @BindView(R.id.searchText)
    EditText searchText;

    @BindView(R.id.searchIcon)
    ImageView searchIcon;

    @BindView(R.id.addNewButton)
    Button addNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_booking);
        setTitle(getString(R.string.newBooking));
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
