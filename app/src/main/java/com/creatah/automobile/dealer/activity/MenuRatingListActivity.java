package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.DealerBookingAdapter1;
import com.creatah.automobile.dealer.manager.ServiceManager;

import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DealerBookingListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class MenuRatingListActivity extends BaseActivity {

    private RecyclerView productRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private DealerBookingAdapter1 completedAdapter1;
    private List<DealerBooking> bookingList = new ArrayList<>();
    private TextView noResultFound;
    private String SEARCH_KEY = null;



    private String customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compelted_list1);
        setTitle("Rating List");
        initializeViews();
        ButterKnife.bind(this);
        getBundle();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        final Calendar cal = Calendar.getInstance();


        productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        getCompletedList(currentPageNumber, true, SEARCH_KEY);


    }
    private void getBundle() {
        Bundle bundle=getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.COMPLETE_CUSTOMER_ID)) {
            customerId = bundle.getString(AppConstants.COMPLETE_CUSTOMER_ID);
        }

    }




        private void initializeViews() {
        productRecyclerView = (RecyclerView) findViewById(R.id.bookingRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getCompletedList(currentPageNumber, false, SEARCH_KEY);
                    return;
                }

            }
        }
    }

    private void getCompletedList(int pageNumber, boolean isFirst, String searchKey) {

        if (DeviceManager.isDeviceOffline(context)) {
            productRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
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

            ServiceManager.getWorkShopJobCardList(context, requestParams, new ServiceResponseListener<DealerBookingListResponse>() {
                        @Override
                        public void onSuccess(DealerBookingListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getBookingList() != null && response.getBookingList().size() > 0) {

                                noResultFound.setVisibility(View.GONE);
                                productRecyclerView.setVisibility(View.VISIBLE);

                                if (isFirstPage) {
                                    completedAdapter1 = null;
                                    bookingList.clear();
                                }
                                bookingList.addAll(response.getBookingList());
                                totalPageCount = response.getTotal_count();
                                totalPageCount = response.getPage_count();

                                updateUi();
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                productRecyclerView.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.no_service_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            productRecyclerView.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.server_unavailable));
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (completedAdapter1 == null) {

            completedAdapter1 = new DealerBookingAdapter1(MenuRatingListActivity.this, bookingList, true);
            productRecyclerView.setAdapter(completedAdapter1);

        } else {

            completedAdapter1.setBookingList(bookingList);
            completedAdapter1.notifyDataSetChanged();
        }
        currentPageNumber++;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }





}
