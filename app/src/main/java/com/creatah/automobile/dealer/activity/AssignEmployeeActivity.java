package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.theme.activity.DialogActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 06-05-2017.
 */

public class AssignEmployeeActivity extends DialogActivity implements View.OnClickListener {

    private Button updateButton;
    private Spinner personSpinner;
    private ImageView closeImageView;
    private List<Employee> employeeList;
    private List<String> employeeStrList = new ArrayList<>();
    private AppInfo appInfo;
    private String employeeId;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_employee);
        context = this;
        initializeViews();
        updateButton.setOnClickListener(this);
        closeImageView.setOnClickListener(this);
        appInfo = AppUtils.getAppInfo(getApplication());

        if (appInfo != null) {
            employeeStrList.add(getString(R.string.selectEmployee));
            if (appInfo.getEmployeeList() != null) {
                employeeList = appInfo.getEmployeeList();
                for (int i = 0; i < employeeList.size(); i++) {
                    employeeStrList.add(employeeList.get(i).getEmpName());
                }
            }


            ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, employeeStrList);
            personSpinner.setAdapter(employeeAdapter);
            personSpinner.setOnItemSelectedListener(employeeListener);
        }
    }

    private void initializeViews() {
        personSpinner = (Spinner) findViewById(R.id.pickUpSpinner);
        updateButton = (Button) findViewById(R.id.updateButton);
        closeImageView = (ImageView) findViewById(R.id.closeImageView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.updateButton:
                validateData();
                break;

            case R.id.closeImageView:
                setResult(RESULT_CANCELED);
                finish();
                break;

        }
    }

    private void validateData() {

        if (employeeId == null) {
            Toast.makeText(context, "Please Select Employee", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent();
            intent.putExtra(AppConstants.EMPLOYEE_ID_EXTRA, employeeId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public AdapterView.OnItemSelectedListener employeeListener = new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                employeeId = String.valueOf(employeeList.get(position - 1).getEmpId());
            } else {
                employeeId = null;
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

}
