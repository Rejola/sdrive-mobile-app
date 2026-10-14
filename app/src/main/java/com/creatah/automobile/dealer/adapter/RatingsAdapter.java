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
import com.creatah.automobile.dealer.modal.Rating;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 06-04-2018.
 */

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.RatingsViewHolder> {
    private Activity activity;
    private List<Rating> ratingLists;
   /* private RatingsClickListener ratingsClickListener;*/

    public RatingsAdapter(Activity activity, List<Rating> ratingLists/*,RatingsClickListener ratingsClickListener*/) {
        this.activity = activity;
        this.ratingLists = ratingLists;
        /*this.ratingsClickListener = ratingsClickListener;*/

    }

    @Override
    public RatingsAdapter.RatingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ratings, parent, false);
        RatingsAdapter.RatingsViewHolder viewHolder = new RatingsAdapter.RatingsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RatingsViewHolder ratingsViewHolder, int i) {

    }


    public int getItemCount() {
        return ratingLists.size();
    }

    public void setRatingList(List<Rating> ratingList) {
    }

   /* public interface RatingsClickListener {
    }*/

    public class RatingsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView nameTextView;

        @BindView(R.id.mobileNumberTextView)
        TextView addressTextView;

        @BindView(R.id.serviceNameTextView)
        TextView serviceNameTextView;

        @BindView(R.id.dateTextView)
        TextView dateTextView;

        @BindView(R.id.feedbackTextView)
        TextView feedbackTextView;

        @BindView(R.id.ratingsLayout)
        LinearLayout ratingsLayout;

        public RatingsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
