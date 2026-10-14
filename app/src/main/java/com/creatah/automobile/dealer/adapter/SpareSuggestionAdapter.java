package com.creatah.automobile.dealer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.SpareLabour;

import java.util.ArrayList;
import java.util.List;


public class SpareSuggestionAdapter extends ArrayAdapter<SpareLabour> implements Filterable {

    private Context mContext;
    private List<SpareLabour> mSuggestionList;
    private List<SpareLabour> mFilteredSuggestionList;

    public SpareSuggestionAdapter(Context context, List<SpareLabour> suggestionList) {
        super(context, -1);
        mContext = context;
        mSuggestionList = suggestionList;
        mFilteredSuggestionList = mSuggestionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.suggestion_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mSuggestionTextView = (TextView) convertView.findViewById(R.id.suggestion_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SpareLabour searchSuggestion = mFilteredSuggestionList.get(position);
        viewHolder.mSuggestionTextView.setText((searchSuggestion.getItemName()));
        return convertView;
    }

    public static class ViewHolder {
        private TextView mSuggestionTextView;
    }

    @Override
    public int getCount() {
        return mFilteredSuggestionList.size();
    }

    @Override
    public SpareLabour getItem(int position) {
        return mFilteredSuggestionList.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)     {
                final FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    constraint = constraint.toString().toLowerCase();
                    List<SpareLabour> tempFilteredList = new ArrayList<>();
                    for (SpareLabour suggestion : mSuggestionList) {
                        String name = suggestion.getItemName();
                        if (name != null && name.toLowerCase().contains(constraint)) {
                            tempFilteredList.add(suggestion);
                        }
                    }
                    results.values = tempFilteredList;
                    results.count = tempFilteredList.size();
                } else {
                    results.values = mSuggestionList;
                    results.count = mSuggestionList.size();
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    mFilteredSuggestionList = (ArrayList<SpareLabour>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}