package com.creatah.automobile.dealer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.fragment.BaseFragment;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.UserBookingDetailActivity;
import com.creatah.automobile.dealer.adapter.InProgressAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.modal.BookingListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by creatah1 on 04-05-2017.
 */

public class UserBookingFragment extends BaseFragment implements InProgressAdapter.BookingClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView bookingRecyclerView;
    private LinearLayoutManager layoutManager;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private InProgressAdapter inProgressAdapter;
    private List<Booking> bookingList = new ArrayList<>();
    private TextView noResultFound;
    private int DETAILS_REQ_CODE = 156;
    private int ACK_BOOKING_REQ_CODE = 788;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.noGeneralServiceFound)
    LinearLayout noGeneralServiceFound;

    @BindView(R.id.noGeneralService)
    TextView noGeneralService;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_booking, container, false);
        ButterKnife.bind(this,view);
        bookingRecyclerView = (RecyclerView) view.findViewById(R.id.bookingRecyclerView);
        noResultFound = (TextView) view.findViewById(R.id.noResultFoundTextView);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
        bookingRecyclerView.setLayoutManager(layoutManager);
        bookingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noGeneralServiceFound.setVisibility(View.GONE);
                noGeneralService.setVisibility(View.GONE);
                bookingRecyclerView.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                getInprogress(currentPageNumber);
            }
        });

         retryButton.setVisibility(View.VISIBLE);
                                retryButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        noGeneralServiceFound.setVisibility(View.GONE);
                                        noGeneralService.setVisibility(View.GONE);
                                        bookingRecyclerView.setVisibility(View.GONE);
                                        getInprogress(currentPageNumber);
                                    }
                                });
    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(getContext())) {

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

    private void getInprogressList(int pageNumber, final boolean isFirst) {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (DeviceManager.isDeviceOffline(getContext())) {
                bookingRecyclerView.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
                noGeneralServiceFound.setVisibility(View.GONE);
                noGeneralService.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);


                return;
            }

            RequestParams requestParams = new RequestParams();
            requestParams.put("page_no", String.valueOf(pageNumber));
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getActivity().getApplication()).getAuthToken());
            requestParams.put("status", AppConstants.OPEN_BOOKING);
            isFirstPage = isFirst;

            if (!isService) {

                isService = true;

                if (isFirst ) {
                    progressLayout.setVisibility(View.VISIBLE);

                } else {
                    footerLayout.setVisibility(View.VISIBLE);
                }

                ServiceManager.getBookingList(getContext(), requestParams, new ServiceResponseListener<BookingListResponse>() {
                            @Override
                            public void onSuccess(BookingListResponse response) {
                                isService = false;
                                progressLayout.setVisibility(View.GONE);
                                footerLayout.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);

                                Activity activity = getActivity();
                                if (activity != null && isAdded()) {
                                    if (response != null && !response.isError() && response.getBookingList() != null && response.getBookingList().size() > 0) {

                                        noResultFound.setVisibility(View.GONE);
                                        progressLayout.setVisibility(View.GONE);
                                        footerLayout.setVisibility(View.GONE);
                                        bookingRecyclerView.setVisibility(View.VISIBLE);
                                        if (isFirstPage) {
                                            inProgressAdapter = null;
                                            bookingList.clear();
                                        }
                                        bookingList.addAll(response.getBookingList());
                                        totalPageCount = response.getTotal_count();
                                        totalPageCount = response.getPage_count();
                                        updateUi();

                                    } else if(response!= null){

                                        if(isFirstPage){

                                            noGeneralServiceFound.setVisibility(View.VISIBLE);
                                            noGeneralService.setVisibility(View.VISIBLE);
                                            retryButton.setVisibility(View.GONE);
                                            footerLayout.setVisibility(View.GONE);
                                            bookingRecyclerView.setVisibility(View.GONE);
                                        }else {

                                            Toast.makeText(getContext(), getString(R.string.noMoreServiceFound), Toast.LENGTH_SHORT).show();

                                        }


                                    }else {

                                        showErrorResponse();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable, String errorResponse) {
                                isService = false;
                                progressLayout.setVisibility(View.GONE);
                                footerLayout.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                showErrorResponse();


                            }
                        }

                );
            }

        }
    }

    void showErrorResponse() {


        if (isFirstPage) {
            noGeneralServiceFound.setVisibility(View.VISIBLE);
            noGeneralService.setVisibility(View.VISIBLE);
            bookingRecyclerView.setVisibility(View.GONE);
            noGeneralService.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
            footerLayout.setVisibility(View.GONE);

        }else {
            getInprogress(currentPageNumber);
        }


    }

    private void getInprogress(int pageNo) {

        currentPageNumber = pageNo;
        getInprogressList(currentPageNumber, currentPageNumber == 1);

    }


    private void updateUi() {

        if (inProgressAdapter == null) {

            inProgressAdapter = new InProgressAdapter(getActivity(), bookingList, this);
            bookingRecyclerView.setAdapter(inProgressAdapter);

        } else {

            inProgressAdapter.setBookingList(bookingList);
            inProgressAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }


    @Override
    public void onUserBookingClicked(Booking booking) {
        Intent detailIntent = new Intent(getContext(), UserBookingDetailActivity.class);
        detailIntent.putExtra(AppConstants.USER_BOOKING_DETAILS_EXTRA, booking);
        startActivityForResult(detailIntent, DETAILS_REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==DETAILS_REQ_CODE && resultCode == Activity.RESULT_OK){
            currentPageNumber = 1;
            isFirstPage = true;
            getInprogressList(currentPageNumber,true);
        }
    }

    @Override
    public void onRefresh() {
        currentPageNumber =1;
        getInprogressList(currentPageNumber,true);

    }
}
