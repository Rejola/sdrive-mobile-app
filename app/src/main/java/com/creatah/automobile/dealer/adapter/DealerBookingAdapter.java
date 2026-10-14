package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.BookingDetailsActivity;
import com.creatah.automobile.dealer.activity.DealerBookingsActivity;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

/**
 * Created by creatah1 on 11-04-2017.
 */

public class DealerBookingAdapter extends RecyclerView.Adapter<DealerBookingAdapter.BookingViewHolder> {

    private Activity activity;
    private List<DealerBooking> bookingList;
    private boolean isCompleted;

    public DealerBookingAdapter(Activity activity, List<DealerBooking> bookingList, boolean isCompleted) {
        this.activity = activity;
        this.bookingList = bookingList;
        this.isCompleted = isCompleted;
    }


    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_dealer_booking, parent, false);
        BookingViewHolder viewHolder = new BookingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
       final DealerBooking booking = bookingList.get(position);

        holder.nameTextView.setText(booking.getCustomerName()  +" - "+booking.getJobcardId());
        holder.vehicleNoTextView.setText(booking.getVehicleNo());
        holder.mobileNoTextView.setText(booking.getMobileNo());
        holder.advisorNameTextView.setText(booking.getAdviserName());
        holder.serviceTypeTextView.setText(booking.getServiceName());

        String makeModal = booking.getMakeName();
        if (booking.getModel() != null && !TextUtils.isEmpty(booking.getModel())) {
            makeModal = makeModal + " " + booking.getModel();
        }
        holder.modalTextView.setText(makeModal);
      /*  if (booking.getAdviserId() !=0){
            holder.advisorNameTextView.setText(booking.getAdviserName());
        }else {
            holder.advisorLayout.setVisibility(View.GONE);
        }*/
        holder.deliveryDateTextView.setText(AppUtils.convertDateStringTodate(booking.getServiceDate()));
       // if (!isCompleted) {
            holder.completedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(booking.getStatus().equals(activity.getString(R.string.vehicleDeliveredKey)))
                    {
                        Intent detailsIntent = new Intent(activity, BookingDetailsActivity.class);
                        detailsIntent.putExtra(AppConstants.CHECK_EDIT_FLOW, true);
                        detailsIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, booking);
                        activity.startActivityForResult(detailsIntent, DealerBookingsActivity.STATUS_UPDATE_REQ_CODE);
                    }

                    else {
                        Intent detailsIntent = new Intent(activity, BookingDetailsActivity.class);
                        detailsIntent.putExtra(AppConstants.CHECK_EDIT_FLOW, false);
                        detailsIntent.putExtra(AppConstants.BOOKING_DETAILS_EXTRA, booking);
                        activity.startActivityForResult(detailsIntent, DealerBookingsActivity.STATUS_UPDATE_REQ_CODE);
                    }

                }
            });

        //}

        if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.checkInCompletedKey))) {
            holder.statusTextView.setText(activity.getString(R.string.serviceCheckIn));
            holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.checkin_completed_color));

            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_checkandestimate));
        }/* else if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.inspectionCompletedKey))) {
            holder.statusTextView.setText(activity.getString(R.string.serviceInspectionComp));
            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.inspection_complet));
        }*/ else if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.serviceUnderProgressKey))) {
            holder.statusTextView.setText(activity.getString(R.string.serviceProgress));
            holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.service_inprogress_color));
            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_serviceinprogress_color));
            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_serviceinprogress_color));
        } else if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.vehicleReadyKey))) {
            holder.statusTextView.setText(activity.getString(R.string.serviceVehicleReady));
            holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.vehicle_ready));
            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_vehicle_ready_color_orange));
        } else if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.vehicleDeliveredKey))) {
            holder.statusTextView.setText(activity.getString(R.string.serviceVehicleDelivered));
            holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.service_completed_color));
            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_completed_color));
        } else if (booking.getStatus().equalsIgnoreCase(activity.getString(R.string.queuedVehicleKey))) {
            holder.statusTextView.setText(activity.getString(R.string.queuedService));
            holder.statusTextView.setTextColor(activity.getResources().getColor(R.color.service_open_color));

            holder.progressImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_open_icon));
        }

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingList(List<DealerBooking> bookingList) {
        this.bookingList = bookingList;
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView vehicleNoTextView;
        private TextView modalTextView;
        private TextView dateTextView;
        private LinearLayout completedLayout;
        private TextView mobileNoTextView;
        private TextView serviceTypeTextView;
        private TextView deliveryDateTextView;
        private TextView advisorNameTextView;
        private LinearLayout advisorLayout;
        private ImageView progressImageView;
        private TextView statusTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mobileNoTextView = (TextView) itemView.findViewById(R.id.mobileNoTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.vehicleNoTextView);
            modalTextView = (TextView) itemView.findViewById(R.id.modalTextView);
//            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            completedLayout = (LinearLayout) itemView.findViewById(R.id.completedLayout);
            serviceTypeTextView = (TextView) itemView.findViewById(R.id.serviceTextView);
            progressImageView = (ImageView) itemView.findViewById(R.id.progressImageView);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);

            advisorNameTextView =(TextView) itemView.findViewById(R.id.advisorNameTextView);
//            advisorLayout = (LinearLayout) itemView.findViewById(R.id.advisorLayout);

            deliveryDateTextView = (TextView) itemView.findViewById(R.id.deliveryDateTextView);

        }
    }
}
