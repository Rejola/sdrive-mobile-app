package com.creatah.automobile.dealer.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddBookingResponse;
import com.creatah.automobile.dealer.modal.AddRemainderResponse;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.AutoMobileService;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.modal.Insurance;
import com.creatah.automobile.dealer.modal.MakeList;
import com.creatah.automobile.dealer.modal.Modallist;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 09-03-2017.
 */

public class AddRemainderActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText nameEditText;
    private EditText noEditText;
    private EditText stateEditText;
    private EditText areaEditText;
    private EditText serialEditText;
    private EditText serialNoEditText;
    private EditText serviceDateEditText;
    private EditText descEditText;


    private Spinner serviceSpinner;
    private Spinner makeSpinner;
    private Spinner modelSpinner;

    private Button addButton;
    private Context context;

    private List<AutoMobileService> serviceList;
    private List<String> serviceStrList = new ArrayList<>();

    private List<MakeList> makeList;
    private List<String> makeStrList = new ArrayList<>();

    private List<Modallist> modalList;
    private List<String> modalStrList = new ArrayList<>();

    private AppInfo appInfo;
    private String areaCode;
    private String serviceID, makeId, modelId, serviceDate = null;

    private int year, month, day;
    private String Date, wsDateFormat, Time = null;
    private int pHour;
    private int pMinute;
    private int cHour;
    private int cMinute;
    private String cAM_PM;
    DatePickerDialog serviceDatePicker;

    private boolean isService = false;
    private String vehicleType = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remainder);
        setTitle(getString(R.string.addRemainder));
        context = this;
        initializeViews();

        getBundle();
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


        appInfo = AppUtils.getAppInfo(getApplication());

        if (appInfo != null) {

            serviceStrList.add(getString(R.string.selectService));
            if (appInfo.getServiceList() != null) {
                serviceList = appInfo.getServiceList();
                for (int i = 0; i < serviceList.size(); i++) {
                    serviceStrList.add(serviceList.get(i).getServiceName());
                }
            }

            ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, serviceStrList);
            serviceSpinner.setAdapter(serviceAdapter);
            serviceSpinner.setOnItemSelectedListener(serviceListener);

            makeStrList.add(getString(R.string.selectMake));
            if (appInfo.getCarMakeList() != null) {
                makeList = appInfo.getCarMakeList();
                for (int i = 0; i < makeList.size(); i++) {
                    makeStrList.add(makeList.get(i).getMakeName());
                }
            }
            ArrayAdapter<String> makeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, makeStrList);
            makeSpinner.setAdapter(makeAdapter);
            makeSpinner.setOnItemSelectedListener(makeListener);

            modalStrList.add(getString(R.string.selectModel));
            ArrayAdapter<String> modalAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
            modelSpinner.setAdapter(modalAdapter);
            modelSpinner.setOnItemSelectedListener(modalListener);
        }

        serviceDateEditText.setKeyListener(null);
        serviceDateEditText.setOnClickListener(this);
        serviceDateEditText.setOnFocusChangeListener(this);
        areaEditText.setOnFocusChangeListener(this);

        stateEditText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }, new InputFilter.LengthFilter(2)

        });
        serialEditText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[A-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }, new InputFilter.LengthFilter(2)
        });

        stateEditText.addTextChangedListener(stateWatcher);
        areaEditText.addTextChangedListener(areaWataher);
        serialEditText.addTextChangedListener(serialWatcher);

        addButton.setOnClickListener(this);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA)) {
            vehicleType = bundle.getString(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA);
        }
    }

    private void initializeViews() {
        nameEditText = (EditText) findViewById(R.id.customerName);
        noEditText = (EditText) findViewById(R.id.customerNo);
        stateEditText = (EditText) findViewById(R.id.stateCode);
        areaEditText = (EditText) findViewById(R.id.areaCode);
        serialEditText = (EditText) findViewById(R.id.serialText);
        serialNoEditText = (EditText) findViewById(R.id.serialNo);
        serviceDateEditText = (EditText) findViewById(R.id.serviceDate);
        descEditText = (EditText) findViewById(R.id.descEditText);
        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
        makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        modelSpinner = (Spinner) findViewById(R.id.modalSpinner);
        addButton = (Button) findViewById(R.id.BookButton);
    }


    public AdapterView.OnItemSelectedListener serviceListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                serviceID = String.valueOf(serviceList.get(position - 1).getServiceId());
            } else {
                serviceID = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public AdapterView.OnItemSelectedListener makeListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            updateAreaText();

            if (position != 0) {
                String makestring = String.valueOf(makeList.get(position - 1).getMakeId());
                modalStrList.clear();
                int pos = makeSpinner.getSelectedItemPosition() - 1;
                modalList = makeList.get(pos).getBrandlist();
                if (modalList != null && modalList.size() > 0) {
                    modalStrList.add(getString(R.string.selectModel));
                    for (Modallist list : modalList) {
                        modalStrList.add(list.getBrandName());
                    }
                    ArrayAdapter<String> modaladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
                    modelSpinner.setAdapter(modaladapter);
                } else {
                    modalStrList.add(getString(R.string.selectModel));
                    ArrayAdapter<String> modaladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
                    modelSpinner.setAdapter(modaladapter);
                    Toast.makeText(context, getString(R.string.modelsNotAvailables), Toast.LENGTH_SHORT).show();
                }

                makeId = makestring;
            } else {
                makeId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    };

    public AdapterView.OnItemSelectedListener modalListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                modelId = String.valueOf(modalList.get(position - 1).getId());
                ;
            } else {
                modelId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.BookButton:
                validateData();
                break;

            case R.id.serviceDate:
                serviceDatePicker.show();
                break;

        }

    }

    private void validateData() {

        String customerName = nameEditText.getText().toString();
        String phoneNo = noEditText.getText().toString();
        String stateCode = stateEditText.getText().toString();
        areaCode = areaEditText.getText().toString();
        String serialText = serialEditText.getText().toString();
        String serialNo = serialNoEditText.getText().toString();
        String desc = descEditText.getText().toString();
        String regNo = null;
        regNo = stateCode + "/" + areaCode + "/" + serialText + "/" + serialNo;

        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(context, getString(R.string.enterMobileNo), Toast.LENGTH_SHORT).show();
        } else if (phoneNo.length() != 10) {
            Toast.makeText(context, getString(R.string.invalidMobilNo), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(context, getString(R.string.pleaseEnterCusName), Toast.LENGTH_SHORT).show();
        } else if (serviceID == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectService), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stateCode)) {
            Toast.makeText(context, getString(R.string.stateCode), Toast.LENGTH_SHORT).show();
        } else if (stateCode.length() != 2) {
            Toast.makeText(context, getString(R.string.invalidStateCode), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(areaCode)) {
            Toast.makeText(context, getString(R.string.areaCode), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(serialText)) {
            Toast.makeText(context, getString(R.string.serialText), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(serialNo)) {
            Toast.makeText(context, getString(R.string.serialNo), Toast.LENGTH_SHORT).show();
        } else if (makeId == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectMake), Toast.LENGTH_SHORT).show();
        } else if (modelId == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectModel), Toast.LENGTH_SHORT).show();
        } else if (serviceDate == null) {
            Toast.makeText(context, getString(R.string.selectFollowUpDateR), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(desc)) {
            Toast.makeText(context, getString(R.string.enter_description), Toast.LENGTH_SHORT).show();
        } else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("customer_name", customerName);
            requestParams.put("customer_phone_no", phoneNo);
            requestParams.put("make_id", makeId);
            requestParams.put("service_id", serviceID);
            requestParams.put("vehicle_no", regNo);
            requestParams.put("model_id", modelId);
            requestParams.put("service_reminder_date", serviceDate);
            requestParams.put("description", desc);
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            addRemainder(requestParams);
        }

    }

    private void addRemainder(RequestParams requestParams) {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.addRemainder(context, requestParams, new ServiceResponseListener<AddRemainderResponse>() {
                        @Override
                        public void onSuccess(AddRemainderResponse response) {
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

            case R.id.serviceDate:
                if (b) {
                    AppUtils.hideKeyboard(AddRemainderActivity.this);
                    serviceDatePicker.show();
                }
                break;

            case R.id.areaCode:
                if (!b) {
                    updateAreaText();
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


    TextWatcher stateWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 1)

                areaEditText.requestFocus();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher areaWataher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            areaCode = charSequence.toString();
            if (charSequence.length() > 2) {
                serialEditText.requestFocus();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher serialWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 1)
                serialNoEditText.requestFocus();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void updateAreaText() {
        if (!TextUtils.isEmpty(areaCode) && areaCode.length() != 2) {
            String area = "0" + areaCode;
//            areaEditText.addTextChangedListener(areaWataher1);
            areaEditText.setText(area);
            areaEditText.addTextChangedListener(areaWataher);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
