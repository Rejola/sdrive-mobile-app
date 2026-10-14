package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.creatah.automobile.dealer.adapter.WorkActionAdapter;
import com.creatah.automobile.dealer.listener.UiUpdateListener;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddAction;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 16-05-2017.
 */

public class AddActionActivity extends BaseActivity implements WorkActionAdapter.ActionListener, View.OnClickListener {

    private Button updateButton;
    private Context context;
    private boolean isService = false;
    private DealerBooking booking;
    private RecyclerView labourRecyclerView;
    private List<Description> descriptionList = new ArrayList<>();
    private WorkActionAdapter actionAdapter;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Description description;
    private TextView noResultFound;
    private LinearLayout addLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_action);
        setTitle(getString(R.string.actionTitle));
        context = this;
        initializeViews();
        getBundle();
        labourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        actionAdapter = new WorkActionAdapter(this, descriptionList, this, booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
        labourRecyclerView.setAdapter(actionAdapter);
        updateButton.setOnClickListener(this);
        updateButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);
        getActionList();
    }

    private void getActionList() {

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
            ServiceManager.getDescriptionList(context, requestParams, new ServiceResponseListener<DescriptionListResponse>() {
                        @Override
                        public void onSuccess(DescriptionListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

/*                            if (response != null && !response.isError() && response.getDescriptionList() != null && response.getDescriptionList().size() > 0) {
                                labourRecyclerView.setVisibility(View.VISIBLE);
                                addLayout.setVisibility(View.VISIBLE);
                                noResultFound.setVisibility(View.GONE);
                                descriptionList = response.getDescriptionList();
                                actionAdapter.setComplaintsList(descriptionList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                labourRecyclerView.setAdapter(actionAdapter);
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                labourRecyclerView.setVisibility(View.GONE);
                                addLayout.setVisibility(View.GONE);
                            }*/
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            noResultFound.setVisibility(View.VISIBLE);
                            labourRecyclerView.setVisibility(View.GONE);
                            noResultFound.setText(getResources().getString(R.string.title_of_data_message));
                            addLayout.setVisibility(View.GONE);
                        }
                    }

            );
        }
    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ADD_DESCRIPTION_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.ADD_DESCRIPTION_EXTRA);
        }
    }


    private void initializeViews() {
        updateButton = (Button) findViewById(R.id.updateButton);
        labourRecyclerView = (RecyclerView) findViewById(R.id.labourRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        addLayout = (LinearLayout) findViewById(R.id.addLayout);
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
                descriptionList = actionAdapter.getComplaintsList();
                descriptionList.remove(description);
                actionAdapter.setComplaintsList(descriptionList);
                labourRecyclerView.setAdapter(actionAdapter);
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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.updateButton:
                if (actionAdapter != null) {


                    try {
                        updateAction();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void updateAction() throws UnsupportedEncodingException, JSONException {
        List<Description> descriptionList = actionAdapter.getComplaintsList();

        ArrayList<AddAction> actionList = new ArrayList<>();
        for (Description description : descriptionList) {
            // if (description.getId()==0 && description.getDescription() != null && !TextUtils.isEmpty(description.getDescription())) {
            AddAction action = new AddAction(description.getId(), description.getAction());
            actionList.add(action);
            // }
        }
        if (actionList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < actionList.size(); i++) {
                jsonArray.put(actionList.get(i).getJSONObject());
            }

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
            jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            jsonParams.put("action_list", jsonArray);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(AddActionActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.ADD_ACTION), entity, "application/json",
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
                                    Toast.makeText(AddActionActivity.this, message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(AddActionActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);

                            showAlert(AddActionActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
