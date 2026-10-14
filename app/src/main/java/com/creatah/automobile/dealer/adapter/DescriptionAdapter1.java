package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
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

public class DescriptionAdapter1 extends RecyclerView.Adapter<DescriptionAdapter1.DescriptionViewHolder> {

    private Activity activity;
    private List<SpareLabour> spareLabourList;
    private WorkItemListener listener;
    private boolean isCompleted;
    private String type;

    public DescriptionAdapter1(Activity activity, List<SpareLabour> spareLabourList) {
        this.activity = activity;
        this.spareLabourList = spareLabourList;
    }

    @Override
    public DescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_description1, parent, false);
        DescriptionViewHolder viewHolder = new DescriptionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DescriptionViewHolder holder, final int position) {
        final SpareLabour spareLabour = spareLabourList.get(position);
        double amount = 0, totalPrice = 0, gstAmount = 0;

        holder.descriptionTextView.setText(spareLabour.getItemName().concat("- ").concat(activity.getString(R.string.rs)).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
//        holder.amountTextView.setText(activity.getString(R.string.rs).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
        holder.rateTextView.setText(activity.getString(R.string.rs).concat(" ").concat(String.valueOf(spareLabour.getItemAmount())));
        holder.quantityCountTextView.setText("Qty " + String.valueOf(spareLabour.getQty()));
        holder.cGstAmountTextView.setText("CGST ( " + spareLabour.getItemCGSTPercentage() + "% ) - " + activity.getString(R.string.rs)+" " + String.format("%.2f",spareLabour.getPerCGSTAmount()*spareLabour.getQty()));
        holder.sGstText.setText("SGST ( " + spareLabour.getItemSGSTPercentage() + "% ) - " + activity.getString(R.string.rs)+" " + String.format("%.2f",spareLabour.getPerSGSTAmount()*spareLabour.getQty()));
        if (position == spareLabourList.size() - 1) {
            holder.spareAndLabourListView.setVisibility(View.GONE);
        } else {

            holder.spareAndLabourListView.setVisibility(View.VISIBLE);
        }


        amount = spareLabour.getQty() * spareLabour.getItemAmount();

        gstAmount = (spareLabour.getQty() * spareLabour.getPerCGSTAmount()) + (spareLabour.getQty() * spareLabour.getPerSGSTAmount());

        totalPrice = amount + gstAmount;

        holder.totalAmountTextView.setText("Total price - " + activity.getString(R.string.rs) +" "+ String.format("%.2f",totalPrice));

    }

    public List<SpareLabour> getSpareLabourList() {
        return spareLabourList;
    }

    public void setSpareLabourList(List<SpareLabour> spareLabourList) {
        this.spareLabourList = spareLabourList;
    }

    @Override
    public int getItemCount() {
        return spareLabourList.size();
    }


    public interface WorkItemListener{
        void onDeletedItem(Description description, String type);
    }


    public class DescriptionViewHolder extends RecyclerView.ViewHolder {


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

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
