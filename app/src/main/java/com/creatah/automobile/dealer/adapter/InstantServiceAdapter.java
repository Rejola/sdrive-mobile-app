package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.InstantDetailsActivity;
import com.creatah.automobile.dealer.modal.InstantService;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class InstantServiceAdapter extends RecyclerView.Adapter<InstantServiceAdapter.InstantViewHolder> {

    private Activity activity;
    private List<InstantService> serviceList;

    public InstantServiceAdapter(Activity activity, List<InstantService> serviceList) {
        this.activity = activity;
        this.serviceList = serviceList;
    }

    @Override
    public InstantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_instantbooking, parent, false);
        InstantViewHolder viewHolder = new InstantViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InstantViewHolder holder, int position) {
        final InstantService enquiry = serviceList.get(position);
        holder.nameTextView.setText(enquiry.getCustomerName());
        holder.modelTextView.setText(enquiry.getModel());
        holder.vehicleNoTextView.setText(enquiry.getVehicleNo());
        holder.requirementTextView.setText(enquiry.getServiceName());
        holder.dateTextView.setText(AppUtils.convertDateTimeStringTodate(enquiry.getDate()));

        holder.bookingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(activity, InstantDetailsActivity.class);
                detailIntent.putExtra(AppConstants.INSTANT_SERVICE_DETAILS_EXTRA, enquiry);
                activity.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void setServiceList(List<InstantService> serviceList) {
        this.serviceList = serviceList;
    }

    public class InstantViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView modelTextView;
        private TextView vehicleNoTextView;
        private TextView requirementTextView;
        private TextView dateTextView;
        private LinearLayout bookingLayout;

        public InstantViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            modelTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.vehicleNoTextView);
            requirementTextView = (TextView) itemView.findViewById(R.id.serviceTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            bookingLayout = (LinearLayout) itemView.findViewById(R.id.bookingLayout);
        }
    }
}
