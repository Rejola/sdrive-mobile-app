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
import com.creatah.automobile.dealer.activity.ReminderDetailsActivity;
import com.creatah.automobile.dealer.activity.ReminderListActivity;
import com.creatah.automobile.dealer.modal.Reminder;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.RemainderViewHolder> {

    private Activity activity;
    private List<Reminder> reminders;

    public ReminderAdapter(Activity activity, List<Reminder> reminders) {
        this.activity = activity;
        this.reminders = reminders;
    }

    @Override
    public RemainderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_remainder, parent, false);
        RemainderViewHolder viewHolder = new RemainderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RemainderViewHolder holder, int position) {

        final Reminder reminder = reminders.get(position);
        holder.nameTextView.setText(reminder.getCustomerName());
        holder.vehicleNoTextView.setText(reminder.getVehicleNo());
        holder.modalTextView.setText(reminder.getMakeName() + " " + reminder.getModel());
        holder.dateTextView.setText(AppUtils.convertstringTodate1(reminder.getFollowUpDate()));
        holder.serviceName.setText(reminder.getServiceName());
//        holder.serviceName1.setText(reminder.getServiceName());
        holder.remainderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent remainderIntent = new Intent(activity, ReminderDetailsActivity.class);
                remainderIntent.putExtra(AppConstants.REMAINDER_DETAILS_EXTRA, reminder);
                activity.startActivityForResult(remainderIntent, ReminderListActivity.REMINDER_REQ_CODE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public class RemainderViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView vehicleNoTextView;
        private TextView modalTextView;
        private TextView dateTextView;
        private LinearLayout remainderLayout;

        @BindView(R.id.serviceTextView)
        TextView serviceName;



        public RemainderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            vehicleNoTextView = (TextView) itemView.findViewById(R.id.vehicleNoTextView);
            modalTextView = (TextView) itemView.findViewById(R.id.modalTextView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            remainderLayout = (LinearLayout) itemView.findViewById(R.id.remainderLayout);
        }
    }
}
