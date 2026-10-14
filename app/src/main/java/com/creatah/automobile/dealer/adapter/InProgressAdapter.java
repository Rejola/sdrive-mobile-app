package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Booking;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.ProgressViewHolder> {

    private Activity activity;
    private List<Booking> bookingList;
    private BookingClickListener listener;

    public InProgressAdapter(Activity activity, List<Booking> bookingList, BookingClickListener listener) {
        this.activity = activity;
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_inprogress, parent, false);
        ProgressViewHolder viewHolder = new ProgressViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {

        final Booking booking = bookingList.get(position);
        holder.nameTextView.setText(booking.getCustomerName());
//        holder.vehicleNoTextView.setText(booking.getVehicleNo());
        holder.modalTextView.setText(booking.getBrand() + " " + booking.getModal());
        holder.dateTextView.setText(AppUtils.convertstringTodate1(booking.getServiceDate()));
        if (booking.getIsAcknowledged() == 1){
            holder.acknowledgeLayout.setVisibility(View.VISIBLE);
        }else {
            holder.acknowledgeLayout.setVisibility(View.GONE);
        }

        holder.serviceName.setText(booking.getServiceType());
        holder.completedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserBookingClicked(booking);
            }
        });

    }



    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public interface BookingClickListener {
        void onUserBookingClicked(Booking booking);
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView vehicleNoTextView;
        private TextView modalTextView;
        private TextView dateTextView;
        private TextView serviceName;
        private LinearLayout completedLayout;
        private TextView makeTextView;
        @BindView(R.id.acknowledgeLayout)
        LinearLayout acknowledgeLayout;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            modalTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            completedLayout = (LinearLayout) itemView.findViewById(R.id.completedLayout);
            serviceName = (TextView) itemView.findViewById(R.id.serviceTextView);
            makeTextView=(TextView)itemView.findViewById(R.id.makeTextView);
        }
    }
}
