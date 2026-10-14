package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.CheckOut;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

/**
 * Created by creatah1 on 17-05-2017.
 */

public class VerifyCheckListAdapter extends RecyclerView.Adapter<VerifyCheckListAdapter.CheckViewHolder> {

    private Activity activity;
    private List<CheckOut> checkOutList;
    private boolean isCompleted;

    public VerifyCheckListAdapter(Activity activity, List<CheckOut> checkOutList, boolean isCompleted) {
        this.activity = activity;
        this.checkOutList = checkOutList;
        this.isCompleted = isCompleted;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_check_verify_list, parent, false);
        CheckViewHolder viewHolder = new CheckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CheckViewHolder holder, int position) {
        final CheckOut checkOut = checkOutList.get(position);
        String title = checkOut.getTitle();
        if(checkOut.getType().equalsIgnoreCase(AppConstants.CHECKOUT_MULTIPLE_KEY)){
            title = title + "(" + checkOut.getCount() + ")";
        }
        holder.titleTextView.setText(title);

        if(!isCompleted) {

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        checkOut.setVerified(true);
                    } else {
                        checkOut.setVerified(false);
                    }
                }
            });

        }
        holder.checkBox.setChecked(checkOut.isVerified());


        if(position==checkOutList.size()-1)
        {
            holder.viewLine.setVisibility(View.GONE);
        }
        else{
            holder.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return checkOutList.size();
    }

    public void setCheckOutList(List<CheckOut> checkOutList) {
        this.checkOutList = checkOutList;
    }

    public List<CheckOut> getCheckOutList() {
        return checkOutList;
    }


    public class CheckViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private CheckBox checkBox;
        private View viewLine;
        public CheckViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.item);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            viewLine=(View) itemView.findViewById(R.id.viewLine);
        }
    }
}
