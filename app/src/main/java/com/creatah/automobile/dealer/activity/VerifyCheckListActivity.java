package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import com.creatah.automobile.dealer.adapter.VerifyCheckListAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CheckListResponse;
import com.creatah.automobile.dealer.modal.CheckOut;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.VerifiedItem;
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
 * Created by creatah1 on 17-05-2017.
 */

public class VerifyCheckListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView checkRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private VerifyCheckListAdapter checkListAdapter;
    private List<CheckOut> checkOutList = new ArrayList<>();
    private TextView noResultFound;
    private DealerBooking booking;
    private String status;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Button updateButton;
    private LinearLayout listLayout;

    private TextView othersTextView;
    private TextView kmsTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button retryButton;

    CardView detailsLayout;
    LinearLayout othersLayout;
    LinearLayout kmsLayout;
    TextView noCheckListFound;

    private boolean isCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_check_list);
        setTitle(getString(R.string.checkListTitle));
        initializeViews();
        context = this;
        getBundle();

        if (booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey))) {
            isCompleted = true;
        }

        layoutManager = new LinearLayoutManager(this);
        checkRecyclerView.setLayoutManager(layoutManager);
        checkRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = visibleItemCount + firstVisibleItemPosition;

                loadNextListProduct(totalItemCount, lastVisiblePosition);

            }
        });
        getCheckList(currentPageNumber, true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCheckList(currentPageNumber, true);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });
        updateButton.setOnClickListener(this);
        updateButton.setVisibility(isCompleted ? View.GONE : View.VISIBLE);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ADD_CHECK_LIST_EXTRA) && bundle.containsKey(AppConstants.CHECK_LIST_STATUS_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.ADD_CHECK_LIST_EXTRA);
            status = bundle.getString(AppConstants.CHECK_LIST_STATUS_EXTRA);
        }
    }

    private void initializeViews() {
        checkRecyclerView = (RecyclerView) findViewById(R.id.checkRecyclerView);
        updateButton = (Button) findViewById(R.id.updateButton);
        listLayout = (LinearLayout) findViewById(R.id.listLayout);
        noResultFound = (TextView) findViewById(R.id.noResultFound);

        othersTextView = (TextView) findViewById(R.id.othersTextView);
        kmsTextView = (TextView) findViewById(R.id.kmsTextView);

        detailsLayout = (CardView) findViewById(R.id.detailsLayout);
        othersLayout = (LinearLayout) findViewById(R.id.othersLayout);
        kmsLayout = (LinearLayout) findViewById(R.id.kmsLayout);
        noCheckListFound = findViewById(R.id.noCheckListFound);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        retryButton=(Button) findViewById(R.id.retryButton);

    }


    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getCheckList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getCheckList(int pageNumber, boolean isFirst) {

        if (DeviceManager.isDeviceOffline(context)) {
            listLayout.setVisibility(View.GONE);
            checkRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            } else {
                // progressBar.setVisibility(View.VISIBLE);
            }

            ServiceManager.getCheckList(context, requestParams, new ServiceResponseListener<CheckListResponse>() {
                        @Override
                        public void onSuccess(CheckListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                detailsLayout.setVisibility(View.VISIBLE);
                                retryButton.setVisibility(View.GONE);


                                String des = response.getDescription();
                                int kms = response.getKmsDriven();


                                if (kms != 0) {
                                    kmsLayout.setVisibility(View.VISIBLE);
                                    kmsTextView.setText("Kms Driven " + " " + String.valueOf(kms));
                                } else {
                                    kmsLayout.setVisibility(View.GONE);
                                }

                                if (des != null && !TextUtils.isEmpty(des)) {
                                    othersLayout.setVisibility(View.VISIBLE);
                                    othersTextView.setText(des);
                                } else {
                                    othersLayout.setVisibility(View.GONE);
                                }


                                if (response.getCheckOutList() != null && response.getCheckOutList().size() > 0) {
                                    checkRecyclerView.setVisibility(View.VISIBLE);

                                    if (isFirstPage) {
                                        checkListAdapter = null;
                                        checkOutList.clear();
                                    }

                                    List<CheckOut> checkOuts = response.getCheckOutList();
                                    for (CheckOut checkOut : checkOuts) {
                                        if (checkOut.isChecked()) {
                                            checkOutList.add(checkOut);
                                        }
                                    }

                                    if (checkOutList.size() > 0) {
                                        noResultFound.setVisibility(View.GONE);
                                        listLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        noResultFound.setVisibility(View.VISIBLE);
                                        listLayout.setVisibility(View.GONE);
                                    }

                                    //checkOutList.addAll(response.getCheckOutList());
                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    updateUi();
                                } else {
                                    noCheckListFound.setVisibility(View.VISIBLE);
                                    checkRecyclerView.setVisibility(View.GONE);
                                }


                                if (kms != 0 || (des != null && !TextUtils.isEmpty(des)) || (checkOutList != null && checkOutList.size() > 0)) {
                                    listLayout.setVisibility(View.VISIBLE);
                                    noResultFound.setVisibility(View.GONE);
                                } else {
                                    listLayout.setVisibility(View.GONE);
                                    noResultFound.setVisibility(View.VISIBLE);
                                    noResultFound.setText(getString(R.string.noCheckList));
                                }

                                if (des != null && !TextUtils.isEmpty(des)) {
                                    othersLayout.setVisibility(View.VISIBLE);
                                    othersTextView.setText(des);
                                } else {
                                    othersLayout.setVisibility(View.GONE);
                                }

                            } else if (response != null) {
                                listLayout.setVisibility(View.GONE);
                                noResultFound.setText(response.getMessage());
                                noResultFound.setVisibility(View.VISIBLE);
                            } else {
                                retryButton.setVisibility(View.GONE);
                                listLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.server_unavailable));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            listLayout.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPageNumber=1;
                                    getCheckList(currentPageNumber, true);

                                }
                            });
                            noResultFound.setVisibility(View.GONE);
                            noResultFound.setText(getString(R.string.server_unavailable));
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (checkListAdapter == null) {

            checkListAdapter = new VerifyCheckListAdapter(VerifyCheckListActivity.this, checkOutList, isCompleted);
            checkRecyclerView.setAdapter(checkListAdapter);

        } else {

            checkListAdapter.setCheckOutList(checkOutList);
            checkListAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }


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
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.updateButton:
                if (checkListAdapter != null) {
                    try {
                        updateCheckList();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    private void updateCheckList() throws UnsupportedEncodingException, JSONException {

        List<CheckOut> checkOuts = checkListAdapter.getCheckOutList();

        ArrayList<VerifiedItem> verifiedItemList = new ArrayList<>();
        for (CheckOut checkOut : checkOuts) {
            if (checkOut.isChecked()) {
                VerifiedItem verifiedItem = new VerifiedItem(checkOut.getItemId(), checkOut.isVerified());
                verifiedItemList.add(verifiedItem);
            }
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < verifiedItemList.size(); i++) {
            jsonArray.put(verifiedItemList.get(i).getJSONObject());
        }

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        jsonParams.put("status", status);
        jsonParams.put("check_in_items_list", jsonArray);
        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(VerifyCheckListActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.UPDATE_VERIFY_CHECK_LIST), entity, "application/json",
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
                                Toast.makeText(VerifyCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(VerifyCheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);

                        showAlert(VerifyCheckListActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                getResources().getString(R.string.ok), OK, null, null);
                    }

                    public void onFinish() {
                        super.onFinish();
                        mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                    }
                });


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
