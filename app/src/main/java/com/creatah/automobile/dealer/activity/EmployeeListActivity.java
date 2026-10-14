package com.creatah.automobile.dealer.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.EmployeeAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.DeleteResponse;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.modal.EmployeeListResponse;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class EmployeeListActivity extends BaseActivity implements EmployeeAdapter.EmployeeListener {

    private RecyclerView productRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList = new ArrayList<>();
    private TextView noResultFound;
    private ImageView addEmployeeButton;
    public static final int ADD_EMPLOYEE_REQUEST = 456;
    public static int EDIT_REQ_CODE = 789;
    private String employeeId;
    private String empMobileNumber;
    private final int CALL_REQUEST_PERMISSION = 7;

    private LinearLayout employeeLayout;
    private LinearLayout footerLayout;

    private LinearLayout noEmployeesFound;
    private LinearLayout progressLayout;
    private TextView noEmployee;
    private ImageView addEmployee;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Button retryButton;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        setTitle(getString(R.string.EmployeeTitle));
        initializeViews();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        getProductList(currentPageNumber, true);
        addEmployeeButton.setVisibility(View.VISIBLE);

        productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                    addEmployeeButton.setVisibility(View.GONE);
                else if (dy < 0)
                    addEmployeeButton.setVisibility(View.VISIBLE);


                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = visibleItemCount + firstVisibleItemPosition;

                loadNextListProduct(totalItemCount, lastVisiblePosition);

            }
        });

        addEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(context, AddEmployeeActivity.class), ADD_EMPLOYEE_REQUEST);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPageNumber=1;
                getProductList(currentPageNumber, true);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setVisibility(View.GONE);
                noEmployeesFound.setVisibility(View.GONE);
                noEmployee.setVisibility(View.GONE);
                noEmployee.setText(getString(R.string.errorOccurred));
                getEmployeeList(currentPageNumber);

            }
        });
    }

    private void initializeViews() {
        productRecyclerView = (RecyclerView) findViewById(R.id.productRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        addEmployeeButton = (ImageView) findViewById(R.id.addEmployee);
        employeeLayout = findViewById(R.id.employeeLayout);
        footerLayout = findViewById(R.id.footerLayout);
        noEmployeesFound=(LinearLayout)findViewById(R.id.noEmployeesFound);
        noEmployee=(TextView) findViewById(R.id.noEmployee);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        retryButton=(Button) findViewById(R.id.retryButton);
        addEmployee=(ImageView) findViewById(R.id.addEmployee);
        progressLayout=(LinearLayout)findViewById(R.id.progressLayout);


    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getProductList(currentPageNumber, false);
                    return;
                }

            }
        }
    }

    private void getProductList(int pageNumber, boolean isFirst) {

        if (DeviceManager.isDeviceOffline(context)) {
            footerLayout.setVisibility(View.GONE);
            productRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                noResultFound.setVisibility(View.GONE);
                productRecyclerView.setVisibility(View.GONE);
                noEmployeesFound.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
            } else {
                footerLayout.setVisibility(View.VISIBLE);
            }

            ServiceManager.getEmployeeList(context, requestParams, new ServiceResponseListener<EmployeeListResponse>() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onSuccess(EmployeeListResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);

                            if (response != null && !response.isError() ) {



                                if(response.getEmployeeList() != null && response.getEmployeeList().size() > 0) {
                                    noResultFound.setVisibility(View.GONE);
                                    employeeLayout.setVisibility(View.VISIBLE);
                                    productRecyclerView.setVisibility(View.VISIBLE);
                                    noEmployee.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);
                                    addEmployeeButton.setVisibility(View.VISIBLE);
                                    noEmployeesFound.setVisibility(View.GONE);
                                    if (isFirstPage) {
                                        employeeAdapter = null;
                                        employeeList.clear();
                                    }
                                    employeeList.addAll(response.getEmployeeList());

                                    AppInfo appInfo = AppUtils.getAppInfo(getApplication());
                                    appInfo.setEmployeeList(employeeList);
                                    AppUtils.setAppInfo(appInfo, getApplication());

                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    updateUi();

                                } else {
                                    if (isFirstPage) {
                                        employeeLayout.setVisibility(View.VISIBLE);
                                        noEmployeesFound.setVisibility(View.GONE);
                                        productRecyclerView.setVisibility(View.VISIBLE);
                                        noEmployee.setVisibility(View.GONE);
                                        retryButton.setVisibility(View.GONE);
                                        addEmployeeButton.setVisibility(View.VISIBLE);
                                    }

                                    else {
                                        Toast.makeText(context, getString(R.string.noMoreEmployeeFound), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } else if (response != null) {

                                if (isFirstPage) {
                                    noEmployeesFound.setVisibility(View.VISIBLE);
                                    noEmployee.setVisibility(View.VISIBLE);
                                    noEmployee.setText("No Employees Found");
                                    employeeLayout.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);
                                    addEmployeeButton.setVisibility(View.VISIBLE);

                                } else {

                                    Toast.makeText(context, getString(R.string.noMoreEmployeeFound), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if (isFirstPage) {
                                    noEmployeesFound.setVisibility(View.VISIBLE);
                                    noEmployee.setVisibility(View.VISIBLE);
                                    noEmployee.setText(getString(R.string.errorOccurred));
                                    employeeLayout.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.VISIBLE);
                                    addEmployeeButton.setVisibility(View.GONE);

//                                    noResultFound.setText(getString(R.string.title_of_data_message));
                                } else {
                                    Toast.makeText(context, getString(R.string.noMoreEmployeeFound), Toast.LENGTH_SHORT).show();
                                }

                            }

                        }


                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);

                            showErrorResponse();
                        }
                    }

            );
        }

    }

    @SuppressLint("RestrictedApi")
    private void showErrorResponse() {

        if (isFirstPage) {

            noEmployeesFound.setVisibility(View.VISIBLE);
            noEmployee.setVisibility(View.VISIBLE);
            employeeLayout.setVisibility(View.GONE);
            noEmployee.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
            addEmployeeButton.setVisibility(View.GONE);
        } else {
            showSnakeBar("Some error occurred. Please try again", "Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getEmployeeList(currentPageNumber);

                }
            });
        }
    }

    private void getEmployeeList(int pageNo) {
        currentPageNumber=pageNo;
        getProductList(currentPageNumber, true);


    }


    private void updateUi() {

        if (employeeAdapter == null) {

            employeeAdapter = new EmployeeAdapter(EmployeeListActivity.this, employeeList, EmployeeListActivity.this);
            productRecyclerView.setAdapter(employeeAdapter);

        } else {

            employeeAdapter.setEmployeeList(employeeList);
            employeeAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }

    @Override
    public void onDeletedItem(Employee employee) {

        employeeId=String.valueOf(employee.getEmpId());

        showAlert(EmployeeListActivity.this, getString(R.string.alert), getString(R.string.deleteAlert), "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteServiceCall();
                dialog.dismiss();

            }
        }, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


    }

    @Override
    public void onCall(Employee employee) {

        empMobileNumber=employee.getEmpPhoneNo();
        onMakeCall();
    }

    private void onMakeCall() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                AppUtils.makeCall(EmployeeListActivity.this, empMobileNumber);
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    showAlert(context, getString(R.string.callpermission), getString(R.string.callpermissiondesc), "Try", Try, "No", No1);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_PERMISSION);
                }
            }
        } else {
            AppUtils.makeCall(EmployeeListActivity.this, empMobileNumber);
        }


    }


    DialogInterface.OnClickListener Try = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(EmployeeListActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_REQUEST_PERMISSION);
        }
    };


    DialogInterface.OnClickListener No1 = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };


    private void deleteServiceCall() {

        if (DeviceManager.isDeviceOffline(context)) {
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }
        progressLayout.setVisibility(View.VISIBLE);
        if (!isService) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("employee_id", employeeId);
            requestParams.put("status", "1");
            ServiceManager.deleteEmployee(getApplicationContext(), requestParams, new ServiceResponseListener<DeleteResponse>() {
                        @Override
                        public void onSuccess(DeleteResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {

                                String message=response.getMessage();
                                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                currentPageNumber=1;
                                getProductList(currentPageNumber, true);
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                           /* retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPageNumber = 1;
                                    getProductList(currentPageNumber, true);
                                }
                            });*/
                            /*showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.title_of_data_message),
                                    getResources().getString(R.string.ok), null, null, null);*/
                        }
                    }

            );
        }


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            currentPageNumber = 1;
            getProductList(currentPageNumber, true);
        }

        if (requestCode == EDIT_REQ_CODE && resultCode == RESULT_OK) {
            currentPageNumber = 1;
            getProductList(currentPageNumber, true);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_REQUEST_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AppUtils.makeCall(EmployeeListActivity.this, empMobileNumber);
                }

                break;


        }
    }
}
