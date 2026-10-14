package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.Description;

import java.util.List;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class WorkActionAdapter extends RecyclerView.Adapter<WorkActionAdapter.ActionViewHolder> {

    private List<Description> complaintsList;
    private Activity activity;
    private ActionListener listener;
    private boolean isCompleted;

    public WorkActionAdapter(Activity activity, List<Description> complaintsList, ActionListener listener, boolean isCompleted) {
        this.activity = activity;
        this.complaintsList = complaintsList;
        this.listener = listener;
        this.isCompleted = isCompleted;
    }


    @Override
    public ActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_work_action, parent, false);
        ActionViewHolder viewHolder = new ActionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActionViewHolder holder, int position) {
        final Description complaints = complaintsList.get(position);
        holder.actionEditText.setText(String.valueOf(complaints.getAction()));
        holder.descriptionEditText.setText(complaints.getDescription());
        holder.actionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                complaints.setAction(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                complaints.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeletedItem(complaints);
            }
        });
        holder.deleteImageView.setVisibility(isCompleted ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return complaintsList.size();
    }

    public void setComplaintsList(List<Description> complaintsList) {
        this.complaintsList = complaintsList;
    }

    public List<Description> getComplaintsList() {
        return complaintsList;
    }

    public interface ActionListener {
        void onDeletedItem(Description description);
    }


    public class ActionViewHolder extends RecyclerView.ViewHolder {
        private EditText descriptionEditText;
        private EditText actionEditText;
        private ImageView deleteImageView;

        public ActionViewHolder(View itemView) {
            super(itemView);
            descriptionEditText = (EditText) itemView.findViewById(R.id.description);
            actionEditText = (EditText) itemView.findViewById(R.id.action);
            deleteImageView = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
