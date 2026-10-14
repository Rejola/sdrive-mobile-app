package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.Description;
import com.creatah.automobile.dealer.modal.GstPercentage;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class EstimateAdapter1 extends RecyclerView.Adapter<EstimateAdapter1.EstimateViewHolder> {

    private List<Description> complaintsList;
    private Activity activity;
    private EstimationListener listener;
    private boolean isCompleted;
    private AppInfo appInfo;
    private String gstString;
    private String quanty;
    private String rate;
    private double amount;
    private double TotalAmount;

    private List<GstPercentage> gstPercentagesList;

    private List<String> gstPercentageString;

    public EstimateAdapter1(Activity activity, List<Description> complaintsList, EstimationListener listener, boolean isCompleted) {
        this.activity = activity;
        this.complaintsList = complaintsList;
        this.listener = listener;
        this.isCompleted = isCompleted;
        this.gstPercentageString = new ArrayList<>();

        appInfo = AppUtils.getAppInfo(activity.getApplication());


        if (appInfo != null && appInfo.getGstList() != null) {
            gstPercentagesList = appInfo.getGstList();
            for (int i = 0; i < gstPercentagesList.size(); i++) {
                gstPercentageString.add(gstPercentagesList.get(i).getGstPercentage());
            }
        }

    }


    @Override
    public EstimateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_estimates, parent, false);
        EstimateViewHolder viewHolder = new EstimateViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final EstimateViewHolder holder, int position) {
        final Description complaints = complaintsList.get(position);

        ArrayAdapter<String> gstAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, gstPercentageString);
        holder.gstSpinner.setAdapter(gstAdapter);

        holder.amountEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(15, 2)});

        if (complaints.getRate() != 0) {
            holder.amountEditText.setText(String.valueOf(complaints.getRate()));
        }
        holder.descriptionEditText.setText(complaints.getDescription());
        if (complaints.getQty() != 0) {
            holder.qtyEditText.setText(String.valueOf(complaints.getQty()));
        }

        holder.amountTextView.setText(activity.getString(R.string.rs).concat(String.valueOf(complaints.getAmount())));

        holder.amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    complaints.setRate(0);
                } else {
                    double rate = Double.valueOf(charSequence.toString());
                    String qty = holder.qtyEditText.getText().toString();
                    gstString = (String) holder.gstSpinner.getSelectedItem();

                    if (!TextUtils.isEmpty(qty)) {
                        double amount = Integer.valueOf(qty) * rate;
                        double gst = amount * (Double.parseDouble(gstString) / 100);
                        TotalAmount = amount + gst;
                        complaints.setAmount(TotalAmount);
                        holder.amountTextView.setText(activity.getString(R.string.rs).concat(String.valueOf(TotalAmount)));
                        listener.onUpdateItem();
                    }

                    complaints.setRate(rate);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.qtyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    complaints.setQty(0);
                } else {
                    int qty = Integer.valueOf(charSequence.toString());
                    String rate = holder.amountEditText.getText().toString();
                    gstString = (String) holder.gstSpinner.getSelectedItem();

                    if (!TextUtils.isEmpty(rate)) {
                        double amount = Double.parseDouble(rate) * qty;
                        double gst = amount * (Double.parseDouble(gstString) / 100);
                        TotalAmount = amount + gst;
                        complaints.setAmount(TotalAmount);
                        holder.amountTextView.setText(activity.getString(R.string.rs).concat(String.valueOf(TotalAmount)));
                        listener.onUpdateItem();
                    }
                    complaints.setQty(qty);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.gstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                gstString = String.valueOf(gstPercentagesList.get(i).getGstPercentage());

                quanty = holder.qtyEditText.getText().toString();
                rate = holder.amountEditText.getText().toString();


                if (!TextUtils.isEmpty(quanty) ) {
                    int a = Integer.valueOf(quanty);
                    double b = Double.parseDouble(rate);
                    gstString = (String) holder.gstSpinner.getSelectedItem();
                    complaints.setRate(b);
                    complaints.setQty(a);
                    amount = a * b;
                    double gst = amount * (Double.parseDouble(gstString) / 100);
                    TotalAmount = amount + gst;
                    complaints.setAmount(TotalAmount);
                    holder.amountTextView.setText(activity.getString(R.string.rs).concat(String.valueOf(TotalAmount)));

                }


                if (!TextUtils.isEmpty(rate)) {
                    gstString = (String) holder.gstSpinner.getSelectedItem();
                    double amount = Double.parseDouble(rate) * Integer.valueOf(quanty);
                    double gst = amount * Double.parseDouble(gstString) / 100;
                    TotalAmount = amount + gst;
                    complaints.setAmount(TotalAmount);
                    holder.amountTextView.setText(activity.getString(R.string.rs).concat(String.valueOf(TotalAmount)));
                    listener.onUpdateItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    public interface EstimationListener {

        void onUpdateItem();

        void onDeletedItem(Description description);

    }


    public class EstimateViewHolder extends RecyclerView.ViewHolder {
        private TextView descriptionEditText;
        private EditText amountEditText;
        private EditText qtyEditText;
        private TextView amountTextView;
        private Spinner gstSpinner;


        //  private ImageView deleteImageView;

        public EstimateViewHolder(View itemView) {
            super(itemView);
            descriptionEditText = (TextView) itemView.findViewById(R.id.descriptionTextView);
            amountEditText = (EditText) itemView.findViewById(R.id.amountEditText);
            qtyEditText = (EditText) itemView.findViewById(R.id.quantityEditText);
            amountTextView = (TextView) itemView.findViewById(R.id.amountTextView);
            gstSpinner = (Spinner) itemView.findViewById(R.id.gstSpinner);
        }
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }


    }

}
