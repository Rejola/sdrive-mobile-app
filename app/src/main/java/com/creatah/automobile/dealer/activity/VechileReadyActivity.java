package com.creatah.automobile.dealer.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.UpdateStatusResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class VechileReadyActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText amountEditText;

    private EditText deliveryEditText;
    private EditText timeEditText;
    private EditText nextDateEditText;
    private RadioButton yesButton;
    private Button submitButton;
    private String serviceDate;
    private DealerBooking booking;
    private int year, month, day;
    private String Date, wsDateFormat, Time = null;
    private int pHour;
    private int pMinute;
    private int cHour;
    private int cMinute;
    private String cAM_PM;
    DatePickerDialog serviceDatePicker;
    DatePickerDialog nextDatePicker;
    TimePickerDialog timePickerDialog;
    private Context context;
    private boolean isService = false;
    private String deliveryDate, nextServiceDate, deliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_ready);
        ButterKnife.bind(this);
        setTitle("Vehicle Ready");
        context = this;
        initializeViews();

        amountEditText.setFocusable(false);
        amountEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        amountEditText.setClickable(false);
        getBundle();
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        serviceDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, servicePickerListener, year, month, day);
        serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        nextDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, insurancePickerListener, year, month, day);
        nextDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
       /* pHour = cal.get(Calendar.HOUR_OF_DAY);
        cHour = pHour;
        if (cHour >= 12) {
            cAM_PM = " PM";
        } else {
            cAM_PM = " AM";
        }
        pMinute = cal.get(Calendar.MINUTE);
        cMinute = pMinute;*/
        timePickerDialog = new TimePickerDialog(context, R.style.AppTheme_DialogTheme, mTimeSetListener, pHour, pMinute, false);

        deliveryEditText.setKeyListener(null);
        timeEditText.setKeyListener(null);
        nextDateEditText.setKeyListener(null);

        submitButton.setOnClickListener(this);
        deliveryEditText.setOnClickListener(this);
        nextDateEditText.setOnClickListener(this);
        timeEditText.setOnClickListener(this);
        deliveryEditText.setOnFocusChangeListener(this);
        nextDateEditText.setOnFocusChangeListener(this);
        timeEditText.setOnFocusChangeListener(this);
        if (booking != null) {
            if (booking.getTotalAmount() != 0) {
                amountEditText.setText(String.format("%.2f", booking.getTotalAmount()));
            } else if (booking.getEstimateAmount() != 0) {
                amountEditText.setText(String.valueOf(booking.getEstimateAmount()));
            }
        }


    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
            amountEditText.setText(String.valueOf(booking.getTotalAmount()));
            serviceDate = booking.getServiceDate();
        } else {
            finish();
        }
    }

    private void initializeViews() {
        amountEditText = (EditText) findViewById(R.id.amount);
        deliveryEditText = (EditText) findViewById(R.id.deliveryDate);
        timeEditText = (EditText) findViewById(R.id.deliveryTime);
        nextDateEditText = (EditText) findViewById(R.id.dnxServiceDate);
        yesButton = (RadioButton) findViewById(R.id.yes);
        submitButton = (Button) findViewById(R.id.submitButton);
    }

    private DatePickerDialog.OnDateSetListener servicePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;

                String dates = String.valueOf(day);
                String months = String.valueOf(month + 1);
                if (dates.length() == 1) {
                    dates = "0" + dates;
                }
                if (months.length() == 1) {
                    months = "0" + months;
                }

                deliveryDate = year + "-" + months + "-" + dates;
                String displayDate = dates + "-" + months + "-" + year;
                deliveryEditText.setText(displayDate);

                if (serviceDate != null) {


                    java.util.Date serviceDt = AppUtils.getDateFormat(serviceDate);
                    java.util.Date deliveryDt = AppUtils.getDateFormat(deliveryDate);
                    java.util.Date nextSerDate = AppUtils.getDateFormat(deliveryDate);

                    if (nextServiceDate != null) {
                        if (nextSerDate.after(deliveryDt)) {

                            Date = dates + "-" + months + "-" + year;
                            nextDateEditText.setText(Date);
                        } else {
                            nextServiceDate = null;
                            nextDateEditText.setText("");
                            Time = null;
                            timeEditText.setText("");
                            timeEditText.setHint("Delivery Time");
                            nextDateEditText.setHint("Next Service Date");
                        }
                    } else {

                        nextServiceDate = null;

                    }


                    if (serviceDt.before(deliveryDt) || deliveryDt.equals(serviceDt)) {
                        deliveryDate = year + "-" + months + "-" + dates;
                        String deliveryDate = dates + "-" + months + "-" + year;
                        deliveryEditText.setText(deliveryDate);
                        timeEditText.setText("");

                    } else {
                        deliveryDate = null;
                        deliveryEditText.setText("");
                        deliveryEditText.setHint("Delivery Date");
                        Toast.makeText(context, getString(R.string.invalidDeliveryDate), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    };

    private DatePickerDialog.OnDateSetListener insurancePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;

                String dates = String.valueOf(day);
                String months = String.valueOf(month + 1);
                if (dates.length() == 1) {
                    dates = "0" + dates;
                }
                if (months.length() == 1) {
                    months = "0" + months;
                }

                nextServiceDate = year + "-" + months + "-" + dates;
                Date = dates + "-" + months + "-" + year;
                java.util.Date sdate = AppUtils.getDateFormat(deliveryDate);

                java.util.Date edate = AppUtils.getDateFormat(nextServiceDate);

                if (deliveryDate != null) {
                    if (edate.after(sdate)) {

                        Date = dates + "-" + months + "-" + year;
                        nextDateEditText.setText(Date);
                    } else {
                        nextServiceDate = null;
                        Toast.makeText(context, "Invalid Next Service Date", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    deliveryDate = null;
                    Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();

                }


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

                    VechileReadyActivity.this.Time = AppUtils.convert12To24StringFormat(Time);

                    if (deliveryDate != null) {

                        java.util.Date sdate = AppUtils.getDateFormat(deliveryDate);
                        java.util.Date sTime = AppUtils.getDateTimeFormat(deliveryDate + " " + AppUtils.getRequiredPickUpTime());
                        java.util.Date eTime = AppUtils.getDateTimeFormat(deliveryDate + " " + AppUtils.convert12To24StringFormat(Time));


                        if (eTime.before(sTime) && AppUtils.isCurentDate(sdate)) {


                            VechileReadyActivity.this.Time = null;
                            timeEditText.setText("");
                            Toast.makeText(context, "Invalid Delivery Time", Toast.LENGTH_SHORT).show();
                        } else {
                            timeEditText.setText(Time);
                        }
                    } else {

                        deliveryDate = null;
                        Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                    }

                }
            };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submitButton:
                validateDate();
                break;

            case R.id.deliveryDate:
                serviceDatePicker.show();
                break;

            case R.id.dnxServiceDate:
                if (deliveryDate == null) {
                    Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                } else if (Time == null) {
                    Toast.makeText(context, "Delivery Time Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                } else {
                    nextDatePicker.show();
                }


                break;

            case R.id.deliveryTime:
                if (deliveryDate != null) {
                    timePickerDialog.show();
                } else {
                    Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void validateDate() {
        Log.d("CUST_MSG","IN VALID FUNC");
        String finalAmt = amountEditText.getText().toString();
        String drop = "";
        String dateTime = deliveryDate + " " + Time;
        if (yesButton.isChecked()) {
            drop = "1";
        } else {
            drop = "0";
        }

        if (TextUtils.isEmpty(finalAmt))
            Toast.makeText(context, getString(R.string.enterBillAmount), Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(deliveryDate)) {
            Toast.makeText(context, getString(R.string.pleaseSelectDeliveryDate), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Time)) {
            Toast.makeText(context, getString(R.string.pleaseSelectDeliveryTime), Toast.LENGTH_SHORT).show();
        } /*else if (TextUtils.isEmpty(nextServiceDate)) {
            Toast.makeText(context, getString(R.string.pleaseSelectNextService), Toast.LENGTH_SHORT).show();

        }*/ else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
            requestParams.put("delivery_date_time", dateTime);
            requestParams.put("vehicle_drop", drop);
            requestParams.put("status_update_type", getString(R.string.vehicleReadyKey));
            requestParams.put("update_status", "true");
            requestParams.put("final_amount", finalAmt);
            requestParams.put("next_service_date", nextServiceDate);
            updateStatus(requestParams);
        }
    }

    private void updateStatus(final RequestParams requestParams) {
        Log.d("CUST_MSG","SUBMIT CLICKED");
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
                            Log.d("CUST_MSG", String.valueOf(requestParams));
                            Log.d("CUST_MSG", String.valueOf(response));
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

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {

/*
            case R.id.deliveryDate:
                if (b) {
                    AppUtils.hideKeyboard(VechileReadyActivity.this);
                    serviceDatePicker.show();
                }
                break;*/

            case R.id.dnxServiceDate:
                if (b) {

                    if (deliveryDate == null) {
                        Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                    } else if (Time == null) {
                        Toast.makeText(context, "Delivery Time Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                    } else {
                        AppUtils.hideKeyboard(VechileReadyActivity.this);
                        nextDatePicker.show();
                    }

                }
                break;

            case R.id.deliveryTime:
                if (b) {

                    if (deliveryDate != null) {

                        AppUtils.hideKeyboard(VechileReadyActivity.this);
                        timePickerDialog.show();
                    } else {
                        Toast.makeText(context, "Delivery Date Is Not Yet Selected", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

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
