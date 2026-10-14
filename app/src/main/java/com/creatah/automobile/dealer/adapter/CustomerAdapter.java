package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Customer;

import java.util.List;

/**
 * Created by creatah1 on 18-05-2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private Activity activity;
    private List<Customer> customerList;
    private CustomerClickListener listener;

    public CustomerAdapter(Activity activity, List<Customer> customerList, CustomerClickListener listener) {
        this.activity = activity;
        this.customerList = customerList;
        this.listener = listener;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_customers, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        final Customer customer = customerList.get(position);
        holder.nameTextView.setText(customer.getName());
        holder.mobileTextView.setText(customer.getMobileNo());
        holder.emailTextView.setText(customer.getEmail());


        holder.viewCompletedServiceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onViewCompletedService(customer);
            }
        });
        holder.callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCallCustomer(customer);
            }
        });

     /*   holder.addressTextView.setVisibility((customer.getAddress() == null || TextUtils.isEmpty(customer.getAddress())) ? View.GONE : View.VISIBLE);
        holder.mobileTextView.setVisibility((customer.getMobileNo() == null || TextUtils.isEmpty(customer.getMobileNo())) ? View.GONE : View.VISIBLE);
        holder.emailTextView.setVisibility((customer.getEmail() == null || TextUtils.isEmpty(customer.getEmail())) ? View.GONE : View.VISIBLE);*/

    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public interface CustomerClickListener {
        void onCallCustomer(Customer customer);

        void onViewCompletedService(Customer customer);
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView mobileTextView;
        private TextView emailTextView;
        private LinearLayout callImageView;
        private TextView viewCompletedServiceTextView;
        public CustomerViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mobileTextView = (TextView) itemView.findViewById(R.id.mobileNumberTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            callImageView = (LinearLayout) itemView.findViewById(R.id.calImageView);
            viewCompletedServiceTextView=(TextView) itemView.findViewById(R.id.viewCompletedServiceTextView);
        }
    }
}
