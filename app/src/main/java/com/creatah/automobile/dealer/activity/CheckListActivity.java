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
import com.creatah.automobile.dealer.adapter.CheckListAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CheckListResponse;
import com.creatah.automobile.dealer.modal.CheckOut;
import com.creatah.automobile.dealer.modal.CheckedItem;
import com.creatah.automobile.dealer.modal.DealerBooking;
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
 * Created by karthikeyan on 20-03-2017.
 */

public class CheckListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView checkRecyclerView;
    private Button updateButton;
    private EditText kmsEditText;
    private EditText descEditText;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private CheckListAdapter checkListAdapter;
    private List<CheckOut> checkOutList = new ArrayList<>();
    private TextView noResultFound;
    private LinearLayout container;
    private DealerBooking booking;
    private CardView checKListCardView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String status;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private boolean isCompleted;
    private String kmsDriven;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        setTitle(getString(R.string.checkListTitle));
        initializeViews();
        context = this;
        getBundle();

        if (booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey))) {
            isCompleted = true;
        }

        layoutManager = new LinearLayoutManager(this);
        checkRecyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setEnabled(true);

        /*if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
            swipeRefreshLayout.setEnabled(true);
        }
        else{
            swipeRefreshLayout.setEnabled(false);
        }*/

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

                currentPageNumber = 1;
                getCheckList(currentPageNumber, true);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });


        updateButton.setVisibility(isCompleted ? View.GONE : View.VISIBLE);

        kmsEditText.setFocusable(!isCompleted);
        kmsEditText.setFocusableInTouchMode(!isCompleted);
        kmsEditText.setClickable(!isCompleted);

        descEditText.setFocusable(!isCompleted);
        descEditText.setFocusableInTouchMode(!isCompleted);
        descEditText.setClickable(!isCompleted);

        updateButton.setOnClickListener(this);
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
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        kmsEditText = (EditText) findViewById(R.id.kmsDriven);
        descEditText = (EditText) findViewById(R.id.anyOthers);
        container = (LinearLayout) findViewById(R.id.container);
        checKListCardView = (CardView) findViewById(R.id.checKListCardView);
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
            container.setVisibility(View.GONE);
            checKListCardView.setVisibility(View.GONE);
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

                            if (response != null && !response.isError() && response.getCheckOutList() != null && response.getCheckOutList().size() > 0) {

                                noResultFound.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                container.setVisibility(View.VISIBLE);
                                checKListCardView.setVisibility(View.VISIBLE);
                                checkRecyclerView.setVisibility(View.VISIBLE);

                                if (isFirstPage) {
                                    checkListAdapter = null;
                                    checkOutList.clear();
                                    if (response.getKmsDriven() != 0) {
                                        kmsEditText.setText(String.valueOf(response.getKmsDriven()));
                                        updateButton.setText("Update");
                                    }
                                    descEditText.setText(response.getDescription());
                                }
                                checkOutList.addAll(response.getCheckOutList());
                                totalPageCount = response.getTotal_count();
                                totalPageCount = response.getPage_count();
                                updateUi();
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                container.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.no_service_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            container.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    currentPageNumber = 1;
                                    getCheckList(currentPageNumber, true);
                                }
                            });
                            /*noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.server_unavailable));*/
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (checkListAdapter == null) {

            checkListAdapter = new CheckListAdapter(CheckListActivity.this, checkOutList, isCompleted);
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

                validateData();


                break;

        }
    }

    private void validateData() {

        kmsDriven = kmsEditText.getText().toString();

        if (TextUtils.isEmpty(kmsDriven)) {
            Toast.makeText(context, "Please Enter Kms", Toast.LENGTH_SHORT).show();
        } else {

            if (checkListAdapter != null) {
                try {
                    updateCheckList();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public DialogInterface.OnClickListener OK = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    private void updateCheckList() throws UnsupportedEncodingException, JSONException {

        String kmsDriven = kmsEditText.getText().toString();
        String desc = descEditText.getText().toString();
        List<CheckOut> checkOuts = checkListAdapter.getCheckOutList();

        ArrayList<CheckedItem> checkedItemList = new ArrayList<>();
        for (CheckOut checkOut : checkOuts) {
            if (checkOut.isChecked()) {
                CheckedItem checkedItem = new CheckedItem(checkOut.getId(), checkOut.getCount());
                checkedItemList.add(checkedItem);
            }
        }
        if (checkedItemList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < checkedItemList.size(); i++) {
                jsonArray.put(checkedItemList.get(i).getJSONObject());
            }

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("booking_id", String.valueOf(booking.getJobcardId()));
            jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            jsonParams.put("status", status);
            jsonParams.put("kms_driven", kmsDriven);
            jsonParams.put("description", desc);
            jsonParams.put("check_list", jsonArray);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(CheckListActivity.this, ServiceManager.getServiceAPIUrl(ServiceConstants.UPDATE_CHECK_LIST), entity, "application/json",
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
                                    Toast.makeText(CheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(CheckListActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);

                            showAlert(CheckListActivity.this, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), OK, null, null);
                        }

                        public void onFinish() {
                            super.onFinish();
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                        }
                    });


        } else {
            Toast.makeText(CheckListActivity.this, "No items selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
