package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;

import java.util.List;

/**
 * Created by creatah1 on 03-05-2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private TypedArray imageArray;
    private TypedArray nameArray;
    private TypedArray countArray;
    private Context activity;
    private MenuListener listener;
    private List<String> stringList;

    public HomeAdapter(Activity activity, List<String> stringList,MenuListener listener) {
        this.imageArray = activity.getResources().obtainTypedArray(R.array.menuIcons);
        this.nameArray =  activity.getResources().obtainTypedArray(R.array.menuName);
        this.countArray=activity.getResources().obtainTypedArray(R.array.countText);
        this.activity = activity;
        this.stringList=stringList;
        this.listener = listener;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_home, parent, false);
        HomeViewHolder viewHolder = new HomeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, final int position) {

        holder.nameTextView.setText(nameArray.getString(position));
        holder.iconImageView.setImageResource(imageArray.getResourceId(position, -1));
        holder.countTextView.setText(stringList.get(position));

        holder.menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMenuClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameArray.length();
    }

    public interface MenuListener{
        void onMenuClicked(int Pos);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconImageView;
        private TextView nameTextView;
        private CardView menuLayout;
        private TextView countTextView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            iconImageView = (ImageView) itemView.findViewById(R.id.icon);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            menuLayout = (CardView) itemView.findViewById(R.id.menuLayout);
            countTextView = (TextView) itemView.findViewById(R.id.countTextView);


        }
    }
}
