package com.creatah.automobile.dealer.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
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
import com.creatah.automobile.dealer.modal.BookingDetailsResponse;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.RefreshHome;
import com.creatah.automobile.dealer.modal.UpdateStatusResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 02-05-2017.
 */

public class BookingDetailsActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView mobileNoTextView;
    private TextView vehicleNoTextView;
    private TextView modalTextView;
    private TextView checkInCount;
    private TextView inspectionCount;
    private TextView inprogressCount;
    private TextView readyCount;
    private TextView deliveredCount;
    private TextView deliveryDate;
    private TextView deliveryTime;
    private TextView noResultFound;

    private TextView serviceDateTextView;
    private FrameLayout detailsLayout;
    /*private LinearLayout deliveryLayout;*/
    private RelativeLayout checkInImage;
    private RelativeLayout checkInCheckList;
    private RelativeLayout checkInDesc;
    private RelativeLayout inspectionImage;
    private RelativeLayout inspectionAmt;
    private RelativeLayout inprogressImage;
    private RelativeLayout inprogressAction;
    private RelativeLayout readyImage;
    private RelativeLayout readyCheckList;
    private RelativeLayout deliveredImage;
    private RelativeLayout billLayout;

    private SwitchCompat checkInSwitch;
    //    private SwitchCompat inspectionSwitch;
    private SwitchCompat underProgressSwitch;
    private SwitchCompat vehicleReadSwitch;
    private SwitchCompat vehicleDeliveredSwitch;
    private DealerBooking dealerBooking;
    private String bookingId = null;
    private String ratingBarString;
    private DealerBooking booking;
    private Context context;
    private boolean isService = false;

    private String STATUS_TYPE = null;
    private String STATUS = "true";
    private int ADD_IMAGE_REQ_CODE = 852;
    private int ADD_EMPLOYEE_REQ_CODE = 853;
    private int ADD_DIGITAL_SIGNATURE = 856;
    private int ADD_FINAL_AMOUNT_REQ_CODE = 854;
    private int EDIT_SERVICE_REQ_CODE = 855;
    private int ADD_SPARE_LABOUR_REQ_CODE = 857;

    /*  private TextView checkindateTextView;
      private TextView inspectiondateTextView;
      private TextView underProgressdateTextView;
      private TextView vehicleReadydateTextView;
      private TextView vehicleDelivereddateTextView;*/
    private LinearLayout employeeNameLinearLayout;
    private TextView employeeNameTextView;

    private LinearLayout serviceNameLinearLayout;
    private TextView serviceTextView;
    private CardView delivery_date_timeLinearLayout;
   /* private LinearLayout checkindatelayout;
    private LinearLayout inspectiondatelayout;
    private LinearLayout vehicleReadydatelayout;
    private LinearLayout vehicleDelivereddatelayout;
    private LinearLayout underProgressdatelayout;*/


    private LinearLayout callButton;

    private final int CALL_REQUEST_PERMISSION = 7;

    @BindView(R.id.checkInDateTextView)
    TextView checkInDateTextView;

    @BindView(R.id.serviceProgressDate)
    TextView serviceProgressDate;

    @BindView(R.id.vehicleReadyDate)
    TextView vehicleReadyDate;

    @BindView(R.id.vehicleDeliveredDate)
    TextView vehicleDeliveredDate;

    @BindView(R.id.vehicleDeliveredDateLayout)
    LinearLayout vehicleDeliveredDateLayout;

    @BindView(R.id.vehicleReadyDateLayout)
    LinearLayout vehicleReadyDateLayout;

    @BindView(R.id.serviceProgressDateLayout)
    LinearLayout serviceProgressDateLayout;

    @BindView(R.id.checkinDateLayout)
    LinearLayout checkinDateLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;


    @BindView(R.id.jobCardDetailsLayout)
    LinearLayout jobCardDetailsLayout;

    @BindView(R.id.noJObCardFound)
    LinearLayout noJObCardFound;

    @BindView(R.id.noJobCardText)
    TextView noJobCardText;

    @BindView(R.id.approximate_date_timeLinearLayout)
    CardView approximate_date_timeLinearLayout;

    @BindView(R.id.approximateDeliveryDate)
    TextView approximateDeliveryDate;


    /*@BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.ratingDescriptionTextview)
    TextView ratingDescriptionTextView;

    @BindView(R.id.ratingDescriptionLacheckInSwitchyout)
    LinearLayout ratingDescriptionLayout;
*/

    private boolean checkEditFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        initializeViews();
        context = this;
        ButterKnife.bind(this);
        getBundle();
        if (bookingId != null) {
            getDetails();
        }


        checkInImage.setOnClickListener(this);
        checkInCheckList.setOnClickListener(this);
        checkInDesc.setOnClickListener(this);
        inprogressImage.setOnClickListener(this);
        inprogressAction.setOnClickListener(this);
        readyImage.setOnClickListener(this);
        readyCheckList.setOnClickListener(this);
        deliveredImage.setOnClickListener(this);
        billLayout.setOnClickListener(this);
        callButton.setOnClickListener(this);
        retryButton.setOnClickListener(this);


    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
            bookingId = String.valueOf(booking.getJobcardId());
            checkEditFlow = bundle.getBoolean(AppConstants.CHECK_EDIT_FLOW, false);
        }
    }

    private void initializeViews() {
        mobileNoTextView = (TextView) findViewById(R.id.mobileNoTextView);
        vehicleNoTextView = (TextView) findViewById(R.id.vehicleNoTextView);
        modalTextView = (TextView) findViewById(R.id.modalTextView);
        serviceTextView = (TextView) findViewById(R.id.serviceTextView);
        checkInCount = (TextView) findViewById(R.id.checkInTextView);
//        inspectionCount = (TextView) findViewById(R.id.inspectionTextView);
        inprogressCount = (TextView) findViewById(R.id.inprogressTextView);
        readyCount = (TextView) findViewById(R.id.readyTextView);
        deliveredCount = (TextView) findViewById(R.id.deliveredTextView);
        deliveryDate = (TextView) findViewById(R.id.deliveryDate);
//        deliveryTime = (TextView) findViewById(R.id.deliveryTime);
        noResultFound = (TextView) findViewById(R.id.noResultFound);

        /*deliveryLayout = (LinearLayout) findViewById(R.id.deliveryLayout);*/
        detailsLayout = (FrameLayout) findViewById(R.id.detailsLayout);
        checkInImage = (RelativeLayout) findViewById(R.id.checkInImage);
        checkInCheckList = (RelativeLayout) findViewById(R.id.checkInCheckList);
        checkInDesc = (RelativeLayout) findViewById(R.id.checkInDesc);
       /* inspectionImage = (RelativeLayout) findViewById(R.id.inspectionImage);
        inspectionAmt = (RelativeLayout) findViewById(R.id.inspectionAmt);*/
        inprogressImage = (RelativeLayout) findViewById(R.id.progressImage);
        readyImage = (RelativeLayout) findViewById(R.id.readyImage);
        readyCheckList = (RelativeLayout) findViewById(R.id.readyCheckList);
        deliveredImage = (RelativeLayout) findViewById(R.id.deliveredImage);
        inprogressAction = (RelativeLayout) findViewById(R.id.progressDesc);
        billLayout = (RelativeLayout) findViewById(R.id.billLayout);

        checkInSwitch = (SwitchCompat) findViewById(R.id.checkInSwitch);
        underProgressSwitch = (SwitchCompat) findViewById(R.id.inProgressSwitch);
        vehicleReadSwitch = (SwitchCompat) findViewById(R.id.readySwitch);
        vehicleDeliveredSwitch = (SwitchCompat) findViewById(R.id.deliveredSwitch);
        serviceDateTextView = (TextView) findViewById(R.id.serviceDateTextView);
        delivery_date_timeLinearLayout = (CardView) findViewById(R.id.delivery_date_timeLinearLayout);
//        employeeNameLinearLayout = (LinearLayout) findViewById(R.id.employeeNameLinearLayout);
        employeeNameTextView = (TextView) findViewById(R.id.employeeNameTextView);
        serviceTextView = (TextView) findViewById(R.id.serviceTextView);
//        serviceNameLinearLayout = (LinearLayout) findViewById(R.id.serviceNameLinearLayout);
        callButton = (LinearLayout) findViewById(R.id.callButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.checkInImage:
                Intent checkIntent = new Intent(context, ImageListActivity.class);
                checkIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
                checkIntent.putExtra(AppConstants.ADD_IMAGE_EXTRA, getString(R.string.checkInCompletedKey));
                startActivityForResult(checkIntent, ADD_IMAGE_REQ_CODE);
                break;

            case R.id.checkInCheckList:
                Intent checkInIntent = new Intent(context, CheckListActivity.class);
                checkInIntent.putExtra(AppConstants.ADD_CHECK_LIST_EXTRA, dealerBooking);
                checkInIntent.putExtra(AppConstants.CHECK_LIST_STATUS_EXTRA, getString(R.string.checkInKey));
                startActivity(checkInIntent);
                break;

            case R.id.checkInDesc:
                Intent checkInDescIntent = new Intent(context, AddSpareAndLabourActivity.class);
                checkInDescIntent.putExtra(AppConstants.ADD_DESCRIPTION_EXTRA, dealerBooking);
                startActivityForResult(checkInDescIntent, ADD_SPARE_LABOUR_REQ_CODE);
                break;

            case R.id.progressImage:
                Intent progressIntent = new Intent(context, ImageListActivity.class);
                progressIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
                progressIntent.putExtra(AppConstants.ADD_IMAGE_EXTRA, getString(R.string.serviceUnderProgressKey));
                startActivityForResult(progressIntent, ADD_IMAGE_REQ_CODE);
                break;

            case R.id.progressDesc:
                Intent desActionIntent = new Intent(context, WorkCheckListActivity.class);
                desActionIntent.putExtra(AppConstants.WORK_CHECK_LIST_EXTRA, dealerBooking);
                startActivity(desActionIntent);
                break;

            case R.id.readyImage:
                Intent readyIntent = new Intent(context, ImageListActivity.class);
                readyIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
                readyIntent.putExtra(AppConstants.ADD_IMAGE_EXTRA, getString(R.string.vehicleReadyKey));
                startActivityForResult(readyIntent, ADD_IMAGE_REQ_CODE);
                break;


            case R.id.readyCheckList:
                Intent verifyCheckIntent = new Intent(context, VerifyCheckListActivity.class);
                verifyCheckIntent.putExtra(AppConstants.ADD_CHECK_LIST_EXTRA, dealerBooking);
                verifyCheckIntent.putExtra(AppConstants.CHECK_LIST_STATUS_EXTRA, getString(R.string.checkInKey));
                startActivity(verifyCheckIntent);
                break;

            case R.id.billLayout:
                Intent billIntent = new Intent(context, BillAmountActivity.class);
                billIntent.putExtra(AppConstants.BILL_AMOUNT_EXTRA, dealerBooking);
                billIntent.putExtra(AppConstants.CHECK_LIST_STATUS_EXTRA, getString(R.string.checkInKey));
                startActivity(billIntent);
                break;


            case R.id.deliveredImage:
                Intent deliveredIntent = new Intent(context, ImageListActivity.class);
                deliveredIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
                deliveredIntent.putExtra(AppConstants.ADD_IMAGE_EXTRA, getString(R.string.vehicleDeliveredKey));
                startActivityForResult(deliveredIntent, ADD_IMAGE_REQ_CODE);
                break;

            case R.id.callButton:
                if (dealerBooking != null && dealerBooking.getMobileNo() != null) {
                    onMakeCall();
                }
                break;

            case R.id.retryButton:
                reTryFn();
                break;


        }
    }

    private void reTryFn() {

        retryButton.setVisibility(View.GONE);
        jobCardDetailsLayout.setVisibility(View.GONE);
        noJObCardFound.setVisibility(View.GONE);
        noJobCardText.setText(R.string.errorOccurred);
        noJobCardText.setVisibility(View.GONE);
        getDetails();
    }

    private void showFeatureUnderDevelopment() {
        Toast.makeText(context, getString(R.string.featureUnderDevelopment), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!checkEditFlow) {
            getMenuInflater().inflate(R.menu.service_details_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;


            case R.id.action_edit:
                Intent editIntent = new Intent(context, AddBookingActivity.class);
                editIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, booking);
                startActivityForResult(editIntent, EDIT_SERVICE_REQ_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDetails() {

        RequestParams requestParams = new RequestParams();
        requestParams.put("booking_id", bookingId);
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        if (DeviceManager.isDeviceOffline(context)) {
            detailsLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            jobCardDetailsLayout.setVisibility(View.GONE);
            noJObCardFound.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        if (!isService) {

            progressLayout.setVisibility(View.VISIBLE);
            isService = true;
            ServiceManager.getBookingDetails(context, requestParams, new ServiceResponseListener<BookingDetailsResponse>() {
                        @Override
                        public void onSuccess(BookingDetailsResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {

                                booking = response.getBooking();
                                noResultFound.setVisibility(View.GONE);
                                detailsLayout.setVisibility(View.VISIBLE);
                                jobCardDetailsLayout.setVisibility(View.VISIBLE);
                                dealerBooking = response.getBooking();
                                retryButton.setVisibility(View.GONE);
                                noJObCardFound.setVisibility(View.GONE);
                                setTitle(dealerBooking.getCustomerName()+" - "+dealerBooking.getJobcardId());
                                updateViews();

                            } else if (response != null) {
                                String message = response.getMessage();
                                noJObCardFound.setVisibility(View.VISIBLE);
                                noJobCardText.setVisibility(View.VISIBLE);
                                jobCardDetailsLayout.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                detailsLayout.setVisibility(View.VISIBLE);
                            }
                            else {

                                progressLayout.setVisibility(View.GONE);
                                jobCardDetailsLayout.setVisibility(View.GONE);
                                noJObCardFound.setVisibility(View.VISIBLE);
                                noJobCardText.setText(R.string.errorOccurred);
                                detailsLayout.setVisibility(View.VISIBLE);
                                noJobCardText.setVisibility(View.VISIBLE);
                                retryButton.setVisibility(View.VISIBLE);
                            }
                        }



                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            jobCardDetailsLayout.setVisibility(View.GONE);
                            noJObCardFound.setVisibility(View.VISIBLE);
                            noJobCardText.setText(R.string.errorOccurred);
                            detailsLayout.setVisibility(View.VISIBLE);
                            noJobCardText.setVisibility(View.VISIBLE);
                            retryButton.setVisibility(View.VISIBLE);


                        }
                    }

            );
        }


    }

    private void updateViews() {
        mobileNoTextView.setText(dealerBooking.getMobileNo());

        if (dealerBooking != null && dealerBooking.getMobileNo() != null) {
            callButton.setVisibility(View.VISIBLE);
        } else {
            callButton.setVisibility(View.GONE);
        }


        vehicleNoTextView.setText(dealerBooking.getVehicleNo());
        descriptionTextView.setText(dealerBooking.getJobcardDescription());

        modalTextView.setText(dealerBooking.getMakeName() + " " + dealerBooking.getModel());

        checkInCount.setText(dealerBooking.getCheckInCount());
        inprogressCount.setText(dealerBooking.getInProgressCount());
        readyCount.setText(dealerBooking.getReadyCount());
        deliveredCount.setText(dealerBooking.getDeliveredCount());

        employeeNameTextView.setText(dealerBooking.getAdviserName());
        serviceTextView.setText(dealerBooking.getServiceName());
        if (dealerBooking.getServiceDate() != null) {
            serviceDateTextView.setText(AppUtils.convertDateStringTodate(dealerBooking.getServiceDate()));
        }


        if (dealerBooking.getCheckInDate() != null) {
            checkinDateLayout.setVisibility(View.VISIBLE);
            checkInDateTextView.setText(AppUtils.convertstringTodate1(dealerBooking.getCheckInDate()));
        } else {
            checkinDateLayout.setVisibility(View.GONE);
        }

        if (dealerBooking.getCheckInDate() != null) {
            checkinDateLayout.setVisibility(View.VISIBLE);
            checkInDateTextView.setText(AppUtils.convertstringTodate1(dealerBooking.getCheckInDate()));
        } else {
            checkinDateLayout.setVisibility(View.GONE);
        }

        if (dealerBooking.getServiceProgressDate() != null) {
            serviceProgressDateLayout.setVisibility(View.VISIBLE);
            serviceProgressDate.setText(AppUtils.convertstringTodate1(dealerBooking.getServiceProgressDate()));
        } else {
            serviceProgressDateLayout.setVisibility(View.GONE);
        }


        if (dealerBooking.getVehicleReadyDate() != null) {
            vehicleReadyDateLayout.setVisibility(View.VISIBLE);
            vehicleReadyDate.setText(AppUtils.convertstringTodate1(dealerBooking.getVehicleReadyDate()));
        } else {
            vehicleReadyDateLayout.setVisibility(View.GONE);
        }

        if (dealerBooking.getCheckInCount() != null)
            checkInCount.setVisibility(dealerBooking.getCheckInCount().equals("0") ? View.INVISIBLE : View.VISIBLE);
        else
            checkInCount.setVisibility(View.GONE);

        if (dealerBooking.getInProgressCount() != null)
            inprogressCount.setVisibility(dealerBooking.getInProgressCount().equals("0") ? View.INVISIBLE : View.VISIBLE);
        else
            inprogressCount.setVisibility(View.GONE);
        if (dealerBooking.getReadyCount() != null)
            readyCount.setVisibility(dealerBooking.getReadyCount().equals("0") ? View.INVISIBLE : View.VISIBLE);
        else
            readyCount.setVisibility(View.GONE);
        if (dealerBooking.getDeliveredCount() != null)
            deliveredCount.setVisibility(dealerBooking.getDeliveredCount().equals("0") ? View.INVISIBLE : View.VISIBLE);
        else
            deliveredCount.setVisibility(View.GONE);


       if (dealerBooking.isCheckInCompleted()|| dealerBooking.isUnderProgress() && dealerBooking.getApproximateDeliveryDate() != null) {
            approximate_date_timeLinearLayout.setVisibility(View.VISIBLE);
            approximateDeliveryDate.setText(" "+AppUtils.getApproximateDate(dealerBooking.getApproximateDeliveryDate()) + " " +dealerBooking.getApproximateDeliveryTime());
        } else {

            approximate_date_timeLinearLayout.setVisibility(View.GONE);
        }

        if(dealerBooking.isVehicleReady())
        {
            delivery_date_timeLinearLayout.setVisibility(View.VISIBLE);
            approximate_date_timeLinearLayout.setVisibility(View.GONE);
            deliveryDate.setText(AppUtils.getDeliveryDate(dealerBooking.getDeliveryDate()));
        }

        setUpSwitch();

    }

    private void setUpSwitch() {
        checkInSwitch.setChecked(dealerBooking.isCheckInCompleted());
        underProgressSwitch.setChecked(dealerBooking.isUnderProgress());
        vehicleReadSwitch.setChecked(dealerBooking.isVehicleReady());
        vehicleDeliveredSwitch.setChecked(dealerBooking.isVehicleCompleted());

        if (dealerBooking.isCheckInCompleted()) {
            checkInSwitch.setClickable(false);
        } else {
            checkInSwitch.setClickable(true);
            checkInSwitch.setOnCheckedChangeListener(this);
        }


        if (dealerBooking.isUnderProgress()) {
            underProgressSwitch.setClickable(false);
        }

        if (dealerBooking.isUnderProgress()) {
            underProgressSwitch.setClickable(false);
        } else if (checkInSwitch.isChecked()) {
            underProgressSwitch.setClickable(true);
            underProgressSwitch.setOnCheckedChangeListener(this);
        }

        if (dealerBooking.isVehicleReady()) {
            vehicleReadSwitch.setClickable(false);
        } else if (underProgressSwitch.isChecked()) {
            vehicleReadSwitch.setClickable(true);
            vehicleReadSwitch.setOnCheckedChangeListener(this);
        }

        if (dealerBooking.isVehicleCompleted()) {
            vehicleDeliveredSwitch.setClickable(false);
        } else if (vehicleReadSwitch.isChecked()) {
            vehicleDeliveredSwitch.setClickable(true);
            vehicleDeliveredSwitch.setOnCheckedChangeListener(this);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_IMAGE_REQ_CODE && resultCode == RESULT_OK) {
            getDetails();
        }

        if (requestCode == ADD_SPARE_LABOUR_REQ_CODE && resultCode == RESULT_OK){
            getDetails();
        }

        if (requestCode == ADD_EMPLOYEE_REQ_CODE && resultCode == RESULT_OK) {
            sendUpdateSuccessBroadcast();
            underProgressSwitch.setChecked(true);
            STATUS_TYPE = getString(R.string.serviceUnderProgressKey);
            updateStatus(data.getStringExtra(AppConstants.EMPLOYEE_ID_EXTRA));
        } else if (requestCode == ADD_EMPLOYEE_REQ_CODE) {
            underProgressSwitch.setChecked(false);
        }

        if (requestCode == ADD_DIGITAL_SIGNATURE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            sendUpdateSuccessBroadcast();
            getDetails();
            checkInSwitch.setChecked(true);
        } else if (requestCode == ADD_DIGITAL_SIGNATURE) {
            checkInSwitch.setChecked(false);
        }

        if (requestCode == ADD_FINAL_AMOUNT_REQ_CODE && resultCode == RESULT_OK) {
            sendUpdateSuccessBroadcast();
            vehicleReadSwitch.setChecked(true);
            setResult(RESULT_OK);
            getDetails();
        } else if (requestCode == ADD_FINAL_AMOUNT_REQ_CODE) {
            vehicleReadSwitch.setChecked(false);
        }


        if (requestCode == EDIT_SERVICE_REQ_CODE && resultCode == RESULT_OK) {
            sendUpdateSuccessBroadcast();
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {

            case R.id.checkInSwitch:

                if (b) {
                    STATUS_TYPE = getString(R.string.checkInCompletedKey);

                    Intent digitalSign = new Intent(context, DigitalSignatureActivity.class);
                    digitalSign.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
                    digitalSign.putExtra(AppConstants.STATUS_TYPE_STRING, STATUS_TYPE);
                    startActivityForResult(digitalSign, ADD_DIGITAL_SIGNATURE);
                }

                break;

            /*case R.id.inspectionSwitch:
                if (b) {
                    STATUS_TYPE = getString(R.string.inspectionCompletedKey);
                    updateStatus(null);
                }
                break;
*/
            case R.id.inProgressSwitch:
                if (b) {
                    assignEmployee();
                }
                break;

            case R.id.readySwitch:
                if (b) {
                    updateFinalAmount();
                }
                break;

            case R.id.deliveredSwitch:
                if (b) {
                    STATUS_TYPE = getString(R.string.vehicleDeliveredKey);

                    updateStatus(null);
                }
                break;
        }
    }


    private void updateFinalAmount() {
        Intent amtIntent = new Intent(context, VechileReadyActivity.class);
        amtIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, dealerBooking);
        startActivityForResult(amtIntent, ADD_FINAL_AMOUNT_REQ_CODE);
    }

    private void assignEmployee() {
        startActivityForResult(new Intent(context, AssignEmployeeActivity.class), ADD_EMPLOYEE_REQ_CODE);
    }

    private void updateStatus(String employeeId) {

        RequestParams requestParams = new RequestParams();
        requestParams.put("booking_id", String.valueOf(dealerBooking.getJobcardId()));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("status_update_type", STATUS_TYPE);
        requestParams.put("update_status", STATUS);
        requestParams.put("emp_id", employeeId);

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
                                getDetails();
                                setResult(RESULT_OK);
                                if (STATUS_TYPE != null) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    sendUpdateSuccessBroadcast();

                                }
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

    public void sendUpdateSuccessBroadcast() {
        EventBus.getDefault().post(new RefreshHome());
/*
        Intent intent = new Intent(AppConstants.UPDATE_HOME_PAGE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void onMakeCall() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                AppUtils.makeCall(BookingDetailsActivity.this, dealerBooking.getMobileNo());
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    showAlert(context, getString(R.string.callpermission), getString(R.string.callpermissiondesc), "Try", Try, "No", No1);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_PERMISSION);
                }
            }
        } else {
            AppUtils.makeCall(BookingDetailsActivity.this, dealerBooking.getMobileNo());
        }

    }

    DialogInterface.OnClickListener Try = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(BookingDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_REQUEST_PERMISSION);
        }
    };


    DialogInterface.OnClickListener No1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_REQUEST_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppUtils.makeCall(BookingDetailsActivity.this, dealerBooking.getMobileNo());
                }

                break;


        }
    }

}

