package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;


/**
 * Created by karthikeyan on 27-07-2016.
 */
public class NavigationAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private TypedArray typedArray;
    private TypedArray navItemArray;

    ViewHolder mViewHolder;

    OnNavigationRecyclerClickListener mCallbak;

    public NavigationAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(this.activity);
        this.typedArray = activity.getResources().obtainTypedArray(R.array.navDrawerImageDrawable);
        this.navItemArray = activity.getResources().obtainTypedArray(R.array.nav_drawer_items);
    }

    public interface OnNavigationRecyclerClickListener {
        public void onNavigationItemsClicked();
    }

    @Override
    public int getCount() {
        return navItemArray.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_row, null);
            mViewHolder = new ViewHolder();
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.rowText);
        mViewHolder.tvTitle.setText(navItemArray.getString(position));

        mViewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.rowIcon);
        mViewHolder.ivIcon.setImageResource(typedArray.getResourceId(position, -1));
        mViewHolder.rowLL = (LinearLayout) convertView.findViewById(R.id.navigation_rowLL);

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
        LinearLayout rowLL;
    }
}