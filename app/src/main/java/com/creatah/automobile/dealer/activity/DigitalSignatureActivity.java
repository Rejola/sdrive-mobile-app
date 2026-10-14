package com.creatah.automobile.dealer.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.CheckListAdapter1;
import com.creatah.automobile.dealer.adapter.ImageGalleryAdapter1;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.BookingImage;
import com.creatah.automobile.dealer.modal.BookingImageListResponse;
import com.creatah.automobile.dealer.modal.CheckListResponse;
import com.creatah.automobile.dealer.modal.CheckOut;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.UpdateStatusResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DigitalSignatureActivity extends BaseActivity implements View.OnClickListener {


    private int VISITOR_SIGNATURE_ACTIVITY_REQ = 678;
    private final int STORAGE_REQUEST_PERMISSION = 712;

    private Context context;
    private boolean isService = false;
    private String STATUS = "true";

    private String STATUS_TYPE;
    private String employeeId;
    private String uniqueId;
    private int pageNumber = 1;
    private Bitmap visitorBitmapitmap;
    private List<BookingImage> bookingImageList;
    private DealerBooking dealerBooking;
    private String serviceDate;

    private ImageGalleryAdapter1 imageGalleryAdapter;
    private List<CheckOut> checkOutList = new ArrayList<>();
    private List<CheckOut> activeCheckList = new ArrayList<>();


    private CheckListAdapter1 checkListAdapter1;

    @BindView(R.id.horizontalRecyclerViewImageList)
    RecyclerView horizontalRecyclerViewImageList;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    @BindView(R.id.checkListLayout)
    LinearLayout checkListLayout;

    @BindView(R.id.imageListLayout)
    LinearLayout imageListLayout;

    @BindView(R.id.checkRecyclerView)
    RecyclerView checkRecyclerView;


    @BindView(R.id.clickSignPageButton)
    Button clickSignPageButton;

    @BindView(R.id.kmsDriven)
    TextView kmsDriven;

    @BindView(R.id.termsAndConditionCheckbox)
    CheckBox termsAndConditionCheckbox;

    @BindView(R.id.termsAndConditionTextView)
    TextView termsAndConditionTextView;

    @BindView(R.id.updateButtonLayout)
    Button updateButtonLayout;

    @BindView(R.id.anyOthers)
    TextView anyOthers;

    @BindView(R.id.anyOthersText)
    TextView anyOthersText;

    @BindView(R.id.signImageView)
    ImageView signImageView;


    @BindView(R.id.noInternetConnection)
    TextView noInternetConnection;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.noCheckListFound)
    TextView noCheckListFound;


    @BindView(R.id.checkListTimeTextView)
    TextView checkListTimeTextView;

    @BindView(R.id.digitalLayout)
    LinearLayout digitalLayout;

    @BindView(R.id.checkListDateTextView)
    TextView checkListDateTextView;

    @BindView(R.id.deleteSignature)
    ImageView deleteSignature;

    private DatePickerDialog approximateDatePicker;
    private TimePickerDialog approximateTimePicker;
    private String Time = null;
    private LinearLayoutManager linearLayoutManager;

    private int year, month, day, pMinute, pHour;

    private String approximateDate;
    private String cAM_PM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_sign);
        ButterKnife.bind(this);
        context = this;
        setTitle("Check In Details");
        updateButtonLayout.setOnClickListener(this);
        termsAndConditionTextView.setOnClickListener(this);
        clickSignPageButton.setOnClickListener(this);
        deleteSignature.setOnClickListener(this);
        signImageView.setOnClickListener(this);
        getBundle();
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        approximateDatePicker = new DatePickerDialog(context, R.style.AppTheme_DialogTheme, approximateDateListener, year, month, day);
        approximateDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        approximateTimePicker = new TimePickerDialog(context, R.style.AppTheme_DialogTheme, mTimeSetListener, pHour, pMinute, false);


        horizontalRecyclerViewImageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        checkRecyclerView.setLayoutManager(new LinearLayoutManager(context));


        getImageList();
        getCheckList();

    }

    @OnClick(R.id.checkListDateTextView)
    public void onClickDate() {
        approximateDatePicker.show();
    }

    private DatePickerDialog.OnDateSetListener approximateDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String dates = String.valueOf(dayOfMonth);
            String months = String.valueOf(month + 1);
            if (dates.length() == 1) {
                dates = "0" + dates;
            }
            if (months.length() == 1) {
                months = "0" + months;
            }
            approximateDate = year + "-" + months + "-" + dates;
            String displayDate = dates + "-" + months + "-" + year;
            checkListDateTextView.setText(displayDate);

            if(serviceDate!=null)
            {

                java.util.Date servicedate = AppUtils.getDateFormat(serviceDate);

                java.util.Date approxiDate = AppUtils.getDateFormat(approximateDate);

                if (servicedate.before(approxiDate) || approxiDate.equals(servicedate)) {

                    approximateDate = year + "-" + months + "-" + dates;
                    String displayApproxiDate = dates + "-" + months + "-" + year;
                    checkListDateTextView.setText(displayApproxiDate);
                    Time = null;
                    checkListTimeTextView.setText("");
                    checkListTimeTextView.setHint("Time");

                } else {
                    approximateDate = null;
                    checkListDateTextView.setText("");
                    checkListDateTextView.setHint("Date");
                    checkListTimeTextView.setText("");
                    checkListTimeTextView.setHint("Time");
                    Toast.makeText(context, "Invalid Approximate Date", Toast.LENGTH_SHORT).show();

                }
            }
            else {

            }


        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    String mm_precede = "";
                    String hh_precede = "";
                    String AM_PM = "AM";
                    if (hourOfDay >= 12) {
                        AM_PM = "PM";
                        if (hourOfDay >= 13 && hourOfDay < 24) {
                            hourOfDay -= 12;
                        } else {
                            hourOfDay = 12;
                        }
                    } else if (hourOfDay == 0) {
                        hourOfDay = 12;
                    }
                    if (minute < 10) {
                        mm_precede = "0";
                    }

                    if (hourOfDay < 10) {
                        hh_precede = "0";
                    }

                    String Time = hh_precede + hourOfDay + ":" + mm_precede + pMinute + " " + AM_PM;

                    DigitalSignatureActivity.this.Time = AppUtils.convert12To24StringFormat(Time);

                    if (approximateDate != null) {
                        java.util.Date sdate = AppUtils.getDateFormat(approximateDate);
                        java.util.Date sTime = AppUtils.getDateTimeFormat(approximateDate + " " + AppUtils.getCurrentTime());
                        java.util.Date eTime = AppUtils.getDateTimeFormat(approximateDate + " " + AppUtils.convert12To24StringFormat(Time));

                       if (eTime.before(sTime) && AppUtils.isCurentDate(sdate)) {
                            DigitalSignatureActivity.this.Time = null;
                            checkListTimeTextView.setText("");
                            Toast.makeText(context, "Invalid Approximate Time", Toast.LENGTH_SHORT).show();
                        } else {
                            checkListTimeTextView.setText(Time);
                        }
                    } else {

                        approximateDate = null;
                        Toast.makeText(context, "Approximate Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();

                    }


                }
            };


    @OnClick(R.id.checkListTimeTextView)
    public void onClickTime() {
        approximateTimePicker.show();
    }


    private void getCheckList() {

        if (DeviceManager.isDeviceOffline(context)) {
            digitalLayout.setVisibility(View.GONE);
            checkListLayout.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            noInternetConnection.setText(getString(R.string.title_of_data_message));
            return;
        }

        final RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(dealerBooking.getJobcardId()));


//            if (!isService) {
        isService = true;
        mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);

        ServiceManager.getCheckList(context, requestParams, new ServiceResponseListener<CheckListResponse>() {
                    @Override
                    public void onSuccess(CheckListResponse response) {
                        isService = false;
                        digitalLayout.setVisibility(View.VISIBLE);
                        noInternetConnection.setVisibility(View.GONE);
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                        checkListLayout.setVisibility(View.VISIBLE);

                        if (response != null && !response.isError() && response.getCheckOutList() != null && response.getCheckOutList().size() > 0) {

                            checkRecyclerView.setVisibility(View.VISIBLE);
                            checkOutList = response.getCheckOutList();
                            activeCheckList = new ArrayList<>();
                            for (CheckOut checkOut : checkOutList) {
                                if (checkOut.isChecked()) {
                                    activeCheckList.add(checkOut);
                                }
                            }
                            checkListAdapter1 = new CheckListAdapter1(DigitalSignatureActivity.this, activeCheckList);
                            checkRecyclerView.setAdapter(checkListAdapter1);

                        } else if (response != null) {
                            checkRecyclerView.setVisibility(View.GONE);
                            noCheckListFound.setText(getString(R.string.noCheckListFound));

                        } else {

                            checkRecyclerView.setVisibility(View.GONE);
                            noCheckListFound.setVisibility(View.VISIBLE);
                            noCheckListFound.setText(context.getString(R.string.noCheckListFound));
                            checkListLayout.setVisibility(View.GONE);
                        }

                        if (response.getKmsDriven() > 0) {
                            kmsDriven.setText(" : " + String.valueOf(response.getKmsDriven()));
                        }

                        if (response.getDescription() != null && !response.getDescription().isEmpty()) {
                            anyOthersText.setVisibility(View.VISIBLE);
                            anyOthers.setVisibility(View.VISIBLE);
                            anyOthers.setText(response.getDescription());

                        } else {

                            anyOthersText.setVisibility(View.GONE);
                            anyOthers.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String errorResponse) {
                        isService = false;
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                        checkListLayout.setVisibility(View.GONE);
                        digitalLayout.setVisibility(View.GONE);
                        noInternetConnection.setVisibility(View.VISIBLE);
                        noInternetConnection.setText(getString(R.string.title_of_data_message));

                    }
                }
        );

    }

    private void getImageList() {

        if (DeviceManager.isDeviceOffline(context)) {
            digitalLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.no_check_in_image_found));
            imageListLayout.setVisibility(View.GONE);

            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(dealerBooking.getJobcardId()));
        requestParams.put("status", STATUS_TYPE);


        if (!isService) {

            isService = true;
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);

            ServiceManager.getBookingImageList(context, requestParams, new ServiceResponseListener<BookingImageListResponse>() {
                        @Override
                        public void onSuccess(BookingImageListResponse response) {
                            digitalLayout.setVisibility(View.VISIBLE);
                            noResultFound.setVisibility(View.GONE);
                            imageListLayout.setVisibility(View.VISIBLE);
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getImageList() != null && response.getImageList().size() > 0) {
                                noResultFound.setVisibility(View.GONE);
                                horizontalRecyclerViewImageList.setVisibility(View.VISIBLE);
                                imageGalleryAdapter = new ImageGalleryAdapter1(DigitalSignatureActivity.this, response);
                                horizontalRecyclerViewImageList.setAdapter(imageGalleryAdapter);

                            } else {

                                horizontalRecyclerViewImageList.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.no_check_in_image_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            checkListLayout.setVisibility(View.GONE);
                            digitalLayout.setVisibility(View.GONE);
                            imageListLayout.setVisibility(View.GONE);
                            noInternetConnection.setVisibility(View.VISIBLE);
                            noInternetConnection.setText(getString(R.string.title_of_data_message));

                        }
                    }

            );
        }
    }


    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            dealerBooking = (DealerBooking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
            STATUS_TYPE = bundle.getString(AppConstants.STATUS_TYPE_STRING);
            serviceDate = dealerBooking.getServiceDate();
        } else {

            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clickSignPageButton:
                deleteSignature.setVisibility(View.GONE);
                checkPermission();
                break;

            case R.id.updateButtonLayout:
                validate();
                break;

            case R.id.signImageView:
                deleteSignature.setVisibility(View.VISIBLE);
                signImageView.setVisibility(View.VISIBLE);
                showSignatureScreen();
                break;
            case R.id.deleteSignature:
                visitorBitmapitmap = null;
                clickSignPageButton.setVisibility(View.VISIBLE);
                signImageView.setVisibility(View.GONE);
                deleteSignature.setVisibility(View.GONE);
                break;

            case R.id.termsAndConditionTextView:
                String url = "http://www.sdrive.com/termsandconditions.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }

    }

    private void validate() {

        if (approximateDate == null) {
            Toast.makeText(context, getString(R.string.approximateDate), Toast.LENGTH_SHORT).show();

        } else if (Time == null) {
            Toast.makeText(context, getString(R.string.approximateTime), Toast.LENGTH_SHORT).show();
        } /*else if (visitorBitmapitmap == null) {
            Toast.makeText(context, getString(R.string.addSignature), Toast.LENGTH_SHORT).show();

        }*/ else if (!termsAndConditionCheckbox.isChecked()) {
            Toast.makeText(context, "please agree the terms and conditions", Toast.LENGTH_SHORT).show();
        } else {

            updateStatus();
        }

    }

    private void updateStatus() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("booking_id", String.valueOf(dealerBooking.getJobcardId()));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("status_update_type", STATUS_TYPE);
        requestParams.put("update_status", STATUS);
        requestParams.put("emp_id", employeeId);
        requestParams.put("approximate_delivery_date", approximateDate);
        requestParams.put("approximate_delivery_time", AppUtils.convert24T12StringFormat(Time));
        if (visitorBitmapitmap != null)
            try {
                requestParams.put("cus_signature", Utils.getFileFromBitmap(context, visitorBitmapitmap));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.updateBookingStatus(context, requestParams, new ServiceResponseListener<UpdateStatusResponse>() {
                        @Override
                        public void onSuccess(UpdateStatusResponse response) {
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

    private void checkPermission() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                showSignatureScreen();
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showAlert(context, getString(R.string.storagepermission), getString(R.string.storagepermissiondesc), "Try", Try1, "No", No);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_PERMISSION);
                }
            }
        } else {
            showSignatureScreen();
        }
    }

    DialogInterface.OnClickListener No = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    DialogInterface.OnClickListener Try1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(DigitalSignatureActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_REQUEST_PERMISSION);

        }
    };

    private void showSignatureScreen() {

        Intent digitalSign = new Intent(context, CaptureSignatureActivity.class);
        startActivityForResult(digitalSign, VISITOR_SIGNATURE_ACTIVITY_REQ);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VISITOR_SIGNATURE_ACTIVITY_REQ && resultCode == RESULT_OK) {
            visitorBitmapitmap = AppConstants.SinatureBitmap;
            signImageView.setVisibility(View.VISIBLE);
            signImageView.setImageBitmap(AppConstants.SinatureBitmap);
            deleteSignature.setVisibility(View.VISIBLE);
            clickSignPageButton.setVisibility(View.GONE);
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case STORAGE_REQUEST_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showSignatureScreen();

                }

        }
    }
}
