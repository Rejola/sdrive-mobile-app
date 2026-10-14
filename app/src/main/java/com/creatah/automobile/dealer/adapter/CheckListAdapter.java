package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
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

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckViewHolder> {

    private Activity activity;
    private List<CheckOut> checkOutList;
    private boolean isCompleted;

    public CheckListAdapter(Activity activity, List<CheckOut> checkOutList, boolean isCompleted) {
        this.activity = activity;
        this.checkOutList = checkOutList;
        this.isCompleted = isCompleted;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_check_list, parent, false);
        CheckViewHolder viewHolder = new CheckViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckViewHolder holder, int position) {

        final CheckOut checkOut = checkOutList.get(position);
        holder.titleTextView.setText(checkOut.getTitle());


        if (checkOut.isChecked()) {
            holder.checkBoxImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_square_select));

            if (checkOut.getType().equals(AppConstants.CHECKOUT_MULTIPLE_KEY)) {
                holder.checkListLayout.setVisibility(View.VISIBLE);
                switch (checkOut.getCount()) {

                    case 0:
                        holder.oneImageView.setImageResource(R.drawable.ic_selected);
                        holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                        holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                        break;
                    case 1:
                        holder.oneImageView.setImageResource(R.drawable.ic_selected);
                        holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                        holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                        break;
                    case 2:
                        holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                        holder.twoImageView.setImageResource(R.drawable.ic_selected);
                        holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                        break;
                    case 3:
                        holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                        holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                        holder.threeImageView.setImageResource(R.drawable.ic_selected);
                        holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                        break;
                    case 4:
                        holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                        holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                        holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fourImageView.setImageResource(R.drawable.ic_selected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                        break;
                    case 5:
                        holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                        holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                        holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                        holder.fiveImageView.setImageResource(R.drawable.ic_selected);
                        break;

                }
            } else {
                holder.checkListLayout.setVisibility(View.GONE);
                holder.oneImageView.setImageResource(R.drawable.ic_selected);
                holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
            }
        } else {
            holder.checkBoxImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_square_unselect));

            holder.checkListLayout.setVisibility(View.GONE);
            holder.oneImageView.setImageResource(R.drawable.ic_selected);
            holder.twoImageView.setImageResource(R.drawable.ic_unselected);
            holder.threeImageView.setImageResource(R.drawable.ic_unselected);
            holder.fourImageView.setImageResource(R.drawable.ic_unselected);
            holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
        }

        if(!isCompleted) {
            holder.checkBoxImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkOut.isChecked()) {
                        checkOut.setChecked(false);
                        holder.checkBoxImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_square_unselect));
                        holder.checkListLayout.setVisibility(View.GONE);
                    } else {
                        checkOut.setChecked(true);
                        holder.checkBoxImageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_square_select));
                        if (checkOut.getType().equals(AppConstants.CHECKOUT_MULTIPLE_KEY)) {
                            holder.checkListLayout.setVisibility(View.VISIBLE);
                            checkOut.setCount(1);
                        } else {
                            holder.checkListLayout.setVisibility(View.GONE);
                        }
                    }


                }
            });


            holder.oneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.oneImageView.setImageResource(R.drawable.ic_selected);
                    holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                    holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                    checkOut.setCount(1);
                }
            });


            holder.twoLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                    holder.twoImageView.setImageResource(R.drawable.ic_selected);
                    holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                    checkOut.setCount(2);
                }
            });

            holder.threeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                    holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                    holder.threeImageView.setImageResource(R.drawable.ic_selected);
                    holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                    checkOut.setCount(3);
                }
            });

            holder.fourLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                    holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                    holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fourImageView.setImageResource(R.drawable.ic_selected);
                    holder.fiveImageView.setImageResource(R.drawable.ic_unselected);
                    checkOut.setCount(4);
                }
            });

            holder.fiveLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.oneImageView.setImageResource(R.drawable.ic_unselected);
                    holder.twoImageView.setImageResource(R.drawable.ic_unselected);
                    holder.threeImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fourImageView.setImageResource(R.drawable.ic_unselected);
                    holder.fiveImageView.setImageResource(R.drawable.ic_selected);
                    checkOut.setCount(5);
                }
            });
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

        @BindView(R.id.checkListLayout)
        LinearLayout checkListLayout;

        @BindView(R.id.oneLayout)
        LinearLayout oneLayout;

        @BindView(R.id.twoLayout)
        LinearLayout twoLayout;

        @BindView(R.id.threeLayout)
        LinearLayout threeLayout;

        @BindView(R.id.fourLayout)
        LinearLayout fourLayout;

        @BindView(R.id.fiveLayout)
        LinearLayout fiveLayout;

        @BindView(R.id.oneImageView)
        AppCompatImageView oneImageView;

        @BindView(R.id.twoImageView)
        AppCompatImageView twoImageView;

        @BindView(R.id.threeImageView)
        AppCompatImageView threeImageView;

        @BindView(R.id.fourImageView)
        AppCompatImageView fourImageView;

        @BindView(R.id.fiveImageView)
        AppCompatImageView fiveImageView;

        @BindView(R.id.oneText)
        TextView oneText;

        @BindView(R.id.twoText)
        TextView twoText;

        @BindView(R.id.threeText)
        TextView threeText;

        @BindView(R.id.fourText)
        TextView fourText;

        @BindView(R.id.fiveText)
        TextView fiveText;

        @BindView(R.id.item)
        TextView titleTextView;

        @BindView(R.id.checkboxImageView)
        ImageView checkBoxImageView;

        public CheckViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
