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
import com.creatah.automobile.dealer.activity.QuickSearch;
import com.creatah.automobile.dealer.modal.Customer;
import com.creatah.automobile.dealer.modal.QuickSearchResult;

import java.util.List;

public class QuickSearchAdapter extends RecyclerView.Adapter<QuickSearchAdapter.QuickSearchViewHolder> {
    private Activity activity;
    private List<QuickSearchResult> quickSearchList;

    public QuickSearchAdapter(Activity activity, List<QuickSearchResult> quickSearchList1) {
        this.activity = activity;
        this.quickSearchList = quickSearchList1;
    }

    @Override
    public QuickSearchViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_quicksearch_list, parent, false);
        QuickSearchViewHolder viewHolder = new QuickSearchAdapter.QuickSearchViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(QuickSearchViewHolder holder, int position) {
        final QuickSearchResult qs = quickSearchList.get(position);
        holder.spare_name.setText(qs.getSpare_name());
        holder.spare_mrp.setText(qs.getSpare_mrp());
        holder.spare_cgst.setText(qs.getSpare_cgst());
        holder.spare_sgst.setText(qs.getSpare_sgst());
        holder.labour_name.setText(qs.getLabour_name());
        holder.labour_mrp.setText(qs.getLabour_mrp());
        holder.labour_cgst.setText(qs.getLabour_cgst());
        holder.labour_sgst.setText(qs.getLabour_sgst());

    }

    @Override
    public int getItemCount() {
        return quickSearchList.size();
    }

    public void setQuickSearchList(List<QuickSearchResult> quickSearchList) {
        this.quickSearchList = quickSearchList;
    }


    public class QuickSearchViewHolder extends RecyclerView.ViewHolder {

        private TextView spare_name;
        private TextView spare_mrp;
        private TextView spare_cgst;
        private TextView spare_sgst;
        private TextView labour_name;
        private TextView labour_mrp;
        private TextView labour_cgst;
        private TextView labour_sgst;

        public QuickSearchViewHolder(View itemView) {
            super(itemView);
            spare_name = (TextView) itemView.findViewById(R.id.spare_name);
            spare_mrp = (TextView) itemView.findViewById(R.id.spare_mrp);
            spare_cgst = (TextView) itemView.findViewById(R.id.spare_cgst);
            spare_sgst = (TextView) itemView.findViewById(R.id.spare_sgst);
            labour_name = (TextView) itemView.findViewById(R.id.labour_name);
            labour_mrp = (TextView) itemView.findViewById(R.id.labour_mrp);
            labour_cgst = (TextView) itemView.findViewById(R.id.labour_cgst);
            labour_sgst = (TextView) itemView.findViewById(R.id.labour_sgst);
        }
    }
}
