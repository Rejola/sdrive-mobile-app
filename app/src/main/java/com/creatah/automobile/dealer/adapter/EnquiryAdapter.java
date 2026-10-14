package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.EnquiryDetailsActivity;
import com.creatah.automobile.dealer.modal.CustomerEnquiry;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.EnquiryViewHolder> {

    private Activity activity;
    private List<CustomerEnquiry> enquiryList;

    public EnquiryAdapter(Activity activity, List<CustomerEnquiry> enquiryList) {
        this.activity = activity;
        this.enquiryList = enquiryList;
    }

    @Override
    public EnquiryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_enquiry, parent, false);
        EnquiryViewHolder viewHolder = new EnquiryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryViewHolder holder, int position) {


        final CustomerEnquiry enquiry = enquiryList.get(position);
        holder.nameTextView.setText(enquiry.getCustomerName());
        holder.makeTextView.setText(enquiry.getMake() + " " + enquiry.getModel());
        holder.vehicleNoTextView.setText(enquiry.getVehicleNo());
       // holder.requirementTextView.setText(enquiry.getRequirement());
        holder.dateTextView.setText(enquiry.getDate());
        holder.enquiryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(activity, EnquiryDetailsActivity.class);
                detailIntent.putExtra(AppConstants.ENQUIRY_DETAILS_EXTRA, enquiry);
                activity.startActivity(detailIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return enquiryList.size();
    }

    public void setEnquiryList(List<CustomerEnquiry> enquiryList) {
        this.enquiryList = enquiryList;
    }

    public class EnquiryViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView makeTextView;
        private TextView requirementTextView;
        private TextView dateTextView;
        private LinearLayout enquiryLayout;
        private TextView vehicleNoTextView;

        public EnquiryViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.customerNameTextView);
            makeTextView = (TextView) itemView.findViewById(R.id.makeTextView);
            vehicleNoTextView = itemView.findViewById(R.id.vehicleNoTextView);
           // requirementTextView = (TextView) itemView.findViewById(R.id.makeTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            enquiryLayout = (LinearLayout) itemView.findViewById(R.id.enquiryLayout);
        }
    }
}
