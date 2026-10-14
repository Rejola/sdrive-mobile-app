package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.CheckOut;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckListAdapter1 extends RecyclerView.Adapter<CheckListAdapter1.CheckViewHolder> {

    private Activity activity;
    private List<CheckOut> checkOutList;

    public CheckListAdapter1(Activity activity, List<CheckOut> checkOutList) {
        this.activity = activity;
        this.checkOutList = checkOutList;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_check_list1, parent, false);
        CheckViewHolder viewHolder = new CheckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {

        final CheckOut checkOut = checkOutList.get(position);
        String title = checkOut.getTitle();
        if(checkOut.getType().equalsIgnoreCase(AppConstants.CHECKOUT_MULTIPLE_KEY)){
            title = title + "(" + checkOut.getCount() + ")";
        }
        holder.titleTextView.setText(title);


//        holder.checkBoxImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.bullet));

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

        @BindView(R.id.item)
        TextView titleTextView;



        public CheckViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
