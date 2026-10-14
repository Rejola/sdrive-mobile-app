package com.creatah.automobile.dealer.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.framework.utils.Utils;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.WorkCheckListActivity;
import com.creatah.automobile.dealer.modal.SpareLabour;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by santhana on 27/04/18.
 */


public class WorkCheckListAdapter extends RecyclerView.Adapter<WorkCheckListAdapter.WorkViewHolder> {

    private Context context;

    private String type;

    private boolean isCompleted;

    private boolean isSpare;

    public String spareList;

    private List<SpareLabour> spareLabourList;
    private List<SpareLabour> selectedSpareLabourList =  new ArrayList<>();

    public WorkCheckListAdapter(Context context, String type, List<SpareLabour> spareLabourList, boolean isSpare, boolean isCompleted) {
        this.context = context;
        this.type = type;
        this.spareLabourList = spareLabourList;
        this.isSpare = isSpare;
        this.isCompleted = isCompleted;
    }

    @Override
    public WorkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_work_check_list, parent, false);
        WorkViewHolder viewHolder = new WorkViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WorkViewHolder holder, int position) {

        final SpareLabour description = spareLabourList.get(position);
        holder.descriptionEditText.setText(description.getItemName());

        final SpareLabour qty = spareLabourList.get(position);
        holder.quantitytext.setText(qty.getQtyVal());

        if (description.isCompleted()) {
            holder.serviceCompletedIcon.setImageResource(R.drawable.work_select);
        } else {
            holder.serviceCompletedIcon.setImageResource(R.drawable.work_unselect);
        }

        holder.selectItem.setChecked(description.isActive());


        if (description.isIssued()) {
            holder.spareIconCheck.setImageResource(R.drawable.spare_select);
        } else {
            holder.spareIconCheck.setImageResource(R.drawable.spare_unselect);
        }


        if (!isCompleted) {
            holder.serviceCompletedIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isSpare || description.isIssued()) {
                        description.setCompleted(!description.isCompleted());
                        if (description.isCompleted()) {
                            holder.serviceCompletedIcon.setImageResource(R.drawable.work_select);
                        } else {
                            holder.serviceCompletedIcon.setImageResource(R.drawable.work_unselect);
                        }
                    }else {
                        Toast.makeText(context, "Please update issued status", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }
            });
            holder.selectItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        description.setActive(true);
                        selectedSpareLabourList.add(description);
                    }else{
                        description.setActive(false);
                        selectedSpareLabourList.remove(description);
                    }
                }
            });

            holder.spareIconCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (description.isIssued()) {
                        description.setCompleted(false);
                        holder.serviceCompletedIcon.setImageResource(R.drawable.work_unselect);
                    }

                    description.setIssued(!description.isIssued());
                    if (description.isIssued()) {
                        holder.spareIconCheck.setImageResource(R.drawable.spare_select);
                    } else {
                        holder.spareIconCheck.setImageResource(R.drawable.spare_unselect);
                    }
                    notifyDataSetChanged();
                }
            });

            holder.quantitytext.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    //Utils.displayToast(context, quantitytext, Toast.LENGTH_SHORT);
                    return false;
                }
            });



        }


        if (!isSpare) {
            holder.spareIconCheck.setVisibility(View.GONE);
            holder.quantitytext.setVisibility(View.GONE);

        } else {

            holder.spareIconCheck.setVisibility(View.VISIBLE);



            holder.quantitytext.setVisibility(View.VISIBLE);

        }

        if (isCompleted) {
            holder.serviceCompletedIcon.setEnabled(false);
            holder.spareIconCheck.setEnabled(false);

        } else {

            holder.serviceCompletedIcon.setEnabled(true);
            holder.spareIconCheck.setEnabled(true);
        }


    }


    @Override
    public int getItemCount() {
        return spareLabourList.size();
    }


    public List<SpareLabour> getSpareLabourList() {
        return selectedSpareLabourList;
    }

    public void setSpareLabourList(List<SpareLabour> spareLabourList) {
        this.spareLabourList = spareLabourList;
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.descriptionEditText)
        TextView descriptionEditText;

        @BindView(R.id.spareIcon)
        ImageView spareIconCheck;

        @BindView(R.id.serviceCompletedIcon)
        ImageView serviceCompletedIcon;

        @BindView(R.id.quantity)
        EditText quantitytext;

        @BindView(R.id.selectItem)
        CheckBox selectItem;







        public WorkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
