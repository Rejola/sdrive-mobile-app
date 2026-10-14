package com.creatah.automobile.dealer.fragment;

import android.app.Activity;
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
import com.creatah.automobile.dealer.adapter.EnquiryAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CustomerEnquiry;
import com.creatah.automobile.dealer.modal.CustomerEnquiryListResponse;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by creatah1 on 18-05-2017.
 */

public class UserEnquiryFragment extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView enquiryRecyclerView;
    private LinearLayoutManager layoutManager;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private EnquiryAdapter enquiryAdapter;
    private List<CustomerEnquiry> enquiryList = new ArrayList<>();
    private TextView noResultFound;
    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;

    @BindView(R.id.noEnquiries)
    TextView noEnquiries;

    @BindView(R.id.noEnquiryFound)
    LinearLayout noEnquiryFound;



   @BindView(R.id.retryButton)
    Button retryButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_enquiry, container, false);
        ButterKnife.bind(this,view);
        enquiryRecyclerView = (RecyclerView) view.findViewById(R.id.enquiryRecyclerView);
        noResultFound = (TextView) view.findViewById(R.id.noResultFoundTextView);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        layoutManager = new LinearLayoutManager(getContext());
        enquiryRecyclerView.setLayoutManager(layoutManager);
        //enquiryRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        enquiryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        getEnquiryList(currentPageNumber, true);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noEnquiryFound.setVisibility(View.GONE);
                noEnquiries.setVisibility(View.GONE);
                enquiryRecyclerView.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                currentPageNumber=1;
                getEnquiry(currentPageNumber);
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noEnquiryFound.setVisibility(View.GONE);
                noEnquiries.setVisibility(View.GONE);
                enquiryRecyclerView.setVisibility(View.GONE);
                getEnquiry(currentPageNumber);
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
                    getEnquiryList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getEnquiryList(int pageNumber, boolean isFirst) {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (DeviceManager.isDeviceOffline(getContext())) {
                enquiryRecyclerView.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
                noEnquiryFound.setVisibility(View.GONE);
                noEnquiries.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);

                /*noResultFound.setText(getString(R.string.title_of_data_message));*/
                return;
            }

            RequestParams requestParams = new RequestParams();
            requestParams.put("page_no", String.valueOf(pageNumber));
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getActivity().getApplication()).getAuthToken());

            isFirstPage = isFirst;

            if (!isService) {

                isService = true;

                if (isFirst) {
                    progressLayout.setVisibility(View.VISIBLE);
                } else {
                     footerLayout.setVisibility(View.GONE);
                }

                ServiceManager.getEnquiryList(getActivity(), requestParams, new ServiceResponseListener<CustomerEnquiryListResponse>() {
                            @Override
                            public void onSuccess(CustomerEnquiryListResponse response) {
                                isService = false;
                                progressLayout.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                if (response != null && !response.isError() && response.getEnquiryList() != null && response.getEnquiryList().size() > 0) {
                                    Activity activity = getActivity();
                                    if (activity != null && isAdded()) {
                                        noResultFound.setVisibility(View.GONE);
                                        progressLayout.setVisibility(View.GONE);
                                        footerLayout.setVisibility(View.GONE);
                                        enquiryRecyclerView.setVisibility(View.VISIBLE);
                                        if (isFirstPage) {
                                            enquiryAdapter = null;
                                            enquiryList.clear();
                                        }
                                        enquiryList.addAll(response.getEnquiryList());
                                        totalPageCount = response.getTotal_count();
                                        totalPageCount = response.getPage_count();
                                        updateUi();
                                    }
                                } /*else {
                                    Activity activity = getActivity();
                                    if (activity != null && isAdded()) {

                                        noEnquiryFound.setVisibility(View.VISIBLE);
                                        noEnquiries.setVisibility(View.VISIBLE);
                                        progressLayout.setVisibility(View.GONE);
                                        enquiryRecyclerView.setVisibility(View.GONE);

                                        *//*noResultFound.setText(getString(R.string.no_results_found));*//*
                                    }*/
                                    else if(response!= null){

                                        if(isFirstPage){

                                            noEnquiryFound.setVisibility(View.VISIBLE);
                                            noEnquiries.setVisibility(View.VISIBLE);
                                            retryButton.setVisibility(View.GONE);
                                            footerLayout.setVisibility(View.GONE);
                                            enquiryRecyclerView.setVisibility(View.GONE);
                                        }else {

                                            Toast.makeText(getContext(), getString(R.string.noMoreServiceFound), Toast.LENGTH_SHORT).show();

                                        }


                                    }else {

                                        showErrorResponse();
                                    }
                                }


                            @Override
                            public void onFailure(Throwable throwable, String errorResponse) {
                                isService = false;
                               /* Activity activity = getActivity();
                                if (activity != null && isAdded()) {*/
                                    progressLayout.setVisibility(View.GONE);
                                    footerLayout.setVisibility(View.GONE);
                                    swipeRefreshLayout.setRefreshing(false);
                                    showErrorResponse();
//                                }

                            }
                        }

                );
            }
        }

    }

    private void showErrorResponse() {

        if (isFirstPage) {
            noEnquiryFound.setVisibility(View.VISIBLE);
            noEnquiries.setVisibility(View.VISIBLE);
            enquiryRecyclerView.setVisibility(View.GONE);
            noEnquiries.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
            footerLayout.setVisibility(View.GONE);

        }else {
            getEnquiry(currentPageNumber);
        }

    }

    private void getEnquiry(int pageNo) {
        currentPageNumber = pageNo;
        getEnquiryList(currentPageNumber, currentPageNumber == 1);


    }

    private void updateUi() {

        if (enquiryAdapter == null) {

            enquiryAdapter = new EnquiryAdapter(getActivity(), enquiryList);
            enquiryRecyclerView.setAdapter(enquiryAdapter);

        } else {

            enquiryAdapter.setEnquiryList(enquiryList);
            enquiryAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }

    @Override
    public void onRefresh() {
        currentPageNumber =1;
        getEnquiryList(currentPageNumber, true);
    }
}
