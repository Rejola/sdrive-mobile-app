package com.sdrive.workshop.app.dev;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.DispatchAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.Customer;
import com.creatah.automobile.dealer.modal.CustomerListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DispatchDelivery extends BaseActivity implements DispatchAdapter.CustomerClickListener  {

    private RecyclerView customerRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private DispatchAdapter DispatchAdapter;
    private List<Customer> customerList = new ArrayList<>();
    private TextView noResultFound;
    private Customer customer;
    private final int CALL_REQUEST_PERMISSION = 7;

    private String customerId;

    @BindView(R.id.searchText)
    EditText searchEditText;

    @BindView(R.id.searchIcon)
    ImageView searchIcon;

    @BindView(R.id.footerLayout)
    LinearLayout footerLayout;

    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;

    @BindView(R.id.customerLayout)
    LinearLayout customerLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.noCustomerFoundLayout)
    LinearLayout noCustomerFoundLayout;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.noCustomerFound)
    TextView noCustomerFound;


    private String SEARCH_KEY = null;
    private boolean isSearched;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_delivery);
        setTitle("Dispatch List");
        ButterKnife.bind(this);
        initializeViews();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        customerRecyclerView.setLayoutManager(layoutManager);
        customerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        getCustomerList(currentPageNumber, true, SEARCH_KEY);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customerLayout.setVisibility(View.GONE);
                noCustomerFoundLayout.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                currentPageNumber = 1;
                getCustomerList(currentPageNumber, true, SEARCH_KEY);
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
                    AppUtils.hideKeyboard(DispatchDelivery.this);
                    currentPageNumber = 1;
                    getCustomerList(currentPageNumber, true, SEARCH_KEY);
                    searchIcon.setImageResource(R.drawable.ic_search);

                }

                isSearched = false;


            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isSearched) {
                    String searchText = searchEditText.getText().toString();
                    SEARCH_KEY = searchText;
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        isSearched = true;
                        searchIcon.setImageResource(R.drawable.ic_cancel_icon);
                        int position = searchEditText.length();
                        Editable etext = searchEditText.getText();
                        Selection.setSelection(etext, position);
                        AppUtils.hideKeyboard(DispatchDelivery.this);
                        searchEditText.setSelection(searchEditText.getText().length());
                        currentPageNumber = 1;
                        getCustomerList(currentPageNumber, true, SEARCH_KEY);
                        searchEditText.setSelection(searchEditText.getText().length());


                    }

                } else {

                    isSearched = false;
                    SEARCH_KEY = null;
                    currentPageNumber = 1;
                    searchEditText.setText("");
                    AppUtils.hideKeyboard(DispatchDelivery.this);
                    getCustomerList(currentPageNumber, true, SEARCH_KEY);
                    searchIcon.setImageResource(R.drawable.ic_search);
                }

            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setVisibility(View.GONE);
                noCustomerFoundLayout.setVisibility(View.GONE);
                noCustomerFound.setVisibility(View.VISIBLE);
                noCustomerFound.setText(getString(R.string.errorOccurred));
//
                getCompletedCustomer(currentPageNumber);

            }
        });


        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    String searchText = searchEditText.getText().toString();
                    if (!searchText.isEmpty() && searchText.trim().length() > 0) {
                        progressLayout.setVisibility(View.GONE);
                        isSearched = true;
                        SEARCH_KEY = searchEditText.getText().toString();
                        searchIcon.setImageResource(R.drawable.ic_cancel_icon);
                        currentPageNumber = 1;
                        getCustomerList(currentPageNumber, true, SEARCH_KEY);
                        AppUtils.hideKeyboard(DispatchDelivery.this);
                    }

                }


                return false;
            }
        });
    }

    private void getCompletedCustomer(int pageNo) {

        currentPageNumber = pageNo;
        getCustomerList(currentPageNumber, currentPageNumber == 1, SEARCH_KEY);

    }

    private void initializeViews() {
        customerRecyclerView = (RecyclerView) findViewById(R.id.customerRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getCustomerList(currentPageNumber, false, SEARCH_KEY);
                    return;
                }

            }
        }
    }

    private void getCustomerList(int pageNumber, boolean isFirst, String searchKey) {

        if (DeviceManager.isDeviceOffline(context)) {
            footerLayout.setVisibility(View.GONE);
            customerLayout.setVisibility(View.GONE);
            progressLayout.setVisibility(View.GONE);
            searchLayout.setVisibility(View.GONE);
            customerRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noCustomerFoundLayout.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("search_keyword", searchKey);

        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                noResultFound.setVisibility(View.GONE);
                customerRecyclerView.setVisibility(View.GONE);
                noCustomerFoundLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
            } else {
                footerLayout.setVisibility(View.VISIBLE);
            }

            ServiceManager.getCustomerList(context, requestParams, new ServiceResponseListener<CustomerListResponse>() {
                        @Override
                        public void onSuccess(CustomerListResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {

                                customerLayout.setVisibility(View.VISIBLE);
                                searchLayout.setVisibility(View.VISIBLE);
                                noCustomerFoundLayout.setVisibility(View.GONE);
                                retryButton.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.GONE);


                                if (response.getCustomerList() != null && response.getCustomerList().size() > 0) {
                                    noCustomerFoundLayout.setVisibility(View.GONE);
                                    searchLayout.setVisibility(View.VISIBLE);
                                    noCustomerFound.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);
                                    customerRecyclerView.setVisibility(View.VISIBLE);

                                    if (isFirstPage) {
                                        DispatchAdapter = null;
                                        customerList.clear();
                                    }
                                    customerList.addAll(response.getCustomerList());
                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    updateUi();
                                } else {
                                    if (isFirstPage) {
                                        if (isSearched) {
                                            searchLayout.setVisibility(View.VISIBLE);
                                            customerLayout.setVisibility(View.VISIBLE);
                                            customerRecyclerView.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            noCustomerFoundLayout.setVisibility(View.VISIBLE);
                                            noCustomerFound.setText(getString(R.string.noSearchResultFound));
                                        } else {
                                            searchLayout.setVisibility(View.GONE);
                                            retryButton.setVisibility(View.GONE);
                                            customerLayout.setVisibility(View.VISIBLE);
                                            customerRecyclerView.setVisibility(View.GONE);
                                            noCustomerFoundLayout.setVisibility(View.VISIBLE);
                                            noCustomerFound.setVisibility(View.VISIBLE);
                                        }

                                    } else {
                                        Toast.makeText(context, getString(R.string.noCustomersFound), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } else if (response != null) {
                                if (isFirstPage) {
                                    if (isSearched) {
                                        customerLayout.setVisibility(View.VISIBLE);
                                        searchLayout.setVisibility(View.VISIBLE);
                                        noCustomerFoundLayout.setVisibility(View.VISIBLE);
                                        customerRecyclerView.setVisibility(View.GONE);
                                        noCustomerFound.setVisibility(View.VISIBLE);
                                        retryButton.setVisibility(View.GONE);
                                        noResultFound.setVisibility(View.GONE);
                                        noCustomerFound.setText(getString(R.string.noSearchResultFound));

                                    } else {
                                        customerLayout.setVisibility(View.VISIBLE);
                                        searchLayout.setVisibility(View.GONE);
                                        noCustomerFoundLayout.setVisibility(View.VISIBLE);
                                        customerRecyclerView.setVisibility(View.GONE);
                                        noCustomerFound.setVisibility(View.VISIBLE);
                                        retryButton.setVisibility(View.GONE);
                                        noResultFound.setVisibility(View.GONE);
                                    }

                                } else {

                                    Toast.makeText(context, "No More Customers Found", Toast.LENGTH_SHORT).show();

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

    private void showErrorResponse() {

        if (isFirstPage) {
            if (isSearched) {
                searchLayout.setVisibility(View.VISIBLE);
                customerLayout.setVisibility(View.VISIBLE);
                customerRecyclerView.setVisibility(View.GONE);
                noCustomerFoundLayout.setVisibility(View.VISIBLE);

            } else {
                searchLayout.setVisibility(View.GONE);
                customerLayout.setVisibility(View.VISIBLE);
                customerRecyclerView.setVisibility(View.GONE);
                noCustomerFoundLayout.setVisibility(View.VISIBLE);
            }
            noCustomerFound.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
        } else {
            getCompletedCustomer(currentPageNumber);
        }
    }

    private void updateUi() {

        if (DispatchAdapter == null) {
            DispatchAdapter = new DispatchAdapter(DispatchDelivery.this, customerList, DispatchDelivery.this);
            customerRecyclerView.setAdapter(DispatchAdapter);

        } else {

            DispatchAdapter.setCustomerList(customerList);
            DispatchAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
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
    public void onCallCustomer(Customer customer) {
        this.customer = customer;
        onMakeCall();
    }

    @Override
    public void onViewCompletedService(Customer customer) {
        this.customer = customer;
        customerId = String.valueOf(customer.getId());
        Intent completeIntent = new Intent(context, DispatchDelivery.class);
        completeIntent.putExtra(AppConstants.COMPLETE_CUSTOMER_ID, customerId);
        startActivity(completeIntent);
    }

    public void onMakeCall() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                AppUtils.makeCall(DispatchDelivery.this, customer.getMobileNo());
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    showAlert(context, getString(R.string.callpermission), getString(R.string.callpermissiondesc), "Try", Try, "No", No);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_PERMISSION);
                }
            }
        } else {
            AppUtils.makeCall(DispatchDelivery.this, customer.getMobileNo());
        }

    }

    DialogInterface.OnClickListener Try = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(DispatchDelivery.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_REQUEST_PERMISSION);
        }
    };


    DialogInterface.OnClickListener No = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_REQUEST_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    AppUtils.makeCall(DispatchDelivery.this, customer.getMobileNo());

                }
                break;


        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
