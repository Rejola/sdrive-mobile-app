package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.CustomerAdapter;
import com.creatah.automobile.dealer.adapter.RatingsAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CustomerListResponse;
import com.creatah.automobile.dealer.modal.Rating;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 06-04-2018.
 */

public class RatingsActivity extends BaseActivity /*implements RatingsAdapter.RatingsClickListener*/{
    private Context context;

    @BindView(R.id.ratingRecyclerView)
    RecyclerView ratingRecyclerView;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    List<Rating> ratingList = new ArrayList<>();
    private RatingsAdapter ratingsAdapter;

    private LinearLayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        setTitle("Ratings");
        ButterKnife.bind(this);
        context = this;

        ratingList.add(new Rating());
        ratingList.add(new Rating());
        ratingList.add(new Rating());

        layoutManager = new LinearLayoutManager(this);
        ratingRecyclerView.setLayoutManager(layoutManager);
        ratingsAdapter = new RatingsAdapter(RatingsActivity.this, ratingList);
        ratingRecyclerView.setAdapter(ratingsAdapter);
    }
        /*ratingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

               *//* loadNextListProduct(totalItemCount, lastVisiblePosition);*//*

            }
        });
        updateUi();*/




    /*public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getCustomerList(currentPageNumber, false);
                    return;
                }

            }
        }
    }*/

   /* private void getCustomerList(int pageNumber, boolean isFirst) {

        if (DeviceManager.isDeviceOffline(context)) {
            customerRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getDealerInfo(getApplication()).getAuthToken());

        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            } else {
                // progressBar.setVisibility(View.VISIBLE);
            }

            ServiceManager.getCustomerList(context, requestParams, new ServiceResponseListener<CustomerListResponse>() {
                        @Override
                        public void onSuccess(CustomerListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getCustomerList() != null && response.getCustomerList().size() > 0) {

                                noResultFound.setVisibility(View.GONE);
                                customerRecyclerView.setVisibility(View.VISIBLE);

                                if (isFirstPage) {
                                    customerAdapter = null;
                                    customerList.clear();
                                }
                                customerList.addAll(response.getCustomerList());
                                totalPageCount = response.getTotal_count();
                                totalPageCount = response.getPage_count();
                                updateUi();
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                customerRecyclerView.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.no_results_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            customerRecyclerView.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.server_unavailable));
                        }
                    }

            );
        }

    }
*/
    /*private void updateUi() {

        if (ratingsAdapter == null) {
            ratingsAdapter = new CustomerAdapter(RatingsActivity.this, ratingList);
            ratingRecyclerView.setAdapter(ratingsAdapter);

        } else {

            ratingsAdapter.setRatingList(ratingList);
            ratingsAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }*/


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
