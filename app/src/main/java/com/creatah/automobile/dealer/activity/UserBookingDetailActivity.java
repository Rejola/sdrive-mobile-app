package com.creatah.automobile.dealer.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AcknowledgementBookingResponse;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.modal.DeleteBookingResponse;
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
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 17-05-2017.
 */

public class UserBookingDetailActivity extends BaseActivity implements View.OnClickListener,OnMapReadyCallback {

    private TextView nameTextView;
    private TextView noTextView;
    private TextView followUpDate;
    private TextView brand;
    private TextView model;
    private TextView vehicleNo;
    private TextView serviceType;

    private Context context;
    private Button addButton;
    private Button deleteButton;
    private int ADD_BOOKING_REQ_CODE = 786;

    private Booking booking;
    private boolean isService;

    @BindView(R.id.ackButton)
    Button ackButton;

    private double latitude;
    private double longitude;

    private GoogleMap mMap;
    private Marker marker;
    private LatLng addresslocation;
    private Fragment map;

    @BindView(R.id.pickUpTimeTextView)
    TextView pickUpTimeTextView;

    @BindView(R.id.pickUpDateTextView)
    TextView pickUpDateTextView;

    @BindView(R.id.pickUpTimeLayout)
    LinearLayout pickUpTimeLayout;

    @BindView(R.id.pickUpDateLayout)
    LinearLayout pickUpDateLayout;

    @BindView(R.id.mapLayout)
    LinearLayout mapLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_details);
        setTitle(getString(R.string.remainderDetailsTitle));
        context = this;
        ButterKnife.bind(this);
        latitude = 13.0971015;
        longitude = 80.253882;
        if (mMap == null) {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fm.getMapAsync(this);
        }
        initializeViews();
        getBundle();

        if (booking != null) {
            nameTextView.setText(booking.getCustomerName());
            noTextView.setText(booking.getCustomerPhoneNo());
            followUpDate.setText(AppUtils.convertstringTodate1(booking.getServiceDate()));
            brand.setText(booking.getBrand());
            model.setText(booking.getModal());
            vehicleNo.setText(booking.getVehicleNo());
            serviceType.setText(booking.getServiceType());
            if (booking.getIsAcknowledged() == 0){
                ackButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
            }else if (booking.getIsAcknowledged() == 1){
                addButton.setVisibility(View.VISIBLE);
            }
            if (booking.getPickUpDateTime() !=null){
                mapLayout.setVisibility(View.VISIBLE);
                pickUpDateTextView.setText(AppUtils.convertstringTodate2(booking.getPickUpDateTime()));
                pickUpTimeTextView.setText(AppUtils.convertstringToTime1(booking.getPickUpDateTime()));
            }else {
                mapLayout.setVisibility(View.GONE);
                pickUpDateLayout.setVisibility(View.GONE);
                pickUpTimeLayout.setVisibility(View.GONE);
            }
        }
        addButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }


    private void initializeViews() {
        nameTextView = (TextView) findViewById(R.id.customerName);
        noTextView = (TextView) findViewById(R.id.customerNo);
        followUpDate = (TextView) findViewById(R.id.followUpDate);
        brand = (TextView) findViewById(R.id.brand);
        model = (TextView) findViewById(R.id.model);
        vehicleNo = (TextView) findViewById(R.id.vehicleNo);
        serviceType = (TextView) findViewById(R.id.serviceType);
        addButton = (Button) findViewById(R.id.addButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.USER_BOOKING_DETAILS_EXTRA)) {
            booking = (Booking) bundle.getSerializable(AppConstants.USER_BOOKING_DETAILS_EXTRA);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                addServiceBooking();
                break;
            case R.id.deleteButton:
                deleteBooking();
                break;
        }
    }


    private void deleteBooking() {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(booking.getBookingId()));

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.deleteBooking(context, requestParams, new ServiceResponseListener<DeleteBookingResponse>() {
                        @Override
                        public void onSuccess(DeleteBookingResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                                setResult(RESULT_OK);
                                finish();
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }

    }

    @OnClick(R.id.ackButton)
    public void AckButtonClicked(View view){
        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(booking.getBookingId()));

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.acknowledgementBooking(context, requestParams, new ServiceResponseListener<AcknowledgementBookingResponse>() {
                        @Override
                        public void onSuccess(AcknowledgementBookingResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                                setResult(RESULT_OK);
                                finish();
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }


    }

    private void addServiceBooking() {
        Intent addIntent = new Intent(context, AddBookingActivity.class);
        addIntent.putExtra(AppConstants.USER_BOOKING_DETAILS_EXTRA, booking);
        startActivityForResult(addIntent, ADD_BOOKING_REQ_CODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_BOOKING_REQ_CODE && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (booking != null) {

            String lat = booking.getLatitude();
            String lng = booking.getLongitude();
            if (lat != null && !TextUtils.isEmpty(lat) && lng != null && !TextUtils.isEmpty(lng)) {
                LatLng defaultPosition;
                defaultPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(defaultPosition).zoom(14.0f).build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                setUpMap(defaultPosition, getString(R.string.app_name));
                mMap.moveCamera(cameraUpdate);
                mapLayout.setVisibility(View.VISIBLE);
            }else {
                mapLayout.setVisibility(View.GONE);
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
}
