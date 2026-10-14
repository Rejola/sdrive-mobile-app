package com.creatah.automobile.dealer.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DeleteResponse;
import com.creatah.automobile.dealer.modal.Reminder;
import com.creatah.automobile.dealer.modal.UpdateRemainderResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 03-05-2017.
 */

public class ReminderDetailsActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextView nameTextView;
    private TextView noTextView;
    private TextView followUpDate;
    private TextView brand;
    private TextView model;
    private TextView vehicleNo;
    private TextView serviceType;
    private TextView descTextView;
    private RelativeLayout addRelativeLayout;

    private EditText serviceDateEditText;
    private EditText descEditText;

    private CardView descLayout;
    private LinearLayout updateLayout;

    private Button addButton;
    private Button updateButton;
    private Context context;

    private Reminder reminder;
    private boolean isService = false;

    private String serviceDate = null;
    private int year, month, day;
    private String Date, wsDateFormat, Time = null;
    private int pHour;
    private int pMinute;
    private int cHour;
    private int cMinute;
    private String cAM_PM;
    DatePickerDialog serviceDatePicker;
    private int ADD_BOOKING_REQ_CODE = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_details);
        setTitle(getString(R.string.remainderDetails));
        initializeViews();
        context = this;
        getBundle();
        if (reminder != null) {
            nameTextView.setText(reminder.getCustomerName());
            noTextView.setText(reminder.getMobileNo());
            followUpDate.setText(AppUtils.convertstringTodate1(reminder.getFollowUpDate()));
            brand.setText(reminder.getMakeName());
            model.setText(reminder.getModel());
            vehicleNo.setText(reminder.getVehicleNo());
            serviceType.setText(reminder.getServiceName());
            descTextView.setText(reminder.getDescription());
            serviceDate=reminder.getFollowUpDate();
            serviceDateEditText.setText(AppUtils.convertstringTodate2(reminder.getFollowUpDate()));
            descEditText.setText(reminder.getDescription());

        }
        initializeDatePicker();
        addButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        serviceDateEditText.setKeyListener(null);
        serviceDateEditText.setOnClickListener(this);
        serviceDateEditText.setOnFocusChangeListener(this);
    }

    private void initializeDatePicker() {
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        serviceDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, servicePickerListener, year, month, day);
        serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        cHour = pHour;
        if (cHour >= 12) {
            cAM_PM = " PM";
        } else {
            cAM_PM = " AM";
        }
        pMinute = cal.get(Calendar.MINUTE);
        cMinute = pMinute;

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.REMAINDER_DETAILS_EXTRA)) {
            reminder = (Reminder) bundle.getSerializable(AppConstants.REMAINDER_DETAILS_EXTRA);
        }
    }

    private void initializeViews() {
        nameTextView = (TextView) findViewById(R.id.customerName);
        noTextView = (TextView) findViewById(R.id.customerNo);
        followUpDate = (TextView) findViewById(R.id.followUpDate);
        brand = (TextView) findViewById(R.id.brand);
        model = (TextView) findViewById(R.id.model);
        vehicleNo = (TextView) findViewById(R.id.vehicleNo);
        serviceType = (TextView) findViewById(R.id.serviceType);
        descTextView = (TextView) findViewById(R.id.description);
        addButton = (Button) findViewById(R.id.addButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        serviceDateEditText = (EditText) findViewById(R.id.serviceDate);
        descEditText = (EditText) findViewById(R.id.descEditText);
        descLayout = (CardView) findViewById(R.id.descLayout);
        updateLayout = (LinearLayout) findViewById(R.id.updateLayout);
        addRelativeLayout = (RelativeLayout) findViewById(R.id.addRelativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remainder_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (updateLayout.getVisibility() == View.VISIBLE) {
                    descLayout.setVisibility(View.VISIBLE);
                    addRelativeLayout.setVisibility(View.VISIBLE);
                    updateLayout.setVisibility(View.GONE);
                } else {
                    finish();
                }
                break;

            case R.id.action_edit:
                addRelativeLayout.setVisibility(View.GONE);
                descLayout.setVisibility(View.GONE);
                updateLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.action_delete:
                showAlert(context, getString(R.string.alert), getString(R.string.deleteAlertR), "YES", YES_DELETE, "No", No);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    DialogInterface.OnClickListener YES_DELETE = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            deleteServiceCall();
            dialog.dismiss();
        }
    };

    DialogInterface.OnClickListener No = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    private void deleteServiceCall() {

        if (DeviceManager.isDeviceOffline(context)) {
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }
        mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
        if (!isService) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("reminder_id", String.valueOf(reminder.getRemainderId()));
            ServiceManager.deleteRemainder(getApplicationContext(), requestParams, new ServiceResponseListener<DeleteResponse>() {
                        @Override
                        public void onSuccess(DeleteResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            if (response != null && !response.isError()) {
                                setResult(RESULT_OK);
                                finish();
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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addButton:
                addServiceBooking();
                break;

            case R.id.updateButton:
                validateData();
                break;


            case R.id.serviceDate:
                serviceDatePicker.show();
                break;
        }
    }

    private void addServiceBooking() {
        Intent addIntent = new Intent(context, AddBookingActivity.class);
        addIntent.putExtra(AppConstants.REMAINDER_DETAILS_EXTRA, reminder);
        startActivityForResult(addIntent, ADD_BOOKING_REQ_CODE);
    }

    private void validateData() {
        String desc = descEditText.getText().toString();
        if (serviceDate == null) {
            Toast.makeText(context, getString(R.string.selectFollowUpDateR), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(desc)) {
            Toast.makeText(context, getString(R.string.enter_description), Toast.LENGTH_SHORT).show();

        } else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("service_reminder_date", serviceDate);
            requestParams.put("desc", desc);
            requestParams.put("reminder_id", String.valueOf(reminder.getRemainderId()));
            updateRemainder(requestParams);
        }
    }

    private void updateRemainder(RequestParams requestParams) {
        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.updateRemainder(context, requestParams, new ServiceResponseListener<UpdateRemainderResponse>() {
                        @Override
                        public void onSuccess(UpdateRemainderResponse response) {
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
                serviceDate = year + "-" + months + "-" + dates;
                Date = dates + "-" + months + "-" + year;
                serviceDateEditText.setText(Date);
            }
        }
    };


    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {

            case R.id.serviceDate:
                if (!b) {
                    AppUtils.hideKeyboard(ReminderDetailsActivity.this);
                    serviceDatePicker.show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_BOOKING_REQ_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
