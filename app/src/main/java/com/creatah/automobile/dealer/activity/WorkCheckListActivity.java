package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.WorkCheckListAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DescriptionListResponse;
import com.creatah.automobile.dealer.modal.SpareLabour;
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
 * Created by santhana on 27/04/18.
 */

public class WorkCheckListActivity extends BaseActivity {

    @BindView(R.id.spareRecyclerView)
    RecyclerView spareRecyclerView;

    @BindView(R.id.labourRecyclerView)
    RecyclerView labourRecyclerView;

    @BindView(R.id.checkLayout)
    LinearLayout checkLayout;

    @BindView(R.id.updateButton)
    Button updateButton;

    @BindView(R.id.stockreqButton)
    Button stockreqButton;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    @BindView(R.id.addSpareLayout)
    LinearLayout spareLayout;

    @BindView(R.id.addLabourLayout)
    LinearLayout labourLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.retryButton)
    Button retryButton;
    @BindView(R.id.viewStatus)
    Button viewStatusBtn;

    private List<SpareLabour> labourList = new ArrayList<>();

    private List<SpareLabour> spareList = new ArrayList<>();

    private Context context;

    private WorkCheckListAdapter labourAdapter;

    private WorkCheckListAdapter spareAdapter;

    private boolean isService = false;

    private DealerBooking booking;

    private boolean isCompleted;

    private static AsyncHttpClient client = new AsyncHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_check_ist);
        setTitle("Spares and Labours");
        ButterKnife.bind(this);

        context = this;
        getBundle();
        if (booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey))) {
            isCompleted = true;
        }
       // stockreqButton.setVisibility(View.GONE);
        labourRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        labourAdapter = new WorkCheckListAdapter(WorkCheckListActivity.this, AppConstants.LABOUR_LIST, labourList, false, isCompleted);
        labourRecyclerView.setAdapter(labourAdapter);

        spareRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        spareAdapter = new WorkCheckListAdapter(WorkCheckListActivity.this, AppConstants.SPARE_LIST, spareList, true, isCompleted);
        spareRecyclerView.setAdapter(spareAdapter);

        updateButton.setVisibility(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)) ? View.GONE : View.VISIBLE);

        getAddedSpareList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAddedSpareList();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            }
        });

        viewStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Utils.getResponse().getdata().getApiservice().get(ServiceConstants.SERVER_URL_KEY)+"admin/stock_status_view_update.php?ws_id="+AppUtils.getWorkshopInfo(getApplication()).getWorkshopId()+"&auth_token="+AppUtils.getWorkshopInfo(getApplication()).getAuthToken()+"&job_id="+String.valueOf(booking.getJobcardId())));
                startActivity(browserIntent);
            }
        });

    }

//    @OnClick(R.id.stockReq)
//    public void onUpdatedClicked1() {
//        Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
//    }

    @OnClick(R.id.updateButton)
    public void onUpdatedClicked() {
        try {
            //Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
            updateEstimateAmount();
            //updateEstimateCheck();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.stockreqButton)
    public void onUpdatedClickedCheck() {
        try {
            //Toast.makeText(context, "hiii", Toast.LENGTH_SHORT).show();
            //updateEstimateAmount();
            updateEstimateCheck();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void getAddedSpareList() {

        if (DeviceManager.isDeviceOffline(context)) {
            checkLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.internet_failed_toast));
            retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("jobcard_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("page_no", String.valueOf(1));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            ServiceManager.getServiceSpareLabourList(context, requestParams, new ServiceResponseListener<DescriptionListResponse>() {
                        @Override
                        public void onSuccess(DescriptionListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            if (response != null && !response.isError()) {
                                if (response.getLabours() != null && response.getLabours().size() > 0) {
                                    labourList = response.getLabours();
                                    labourAdapter.setSpareLabourList(labourList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    labourAdapter.notifyDataSetChanged();
                                    labourLayout.setVisibility(View.VISIBLE);
                                    noResultFound.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);

                                } else {
                                    labourLayout.setVisibility(View.GONE);
                                }

                                if (response.getSpares() != null && response.getSpares().size() > 0) {
                                    spareList = response.getSpares();
                                    spareAdapter.setSpareLabourList(spareList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    spareAdapter.notifyDataSetChanged();
                                    spareLayout.setVisibility(View.VISIBLE);
                                    retryButton.setVisibility(View.GONE);
                                    noResultFound.setVisibility(View.GONE);
                                } else {
                                    spareLayout.setVisibility(View.GONE);
                                }

                                if ((response.getLabours() != null && response.getLabours().size() > 0) || (response.getSpares() != null && response.getSpares().size() > 0)) {
                                    checkLayout.setVisibility(View.VISIBLE);
                                    noResultFound.setVisibility(View.GONE);
                                } else {
                                    checkLayout.setVisibility(View.GONE);
                                    noResultFound.setVisibility(View.VISIBLE);
                                    noResultFound.setText(getString(R.string.pleaseSelectSpareAndLabours));
                                }

                            } else if (response != null) {
                                checkLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                retryButton.setVisibility(View.GONE);
                                noResultFound.setText(response.getMessage());
                            } else {
                                checkLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                retryButton.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.tryAgainLater));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            checkLayout.setVisibility(View.GONE);
                           noResultFound.setVisibility(View.GONE);
                           retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {

                                   getAddedSpareList();
                               }
                           });
//                            noResultFound.setText(getResources().getString(R.string.title_of_data_message));

                        }
                    }

            );
        }

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.WORK_CHECK_LIST_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.WORK_CHECK_LIST_EXTRA);
        }
    }

    public void updateEstimateCheck() throws UnsupportedEncodingException, JSONException {
        List<SpareLabour> spareList = spareAdapter.getSpareLabourList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < spareList.size(); i++) {
            System.out.println("reqData"+spareList.get(i).getAddSpareJSONObject());
            jsonArray.put(spareList.get(i).getAddSpareJSONObject());
        }
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        jsonParams.put("spares_list", jsonArray);

        System.out.println("json::"+jsonParams);


        StringEntity entity = new StringEntity(jsonParams.toString());
        String url = Utils.getResponse().getdata().getApiservice().get(ServiceConstants.SERVER_URL_KEY) +
                Utils.getResponse().getdata().getApiservice().get(ServiceConstants.API_URL_ENDPOINT)+"updateSpareLabourCheck";
        client.post(WorkCheckListActivity.this,url, entity, "application/json",
                new AsyncHttpResponseHandler() {

                    public void onStart() {

                        super.onStart();
                        mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.SHOW_PROGRESS_DIALOG);

                    }

                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println("Response::"+arg1);
                            JSONObject json = new JSONObject(arg1);
                            boolean error = json.getBoolean("error");
                            String message = json.getString("message");
                            if (!error) {
                                Toast.makeText(WorkCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(WorkCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);

                        retryButton.setVisibility(View.VISIBLE);
                        retryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    updateEstimateCheck();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                            /*showAlert(WorkCheckListActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), OK, null, null);
                        */
                    }

                    public void onFinish() {
                        super.onFinish();
                        mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                    }
                });




    }


    private void updateEstimateAmount() throws UnsupportedEncodingException, JSONException {
        //Toast.makeText(context, "Checking", Toast.LENGTH_SHORT).show();
        List<SpareLabour> labourList = labourAdapter.getSpareLabourList();

        List<SpareLabour> spareList = spareAdapter.getSpareLabourList();


        if (labourList.size() > 0 || spareList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < spareList.size(); i++) {
                jsonArray.put(spareList.get(i).getStatusJSONObject());
            }

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < labourList.size(); i++) {
                jsonArray1.put(labourList.get(i).getStatusJSONObject());
            }

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
            jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            jsonParams.put("labour_list", jsonArray1);
            jsonParams.put("spares_list", jsonArray);

            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(WorkCheckListActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.UPDATE_WORK_CHECK_LIST), entity, "application/json",
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
                                    Toast.makeText(WorkCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(WorkCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);

                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        updateEstimateAmount();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            /*showAlert(WorkCheckListActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), OK, null, null);
                        */
                        }

                        public void onFinish() {
                            super.onFinish();
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                        }
                    });


        } else {
            Toast.makeText(context, getString(R.string.noDataUpdated), Toast.LENGTH_SHORT).show();
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
