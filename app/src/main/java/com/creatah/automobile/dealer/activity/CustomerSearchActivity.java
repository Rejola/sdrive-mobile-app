package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.CustomerSearchResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

/**
 * Created by creatah1 on 13-05-2017.
 */

public class CustomerSearchActivity extends BaseActivity {

    private ImageView noResultFound;
    private boolean isService = false;
    private EditText searchEditText;
    private ImageView searchImageView;
    private TextView noResultFoundTV;
    private Button addButton;
    private String SEARCH_KEY = null;
    private boolean isSearched = false;
    private Context context;
    private int BOOK_REQ_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_search);
        setTitle(getString(R.string.searchCustomer));
        initializeView();
        context = this;
        searchEditText.setSelection(searchEditText.length());
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSearched) {
                    getCustomerName();
                } else {
                    SEARCH_KEY = null;
                    isSearched = false;
                    searchImageView.setImageDrawable(getResources().getDrawable(R.drawable.search_icon));
                    searchEditText.setText("");
                    //getDealerDetails();
                    noResultFoundTV.setVisibility(View.GONE);
                    noResultFound.setVisibility(View.VISIBLE);
                }
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getCustomerName();
                    return true;
                }
                return false;
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(context, AddBookingActivity.class);
                startActivityForResult(addIntent, BOOK_REQ_CODE);
            }
        });

    }


    private void getCustomerName() {

        SEARCH_KEY = searchEditText.getText().toString();
        if (!TextUtils.isEmpty(SEARCH_KEY)) {
            isSearched = true;
            searchImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
            getCustomerDetails();

        }
        AppUtils.hideKeyboard(CustomerSearchActivity.this);
    }

    private void getCustomerDetails() {

        if (DeviceManager.isDeviceOffline(context)) {
            noResultFound.setVisibility(View.GONE);
            noResultFoundTV.setText(getString(R.string.title_of_data_message));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile_no", SEARCH_KEY);
        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            requestParams.put("auth_token", String.valueOf(AppUtils.getWorkshopInfo(getApplication()).getAuthToken()));
        }
        noResultFoundTV.setVisibility(View.GONE);
        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.SHOW_PROGRESS_DIALOG);

            ServiceManager.getCustomerDetails(context, requestParams, new ServiceResponseListener<CustomerSearchResponse>() {
                        @Override
                        public void onSuccess(CustomerSearchResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                Intent addIntent = new Intent(context, AddBookingActivity.class);
                                addIntent.putExtra(AppConstants.CUSTOMER_DETAILS_EXTRA, response);
                                startActivityForResult(addIntent, BOOK_REQ_CODE);

                            } else if (response != null && response.isError()) {
                                String message = response.getMessage();
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFoundTV.setVisibility(View.VISIBLE);
                                noResultFoundTV.setText(message);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } else {
                                noResultFound.setVisibility(View.GONE);
                                noResultFoundTV.setVisibility(View.VISIBLE);
                                noResultFoundTV.setText(getString(R.string.internet_failed_toast));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);
                            noResultFound.setVisibility(View.GONE);
                            noResultFoundTV.setVisibility(View.VISIBLE);
                            noResultFoundTV.setText(getString(R.string.title_of_data_message));

                        }
                    }

            );
        }

    }

    private void initializeView() {
        noResultFound = (ImageView) findViewById(R.id.noResultFound);
        searchEditText = (EditText) findViewById(R.id.searchText);
        searchImageView = (ImageView) findViewById(R.id.searchIcon);
        noResultFoundTV = (TextView) findViewById(R.id.noResultFoundTV);
        addButton = (Button) findViewById(R.id.addButton);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BOOK_REQ_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
