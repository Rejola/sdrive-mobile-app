package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.AddEmployeeActivity;

import com.creatah.automobile.dealer.activity.EmployeeListActivity;

import com.creatah.automobile.dealer.modal.Description;
import com.creatah.automobile.dealer.modal.Employee;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Activity activity;
    private List<Employee> employeeList;
    private EmployeeListener employeeListener;

    public EmployeeAdapter(Activity activity, List<Employee> employeeList, EmployeeListener employeeListener) {
        this.activity = activity;
        this.employeeList = employeeList;
        this.employeeListener=employeeListener;
    }



    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_employee, parent, false);
        EmployeeViewHolder viewHolder = new EmployeeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        final Employee employee = employeeList.get(position);
        holder.nameTextView.setText(employee.getEmpName());
        holder.designationTextView.setText(employee.getDesignation());
        holder.mobileNoTextView.setText(employee.getEmpPhoneNo());
        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(activity, AddEmployeeActivity.class);
                detailIntent.putExtra(AppConstants.EMPLOYEE_DETAILS_EXTRA, employee);
                activity.startActivityForResult(detailIntent, EmployeeListActivity.EDIT_REQ_CODE);
            }
        });

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeListener.onDeletedItem(employee);
            }
        });

        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employeeListener.onCall(employee);

            }
        });

    }

    public interface EmployeeListener
    {
        void onDeletedItem(Employee employee);
        void onCall(Employee employee);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView designationTextView;
        private ImageView editImage;
        private ImageView deleteImage;
        private LinearLayout callButton;
        private TextView mobileNoTextView;


        public EmployeeViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mobileNoTextView = (TextView) itemView.findViewById(R.id.mobileNoTextView);
            designationTextView = (TextView) itemView.findViewById(R.id.designationTextView);
            editImage = (ImageView) itemView.findViewById(R.id.editImage);
            deleteImage = (ImageView) itemView.findViewById(R.id.deleteImage);
            callButton = (LinearLayout) itemView.findViewById(R.id.callButton);

        }
    }
}
