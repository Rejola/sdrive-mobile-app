package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.ServiceConstants;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.EstimateAdapter1;
import com.creatah.automobile.dealer.listener.UiUpdateListener;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddEstimateAmount;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DeleteResponse;
import com.creatah.automobile.dealer.modal.Description;
import com.creatah.automobile.dealer.modal.DescriptionListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class EstimateActivity extends BaseActivity implements View.OnClickListener, EstimateAdapter1.EstimationListener {

    private Button updateButton;

    private Context context;
    private boolean isService = false;
    private DealerBooking booking;

    private List<Description> labourList = new ArrayList<>();
    private List<Description> spareList = new ArrayList<>();

    private EstimateAdapter1 labourAdapter;
    private EstimateAdapter1 spareAdapter;

    private static AsyncHttpClient client = new AsyncHttpClient();

    private Description description;

    private LinearLayout detailLayout;

    private TextView noResultFound;

    @BindView(R.id.labourRecyclerView)
    RecyclerView labourRecyclerView;

    @BindView(R.id.spareRecyclerView)
    RecyclerView spareRecyclerView;

    @BindView(R.id.labourLayout)
    LinearLayout labourLayout;

    @BindView(R.id.spareLayout)
    LinearLayout spareLayout;

    @BindView(R.id.totalEditText)
    EditText totalEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimates);
        ButterKnife.bind(this);
        setTitle(getString(R.string.estimates));
        context = this;
        initializeViews();
        getBundle();

        labourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        labourAdapter = new EstimateAdapter1(this, labourList, this, booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
        labourRecyclerView.setAdapter(labourAdapter);

        spareRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        spareAdapter = new EstimateAdapter1(this, spareList, this, booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
        spareRecyclerView.setAdapter(spareAdapter);


        updateButton.setOnClickListener(this);
        updateButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);
        getEstimateList();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ADD_DESCRIPTION_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.ADD_DESCRIPTION_EXTRA);
        }
    }


    private void getEstimateList() {

        if (DeviceManager.isDeviceOffline(context)) {
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("page_no", String.valueOf(1));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            ServiceManager.getInspectionList(context, requestParams, new ServiceResponseListener<DescriptionListResponse>() {
                        @Override
                        public void onSuccess(DescriptionListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                if (response.getLabourList() != null && response.getLabourList().size() > 0) {
                                    labourList = response.getLabourList();
                                    labourAdapter.setComplaintsList(labourList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    labourAdapter.notifyDataSetChanged();
                                    onUpdateItem();
                                    labourLayout.setVisibility(View.VISIBLE);
                                } else {
                                    labourLayout.setVisibility(View.GONE);
                                }

                                if (response.getSpareList() != null && response.getSpareList().size() > 0) {
                                    spareList = response.getSpareList();
                                    spareAdapter.setComplaintsList(spareList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    spareAdapter.notifyDataSetChanged();
                                    onUpdateItem();
                                    spareLayout.setVisibility(View.VISIBLE);
                                } else {
                                    spareLayout.setVisibility(View.GONE);
                                }

                                if (response.getEstimateCost() != 0) {
                                    totalEditText.setText(String.valueOf(response.getEstimateCost()));
                                }

                                detailLayout.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            detailLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getResources().getString(R.string.title_of_data_message));
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                        }
                    }

            );
        }


    }


    private void initializeViews() {
/*
        totalEditText = (EditText) findViewById(R.id.totalEditText);
        addButton = (Button) findViewById(R.id.addButton);
*/
        updateButton = (Button) findViewById(R.id.updateButton);
/*
        calculateButton = (Button) findViewById(R.id.calculateButton);
*/
        detailLayout = (LinearLayout) findViewById(R.id.detailsLayout);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addButton:
/*
                descriptionList = estimateAdapter.getComplaintsList();
                descriptionList.add(new Description());
                estimateAdapter.setComplaintsList(descriptionList);
                complaintRecyclerView.setAdapter(estimateAdapter);
*/
                break;

            case R.id.updateButton:

                updateAmount();
                break;

/*
            case R.id.calculateButton:
*/
/*
                descriptionList = estimateAdapter.getComplaintsList();
*//*

                int amount = 0;
*/
/*
                for (Description complaints : descriptionList) {
                    int amt = complaints.getAmount();
                    if (amt != 0) {
                        amount = amount + amt;
                    }
                }
*//*

*/
/*
                totalEditText.setText(String.valueOf(amount));
*//*


                break;
*/
        }
    }

    private void updateAmount() {


/*
        if (estimateAdapter != null) {

            descriptionList = estimateAdapter.getComplaintsList();
            int amount1 = 0;
*/
/*
            for (Description complaints : descriptionList) {
                int amt = complaints.getAmount();
                if (amt != 0) {
                    amount1 = amount1 + amt;
                }
            }
*//*

*/
/*
            totalEditText.setText(String.valueOf(amount1));
*//*
*/
        String totalAmount = totalEditText.getText().toString();
        if (!TextUtils.isEmpty(totalAmount)) {
            try {
                updateEstimateAmount(totalAmount);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "Please enter estimate amount", Toast.LENGTH_SHORT).show();
        }


    }

    private void updateEstimateAmount(String amount) throws UnsupportedEncodingException, JSONException {

        List<Description> labourList = labourAdapter.getComplaintsList();

        ArrayList<AddEstimateAmount> labourItemList = new ArrayList<>();
        for (Description description : labourList) {
            // if (description.getId()==0 && description.getDescription() != null && !TextUtils.isEmpty(description.getDescription())) {
            AddEstimateAmount estimateAmount = new AddEstimateAmount(description.getId(), description.getQty(), description.getRate());
            labourItemList.add(estimateAmount);
            // }
        }

        List<Description> spareList = spareAdapter.getComplaintsList();

        ArrayList<AddEstimateAmount> spareItemList = new ArrayList<>();
        for (Description description : spareList) {
            // if (description.getId()==0 && description.getDescription() != null && !TextUtils.isEmpty(description.getDescription())) {
            AddEstimateAmount estimateAmount = new AddEstimateAmount(description.getId(), description.getQty(), description.getRate());
            spareItemList.add(estimateAmount);
            // }
        }
        JSONObject jsonParams = new JSONObject();

        if (labourItemList.size() > 0 || spareItemList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < labourItemList.size(); i++) {
                jsonArray.put(labourItemList.get(i).getJSONObject());
            }

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < spareItemList.size(); i++) {
                jsonArray1.put(spareItemList.get(i).getJSONObject());
            }

            jsonParams.put("labour_list", jsonArray);
            jsonParams.put("spares_list", jsonArray1);

        }
        jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        jsonParams.put("estimate_amount", amount);

        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(EstimateActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.UPDATE_INSPECTION_AMOUNT), entity, "application/json",
                new AsyncHttpResponseHandler() {

                    public void onStart() {

                        super.onStart();
                        mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.SHOW_PROGRESS_DIALOG);

                    }

                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            JSONObject json = new JSONObject(arg1);
                            boolean error = json.getBoolean("error");
                            String message = json.getString("message");
                            if (!error) {
                                Toast.makeText(EstimateActivity.this, message, Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(EstimateActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);

                        showAlert(EstimateActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                getResources().getString(R.string.ok), OK, null, null);
                    }

                    public void onFinish() {
                        super.onFinish();
                        mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                    }
                });


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

    public DialogInterface.OnClickListener OK = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    @Override
    public void onUpdateItem() {
        List<Description> labourDescriptions = new ArrayList<>();
        List<Description> spareDescriptions = new ArrayList<>();

        double totalAmount = 0;
        if (spareAdapter != null) {
            spareDescriptions = spareAdapter.getComplaintsList();
        }
        if (labourAdapter != null) {
            labourDescriptions = labourAdapter.getComplaintsList();
        }

        if (labourDescriptions != null && labourDescriptions.size() > 0) {
            for (Description description : labourDescriptions) {
                totalAmount = totalAmount + description.getAmount();
            }
        }

        if (spareDescriptions != null && spareDescriptions.size() > 0) {
            for (Description description : spareDescriptions) {
                totalAmount = totalAmount + description.getAmount();
            }
        }

        totalEditText.setText(String.valueOf(totalAmount));
    }

    @Override
    public void onDeletedItem(Description description) {
        this.description = description;
        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("description_id", String.valueOf(description.getId()));
        ServiceManager.deleteWorkItem(getApplicationContext(), requestParams, uiUpdateListener);

    }

    UiUpdateListener uiUpdateListener = new UiUpdateListener() {
        @Override
        public void OnUpdateUI(Object... object) {
            isService = false;
            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
            DeleteResponse deleteResponse = (DeleteResponse) object[0];
            if (deleteResponse != null && !deleteResponse.isError()) {
/*
                descriptionList = estimateAdapter.getComplaintsList();
                descriptionList.remove(description);
                estimateAdapter.setComplaintsList(descriptionList);
                complaintRecyclerView.setAdapter(estimateAdapter);
*/
            }
        }

        @Override
        public void OnFailure(String errorMessage) {
            isService = false;
            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
            showAlert(context, getResources().getString(R.string.server_failed),
                    getResources().getString(R.string.server_unavailable),
                    getResources().getString(R.string.ok), null, null, null);
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
