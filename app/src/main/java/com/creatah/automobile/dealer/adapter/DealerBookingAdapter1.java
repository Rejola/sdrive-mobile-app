package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

public class DealerBookingAdapter1 extends RecyclerView.Adapter<DealerBookingAdapter1.BookingViewHolder> {

    private Activity activity;
    private List<DealerBooking> bookingList;
    private boolean isCompleted;

    public DealerBookingAdapter1(Activity activity, List<DealerBooking> bookingList, boolean isCompleted) {
        this.activity = activity;
        this.bookingList = bookingList;
        this.isCompleted = isCompleted;
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_dealer_booking1, parent, false);
        BookingViewHolder viewHolder = new BookingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        final DealerBooking booking = bookingList.get(position);



            holder.nameTextView.setText(booking.getCustomerName());
            holder.vehicleNoTextView.setText(booking.getVehicleNo());
            holder.mobileNoTextView.setText(booking.getMobileNo());
            holder.serviceTypeTextView.setText(booking.getServiceName());
            holder.ratingBar.setRating(booking.getRatingCount());

            holder.ratingDescriptionTextview.setText(booking.getRatingDescription());
            holder.ratingDateTextView.setText(AppUtils.convertDateTimeStringTodate(booking.getDateOfRating()));

            holder.ratingBar.setEnabled(false);

            String makeModal = booking.getMakeName();
            if (booking.getModel() != null && !TextUtils.isEmpty(booking.getModel())) {
                makeModal = makeModal + " " + booking.getModel();
            }
            holder.modalTextView.setText(makeModal);
            if (booking.getAdviserId() !=0){
                holder.advisorNameTextView.setText(booking.getAdviserName());
            }else {
                holder.advisorLayout.setVisibility(View.GONE);
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
        private TextView serviceStatusTextView;
        private LinearLayout completedLayout;
        private TextView mobileNoTextView;
        private TextView serviceTypeTextView;
        private ImageView progressImageView;
        private TextView advisorNameTextView;
        private LinearLayout advisorLayout;
        private RatingBar ratingBar;
        private TextView  ratingDescriptionTextview;
        private TextView ratingDateTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            mobileNoTextView = (TextView) itemView.findViewById(R.id.mobileNoTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.addressTextView);
            modalTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
//            serviceStatusTextView = (TextView) itemView.findViewById(R.id.serviceStatusTextView);
            completedLayout = (LinearLayout) itemView.findViewById(R.id.completedLayout);
            serviceTypeTextView = (TextView) itemView.findViewById(R.id.serviceTypeTextView);
//            progressImageView = (ImageView) itemView.findViewById(R.id.progressImageView);
            advisorNameTextView =(TextView) itemView.findViewById(R.id.advisorNameTextView);
            advisorLayout = (LinearLayout) itemView.findViewById(R.id.advisorLayout);
            ratingBar=(RatingBar)itemView.findViewById(R.id.ratingBar);
            ratingDescriptionTextview=(TextView)itemView.findViewById(R.id.ratingDescriptionTextview);
            ratingDateTextView=(TextView)itemView.findViewById(R.id.ratingDateTextView);

        }
    }
}
