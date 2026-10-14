package com.creatah.automobile.dealer.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.DealerBookingAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DealerBookingListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class CompleteBookingActivity extends BaseActivity {

    private RecyclerView productRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private DealerBookingAdapter completedAdapter;
    private List<DealerBooking> bookingList = new ArrayList<>();
    private TextView noResultFound;
    private String SEARCH_KEY = null;
    private boolean isSearched;

    private String todate;
    private String newDate;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.searchIcon)
    AppCompatImageView searchIcon;

    @BindView(R.id.bottom_sheet)
    FrameLayout bottomSheet;

    @BindView(R.id.fromDateTextView)
    TextView fromDateTextView;

    @BindView(R.id.toDateTextView)
    TextView toDateTextView;

    @BindView(R.id.applyFilter)
    Button applyFilter;

    @BindView(R.id.clearButton)
    Button clearButton;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;

    @BindView(R.id.serviceLayout)
    LinearLayout serviceLayout;

    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;

    @BindView(R.id.noJObCardFound)
    LinearLayout noJObCardFound;

    @BindView(R.id.completedServiceLayout)
    LinearLayout completedServiceLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.noJobCardText)
    TextView noJobCardText;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    private String customerId;
    private DatePickerDialog fromDatePicker;
    private DatePickerDialog toDatePicker;
    private BottomSheetBehavior mBottomSheetBehavior;
    private int year, month, day;
    private String selectFromDate, selectToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compelted_list);
        setTitle(getString(R.string.completedTitle));
        initializeViews();
        ButterKnife.bind(this);
        AppUtils.hideKeyboard(CompleteBookingActivity.this);
        getBundle();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
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
        getCompletedList(currentPageNumber, true, SEARCH_KEY);

        fromDatePicker = new DatePickerDialog(this, fromDatePickListener, year, month, day);
        toDatePicker = new DatePickerDialog(this, toDatePickListener, year, month, day);

        fromDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() + 10000);
        toDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() + 10000);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSearched) {
                    String searchText = searchEditText.getText().toString();
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        SEARCH_KEY = searchText;
                        isSearched = true;
                        AppUtils.hideKeyboard(CompleteBookingActivity.this);
                        searchIcon.setImageResource(R.drawable.ic_cancel_icon);
                        currentPageNumber = 1;
                        getCompletedList(currentPageNumber, true, SEARCH_KEY);

                    }

                } else {
                    isSearched = false;
                    SEARCH_KEY = null;
                    AppUtils.hideKeyboard(CompleteBookingActivity.this);
                    currentPageNumber = 1;
                    getCompletedList(currentPageNumber, true, SEARCH_KEY);
                    searchIcon.setImageResource(R.drawable.ic_search);
                    searchEditText.setText("");

                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPageNumber = 1;
                getCompletedList(currentPageNumber, true, SEARCH_KEY);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });
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
                    searchIcon.setImageResource(R.drawable.ic_search);
                } else {

                    isSearched = false;
                    SEARCH_KEY = null;
                    AppUtils.hideKeyboard(CompleteBookingActivity.this);
                    currentPageNumber = 1;
                    searchIcon.setImageResource(R.drawable.ic_search);
                    getCompletedList(currentPageNumber, true, SEARCH_KEY);
                }

                isSearched = false;

            }
        });
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = searchEditText.getText().toString();
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        isSearched = true;
                        AppUtils.hideKeyboard(CompleteBookingActivity.this);
                        SEARCH_KEY = searchEditText.getText().toString();
                        searchIcon.setImageResource(R.drawable.ic_cancel_icon);
                        currentPageNumber = 1;
                        getCompletedList(currentPageNumber, true, SEARCH_KEY);

                    }
                }
                return false;
            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
                noResultFound.setText(getString(R.string.errorOccurred));
                getCompletedServices(currentPageNumber);
            }
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
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
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.COMPLETE_CUSTOMER_ID)) {
            customerId = bundle.getString(AppConstants.COMPLETE_CUSTOMER_ID);
            searchEditText.setHint("Vehicle No / Make / Model");
        }

    }

    @OnClick(R.id.fromDateTextView)
    public void onSelectFromDate() {
        fromDatePicker.show();
    }

    @OnClick(R.id.toDateTextView)
    public void onSelectToDate() {

        toDatePicker.show();
    }

    private DatePickerDialog.OnDateSetListener fromDatePickListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            String day = String.valueOf(selectedDay);
            String months = String.valueOf(selectedMonth + 1);

            if (day.length() == 1) {
                day = "0" + day;
            }
            if (months.length() == 1) {
                months = "0" + months;
            }

            selectFromDate = selectedYear + "-" + months + "-" + day;
            newDate = day + "/" + months + "/" + selectedYear;

            java.util.Date sdate = AppUtils.getDateFormat(selectFromDate);

            if (selectToDate != null) {
                java.util.Date edate = AppUtils.getDateFormat(selectToDate);

                if (sdate.before(edate) || sdate.equals(edate)) {
                    fromDateTextView.setText(newDate);
                } else {
                    selectFromDate = null;
                    Toast.makeText(context, "from date cannot be greater than to date ", Toast.LENGTH_SHORT).show();
                }

            } else {
                fromDateTextView.setText(newDate);
            }


        }

    };


    private DatePickerDialog.OnDateSetListener toDatePickListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            String dates = String.valueOf(selectedDay);
            String months = String.valueOf(selectedMonth + 1);
            if (dates.length() == 1) {
                dates = "0" + dates;
            }
            if (months.length() == 1) {
                months = "0" + months;
            }

            selectToDate = selectedYear + "-" + months + "-" + dates;
            todate = dates + "/" + months + "/" + selectedYear;

            java.util.Date edate = AppUtils.getDateFormat(selectToDate);

            if (selectFromDate != null) {
                java.util.Date sdate = AppUtils.getDateFormat(selectFromDate);

                if (edate.after(sdate) || edate.equals(sdate)) {
                    todate = dates + "/" + months + "/" + year;
                    toDateTextView.setText(todate);
                } else {
                    selectToDate = null;
//                toDateTextView.setText(todate);
                    Toast.makeText(context, "to date cannot be less than from date", Toast.LENGTH_SHORT).show();
                }

            } else {
                toDateTextView.setText(todate);
            }

        }


    };


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

    private void getCompletedList(int pageNumber, final boolean isFirst, final String searchKey) {

        if (DeviceManager.isDeviceOffline(context)) {
            productRecyclerView.setVisibility(View.GONE);
            noJObCardFound.setVisibility(View.GONE);
            searchLayout.setVisibility(View.GONE);
            completedServiceLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noJobCardText.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("customer_id", customerId);
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("search_keyword", searchKey);
        requestParams.put("status", getString(R.string.vehicleDeliveredKey));
        requestParams.put("from_date", selectFromDate);
        requestParams.put("to_date", selectToDate);
        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                noResultFound.setVisibility(View.GONE);
                noJobCardText.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);

            } else {
                footerLayout.setVisibility(View.VISIBLE);
            }

            ServiceManager.getWorkShopJobCardList(context, requestParams, new ServiceResponseListener<DealerBookingListResponse>() {
                        @Override
                        public void onSuccess(DealerBookingListResponse response) {
                            isService = false;
                            footerLayout.setVisibility(View.GONE);
                            progressLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {
                                if (response.getBookingList() != null && response.getBookingList().size() > 0) {
                                    noJObCardFound.setVisibility(View.GONE);
                                    completedServiceLayout.setVisibility(View.VISIBLE);
                                    searchLayout.setVisibility(View.VISIBLE);
                                    noResultFound.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);
                                    productRecyclerView.setVisibility(View.VISIBLE);
                                    serviceLayout.setVisibility(View.VISIBLE);

                                    if (isFirstPage) {
                                        completedAdapter = null;
                                        bookingList.clear();
                                    }
                                    bookingList.addAll(response.getBookingList());
                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    setTitle("Service Completed (" + String.valueOf(response.getTotal_count()) + ")");
                                    updateUi();
                                } else {
                                    if (isFirst) {
                                        if (isSearched) {
                                            searchLayout.setVisibility(View.VISIBLE);
                                            serviceLayout.setVisibility(View.VISIBLE);
                                            productRecyclerView.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            noJObCardFound.setVisibility(View.VISIBLE);
                                            noJobCardText.setText(getString(R.string.noSearchResultFound));
//                                        noResultFound.setText(getString(R.string.no_service_found));
                                            setTitle("Service Completed");
                                        } else {
                                            searchLayout.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            serviceLayout.setVisibility(View.VISIBLE);
                                            productRecyclerView.setVisibility(View.GONE);
                                            noJObCardFound.setVisibility(View.VISIBLE);
                                            noJobCardText.setVisibility(View.VISIBLE);

                                        }
                                    } else {
                                        Toast.makeText(context, getString(R.string.noMoreServiceFound), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else if (response != null) {
                                if (isFirst) {
                                    if (isSearched) {
                                        searchLayout.setVisibility(View.VISIBLE);
                                        serviceLayout.setVisibility(View.VISIBLE);
                                        productRecyclerView.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        noJobCardText.setVisibility(View.VISIBLE);
                                        noJObCardFound.setVisibility(View.VISIBLE);
                                        noJobCardText.setText(getString(R.string.noSearchResultFound));
                                        setTitle("Service Completed");
                                    } else {

                                        searchLayout.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        serviceLayout.setVisibility(View.VISIBLE);
                                        productRecyclerView.setVisibility(View.GONE);
                                        noJObCardFound.setVisibility(View.VISIBLE);
                                        noJobCardText.setVisibility(View.VISIBLE);
                                    }

                                } else {
                                    Toast.makeText(context, getString(R.string.noJobCardsFound), Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
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

    private void showErrorResponse() {


        if (isFirstPage) {
            if (isSearched) {
                searchLayout.setVisibility(View.VISIBLE);
                completedServiceLayout.setVisibility(View.VISIBLE);
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.VISIBLE);
            } else {
                searchLayout.setVisibility(View.GONE);
                completedServiceLayout.setVisibility(View.VISIBLE);
                productRecyclerView.setVisibility(View.GONE);
                noJObCardFound.setVisibility(View.VISIBLE);
            }
             noJobCardText.setVisibility(View.VISIBLE);
            noJobCardText.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
        } else {
            //Toast.makeText(context, getString(R.string.noJobCardsFound), Toast.LENGTH_SHORT).show();
            showSnakeBar("Some error occurred. Please try again", "Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCompletedServices(currentPageNumber);
                }
            });
        }
    }

     void getCompletedServices(int pageNo) {


         currentPageNumber = pageNo;
            getCompletedList(currentPageNumber, currentPageNumber == 1, SEARCH_KEY);

        }

    private void updateUi() {

        if (completedAdapter == null) {

            completedAdapter = new DealerBookingAdapter(CompleteBookingActivity.this, bookingList, true);
            productRecyclerView.setAdapter(completedAdapter);

        } else {

            completedAdapter.setBookingList(bookingList);
            completedAdapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dealer_bookings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
////                finish();
//                break;
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

    @OnClick(R.id.applyFilter)
    public void onApplyFilter() {
        validateData();

    }

    private void validateData() {

        if (TextUtils.isEmpty(selectFromDate)) {
            Toast.makeText(CompleteBookingActivity.this, "Select From date ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(selectToDate)) {
            Toast.makeText(CompleteBookingActivity.this, "Select To date ", Toast.LENGTH_SHORT).show();
        } else {

            isFirstPage = true;
            currentPageNumber = 1;
            getCompletedList(currentPageNumber, true, SEARCH_KEY);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        }
    }

    @OnClick(R.id.clearButton)
    public void onClearFilter() {

        fromDateTextView.setText("");
        toDateTextView.setText("");
        /*currentPageNumber = 1;
        isFirstPage = true;
        getCompletedList(currentPageNumber, true, SEARCH_KEY);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
