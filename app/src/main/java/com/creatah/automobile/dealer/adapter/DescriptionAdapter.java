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
 * Created by creatah1 on 05-05-2017.
 */

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder> {

    private Activity activity;
    private List<Description> descriptionList;
    private WorkItemListener listener;
    private boolean isCompleted;
    private String type;

    public DescriptionAdapter(Activity activity, String type, List<Description> descriptionList, WorkItemListener listener, boolean isCompleted) {
        this.activity = activity;
        this.type = type;
        this.descriptionList = descriptionList;
        this.listener = listener;
        this.isCompleted = isCompleted;
    }

    @Override
    public DescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_description, parent, false);
        DescriptionViewHolder viewHolder = new DescriptionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DescriptionViewHolder holder, final int position) {
        final Description description = descriptionList.get(position);
        holder.descEditText.setText(description.getDescription());
        holder.descEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                description.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               listener.onDeletedItem(description, type);
            }
        });

        if(isCompleted){
            holder.deleteImageView.setVisibility(View.GONE);
        }else {
            holder.deleteImageView.setVisibility(View.VISIBLE);
        }

    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
    }

    @Override
    public int getItemCount() {
        return descriptionList.size();
    }


    public interface WorkItemListener{
        void onDeletedItem(Description description, String type);
    }


    public class DescriptionViewHolder extends RecyclerView.ViewHolder {

        private EditText descEditText;
        private ImageView deleteImageView;
        public DescriptionViewHolder(View itemView) {
            super(itemView);
            descEditText = (EditText) itemView.findViewById(R.id.description);
            deleteImageView = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
