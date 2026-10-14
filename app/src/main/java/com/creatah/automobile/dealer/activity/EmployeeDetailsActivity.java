package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DeleteResponse;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class EmployeeDetailsActivity extends BaseActivity {

    private TextView nameTextView;
    private TextView noTextView;
    private TextView designationTextView;
    private Employee employee;
    private boolean isService = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        setTitle(getString(R.string.employeeDetailsTitle));
        initializeViews();
        context = this;
        getBundle();
        if (employee != null) {
            nameTextView.setText(employee.getEmpName());
            noTextView.setText(employee.getEmpPhoneNo());
            designationTextView.setText(employee.getDesignation());
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.EMPLOYEE_DETAILS_EXTRA)) {
            employee = (Employee) bundle.getSerializable(AppConstants.EMPLOYEE_DETAILS_EXTRA);
        }
    }

    private void initializeViews() {
        nameTextView = (TextView) findViewById(R.id.employeeName);
        noTextView = (TextView) findViewById(R.id.employeeNo);
        designationTextView = (TextView) findViewById(R.id.employeeDesignation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supportFinishAfterTransition();
                } else {
                    finish();
                }
                break;

            case R.id.action_edit:
                /*Intent editIntent = new Intent(context, AddEmployeeActivity.class);
                editIntent.putExtra(AppConstants.EMPLOYEE_DETAILS_EXTRA, employee);
                startActivityForResult(editIntent, EDIT_REQ_CODE);*/
                break;

            case R.id.action_delete:
                showAlert(EmployeeDetailsActivity.this, getString(R.string.alert), getString(R.string.deleteAlert), "YES", YES_DELETE, "No", No);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    DialogInterface.OnClickListener YES_DELETE = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            deleteServiceCall();
            dialog.dismiss();
        }
    };

    private void deleteServiceCall() {

        if (DeviceManager.isDeviceOffline(context)) {
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }
        mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
        if (!isService) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("employee_id", String.valueOf(employee.getEmpId()));
            requestParams.put("status", "1");
            ServiceManager.deleteEmployee(getApplicationContext(), requestParams, new ServiceResponseListener<DeleteResponse>() {
                        @Override
                        public void onSuccess(DeleteResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            if (response != null && !response.isError()) {
                                setResult(RESULT_OK);
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }


    }


    DialogInterface.OnClickListener No = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
