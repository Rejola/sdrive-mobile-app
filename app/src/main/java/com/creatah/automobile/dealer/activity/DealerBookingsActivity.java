package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.DealerBookingAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DealerBookingListResponse;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 11-04-2017.
 */

public class DealerBookingsActivity extends BaseActivity {

    private RecyclerView productRecyclerView;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.searchIcon)
    AppCompatImageView searchImageView;

    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;

    @BindView(R.id.adviserSpinner)
    Spinner adviserSpinner;

    @BindView(R.id.bottom_sheet)
    FrameLayout frameLayout;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;

    @BindView(R.id.serviceInprogressLayout)
    LinearLayout serviceInprogressLayout;

    @BindView(R.id.noJObCardFound)
    LinearLayout noJObCardFound;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    @BindView(R.id.noInternetConnection)
    TextView noInternetConnection;

    private BottomSheetBehavior mBottomSheetBehavior;

    List<String> statusList = new ArrayList<>();

    private List<Employee> employeeList;
    List<String> employeeStrList = new ArrayList<>();

    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private DealerBookingAdapter inProgressAdapter;
    private List<DealerBooking> bookingList = new ArrayList<>();
    private TextView noResultFound;
    private String STATUS_KEY = null;
    private String PAGE_TYPE = null;
    private String SEARCH_KEY = null;
    public static int STATUS_UPDATE_REQ_CODE = 954;

    private boolean isSearched;

    private String EMPLOYEE_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_bookings);
        ButterKnife.bind(this);
//        setTitle(getString(R.string.inProgressTitle));
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



              /*  if(layoutManager.findFirstCompletelyVisibleItemPosition()==0){
                    // Its at top
                }*/

                if (layoutManager.findLastCompletelyVisibleItemPosition() == bookingList.size() - 1) {
                    // Its at bottom
                    loadNextListProduct(totalItemCount, lastVisiblePosition);
                }

            }
        });


//        setDefaultFilter();


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SEARCH_KEY = searchEditText.getText().toString();

                if (!SEARCH_KEY.isEmpty()) {
                    isSearched = true;
                    searchImageView.setImageResource(R.drawable.ic_search);
                } else {

                    isSearched = false;
                    SEARCH_KEY = null;
                    AppUtils.hideKeyboard(DealerBookingsActivity.this);
                    currentPageNumber = 1;
                    searchImageView.setImageResource(R.drawable.ic_search);
                    getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
                }

                isSearched = false;


            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPageNumber = 1;
                getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSearched) {
                    String searchText = searchEditText.getText().toString();
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        SEARCH_KEY = searchText;
                        isSearched = true;
                        AppUtils.hideKeyboard(DealerBookingsActivity.this);
                        searchImageView.setImageResource(R.drawable.ic_cancel_icon);
                        currentPageNumber = 1;
                        getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);

                    }

                } else {
                    isSearched = false;
                    SEARCH_KEY = null;
                    AppUtils.hideKeyboard(DealerBookingsActivity.this);
                    currentPageNumber = 1;
                    getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
                    searchImageView.setImageResource(R.drawable.ic_search);
                    searchEditText.setText("");
                }
            }
        });


        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = searchEditText.getText().toString();
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        isSearched = true;
                        SEARCH_KEY = searchEditText.getText().toString();
                        AppUtils.hideKeyboard(DealerBookingsActivity.this);
                        searchImageView.setImageResource(R.drawable.ic_cancel_icon);
                        currentPageNumber = 1;
                        getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);

                    }
                }
                return false;
            }
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
        mBottomSheetBehavior.setPeekHeight(10);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setHideable(true);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {

                } else if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBottomSheetBehavior.setPeekHeight(1000);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        AppInfo appInfo = AppUtils.getAppInfo(getApplication());
        if (appInfo != null && appInfo.getEmployeeList() != null) {
            employeeList = appInfo.getEmployeeList();
            employeeStrList.add(getString(R.string.selectAdvisor));
            for (Employee employee : employeeList) {
                if (employee.getDesignation().equals(getString(R.string.serviceAdvisor))) {
                    employeeStrList.add(employee.getEmpName());
                }
            }
        }

        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, employeeStrList);
        adviserSpinner.setAdapter(employeeAdapter);
        adviserSpinner.setOnItemSelectedListener(adviserListener);


       /* statusList = Arrays.asList(getResources().getStringArray(R.array.statusArray));
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, statusList);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setSelection(4);
*/

        getBundle();

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
                noResultFound.setText(getString(R.string.errorOccurred));
//                progressLayout.setVisibility(View.VISIBLE);
                getDealerBookings(currentPageNumber);

            }
        });

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(AppConstants.STATUSTYPE)) {
            STATUS_KEY = bundle.getString(AppConstants.STATUSTYPE);
            PAGE_TYPE = STATUS_KEY;
            setPageTypeDefault();
            getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
        }

    }

    /*private void setDefaultFilter() {
        STATUS_KEY = getString(R.string.checkInCompletedKey) + "," + getString(R.string.inspectionCompletedKey) + "," + getString(R.string.serviceUnderProgressKey) + ","
                + getString(R.string.vehicleReadyKey) + "," + getString(R.string.queuedVehicleKey);
    }*/

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
                    getDealerBookingsList(currentPageNumber, false, STATUS_KEY, SEARCH_KEY);
                    return;
                }

            }
        }
    }

    private void getDealerBookingsList(int pageNumber, boolean isFirst, String filterKey, String searchKey) {

        if (DeviceManager.isDeviceOffline(context)) {
            serviceInprogressLayout.setVisibility(View.VISIBLE);
            productRecyclerView.setVisibility(View.GONE);
            noJObCardFound.setVisibility(View.GONE);
            searchLayout.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.GONE);

            noInternetConnection.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("status", filterKey);
        requestParams.put("service_advisor_id", EMPLOYEE_ID);
        requestParams.put("search_keyword", searchKey);
        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                noInternetConnection.setVisibility(View.GONE);
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                serviceInprogressLayout.setVisibility(View.VISIBLE);
                progressLayout.setVisibility(View.VISIBLE);

            } else {
                footerLayout.setVisibility(View.VISIBLE);
            }

            ServiceManager.getWorkShopJobCardList(context, requestParams, new ServiceResponseListener<DealerBookingListResponse>() {
                        @Override
                        public void onSuccess(DealerBookingListResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {


                                if (response.getBookingList() != null && response.getBookingList().size() > 0) {
                                    retryButton.setVisibility(View.GONE);
                                    noInternetConnection.setVisibility(View.GONE);
                                    searchLayout.setVisibility(View.VISIBLE);
                                    serviceInprogressLayout.setVisibility(View.VISIBLE);
                                    noJObCardFound.setVisibility(View.GONE);
                                    productRecyclerView.setVisibility(View.VISIBLE);

                                    if (isFirstPage) {
                                        inProgressAdapter = null;
                                        bookingList.clear();
                                    }
                                    bookingList.addAll(response.getBookingList());
                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    updateUi();
                                } else {
                                    if (isFirstPage) {
                                        if (isSearched) {
                                            searchLayout.setVisibility(View.VISIBLE);
                                            serviceInprogressLayout.setVisibility(View.VISIBLE);
                                            productRecyclerView.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            noJObCardFound.setVisibility(View.VISIBLE);
                                            noResultFound.setText(getString(R.string.noSearchResultFound));
                                        } else {
                                            searchLayout.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            serviceInprogressLayout.setVisibility(View.VISIBLE);
                                            productRecyclerView.setVisibility(View.GONE);
                                            noJObCardFound.setVisibility(View.VISIBLE);
                                        }

                                    } else {
                                        Toast.makeText(context, getString(R.string.noJobCardsFound), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else if (response != null) {
                                if (isFirstPage) {
                                    if (isSearched) {
                                        searchLayout.setVisibility(View.VISIBLE);
                                        serviceInprogressLayout.setVisibility(View.VISIBLE);
                                        productRecyclerView.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        noResultFound.setVisibility(View.VISIBLE);
                                        noJObCardFound.setVisibility(View.VISIBLE);
                                        noResultFound.setText(getString(R.string.noSearchResultFound));
                                    } else {
                                        searchLayout.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        serviceInprogressLayout.setVisibility(View.VISIBLE);
                                        productRecyclerView.setVisibility(View.GONE);
                                        noJObCardFound.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Toast.makeText(context, getString(R.string.noJobCardsFound), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                showErrorResponse();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            footerLayout.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            showErrorResponse();
                        }
                    }

            );
        }

    }

    private void updateUi() {

        if (inProgressAdapter == null) {

            inProgressAdapter = new DealerBookingAdapter(DealerBookingsActivity.this, bookingList, false);
            productRecyclerView.setAdapter(inProgressAdapter);

        } else {

            inProgressAdapter.setBookingList(bookingList);
            inProgressAdapter.notifyDataSetChanged();

        }
        currentPageNumber++;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dealer_bookings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.showFilter:
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == STATUS_UPDATE_REQ_CODE && resultCode == RESULT_OK) {
            getDealerBookings(1);
        }
    }

    public Spinner.OnItemSelectedListener adviserListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                EMPLOYEE_ID = String.valueOf(employeeList.get(position - 1).getEmpId());

            } else {
                EMPLOYEE_ID = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.applyFilter)
    public void onApplyFilter() {
//        String selectedOption = statusSpinner.getSelectedItem().toString();

       /* if (selectedOption.equals(getString(R.string.ALL))) {
            setDefaultFilter();
        } else if (selectedOption.equals(getString(R.string.vehicleDelivered))) {
            STATUS_KEY = getString(R.string.vehicleReadyKey);
        } else if (selectedOption.equals(getString(R.string.serviceUnderProgress))) {
            STATUS_KEY = getString(R.string.serviceUnderProgressKey);
        } else if (selectedOption.equals(getString(R.string.inspectionCompleted))) {
            STATUS_KEY = getString(R.string.inspectionCompletedKey);
        } else if (selectedOption.equals(getString(R.string.checkInCompleted))) {
            STATUS_KEY = getString(R.string.checkInCompletedKey);
        } else if (selectedOption.equals(getString(R.string.queuedService))) {
            STATUS_KEY = getString(R.string.queuedVehicleKey);
        }
        if (selectedOption.equals(getString(R.string.vehicleReady))) {
            STATUS_KEY = getString(R.string.vehicleReadyKey);
        }*/

        if (EMPLOYEE_ID == null) {
            Toast.makeText(context, "Please Select Advisor", Toast.LENGTH_SHORT).show();
        } else {

            isFirstPage = true;
            currentPageNumber = 1;
            getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

    }


    @OnClick(R.id.clearButton)
    public void onClearFilter() {
//        setPageTypeDefault();

        if (EMPLOYEE_ID != null) {
            EMPLOYEE_ID = "";
            ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, employeeStrList);
            adviserSpinner.setAdapter(employeeAdapter);
            adviserSpinner.setOnItemSelectedListener(adviserListener);
           /* currentPageNumber = 1;
            isFirstPage = true;
            getDealerBookingsList(currentPageNumber, true, STATUS_KEY, SEARCH_KEY);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/
        } else {
            Toast.makeText(context, "Advisor Not Yet Selected", Toast.LENGTH_SHORT).show();
        }

    }


    private void setPageTypeDefault() {

        if (PAGE_TYPE.equals(AppConstants.QUEUEDFORSERVICE)) {
            setTitle(getString(R.string.open));
//            statusSpinner.setSelection(0);
            STATUS_KEY = getString(R.string.queuedVehicleKey);
        } else if (PAGE_TYPE.equals(AppConstants.SERVICE_UNDER_PROGRESS)) {
            setTitle(getString(R.string.inProgressTitle));
//            statusSpinner.setSelection(2);
            STATUS_KEY = getString(R.string.serviceUnderProgressKey);
        } else if (PAGE_TYPE.equals(AppConstants.CHECKINCOMPLETED)) {
            setTitle(getString(R.string.checkinAndEstimate));
//            statusSpinner.setSelection(1);
            STATUS_KEY = getString(R.string.checkInCompletedKey);
        } else if (PAGE_TYPE.equals(AppConstants.VEHICLE_READY)) {
            setTitle(getString(R.string.vehicleReady));
//            statusSpinner.setSelection(3);
            STATUS_KEY = getString(R.string.vehicleReadyKey);
        }/*else if (PAGE_TYPE.equals(AppConstants.SERVICE_COMPLETED)) {
            setTitle(getString(R.string.vehicleDelivered));
            statusSpinner.setSelection(3);
            STATUS_KEY = getString(R.string.vehicleDeliveredKey);
        }*/ else {
//            setDefaultFilter();
//            statusSpinner.setSelection(5);
            adviserSpinner.setSelection(0);
        }

    }

    void getDealerBookings(int pageNo) {

        currentPageNumber = pageNo;
        getDealerBookingsList(currentPageNumber, currentPageNumber == 1, STATUS_KEY, SEARCH_KEY);

    }

    void showErrorResponse() {
        if (isFirstPage) {
            if (isSearched) {

                searchLayout.setVisibility(View.VISIBLE);
                serviceInprogressLayout.setVisibility(View.VISIBLE);
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.VISIBLE);

            } else {
                searchLayout.setVisibility(View.GONE);
                serviceInprogressLayout.setVisibility(View.VISIBLE);
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.VISIBLE);
            }
            noResultFound.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
        } else {
            //Toast.makeText(context, getString(R.string.noJobCardsFound), Toast.LENGTH_SHORT).show();
            showSnakeBar("Some error occurred. Please try again", "Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDealerBookings(currentPageNumber);

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }
    }


}
