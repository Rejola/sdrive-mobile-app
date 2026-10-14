package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Description;
import com.creatah.automobile.dealer.modal.SpareLabour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 06-04-2018.
 */

public class VehicleReadyAdapter extends RecyclerView.Adapter<VehicleReadyAdapter.VehicleReadyViewHolder> {

    private Activity activity;

    private List<SpareLabour> spareLabourList;

    public VehicleReadyAdapter(Activity activity, List<SpareLabour> spareLabourList) {
        this.activity = activity;
        this.spareLabourList = spareLabourList;

    }

    @Override
    public VehicleReadyAdapter.VehicleReadyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vehicle_ready, parent, false);
        VehicleReadyAdapter.VehicleReadyViewHolder viewHolder = new VehicleReadyAdapter.VehicleReadyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleReadyViewHolder holder, int position) {

        double amount = 0, totalPrice = 0, gstAmount = 0;
        final SpareLabour spareLabour = spareLabourList.get(position);

        holder.descriptionTextView.setText(spareLabour.getItemName().concat("- ").concat(activity.getString(R.string.rs)).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
//        holder.amountTextView.setText(activity.getString(R.string.rs).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
        holder.rateTextView.setText(activity.getString(R.string.rs).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
        holder.quantityCountTextView.setText("Qty " + String.format("%.2f",spareLabour.getQty()));
        holder.cGstAmountTextView.setText("CGST ( " + String.format("%.2f",spareLabour.getItemCGSTPercentage()) + "% ) - " + activity.getString(R.string.rs)+" " + String.format("%.2f",spareLabour.getPerCGSTAmount()*spareLabour.getQty()));
        holder.sGstText.setText("SGST ( " + String.format("%.2f",spareLabour.getItemSGSTPercentage()) + "% ) - " + activity.getString(R.string.rs)+" " + String.format("%.2f", spareLabour.getPerSGSTAmount() * spareLabour.getQty()));
        if (position == spareLabourList.size() - 1) {
            holder.spareAndLabourListView.setVisibility(View.GONE);
        } else {

            holder.spareAndLabourListView.setVisibility(View.VISIBLE);
        }


        amount = spareLabour.getQty() * spareLabour.getItemAmount();

        gstAmount = (spareLabour.getQty() * spareLabour.getItemCGSTAmount()) + (spareLabour.getQty() * spareLabour.getItemSGSTAmount());

        totalPrice = amount + gstAmount;

        holder.totalAmountTextView.setText("Total price - " + activity.getString(R.string.rs) +" "+ String.format("%.2f", totalPrice));


    }


    @Override
    public int getItemCount() {
        return spareLabourList.size();
    }

    public List<SpareLabour> getSpareLabourList() {
        return spareLabourList;
    }

    public void setSpareLabourList(List<SpareLabour> spareLabourList) {
        this.spareLabourList = spareLabourList;
    }

    public class VehicleReadyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lineView)
        View lineView;
/*

        @BindView(R.id.amountTextView)
        TextView amountTextView;
*/

        @BindView(R.id.rateTextView)
        TextView rateTextView;

        @BindView(R.id.quantityCountTextView)
        TextView quantityCountTextView;

        @BindView(R.id.descriptionTextView)
        TextView descriptionTextView;

        @BindView(R.id.cGstAmountTextView)
        TextView cGstAmountTextView;

        @BindView(R.id.totalAmountTextView)
        TextView totalAmountTextView;

        @BindView(R.id.sGstText)
        TextView sGstText;

        @BindView(R.id.spareAndLabourListView)
        View spareAndLabourListView;


        public VehicleReadyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
