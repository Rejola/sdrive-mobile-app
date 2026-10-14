package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.ServiceConstants;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.DescriptionAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddWorkItem;
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
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by creatah1 on 05-05-2017.
 */

public class RepairDescriptionActivity extends BaseActivity implements View.OnClickListener, DescriptionAdapter.WorkItemListener {

    private Button updateButton;
    private Context context;
    private boolean isService = false;

    private DealerBooking booking;

    private static AsyncHttpClient client = new AsyncHttpClient();

    private DescriptionAdapter labourAdapter;

    private DescriptionAdapter spareAdapter;

    private List<Description> labourList = new ArrayList<>();

    private List<Description> spareList = new ArrayList<>();

    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;

    @BindView(R.id.description1Layout)
    LinearLayout description1Layout;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    @BindView(R.id.descriptionLayout)
    LinearLayout descriptionLayout;

    @BindView(R.id.addLabourButton)
    Button addLabourButton;

    @BindView(R.id.addSpareButton)
    Button addSpareButton;

    @BindView(R.id.labourRecyclerView)
    RecyclerView labourRecyclerView;

    @BindView(R.id.spareRecyclerView)
    RecyclerView spareRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_description);
        ButterKnife.bind(this);
        setTitle("Spares and Labours");
        context = this;
        initializeViews();
        getBundle();

        labourRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        labourAdapter = new DescriptionAdapter(RepairDescriptionActivity.this, AppConstants.LABOUR_LIST, labourList, RepairDescriptionActivity.this, booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
        labourRecyclerView.setAdapter(labourAdapter);

        spareRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        spareAdapter = new DescriptionAdapter(RepairDescriptionActivity.this, AppConstants.SPARE_LIST, spareList, RepairDescriptionActivity.this, booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
        spareRecyclerView.setAdapter(spareAdapter);


        getWorkList();
        updateButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);
        addLabourButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);
        addSpareButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);
        updateButton.setOnClickListener(this);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ADD_DESCRIPTION_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.ADD_DESCRIPTION_EXTRA);
        }
    }


    private void getWorkList() {


        if (DeviceManager.isDeviceOffline(context)) {
            descriptionLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.internet_failed_toast));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("page_no", String.valueOf(1));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            ServiceManager.getCheckInWorkList(context, requestParams, new ServiceResponseListener<DescriptionListResponse>() {
                        @Override
                        public void onSuccess(DescriptionListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {

                                if (response.getDescription() != null){
                                    descriptionTextView.setText(response.getDescription());
                                    description1Layout.setVisibility(View.VISIBLE);
                                } else{
                                    description1Layout.setVisibility(View.GONE);
                                }

                                if (response.getLabourList() != null && response.getLabourList().size() > 0) {
                                    labourRecyclerView.setVisibility(View.VISIBLE);
                                    labourList = response.getLabourList();
                                    labourAdapter.setDescriptionList(labourList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    labourAdapter.notifyDataSetChanged();
                                } else {
                                    Description description = new Description();
                                    labourList.add(description);
                                    labourAdapter.setDescriptionList(labourList);
                                    labourRecyclerView.setAdapter(labourAdapter);
                                }


                                if (response.getSpareList() != null && response.getSpareList().size() > 0) {
                                    spareRecyclerView.setVisibility(View.VISIBLE);
                                    spareList = response.getSpareList();
                                    spareAdapter.setDescriptionList(spareList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    spareAdapter.notifyDataSetChanged();
                                } else {
                                    Description description = new Description();
                                    spareList.add(description);
                                    spareAdapter.setDescriptionList(spareList);
                                    spareRecyclerView.setAdapter(spareAdapter);
                                }
                                descriptionLayout.setVisibility(View.VISIBLE);
                            } else if (response != null && response.isError()) {
                                descriptionLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.no_results_found));

                            } else {
                                descriptionLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.internet_failed_toast));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            descriptionLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.internet_failed_toast));
                        }
                    }

            );
        }

    }

    private void initializeViews() {
        updateButton = (Button) findViewById(R.id.updateButton);
    }


    @OnClick(R.id.addLabourButton)
    public void onAddLabour() {
        Description description = new Description();
        labourList.add(description);
        labourAdapter.setDescriptionList(labourList);
        labourRecyclerView.setAdapter(labourAdapter);

    }

    @OnClick(R.id.addSpareButton)
    public void onAddSpare() {
        Description description = new Description();
        spareList.add(description);
        spareAdapter.setDescriptionList(spareList);
        spareRecyclerView.setAdapter(spareAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addButton:
                Description description = new Description();
                spareList.add(description);
                labourAdapter.setDescriptionList(spareList);
                spareRecyclerView.setAdapter(labourAdapter);
                break;

            case R.id.updateButton:
                if (labourAdapter != null) {
                    try {
                        updateWorkList();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;

        }
    }

    private void updateWorkList() throws UnsupportedEncodingException, JSONException {

        List<Description> labourList1 = labourAdapter.getDescriptionList();
        List<Description> spareList1 = spareAdapter.getDescriptionList();

        ArrayList<AddWorkItem> labourItemList = new ArrayList<>();
        for (Description description : labourList1) {
            if (description.getId() == 0 && description.getDescription() != null && !TextUtils.isEmpty(description.getDescription())) {
                AddWorkItem workItem = new AddWorkItem(description.getDescription());
                labourItemList.add(workItem);
            }
        }

        ArrayList<AddWorkItem> spareItemList = new ArrayList<>();
        for (Description description : spareList1) {
            if (description.getId() == 0 && description.getDescription() != null && !TextUtils.isEmpty(description.getDescription())) {
                AddWorkItem workItem = new AddWorkItem(description.getDescription());
                spareItemList.add(workItem);
            }
        }


        if (labourItemList.size() > 0 || spareItemList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < labourItemList.size(); i++) {
                jsonArray.put(labourItemList.get(i).getJSONObject());
            }

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < spareItemList.size(); i++) {
                jsonArray1.put(spareItemList.get(i).getJSONObject());
            }


            JSONObject jsonParams = new JSONObject();
            jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
            jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            jsonParams.put("labour_list", jsonArray);
            jsonParams.put("spares_list", jsonArray1);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(RepairDescriptionActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.ADD_CHECK_IN_WORK_LIST), entity, "application/json",
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
                                    Toast.makeText(RepairDescriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(RepairDescriptionActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);

                            showAlert(RepairDescriptionActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), OK, null, null);
                        }

                        public void onFinish() {
                            super.onFinish();
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                        }
                    });


        } else {
            Toast.makeText(context, getString(R.string.noNewDesAdded), Toast.LENGTH_SHORT).show();
        }
    }

    public DialogInterface.OnClickListener OK = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supportFinishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeletedItem(final Description description, final String type) {

        if (description.getId() != 0) {
            if (DeviceManager.isDeviceOffline(context)) {
                showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                        getResources().getString(R.string.ok), null, null, null);
                return;
            }
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            if (!isService) {
                RequestParams requestParams = new RequestParams();
                requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
                requestParams.put("item_id", String.valueOf(description.getId()));
                requestParams.put("type", type);
                ServiceManager.deleteCheckInWorkList(getApplicationContext(), requestParams, new ServiceResponseListener<DeleteResponse>() {
                            @Override
                            public void onSuccess(DeleteResponse response) {
                                isService = false;
                                mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                                if (response != null && !response.isError()) {
                                    if (type.equalsIgnoreCase(AppConstants.LABOUR_LIST)) {
                                        labourList = labourAdapter.getDescriptionList();
                                        labourList.remove(description);
                                        labourAdapter.setDescriptionList(labourList);
                                        labourRecyclerView.setAdapter(labourAdapter);
                                    } else {
                                        spareList = spareAdapter.getDescriptionList();
                                        spareList.remove(description);
                                        spareAdapter.setDescriptionList(spareList);
                                        spareRecyclerView.setAdapter(spareAdapter);
                                    }
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

        } else {
            if (type.equalsIgnoreCase(AppConstants.LABOUR_LIST)) {
                labourList = labourAdapter.getDescriptionList();
                labourList.remove(description);
                labourAdapter.setDescriptionList(labourList);
                labourRecyclerView.setAdapter(labourAdapter);
            } else {
                spareList = spareAdapter.getDescriptionList();
                spareList.remove(description);
                spareAdapter.setDescriptionList(spareList);
                spareRecyclerView.setAdapter(spareAdapter);
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
