package com.creatah.automobile.dealer.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.VehiclePagerAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddBookingResponse;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.AutoMobileService;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.modal.CustomerSearchResponse;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.EditBookingResponse;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.modal.Insurance;
import com.creatah.automobile.dealer.modal.MakeList;
import com.creatah.automobile.dealer.modal.Modallist;
import com.creatah.automobile.dealer.modal.RefreshHome;
import com.creatah.automobile.dealer.modal.Reminder;
import com.creatah.automobile.dealer.modal.Vehicle;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 09-03-2017.
 */

public class AddBookingActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener, VehiclePagerAdapter.VehicleSelectListener {
    private EditText descriptionEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText customerNo;
    private EditText searchEditText;
    private EditText nameEditText;
    private EditText noEditText;
    private EditText stateEditText;
    private EditText areaEditText;
    private EditText serialEditText;
    private EditText serialNoEditText;
    private TextView serviceDateEditText;
    private TextView pickUpDateEditText;
    private TextView pickUpTimeEditText;

    private Spinner serviceSpinner;
    private Spinner makeSpinner;
    private Spinner modelSpinner;
    private Spinner fuelSpinner;
    private Spinner personSpinner;
    private Spinner adviserSpinner;

    private RadioGroup radioGroup;
    private RadioButton yesButton;
    private RadioButton noButton;
    private LinearLayout pickUpLayout;
    private Button addButton;
    private EditText pickupAddressEditText;
    private LinearLayout searchLayout;

    private RecyclerView vehicleRecyclerView;

    private CardView vehicleLayout;

    private ImageView searchImageView;

    private ViewPager vehicleViewPager;

    private VehiclePagerAdapter pagerAdapter;


    private Context context;

    private String reminderId;

    private List<Employee> employeeList;
    private List<Employee> advisorList = new ArrayList<>();

    private List<String> employeeStrList = new ArrayList<>();

    private List<String> advisorStrList = new ArrayList<>();

    private List<AutoMobileService> serviceList;
    private List<String> serviceStrList = new ArrayList<>();

    private List<Insurance> insuranceList;
    private List<String> insuranceStrList = new ArrayList<>();

    private List<MakeList> makeList;
    private List<String> makeStrList = new ArrayList<>();

    private List<Modallist> modalList;
    private List<String> modalStrList = new ArrayList<>();

    private List<String> fuelString = new ArrayList<>();

    private AppInfo appInfo;

    private String employeeId, adviserId, serviceID, makeId, modelId, insuranceId, fuelType, serviceDate, insuranceDate, pickUpDate = null;

    private int year, month, day;
    private String Time = null;
    private int pHour;
    private int pMinute;
    private int cHour;
    DatePickerDialog serviceDatePicker;
    DatePickerDialog insuranceDatePicker;
    DatePickerDialog pickUpeDatePicker;
    TimePickerDialog timePickerDialog;

    private boolean isService = false;
    private String vehicleType = " ";

    private Reminder reminder;
    private Booking userBooking;
    private int makePos = 0;
    private int modalPos = 0;
    private int servicePos = 0;
    private int adviserPos = 0;
    private int pickPersonPos = 0;
    private boolean isFirstTimeModel = true;
    private String areaCode;

    private CustomerSearchResponse customerResponse;

    private List<Vehicle> vehicleList = new ArrayList<>();

    private Vehicle vehicle;

    private String SEARCH_KEY = null;
    private boolean isSearched = false;

    private DealerBooking dealerBooking;


    private boolean isEdit;
    private final Calendar cal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        setTitle(getString(R.string.addBooking));
        context = this;
        initializeViews();

        getBundle();

        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        serviceDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, servicePickerListener, year, month, day);
        serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pickUpeDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, pickUpPickerListener, year, month, day);
        pickUpeDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        cHour = pHour;
        pMinute = cal.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(context, R.style.AppTheme_DialogTheme, mTimeSetListener, pHour, pMinute, false);


        if (vehicleType.equals(getString(R.string.bike))) {
            radioGroup.setVisibility(View.GONE);
        } else if (vehicleType.equals(getString(R.string.car))) {
            radioGroup.setVisibility(View.VISIBLE);
        }

        appInfo = AppUtils.getAppInfo(getApplication());


        if (appInfo != null) {
            employeeStrList.add(getString(R.string.selectPickUpPerson));
            advisorStrList.add(getString(R.string.selectAdvisor));
            if (appInfo.getEmployeeList() != null) {
                employeeList = appInfo.getEmployeeList();
                for (int i = 0; i < employeeList.size(); i++) {
                    if (employeeList.get(i).getDesignation().equals(getString(R.string.serviceAdvisor))) {
                        advisorStrList.add(employeeList.get(i).getEmpName());
                        advisorList.add(employeeList.get(i));
                    }
                }

                for (int i = 0; i < employeeList.size(); i++) {

                    employeeStrList.add(employeeList.get(i).getEmpName());

                }
            }

            ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, employeeStrList);
            personSpinner.setAdapter(employeeAdapter);
            personSpinner.setOnItemSelectedListener(employeeListener);


            ArrayAdapter<String> advisorAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, advisorStrList);
            adviserSpinner.setAdapter(advisorAdapter);
            adviserSpinner.setOnItemSelectedListener(adviserListener);


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

        fuelString.add(getString(R.string.petrol));
        fuelString.add(getString(R.string.diesel));
        fuelString.add(getString(R.string.gas));
        fuelString.add(getString(R.string.electric));

        ArrayAdapter<String> fueladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, fuelString);
        fuelSpinner.setAdapter(fueladapter);
        addButton.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.pickUp) {
                    pickUpLayout.setVisibility(View.VISIBLE);
                } else {
                    pickUpLayout.setVisibility(View.GONE);
                }
            }
        });

        serviceDateEditText.setKeyListener(null);
        pickUpDateEditText.setKeyListener(null);
        pickUpTimeEditText.setKeyListener(null);


        serviceDateEditText.setOnClickListener(this);
        pickUpDateEditText.setOnClickListener(this);
        pickUpTimeEditText.setOnClickListener(this);

        serviceDateEditText.setOnFocusChangeListener(this);
        pickUpDateEditText.setOnFocusChangeListener(this);
        pickUpTimeEditText.setOnFocusChangeListener(this);
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


//        nameEditText.setFilters(new InputFilter[] { filter });


        stateEditText.addTextChangedListener(stateWatcher);
        areaEditText.addTextChangedListener(areaWataher);
        serialEditText.addTextChangedListener(serialWatcher);
        serialNoEditText.addTextChangedListener(serialNoWatcher);

        if (reminder != null) {
            nameEditText.setText(reminder.getCustomerName());
            noEditText.setText(reminder.getMobileNo());
            descriptionEditText.setText(reminder.getDescription());
            if (reminder.getVehicleNo() != null) {
                if (reminder.getVehicleNo().length() > 3) {
                    String[] vehicleNo = reminder.getVehicleNo().split("/");
                    stateEditText.setText(vehicleNo[0]);
                    areaEditText.setText(vehicleNo[1]);
                    serialEditText.setText(vehicleNo[2]);
                    serialNoEditText.setText(vehicleNo[3]);
                }
            }
            getMakeIdFromName(reminder.getMakeName());
            getServiceIdFromName(reminder.getServiceName());
            serviceSpinner.setSelection(servicePos);
            makeSpinner.setSelection(makePos);
            searchImageView.setVisibility(View.GONE);
        }

        if (userBooking != null) {

            nameEditText.setText(userBooking.getCustomerName());
            noEditText.setText(userBooking.getCustomerPhoneNo());
            if (userBooking.getVehicleNo() != null) {
                if (userBooking.getVehicleNo().length() > 3) {
                    String[] vehicleNo = userBooking.getVehicleNo().split("/");
                    stateEditText.setText(vehicleNo[0]);
                    areaEditText.setText(vehicleNo[1]);
                    serialEditText.setText(vehicleNo[2]);
                    serialNoEditText.setText(vehicleNo[3]);
                }
            }
            getMakeIdFromName(userBooking.getBrand());
            getServiceIdFromName(userBooking.getServiceType());
            serviceSpinner.setSelection(servicePos);
            makeSpinner.setSelection(makePos);
            serviceDate = userBooking.getServiceDate();
            serviceDateEditText.setText(AppUtils.convertstringTodate2(userBooking.getServiceDate()));
            searchImageView.setVisibility(View.GONE);
            if (userBooking.getPickUpDateTime() != null && !userBooking.getPickUpDateTime().isEmpty()) {
                yesButton.setChecked(true);
            } else {
                noButton.setChecked(true);
            }

        }


        if (dealerBooking != null) {
            nameEditText.setText(dealerBooking.getCustomerName());
            noEditText.setText(dealerBooking.getMobileNo());
            pickupAddressEditText.setText(dealerBooking.getPickupAddress());
            if (dealerBooking.getVehicleNo() != null) {
                if (dealerBooking.getVehicleNo().length() > 3) {
                    String[] vehicleNo = dealerBooking.getVehicleNo().split("/");
                    stateEditText.setText(vehicleNo[0]);
                    areaEditText.setText(vehicleNo[1]);
                    serialEditText.setText(vehicleNo[2]);
                    serialNoEditText.setText(vehicleNo[3]);
                }
            }
            adviserId = String.valueOf(dealerBooking.getAdviserId());
            getMakeIdFromName(dealerBooking.getMakeName());
            getServiceIdFromName(dealerBooking.getServiceName());
            getAdviserIdFromName(dealerBooking.getAdviserId());
            getPickPersonIdFromName(dealerBooking.getPickupPerson());

            personSpinner.setSelection(pickPersonPos);
            serviceSpinner.setSelection(servicePos);
            makeSpinner.setSelection(makePos);
            adviserSpinner.setSelection(adviserPos);
            pickUpDate = dealerBooking.getPickDate();
            if (dealerBooking.getPickDate() != null) {

                pickUpDateEditText.setText(AppUtils.convertstringTodate2(dealerBooking.getPickDate()));
            }

            if (dealerBooking.getPickUpTime() != null) {
                Time=dealerBooking.getPickUpTime();
                pickUpTimeEditText.setText(AppUtils.convert24T12StringFormat(dealerBooking.getPickUpTime()));
            }
            if (dealerBooking.getServiceDate() != null) {
                serviceDate = dealerBooking.getServiceDate();
                serviceDateEditText.setText(AppUtils.convertstringTodate2(dealerBooking.getServiceDate()));
            }
            searchImageView.setVisibility(View.GONE);
            if (dealerBooking.isPickupStatus()) {
                yesButton.setChecked(true);
            } else {
                noButton.setChecked(true);
            }

            addressEditText.setText(dealerBooking.getAddress());
            fuelType = dealerBooking.getFuelType();

            for (int i = 0; i < fuelString.size(); i++) {
                if (fuelString.get(i).equalsIgnoreCase(fuelType)) {
                    fuelSpinner.setSelection(i);
                }
            }
            descriptionEditText.setText(dealerBooking.getJobcardDescription());
            emailEditText.setText(dealerBooking.getEmailId());
        }



        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!isSearched) {
                        getCustomerName();
                    } else {
                        SEARCH_KEY = null;
                        isSearched = false;
                        searchImageView.setImageResource(R.drawable.ic_search);
                        noEditText.setText("");
                        nameEditText.setText("");
                        emailEditText.setText("");
                        noEditText.setText("");
                        addressEditText.setText("");
                        stateEditText.setText("");
                        areaEditText.setText("");
                        serialEditText.setText("");
                        serialNoEditText.setText("");
                        makeStrList.clear();
                        fuelString.clear();
                        clearSearchCustomer();
                    }
                }
                return false;
            }
        });


        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSearched) {
                    getCustomerName();
                } else {
                    SEARCH_KEY = null;
                    isSearched = false;
                    searchImageView.setImageResource(R.drawable.ic_search);
                    searchEditText.setText("");
                    noEditText.setText("");
                    nameEditText.setText("");
                    emailEditText.setText("");
                    noEditText.setText("");
                    addressEditText.setText("");
                    stateEditText.setText("");
                    areaEditText.setText("");
                    serialEditText.setText("");
                    serialNoEditText.setText("");
                    makeStrList.clear();
                    fuelString.clear();
                    clearSearchCustomer();
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                SEARCH_KEY = searchEditText.getText().toString();

                if (s.length()!=0) {
                    isSearched = false;
                    searchImageView.setImageResource(R.drawable.ic_search);
                    noEditText.setText("");
                    nameEditText.setText("");
                    emailEditText.setText("");
                    noEditText.setText("");
                    addressEditText.setText("");
                    stateEditText.setText("");
                    areaEditText.setText("");
                    serialEditText.setText("");
                    serialNoEditText.setText("");
                   /* makeStrList.clear();
                    fuelString.clear();*/
                    clearSearchCustomer();

                } else {

                    clearSearchCustomer();
                }
/*

                if (SEARCH_KEY.length() == 0) {
                    nameEditText.setText("");
                    emailEditText.setText("");
                    addressEditText.setText("");
                    stateEditText.setText("");
                    areaEditText.setText("");
                    serialEditText.setText("");
                    serialNoEditText.setText("");
                    clearSearchCustomer();
                }
*/


            }
        });

/*
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getCustomerName();
                    return true;
                }
                return false;
            }
        });


*/


    }

    private void clearSearchCustomer() {
        customerResponse = null;
        nameEditText.setEnabled(true);
        emailEditText.setEnabled(true);
        noEditText.setEnabled(true);
        addressEditText.setEnabled(true);
        stateEditText.setEnabled(true);
        areaEditText.setEnabled(true);
        serialEditText.setEnabled(true);
        serialNoEditText.setEnabled(true);
        vehicleLayout.setVisibility(View.GONE);
    }

    private void updateViewSearchResult() {
        if (customerResponse != null) {

            nameEditText.setText(customerResponse.getName());
            emailEditText.setText(customerResponse.getEmail());
            noEditText.setText(customerResponse.getMobileNo());
            addressEditText.setText(customerResponse.getAddress());

            int valueInPixels = 140;
            vehicleViewPager.setPageMargin((int) dpFromPx(80));
            vehicleViewPager.setPadding((int) dpFromPx(valueInPixels), (int) dpFromPx(5), (int) dpFromPx(valueInPixels), (int) dpFromPx(5));
            vehicleViewPager.setClipToPadding(false);


            vehicleList = customerResponse.getVehicleList();
            vehicleRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            if (vehicleList != null && vehicleList.size() > 0) {
                vehicleLayout.setVisibility(View.VISIBLE);
                // vehicleRecyclerView.setAdapter(new VehicleAdapter(context, vehicleList, AddBookingActivity.this));
                pagerAdapter = new VehiclePagerAdapter(context, vehicleList, AddBookingActivity.this);
                vehicleViewPager.setAdapter(pagerAdapter);


            } else {
                vehicleLayout.setVisibility(View.GONE);
            }
            nameEditText.setEnabled(false);
            emailEditText.setEnabled(false);
            //noEditText.setEnabled(false);
            addressEditText.setEnabled(false);

            //stateEditText.setEnabled(false);
            //areaEditText.setEnabled(false);
            //serialEditText.setEnabled(false);
            //serialNoEditText.setEnabled(false);

        } else {
//            nameEditText.setEnabled(true);
//            emailEditText.setEnabled(true);
//            noEditText.setEnabled(true);
//            addressEditText.setEnabled(true);
//
//            stateEditText.setEnabled(true);
//            areaEditText.setEnabled(true);
//            serialEditText.setEnabled(true);
//            serialNoEditText.setEnabled(true);

            nameEditText.setText("");
            emailEditText.setText("");
            noEditText.setText("");
            addressEditText.setText("");

            stateEditText.setText("");
            areaEditText.setText("");
            serialEditText.setText("");
            serialNoEditText.setText("");


        }

    }

    private void getServiceIdFromName(String serviceTypeName) {
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).getServiceName().equalsIgnoreCase(serviceTypeName)) {
                servicePos = i + 1;
            }
        }
    }


    private void getAdviserIdFromName(int adviserId) {
        for (int i = 0; i < advisorList.size(); i++) {
            if (advisorList.get(i).getEmpId() == adviserId) {
                adviserPos = i + 1;
            }
        }
    }

    private void getPickPersonIdFromName(int pickPersonId) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmpId() == pickPersonId) {
                pickPersonPos = i + 1;
            }/*else if ( employeeList.get(i).getEmpId() == pickPersonId) {
                pickPersonId = i + 1;
            }*/
        }
    }


    private void getModalIdFromName(String model) {
        for (int i = 0; i < modalList.size(); i++) {
            if (modalList.get(i).getBrandName().equalsIgnoreCase(model)) {
                modalPos = i + 1;
            }
        }
    }

    private void getMakeIdFromName(String make) {
        for (int i = 0; i < makeList.size(); i++) {
            if (makeList.get(i).getMakeName().equalsIgnoreCase(make)) {
                makePos = i + 1;
            }
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA)) {
            vehicleType = bundle.getString(AppConstants.BOOKING_VEHICLE_TYPE_EXTRA);
            searchLayout.setVisibility(View.GONE);

        }

        if (bundle != null && bundle.containsKey(AppConstants.REMAINDER_DETAILS_EXTRA)) {
            reminder = (Reminder) bundle.getSerializable(AppConstants.REMAINDER_DETAILS_EXTRA);
            reminderId = String.valueOf(reminder.getRemainderId());
            searchLayout.setVisibility(View.GONE);
        }

        if (bundle != null && bundle.containsKey(AppConstants.USER_BOOKING_DETAILS_EXTRA)) {
            userBooking = (Booking) bundle.getSerializable(AppConstants.USER_BOOKING_DETAILS_EXTRA);
            searchLayout.setVisibility(View.GONE);

        }

        if (bundle != null && bundle.containsKey(AppConstants.CUSTOMER_DETAILS_EXTRA)) {
            customerResponse = (CustomerSearchResponse) bundle.getSerializable(AppConstants.CUSTOMER_DETAILS_EXTRA);
        }

        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            dealerBooking = (DealerBooking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
            searchLayout.setVisibility(View.GONE);
            isEdit = true;
            setTitle(getString(R.string.editBooking));
            addButton.setText(getString(R.string.editBooking));
        }


    }

    private void initializeViews() {
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
//        customerNo = (EditText) findViewById(R.id.customerNo);
        searchEditText = (EditText) findViewById(R.id.searchEditText);
        nameEditText = (EditText) findViewById(R.id.customerName);
        noEditText = (EditText) findViewById(R.id.customerNo);
        stateEditText = (EditText) findViewById(R.id.stateCode);
        areaEditText = (EditText) findViewById(R.id.areaCode);
        serialEditText = (EditText) findViewById(R.id.serialText);
        serialNoEditText = (EditText) findViewById(R.id.serialNo);
        serviceDateEditText = (TextView) findViewById(R.id.serviceDate);
        pickUpDateEditText = (TextView) findViewById(R.id.pickUpDate);
        pickUpTimeEditText = (TextView) findViewById(R.id.pickUpTime);
        pickupAddressEditText = (EditText) findViewById(R.id.pickupAddressEditText);
        serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
        makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        modelSpinner = (Spinner) findViewById(R.id.modalSpinner);
        fuelSpinner = (Spinner) findViewById(R.id.fuelSpinner);
        searchLayout=(LinearLayout) findViewById(R.id.searchLayout);


        personSpinner = (Spinner) findViewById(R.id.pickUpSpinner);

        yesButton = (RadioButton) findViewById(R.id.pickUp);
        noButton = (RadioButton) findViewById(R.id.noPickUp);
        addButton = (Button) findViewById(R.id.BookButton);
        radioGroup = (RadioGroup) findViewById(R.id.pickUpGroup);

        pickUpLayout = (LinearLayout) findViewById(R.id.pickUpLayout);

        vehicleRecyclerView = (RecyclerView) findViewById(R.id.vehicleRecyclerView);
        vehicleLayout = (CardView) findViewById(R.id.vehicleLayout);

        searchImageView = (AppCompatImageView) findViewById(R.id.searchIcon);
        adviserSpinner = (Spinner) findViewById(R.id.adviserSpinner);
        vehicleViewPager = findViewById(R.id.vehicleViewPager);
    }

    public AdapterView.OnItemSelectedListener employeeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                employeeId = String.valueOf(employeeList.get(position - 1).getEmpId());
            } else {
                employeeId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public AdapterView.OnItemSelectedListener adviserListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {

                adviserId = String.valueOf(advisorList.get(position - 1).getEmpId());

            } else {
                adviserId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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

    public AdapterView.OnItemSelectedListener insuranceListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                insuranceId = String.valueOf(insuranceList.get(position - 1).getInsuranceID());
            } else {
                insuranceId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    public AdapterView.OnItemSelectedListener makeListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                if (reminder != null && reminder.getModel() != null && isFirstTimeModel) {
                    getModalIdFromName(reminder.getModel());
                    modelSpinner.setSelection(modalPos);
                }

                if (userBooking != null && userBooking.getModal() != null && isFirstTimeModel) {
                    getModalIdFromName(userBooking.getModal());
                    modelSpinner.setSelection(modalPos);
                    isFirstTimeModel = false;
                }

                if (dealerBooking != null && dealerBooking.getModel() != null && isFirstTimeModel) {
                    getModalIdFromName(dealerBooking.getModel());
                    modelSpinner.setSelection(modalPos);
                    isFirstTimeModel = false;
                }


                if (vehicle != null && vehicle.getModel() != null && isFirstTimeModel) {
                    getModalIdFromName(vehicle.getModel());
                    modelSpinner.setSelection(modalPos);
                    isFirstTimeModel = false;
                }


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
                String Date = dates + "-" + months + "-" + year;
                serviceDateEditText.setText(Date);
                pickUpDate = null;
                pickUpDateEditText.setText("");
                Time = null;
                pickUpTimeEditText.setText("");

            }
        }
    };


    private DatePickerDialog.OnDateSetListener pickUpPickerListener = new DatePickerDialog.OnDateSetListener() {

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

                pickUpDate = year + "-" + months + "-" + dates;
                String Date = dates + "-" + months + "-" + year;


                java.util.Date sdate = AppUtils.getDateFormat(serviceDate);

                java.util.Date edate = AppUtils.getDateFormat(pickUpDate);


                if (edate.before(sdate) || edate.equals(sdate)) {
                    pickUpDateEditText.setText(Date);
                    Time = null;
                    pickUpTimeEditText.setText("");
                } else {
                    pickUpDate = null;
                    pickUpDateEditText.setText("");
                    pickUpDateEditText.setHint("Pick Up Date");
                    pickUpTimeEditText.setText("");
                    pickUpTimeEditText.setHint("Pick Up Time");
                    Toast.makeText(context, "Invalid Pickup Date", Toast.LENGTH_SHORT).show();
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

                    AddBookingActivity.this.Time = AppUtils.convert12To24StringFormat(Time);

                    java.util.Date sdate = AppUtils.getDateFormat(pickUpDate);


                    java.util.Date sTime = AppUtils.getDateTimeFormat(pickUpDate + " " + AppUtils.getRequiredPickUpTime());
                    java.util.Date eTime = AppUtils.getDateTimeFormat(pickUpDate + " " + AppUtils.convert12To24StringFormat(Time));
                    if ((sTime.after(eTime) || eTime.equals(sTime)) && AppUtils.isCurentDate(sdate)) {
                        AddBookingActivity.this.Time = null;
                        pickUpTimeEditText.setText("");
                        pickUpTimeEditText.setHint("Pick Up Time");
                        Toast.makeText(context, "Invalid Pickup Time", Toast.LENGTH_SHORT).show();
                    } else {
                        pickUpTimeEditText.setText(Time);
                    }

                }
            };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.BookButton:

                validateDate();
                break;

            case R.id.serviceDate:

                if (serviceDate != null) {
                    DatePickerDialog serviceDatePicker;

                    Calendar cal = Calendar.getInstance();

                    if (serviceDate != null && !TextUtils.isEmpty(serviceDate)) {
                        cal.setTime(AppUtils.getDateTimeFormat1(serviceDate));
                    }
                    serviceDatePicker = new DatePickerDialog(this, servicePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    serviceDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    serviceDatePicker.show();
                } else {
                    serviceDatePicker.show();
                }


                break;


            case R.id.pickUpDate:
                if (serviceDate != null) {
                    DatePickerDialog pickUpeDatePicker;

                    Calendar cal2 = Calendar.getInstance();

                    if (pickUpDate != null && !TextUtils.isEmpty(pickUpDate)) {
                        cal2.setTime(AppUtils.getDateTimeFormat1(pickUpDate));
                    }
                    pickUpeDatePicker = new DatePickerDialog(this, pickUpPickerListener, cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                    pickUpeDatePicker.updateDate(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
                    pickUpeDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    pickUpeDatePicker.show();
                } else {

                    Toast.makeText(context, "Please Select Service Date ", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pickUpTime:

                if (serviceDate != null && pickUpDate != null) {

                    TimePickerDialog timePickerDialog;
                    Calendar cal1 = Calendar.getInstance();

                    if (Time != null && !TextUtils.isEmpty(Time)) {
                        cal1.setTime(AppUtils.getTimeFormatNew(Time));
                    }
                    timePickerDialog = new TimePickerDialog(context, mTimeSetListener, pHour, pMinute, false);
                    timePickerDialog.updateTime(cal1.get(Calendar.HOUR_OF_DAY), cal1.get(Calendar.MINUTE));
                    timePickerDialog.show();

                    timePickerDialog.show();
                } else {

                    Toast.makeText(context, "Pick Up Date Is Not Yet Select", Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }

    private void validateDate() {
        String emailAddress = emailEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String customerName = nameEditText.getText().toString();
        String phoneNo = noEditText.getText().toString();
        String stateCode = stateEditText.getText().toString();
        areaCode = areaEditText.getText().toString();
        String serialText = serialEditText.getText().toString();
        String serialNo = serialNoEditText.getText().toString();
        String regNo = null;
        String pickUp = "0";
        String pickUpTiming = "";
        String pickUpAddress = pickupAddressEditText.getText().toString();
        String description = descriptionEditText.getText().toString();


        fuelType = fuelSpinner.getSelectedItem().toString();

        regNo = stateCode + "/" + areaCode + "/" + serialText + "/" + serialNo;

        if (yesButton.isChecked()) {
            pickUp = "1";
        }

        if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(context, getString(R.string.enterMobileNo), Toast.LENGTH_SHORT).show();
        } else if (phoneNo.length() != 10) {
            Toast.makeText(context, getString(R.string.invalidMobilNo), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(context, getString(R.string.pleaseEnterCusName), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(context, getString(R.string.enterEmail), Toast.LENGTH_SHORT).show();
        } else if (!AppUtils.isValidEmail(emailAddress)) {
            Toast.makeText(context, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(context, getString(R.string.enterAddress), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, getString(R.string.selectModel), Toast.LENGTH_SHORT).show();
        } else if (serviceID == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectService), Toast.LENGTH_SHORT).show();
        } else if (serviceDate == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectDate), Toast.LENGTH_SHORT).show();
        } else if (adviserId == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectAdvisor), Toast.LENGTH_SHORT).show();
        } else if (yesButton.isChecked() && employeeId == null) {
            Toast.makeText(context, getString(R.string.pleaseSelectPickUpPerson), Toast.LENGTH_SHORT).show();
        } else if (yesButton.isChecked() && pickUpDate == null) {
            Toast.makeText(context, getString(R.string.pleasePickUpDate), Toast.LENGTH_SHORT).show();
        } else if (yesButton.isChecked() && Time == null) {
            Toast.makeText(context, getString(R.string.pleasePickUpTime), Toast.LENGTH_SHORT).show();
        } else if (yesButton.isChecked() && TextUtils.isEmpty(pickUpAddress)) {
            Toast.makeText(context, getString(R.string.pickUpAddress), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(context, getString(R.string.enter_description), Toast.LENGTH_SHORT).show();

        } else {
            if (pickUpDate != null && Time != null) {
                pickUpTiming = pickUpDate + " " + Time;
            }
            RequestParams requestParams = new RequestParams();

            if (userBooking != null) {

                requestParams.put("reference_id", String.valueOf(userBooking.getBookingId()));

            }
            requestParams.put("customer_name", customerName);
            requestParams.put("customer_phone_no", phoneNo);
            requestParams.put("customer_email", emailAddress);
            requestParams.put("customer_address", address);
            requestParams.put("make_id", makeId);
            requestParams.put("service_id", serviceID);
            requestParams.put("fuel_type", fuelType);
            requestParams.put("vehicle_no", regNo);
            requestParams.put("model_id", modelId);
            requestParams.put("service_date", serviceDate);
            requestParams.put("pick_up", pickUp);
            requestParams.put("pick_up_person_id", employeeId);
            requestParams.put("pick_up_date", pickUpDate);
            requestParams.put("description", description);
            requestParams.put("pick_up_time", Time);
            requestParams.put("pick_up_address", pickUpAddress);
            requestParams.put("service_advisor_id", adviserId);
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

            if (!isEdit) {
                if (reminderId != null) {
                    requestParams.put("reminder_id", String.valueOf(reminder.getRemainderId()));
                } else {
                    requestParams.put("reminder_id", "0");


                }

                addBooking(requestParams);

            } else {
                requestParams.put("booking_id", String.valueOf(dealerBooking.getJobcardId()));
                editBooking(requestParams);
            }
        }

    }

    private void addBooking(RequestParams requestParams) {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.addBooking(context, requestParams, new ServiceResponseListener<AddBookingResponse>() {
                        @Override
                        public void onSuccess(AddBookingResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                sendUpdateSuccessBroadcast();
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

    private void editBooking(RequestParams requestParams) {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.editBooking(context, requestParams, new ServiceResponseListener<EditBookingResponse>() {
                        @Override
                        public void onSuccess(EditBookingResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                sendUpdateSuccessBroadcast();
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
                    AppUtils.hideKeyboard(AddBookingActivity.this);

                    if (serviceDate != null) {
                        DatePickerDialog serviceDatePicker;

                        Calendar cal = Calendar.getInstance();

                        if (serviceDate != null && !TextUtils.isEmpty(serviceDate)) {
                            cal.setTime(AppUtils.getDateTimeFormat1(serviceDate));
                        }
                        serviceDatePicker = new DatePickerDialog(this, pickUpPickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        serviceDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        serviceDatePicker.show();
                    } else {
                        serviceDatePicker.show();
                    }


                }
                break;

          /*  case R.id.insuranceDate:
                if (b) {
                    AppUtilcustomerNos.hideKeyboard(AddBookingActivity.this);
                    insuranceDatePicker.show();
                }
                break;*/

            case R.id.pickUpDate:
                if (b) {
                    AppUtils.hideKeyboard(AddBookingActivity.this);
                    if (serviceDate != null) {
                        DatePickerDialog pickUpeDatePicker;

                        Calendar cal = Calendar.getInstance();

                        if (pickUpDate != null && !TextUtils.isEmpty(pickUpDate)) {
                            cal.setTime(AppUtils.getDateTimeFormat1(pickUpDate));
                        }
                        pickUpeDatePicker = new DatePickerDialog(this, pickUpPickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        pickUpeDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        pickUpeDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                        pickUpeDatePicker.show();
                    } else {
                        Toast.makeText(context, "Please Select Service Date", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.pickUpTime:
                if (b) {
                    AppUtils.hideKeyboard(AddBookingActivity.this);
                    if (serviceDate != null && pickUpDate != null) {
                        TimePickerDialog timePickerDialog;
                        Calendar cal = Calendar.getInstance();

                        if (Time != null && !TextUtils.isEmpty(Time)) {
                            cal.setTime(AppUtils.getTimeFormatNew(Time));
                        }
                        timePickerDialog = new TimePickerDialog(context, mTimeSetListener, pHour, pMinute, false);
                        timePickerDialog.updateTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
                        timePickerDialog.show();

                        timePickerDialog.show();
                    } else {

                        Toast.makeText(context, "Pick Up Date Is Not Yet Select", Toast.LENGTH_SHORT).show();

                    }

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
            if (charSequence.length() == 2) {
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
            if (charSequence.length() > 1) {
                serialNoEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher serialNoWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private void updateAreaText() {
        if (!TextUtils.isEmpty(areaCode) && areaCode.length() != 2) {
            String area = "0" + areaCode;
            areaEditText.setText(area);
            areaEditText.addTextChangedListener(areaWataher);
        }
    }

    private void sendUpdateSuccessBroadcast() {
        EventBus.getDefault().post(new RefreshHome());
/*
        Intent intent = new Intent(AppConstants.a);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void getCustomerName() {

        SEARCH_KEY = searchEditText.getText().toString();
        if (!TextUtils.isEmpty(SEARCH_KEY)) {
            if (SEARCH_KEY.length() > 0) {
                isSearched = true;
                searchImageView.setImageResource(R.drawable.ic_cancel_icon);
                getCustomerDetails();
            } /*else {

                Toast.makeText(context, getString(R.string.invalidMobilNo), Toast.LENGTH_SHORT).show();
            }
*/
        } else {

            Toast.makeText(context, "Please Enter Mobile No or Vehicle No", Toast.LENGTH_SHORT).show();
        }
        AppUtils.hideKeyboard(AddBookingActivity.this);
    }

    private void getCustomerDetails() {

        if (DeviceManager.isDeviceOffline(context)) {
            Toast.makeText(context, getString(R.string.title_of_data_message), Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile_no", SEARCH_KEY);
        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            requestParams.put("auth_token", String.valueOf(AppUtils.getWorkshopInfo(getApplication()).getAuthToken()));
        }

        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.SHOW_PROGRESS_DIALOG);

            ServiceManager.getCustomerDetails(context, requestParams, new ServiceResponseListener<CustomerSearchResponse>() {
                        @Override
                        public void onSuccess(CustomerSearchResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                customerResponse = response;
                                updateViewSearchResult();
                            } else if (response != null && response.isError()) {
                                String message = response.getMessage();
                                Toast.makeText(context, getString(R.string.no_results_found), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, getString(R.string.internet_failed_toast), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);
                            Toast.makeText(context, getString(R.string.internet_failed_toast), Toast.LENGTH_SHORT).show();

                        }
                    }

            );
        }

    }

    @Override
    public void onSelectVehicle(Vehicle vehicle, int position) {
        this.vehicle = vehicle;
        isFirstTimeModel = true;
        if (vehicle.getVehicleNo().length() > 3) {
            String[] vehicleNo = vehicle.getVehicleNo().split("/");
            stateEditText.setText(vehicleNo[0]);
            areaEditText.setText(vehicleNo[1]);
            serialEditText.setText(vehicleNo[2]);
            serialNoEditText.setText(vehicleNo[3]);

            getMakeIdFromName(vehicle.getMake());
            getServiceIdFromName(vehicle.getModel());
            makeSpinner.setSelection(makePos);

            for (int i = 0; i < fuelString.size(); i++) {
                if (fuelString.get(i).equalsIgnoreCase(vehicle.getFuelType())) {
                    fuelSpinner.setSelection(i);
                }
            }
        }
        pagerAdapter = new VehiclePagerAdapter(context, pagerAdapter.getVehicleList(), AddBookingActivity.this);
        vehicleViewPager.setAdapter(pagerAdapter);
        pagerAdapter.setVehicleList(pagerAdapter.getVehicleList());
        pagerAdapter.notifyDataSetChanged();
        vehicleViewPager.setCurrentItem(position, false);


    }

    private float dpFromPx(final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

}
