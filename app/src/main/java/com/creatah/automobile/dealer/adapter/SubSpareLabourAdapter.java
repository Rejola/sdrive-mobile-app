package com.creatah.automobile.dealer.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.SpareLabour;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubSpareLabourAdapter extends RecyclerView.Adapter<SubSpareLabourAdapter.SpareLabourView> {

    private List<SpareLabour> itemList;
    private Context context;
    private String addRemoveType;

    public SubSpareLabourAdapter(Context context, List<SpareLabour> itemList, String addRemoveType) {
        this.itemList = itemList;
        this.context = context;
        this.addRemoveType = addRemoveType;
    }

    @NonNull
    @Override
    public SpareLabourView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout_sub_spare_labour, viewGroup, false);
        SpareLabourView spareLabourView = new SpareLabourView(view);
        return spareLabourView;
    }

    @Override
    public void onBindViewHolder(@NonNull SpareLabourView spareLabourView, int i) {

        final SpareLabour spareLabour = itemList.get(i);

        spareLabourView.titleTextView.setText(spareLabour.getItemName());
        /*if (addRemoveType.equals(AppConstants.ADD_TYPE)) {
            spareLabourView.checkBox.setChecked(spareLabour.isAdded());
            spareLabourView.checkBox.setClickable(true);
            spareLabourView.checkBox.setEnabled(true);
        } else*/ if (addRemoveType.equals(AppConstants.ADD_TYPE) && !spareLabour.isCanRemove()) {
            spareLabourView.checkBox.setChecked(true);
            spareLabourView.checkBox.setClickable(false);
            spareLabourView.checkBox.setEnabled(false);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // unchecked
                            new int[]{android.R.attr.state_checked}  // checked
                    },
                    new int[]{
                            ContextCompat.getColor(context, R.color.colorPrimary),
                            ContextCompat.getColor(context, R.color.colorPrimary)
                    }
            );

            if (Build.VERSION.SDK_INT < 21) {
                CompoundButtonCompat.setButtonTintList(spareLabourView.checkBox, colorStateList);
            } else {
                spareLabourView.checkBox.setButtonTintList(colorStateList);
            }

            spareLabourView.titleTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else if (addRemoveType.equals(AppConstants.REMOVE_TYPE) && !spareLabour.isCanRemove()) {
            spareLabourView.checkBox.setChecked(false);
            spareLabourView.checkBox.setClickable(false);
            spareLabourView.checkBox.setEnabled(false);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // unchecked
                            new int[]{android.R.attr.state_checked}  // checked
                    },
                    new int[]{
                            ContextCompat.getColor(context, android.R.color.darker_gray),
                            ContextCompat.getColor(context, android.R.color.darker_gray)
                    }
            );

            if (Build.VERSION.SDK_INT < 21) {
                CompoundButtonCompat.setButtonTintList(spareLabourView.checkBox, colorStateList);
            } else {
                spareLabourView.checkBox.setButtonTintList(colorStateList);
            }
            spareLabourView.titleTextView.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        } else if ((addRemoveType.equals(AppConstants.REMOVE_TYPE) || addRemoveType.equals(AppConstants.ADD_TYPE)) && spareLabour.isCanRemove()) {
            spareLabourView.checkBox.setChecked(spareLabour.isAdded());
            spareLabourView.checkBox.setClickable(true);
            spareLabourView.checkBox.setEnabled(true);
            spareLabourView.titleTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked}, // unchecked
                            new int[]{android.R.attr.state_checked}  // checked
                    },
                    new int[]{
                            ContextCompat.getColor(context, R.color.colorPrimary),
                            ContextCompat.getColor(context, R.color.colorPrimary)
                    }
            );
            if (Build.VERSION.SDK_INT < 21) {
                CompoundButtonCompat.setButtonTintList(spareLabourView.checkBox, colorStateList);
            } else {
                spareLabourView.checkBox.setButtonTintList(colorStateList);
            }
        }

        spareLabourView.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    spareLabour.setAdded(true);
                } else {
                    spareLabour.setAdded(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<SpareLabour> getItemList() {
        return itemList;
    }

    public class SpareLabourView extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        TextView titleTextView;

        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public SpareLabourView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
