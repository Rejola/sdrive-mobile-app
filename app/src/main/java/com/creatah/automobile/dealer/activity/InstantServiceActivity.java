package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.EnquiryAdapter;
import com.creatah.automobile.dealer.adapter.InstantServiceAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CustomerEnquiry;
import com.creatah.automobile.dealer.modal.CustomerEnquiryListResponse;
import com.creatah.automobile.dealer.modal.InstantService;
import com.creatah.automobile.dealer.modal.InstantServiceListResponse;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class InstantServiceActivity extends BaseActivity {

    private RecyclerView serviceRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private InstantServiceAdapter serviceAdapter;
    private List<InstantService> serviceList = new ArrayList<>();
    private TextView noResultFound;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button retryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_service);
        setTitle(getString(R.string.instantService));
        initializeViews();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        serviceRecyclerView.setLayoutManager(layoutManager);
        //serviceRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        serviceRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        getCompletedList(currentPageNumber, true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getCompletedList(currentPageNumber, true);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });

    }

    private void initializeViews() {
        serviceRecyclerView = (RecyclerView) findViewById(R.id.serviceRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        retryButton=(Button) findViewById(R.id.retryButton);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getCompletedList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getCompletedList(int pageNumber, boolean isFirst) {

        if (DeviceManager.isDeviceOffline(context)) {
            serviceRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            } else {
                // progressBar.setVisibility(View.VISIBLE);
            }

            ServiceManager.getInstantServiceList(context, requestParams, new ServiceResponseListener<InstantServiceListResponse>() {
                        @Override
                        public void onSuccess(InstantServiceListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getServiceList() != null && response.getServiceList().size() > 0) {

                                noResultFound.setVisibility(View.GONE);
                                serviceRecyclerView.setVisibility(View.VISIBLE);
                                retryButton.setVisibility(View.GONE);

                                if (isFirstPage) {
                                    serviceAdapter = null;
                                    serviceList.clear();
                                }
                                serviceList.addAll(response.getServiceList());
                                totalPageCount = response.getTotal_count();
                                totalPageCount = response.getPage_count();
                                updateUi();
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                serviceRecyclerView.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.no_service_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            serviceRecyclerView.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPageNumber=1;
                                    getCompletedList(currentPageNumber, true);

                                }
                            });
//                            noResultFound.setText(getString(R.string.server_unavailable));
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (serviceAdapter == null) {

            serviceAdapter = new InstantServiceAdapter(InstantServiceActivity.this, serviceList);
            serviceRecyclerView.setAdapter(serviceAdapter);

        } else {

            serviceAdapter.setServiceList(serviceList);
            serviceAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = 0;
            outRect.top = space;
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
