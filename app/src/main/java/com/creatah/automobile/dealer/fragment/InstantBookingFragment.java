package com.creatah.automobile.dealer.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.creatah.automobile.dealer.activity.InstantServiceActivity;
import com.creatah.automobile.dealer.adapter.InstantServiceAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.InstantService;
import com.creatah.automobile.dealer.modal.InstantServiceListResponse;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by creatah1 on 04-05-2017.
 */

public class InstantBookingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView serviceRecyclerView;
    private LinearLayoutManager layoutManager;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private InstantServiceAdapter serviceAdapter;
    private List<InstantService> serviceList = new ArrayList<>();
    private TextView noResultFound;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.noInstanceServiceFound)
    LinearLayout noInstanceServiceFound;

    @BindView(R.id.noInstanceService)
    TextView noInstanceService;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instant_booking, container, false);
        ButterKnife.bind(this, view);
        serviceRecyclerView = (RecyclerView) view.findViewById(R.id.serviceRecyclerView);
        noResultFound = (TextView) view.findViewById(R.id.noResultFoundTextView);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
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
        getInstantList(currentPageNumber, true);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noInstanceServiceFound.setVisibility(View.GONE);
                noInstanceService.setVisibility(View.GONE);
                serviceRecyclerView.setVisibility(View.GONE);
                getInprogressService(currentPageNumber);
            }
        });

    }

    private void getInprogressService(int pageNo) {
        currentPageNumber = pageNo;
        getInstantList(currentPageNumber, currentPageNumber == 1);


    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(getContext())) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getInstantList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getInstantList(int pageNumber, boolean isFirst) {
        Activity activity = getActivity();
        if (activity != null && isAdded()) {
            if (DeviceManager.isDeviceOffline(getContext())) {
                serviceRecyclerView.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
                noInstanceServiceFound.setVisibility(View.GONE);
                noInstanceService.setVisibility(View.GONE);
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

                if (isFirst && !swipeRefreshLayout.isRefreshing()) {
                    //mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
                    progressLayout.setVisibility(View.VISIBLE);
                } else {
                     footerLayout.setVisibility(View.GONE);
                }

                ServiceManager.getInstantServiceList(getContext(), requestParams, new ServiceResponseListener<InstantServiceListResponse>() {
                            @Override
                            public void onSuccess(InstantServiceListResponse response) {
                                isService = false;
                                //mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                                progressLayout.setVisibility(View.GONE);
                                swipeRefreshLayout.setRefreshing(false);
                                Activity activity = getActivity();
                                if (activity != null && isAdded()) {
                                    if (response != null && !response.isError() && response.getServiceList() != null && response.getServiceList().size() > 0) {
                                        noResultFound.setVisibility(View.GONE);
                                        progressLayout.setVisibility(View.GONE);
                                        footerLayout.setVisibility(View.GONE);
                                        serviceRecyclerView.setVisibility(View.VISIBLE);

                                        if (isFirstPage) {
                                            serviceAdapter = null;
                                            serviceList.clear();
                                        }
                                        serviceList.addAll(response.getServiceList());
                                        totalPageCount = response.getTotal_count();
                                        totalPageCount = response.getPage_count();
                                        updateUi();
                                    } else if(response!= null){

                                        if(isFirstPage){

                                            noInstanceServiceFound.setVisibility(View.VISIBLE);
                                            noInstanceService.setVisibility(View.VISIBLE);
                                            retryButton.setVisibility(View.GONE);
                                            footerLayout.setVisibility(View.GONE);
                                            serviceRecyclerView.setVisibility(View.GONE);
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
                                swipeRefreshLayout.setRefreshing(false);
                                progressLayout.setVisibility(View.GONE);
                                footerLayout.setVisibility(View.GONE);
                                showErrorResponse();
                            }
                        }

                );
            }

        }
    }

    private void showErrorResponse() {

        if (isFirstPage) {
            noInstanceServiceFound.setVisibility(View.VISIBLE);
            noInstanceService.setVisibility(View.VISIBLE);
            serviceRecyclerView.setVisibility(View.GONE);
            noInstanceService.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
            footerLayout.setVisibility(View.GONE);

        }else {
            getInprogressService(currentPageNumber);
        }


    }

    private void updateUi() {

        if (serviceAdapter == null) {

            serviceAdapter = new InstantServiceAdapter(getActivity(), serviceList);
            serviceRecyclerView.setAdapter(serviceAdapter);

        } else {

            serviceAdapter.setServiceList(serviceList);
            serviceAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        currentPageNumber = 1;
        getInstantList(currentPageNumber, true);
    }


}
