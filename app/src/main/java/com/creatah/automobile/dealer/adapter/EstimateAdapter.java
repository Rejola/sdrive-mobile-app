package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Description;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class EstimateAdapter extends RecyclerView.Adapter<EstimateAdapter.EstimateViewHolder> {

    private List<Description> complaintsList;
    private Activity activity;
    private EstimationListener listener;
    private boolean isCompleted;
    public EstimateAdapter(Activity activity, List<Description> complaintsList, EstimationListener listener, boolean isCompleted) {
        this.activity = activity;
        this.complaintsList = complaintsList;
        this.listener = listener;
        this.isCompleted = isCompleted;
    }


    @Override
    public EstimateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_estimates, parent, false);
        EstimateViewHolder viewHolder = new EstimateViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EstimateViewHolder holder, int position) {
        final Description complaints = complaintsList.get(position);
        if(complaints.getAmount()!=0) {
            holder.amountTextView.setText(String.valueOf(complaints.getAmount()));
        }
        /*holder.descriptionEditText.setText(complaints.getDescription());*/
        holder.amountTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence)){
                    complaints.setAmount(0);
                }else {
                    complaints.setAmount(Integer.valueOf(charSequence.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return complaintsList.size();
    }

    public void setComplaintsList(List<Description> complaintsList){
        this.complaintsList = complaintsList;
    }

    public List<Description> getComplaintsList() {
        return complaintsList;
    }

    public interface EstimationListener{
        void onDeletedItem(Description description);
    }


    public class EstimateViewHolder extends RecyclerView.ViewHolder {
        private EditText descriptionEditText;
        private EditText amountEditText;
        private ImageView deleteImageView;

        @BindView(R.id.quantityEditText)
        EditText quantity;

        @BindView(R.id.amountEditText)
        EditText rate;

        @BindView(R.id.amountTextView)
        TextView amountTextView;

        public EstimateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            /*descriptionEditText = (EditText) itemView.findViewById(R.id.description);*/
            /*deleteImageView = (ImageView) itemView.findViewById(R.id.delete);*/
        }
    }
}
