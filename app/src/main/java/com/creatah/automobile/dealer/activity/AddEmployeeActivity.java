package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddEmployeeResponse;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.Designation;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class AddEmployeeActivity extends BaseActivity {

    private EditText nameEditText;
    private EditText noEditText;
    private Spinner designationSpinner;
    private Button addButton;
    private String designationId = null;
    private Context context;
    private boolean isService = false;
    private Employee employee;
    private boolean isEdit = false;

    private int designationPos;
    private AppInfo appInfo;
    private List<Designation> designationList = new ArrayList<>();
    private List<String> designationStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        setTitle(getString(R.string.addEmployeeTitle));
        context = this;
        initializeViews();
        getBundle();
        if (isEdit) {
            setTitle("Edit Employee");
            addButton.setText(getString(R.string.updateButton));
        } else {
            addButton.setText(getString(R.string.addButton));
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
        appInfo = AppUtils.getAppInfo(getApplication());
        if (appInfo != null) {
            designationStrings.add(getString(R.string.selectDesignation));
            if (appInfo.getEmployeeList() != null) {
                designationList = appInfo.getDesignationList();
                for (int i = 0; i < designationList.size(); i++) {
                    designationStrings.add(designationList.get(i).getDesignation());
                }
            }
            ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, designationStrings);
            designationSpinner.setAdapter(employeeAdapter);
            designationSpinner.setOnItemSelectedListener(employeeListener);
            if (isEdit) {
                getPosFromDesignation(employee.getDesignation());
                designationSpinner.setSelection(designationPos + 1);
            }
        }


        if (employee != null) {
            nameEditText.setText(employee.getEmpName());
            nameEditText.setSelection(nameEditText.length());
            noEditText.setText(employee.getEmpPhoneNo());
        }
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.EMPLOYEE_DETAILS_EXTRA)) {
            employee = (Employee) bundle.getSerializable(AppConstants.EMPLOYEE_DETAILS_EXTRA);
            isEdit = true;
        } else {
            isEdit = false;
        }
    }

    private void validateData() {
        String name = nameEditText.getText().toString();
        String phoneNo = noEditText.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, getString(R.string.enterEmployeeName), Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneNo)) {
            Toast.makeText(context, getString(R.string.enterMobileNo), Toast.LENGTH_SHORT).show();

        } else if (phoneNo.length() != 10) {
            Toast.makeText(context, getString(R.string.invalidMobilNo), Toast.LENGTH_SHORT).show();
        } else if (designationId == null || TextUtils.isEmpty(designationId)) {
            Toast.makeText(AddEmployeeActivity.this, getString(R.string.pleaseSelectDesignation), Toast.LENGTH_SHORT).show();
        } else {
            RequestParams requestParams = new RequestParams();
            requestParams.put("employee_name", name);
            requestParams.put("employee_no", phoneNo);
            requestParams.put("designation_id", designationId);
            if (isEdit) {
                requestParams.put("employee_id", String.valueOf(employee.getEmpId()));
                requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
                EditEmployee(requestParams);
            } else {
                requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
                AddEmployee(requestParams);
            }
        }
    }

    private void EditEmployee(RequestParams requestParams) {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.editEmployee(context, requestParams, new ServiceResponseListener<AddEmployeeResponse>() {
                        @Override
                        public void onSuccess(AddEmployeeResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                                setResult(RESULT_OK);
                                finish();
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.title_of_data_message),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }


    }

    private void AddEmployee(RequestParams requestParams) {


        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.addEmployee(context, requestParams, new ServiceResponseListener<AddEmployeeResponse>() {
                        @Override
                        public void onSuccess(AddEmployeeResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                                setResult(RESULT_OK);
                                finish();
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
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

    private void initializeViews() {
        nameEditText = (EditText) findViewById(R.id.employeeName);
        noEditText = (EditText) findViewById(R.id.employeeNo);
        designationSpinner = (Spinner) findViewById(R.id.designationSpinner);
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

    public AdapterView.OnItemSelectedListener employeeListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                designationId = String.valueOf(designationList.get(position - 1).getId());
            } else {
                designationId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void getPosFromDesignation(String designation) {
        for (int i = 0; i < designationList.size(); i++) {
            if (designationList.get(i).getDesignation().equals(designation)) {
                designationPos = i;
            }
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
