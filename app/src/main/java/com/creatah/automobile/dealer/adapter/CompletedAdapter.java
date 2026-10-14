package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.CompletedDetailsActivity;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.CompletedViewHolder> {

    private Activity activity;
    private List<Booking> bookingList;

    public CompletedAdapter(Activity activity, List<Booking> bookingList){
        this.activity = activity;
        this.bookingList = bookingList;
    }

    @Override
    public CompletedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_completed, parent, false);
        CompletedViewHolder viewHolder = new CompletedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CompletedViewHolder holder, int position) {
        final Booking booking = bookingList.get(position);
        holder.nameTextView.setText(booking.getCustomerName());
        holder.vehicleNoTextView.setText(booking.getVehicleNo());
        holder.modalTextView.setText(booking.getBrand() + " " + booking.getModal());
        holder.dateTextView.setText(AppUtils.convertDateTimeStringTodate(booking.getServiceDate()));
        if(booking.getRatting()!=null) {
            Double star = Math.ceil(Double.valueOf(booking.getRatting()));
            holder.ratingBar.setNumStars(star.intValue());
            holder.ratingBar.setRating(Float.valueOf(booking.getRatting()));
        }
        holder.completedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent completedIntent = new Intent(activity, CompletedDetailsActivity.class);
                completedIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, booking);
                activity.startActivity(completedIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingList(List<Booking> bookingList){
        this.bookingList = bookingList;
    }

    public class CompletedViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView vehicleNoTextView;
        private TextView modalTextView;
        private TextView dateTextView;
        private RatingBar ratingBar;
        private LinearLayout completedLayout;

        public CompletedViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            modalTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            completedLayout = (LinearLayout) itemView.findViewById(R.id.completedLayout);
        }
    }
}
