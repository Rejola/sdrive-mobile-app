package com.creatah.automobile.dealer.activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.InstantService;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 18-05-2017.
 */

public class InstantDetailsActivity extends BaseActivity implements OnMapReadyCallback {
    private double latitude;
    private double longitude;

    private GoogleMap mMap;
    private Marker marker;
    private LatLng addresslocation;
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
    private Context context;

    private LinearLayout addressLayout;
    private LinearLayout descLayout;
    private LinearLayout fuelLayout;
    private LinearLayout variantLayout;
    private LinearLayout yearLayout;

    private InstantService instantService;
    private Fragment map;

    @BindView(R.id.costTextView)
    TextView costTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_details);
        setTitle(getString(R.string.details));
        ButterKnife.bind(this);
        context = this;
        latitude = 13.0971015;
        longitude = 80.253882;
        if (mMap == null) {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fm.getMapAsync(this);
        }

        initializeViews();
        getBundle();

        if (instantService != null) {
            nameTextView.setText(instantService.getCustomerName());
            noTextView.setText(instantService.getCusPhoneNo());
            date.setText(AppUtils.convertDateTimeStringTodate(instantService.getDate()));
            brand.setText(instantService.getMake());
            model.setText(instantService.getModel());
            vehicleNo.setText(instantService.getVehicleNo());
            serviceType.setText(instantService.getServiceName());
            descTextView.setText(instantService.getDescription());
            costTextView.setText(String.valueOf(instantService.getServiceCost()));
            email.setText(instantService.getCusEmail());
            address.setText(instantService.getAddress());
            kmsDriven.setText(instantService.getKmsDriven());
            fuelType.setText(instantService.getFuelType());
            variant.setText(instantService.getVehicleVariant());
            mfgYear.setText(instantService.getManufactureYear());

            addressLayout.setVisibility((instantService.getAddress() == null || TextUtils.isEmpty(instantService.getAddress())) ? View.GONE : View.VISIBLE);
            fuelLayout.setVisibility((instantService.getFuelType() == null || TextUtils.isEmpty(instantService.getFuelType())) ? View.GONE : View.VISIBLE);
            variantLayout.setVisibility((instantService.getVehicleVariant() == null || TextUtils.isEmpty(instantService.getVehicleVariant())) ? View.GONE : View.VISIBLE);
            descLayout.setVisibility((instantService.getDescription() == null || TextUtils.isEmpty(instantService.getDescription())) ? View.GONE : View.VISIBLE);
            yearLayout.setVisibility((instantService.getManufactureYear() == null || TextUtils.isEmpty(instantService.getManufactureYear())) ? View.GONE : View.VISIBLE);


        }

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.INSTANT_SERVICE_DETAILS_EXTRA)) {
            instantService = (InstantService) bundle.getSerializable(AppConstants.INSTANT_SERVICE_DETAILS_EXTRA);
        }
    }


    private void initializeViews() {
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

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (instantService != null) {

            String lat = instantService.getLatitude();
            String lng = instantService.getLongitude();
            if (lat != null && !TextUtils.isEmpty(lat) && lng != null && !TextUtils.isEmpty(lng)) {
                LatLng defaultPosition;
                defaultPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(defaultPosition).zoom(14.0f).build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                setUpMap(defaultPosition, getString(R.string.app_name));
                mMap.moveCamera(cameraUpdate);
            }
        }
    }

    private void setUpMap(LatLng poins, String s) {
        if (mMap != null) {

            if (marker != null) {
                marker.remove();
            }
            latitude = poins.latitude;
            longitude = poins.longitude;
            marker = mMap.addMarker(new MarkerOptions()
                    .title(s)
                    .position(poins));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(poins).zoom(14.0f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            mMap.moveCamera(cameraUpdate);

        } else {
            Toast.makeText(context, "Invalid bundleAddressString", Toast.LENGTH_SHORT).show();
        }
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
