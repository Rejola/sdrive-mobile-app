package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
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
import com.creatah.automobile.dealer.adapter.InProgressAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.modal.BookingListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class InProgerssBookingActivity extends BaseActivity implements InProgressAdapter.BookingClickListener {

    private RecyclerView productRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private InProgressAdapter inProgressAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    private TextView noResultFound;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inproress_list);
        setTitle(getString(R.string.userBooking));
        initializeViews();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        //productRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
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
        getInprogressList(currentPageNumber, true);

    }

    private void initializeViews() {
        productRecyclerView = (RecyclerView) findViewById(R.id.bookingRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        retryButton=(Button) findViewById(R.id.retryButton);
    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getInprogressList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getInprogressList(int pageNumber, boolean isFirst) {

        if (DeviceManager.isDeviceOffline(context)) {
            productRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("status", AppConstants.INPROGRESS_BOOKING);
        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            } else {
                // progressBar.setVisibility(View.VISIBLE);
            }

            ServiceManager.getBookingList(context, requestParams, new ServiceResponseListener<BookingListResponse>() {
                        @Override
                        public void onSuccess(BookingListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getBookingList() != null && response.getBookingList().size() > 0) {

                                noResultFound.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                productRecyclerView.setVisibility(View.VISIBLE);

                                if (isFirstPage) {
                                    inProgressAdapter = null;
                                    bookingList.clear();
                                }
                                bookingList.addAll(response.getBookingList());
                                totalPageCount = response.getTotal_count();
                                totalPageCount = response.getPage_count();
                                updateUi();
                            } else if(response !=null && response.isError()){
                                noResultFound.setVisibility(View.VISIBLE);
                                productRecyclerView.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);

                                noResultFound.setText(getString(R.string.no_service_found));
                            }

                        }


                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            productRecyclerView.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    currentPageNumber=1;
                                    getInprogressList(currentPageNumber, true);
                                }
                            });
                            /*noResultFound.setText(getString(R.string.server_unavailable));*/
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (inProgressAdapter == null) {

            inProgressAdapter = new InProgressAdapter(InProgerssBookingActivity.this, bookingList, InProgerssBookingActivity.this);
            productRecyclerView.setAdapter(inProgressAdapter);

        } else {

            inProgressAdapter.setBookingList(bookingList);
            inProgressAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }

    @Override
    public void onUserBookingClicked(Booking booking) {

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
