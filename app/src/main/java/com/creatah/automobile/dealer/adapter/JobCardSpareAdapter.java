package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.modal.SpareLabour;
import com.creatah.automobile.dealer.utils.AppUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobCardSpareAdapter extends RecyclerView.Adapter<JobCardSpareAdapter.DescriptionViewHolder> {

    private Activity activity;
    private List<SpareLabour> spareLabourList;
    private SpareListener spareListener;
    private boolean isDelivered;
    private boolean isEnable;


    public JobCardSpareAdapter(Activity activity, List<SpareLabour> spareLabourList, SpareListener spareListener, boolean isDelivered) {
        this.activity = activity;
        this.spareLabourList = spareLabourList;
        this.spareListener = spareListener;
        this.isDelivered = isDelivered;
    }


    @Override
    public DescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_spare_details, parent, false);
        DescriptionViewHolder viewHolder = new DescriptionViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final DescriptionViewHolder holder, final int position) {

        if(!isEnable) {
            holder.quantityEditText.setFocusable(false);
            holder.quantityEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.quantityEditText.setClickable(false); // user navigates with wheel and selects widget


            holder.amountEditText.setFocusable(false);
            holder.amountEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.amountEditText.setClickable(false); // user navigates with wheel and selects widget


            holder.cGSTPercentageEditText.setFocusable(false);
            holder.cGSTPercentageEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.cGSTPercentageEditText.setClickable(false); // user navigates with wheel and selects widget

            holder.sGSTPercentageEditText.setFocusable(false);
            holder.sGSTPercentageEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.sGSTPercentageEditText.setClickable(false); //
        }

        final SpareLabour spareLabour = spareLabourList.get(position);

        holder.amountEditTextListener.updatePosition(position, holder, holder.amountEditText);
        holder.quantityEditTextListener.updatePosition(position, holder, holder.quantityEditText);
        holder.cGSTEditTextListener.updatePosition(position, holder, holder.cGSTPercentageEditText);
        holder.sGSTEditTextListener.updatePosition(position, holder, holder.sGSTPercentageEditText);

        holder.spareNameTextView.setText(spareLabour.getItemName());


        double qty = spareLabour.getQty();

        if (spareLabour.getQty() % 1 == 0) {
            holder.quantityEditText.setText(String.valueOf((int) spareLabour.getQty()));
            holder.quantityEditText.setSelection(holder.quantityEditText.length());
        } else {
//            holder.quantityEditText.setText(String.format("%.2f",spareLabour.getQty()));
//            holder.quantityEditText.setSelection(holder.quantityEditText.length());
            holder.quantityEditText.setText(String.valueOf(spareLabour.getQty()));
        }

        if (spareLabour.getQty() == 0) {
            holder.quantityEditText.setText("1");
            holder.quantityEditText.setSelection(holder.quantityEditText.length());
            qty = 1;
        }


        if (spareLabour.getItemCGSTPercentage() % 1 == 0) {
            holder.cGSTPercentageEditText.setText(String.valueOf((int) spareLabour.getItemCGSTPercentage()));

            holder.cGSTPercentageEditText.setSelection(holder.cGSTPercentageEditText.length());
        } else {
            holder.cGSTPercentageEditText.setText(String.format("%.2f",spareLabour.getItemCGSTPercentage()));
            holder.cGSTPercentageEditText.setSelection(holder.cGSTPercentageEditText.length());
        }
        if (spareLabour.getItemSGSTPercentage() % 1 == 0) {
            holder.sGSTPercentageEditText.setText(String.valueOf((int) spareLabour.getItemSGSTPercentage()));
            holder.sGSTPercentageEditText.setSelection(holder.sGSTPercentageEditText.length());
        } else {
            holder.sGSTPercentageEditText.setText(String.format("%.2f" ,spareLabour.getItemSGSTPercentage()));
            holder.sGSTPercentageEditText.setSelection(holder.sGSTPercentageEditText.length());
        }

        if (spareLabour.getItemAmount() % 1 == 0) {
            holder.amountEditText.setText(String.valueOf((int) spareLabour.getItemAmount()));
            holder.amountEditText.setSelection(holder.amountEditText.length());
        } else {
            holder.amountEditText.setText(String.format("%.2f",spareLabour.getItemAmount()));
            holder.amountEditText.setSelection(holder.amountEditText.length());
        }


        double sGSTPercentage = getDoubleFromEditText(holder.sGSTPercentageEditText);
        double cGSTPercentage = getDoubleFromEditText(holder.cGSTPercentageEditText);
        double amount = getDoubleFromEditText(holder.amountEditText);
        setUpAmount(holder, qty, amount, cGSTPercentage, sGSTPercentage, spareLabour);

        if (isDelivered) {
            holder.deleteImageView.setVisibility(View.GONE);
        } else {
            holder.deleteImageView.setVisibility(View.VISIBLE);
        }

        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 2;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([0-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        InputFilter qtyFilter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 5;
            final int maxDigitsAfterDecimalPoint = 1;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([0-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        InputFilter filterAmount = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 8;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([0-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        holder.cGSTPercentageEditText.setFilters(new InputFilter[]{filter});
        holder.sGSTPercentageEditText.setFilters(new InputFilter[]{filter});
        holder.amountEditText.setFilters(new InputFilter[]{filterAmount});
        holder.quantityEditText.setFilters(new InputFilter[]{qtyFilter});


        boolean canEdit = AppUtils.getAppInfo(activity.getApplication()).isEditJobCardPrice();
        if (!canEdit || isDelivered) { // disable editing password

            holder.quantityEditText.setFocusable(false);
            holder.quantityEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.quantityEditText.setClickable(false); // user navigates with wheel and selects widget


            holder.amountEditText.setFocusable(false);
            holder.amountEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.amountEditText.setClickable(false); // user navigates with wheel and selects widget


            holder.cGSTPercentageEditText.setFocusable(false);
            holder.cGSTPercentageEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.cGSTPercentageEditText.setClickable(false); // user navigates with wheel and selects widget

            holder.sGSTPercentageEditText.setFocusable(false);
            holder.sGSTPercentageEditText.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            holder.sGSTPercentageEditText.setClickable(false); // user navigates with wheel and selects widget

        } else { // enable editing of password

            holder.quantityEditText.setFocusable(true);
            holder.quantityEditText.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
            holder.quantityEditText.setClickable(true); // user navigates with wheel and selects widget


            holder.amountEditText.setFocusable(true);
            holder.amountEditText.setFocusableInTouchMode(true);
            holder.amountEditText.setClickable(true);

            holder.sGSTPercentageEditText.setFocusable(true);
            holder.sGSTPercentageEditText.setFocusableInTouchMode(true);
            holder.sGSTPercentageEditText.setClickable(true);


            holder.cGSTPercentageEditText.setFocusable(true);
            holder.cGSTPercentageEditText.setFocusableInTouchMode(true);
            holder.cGSTPercentageEditText.setClickable(true);
        }


        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spareListener.onRemoveSpare(spareLabour);
            }
        });
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    private double getDoubleFromEditText(EditText editText) {
        return Double.valueOf((editText.getText().toString().equals("null") || editText.getText().toString().equals("") || editText.getText().toString().equals(".")) ? "0" : editText.getText().toString());
    }

    private double getIntValueFromEditText(EditText editText) {

        return Double.valueOf((editText.getText().toString().equals("null") || editText.getText().toString().equals("") || editText.getText().toString().equals(".")) ? "0" : editText.getText().toString());
    }


    private void setUpAmount(DescriptionViewHolder holder, double qty, double amount, double cGSTPercentage, double sGSTPercentage, SpareLabour spareLabour) {

        double rate = amount * qty;

        double perCGSTAmount = amount * cGSTPercentage / 100;
        double perSGSTAmount = amount * sGSTPercentage / 100;

        double cGSTAmount = perCGSTAmount * qty;
        double sGSTAmount = perSGSTAmount * qty;
        double totalAmount = rate + cGSTAmount + sGSTAmount;
        spareLabour.setItemCGSTPercentage(cGSTPercentage);
        spareLabour.setItemSGSTPercentage(sGSTPercentage);
        spareLabour.setItemCGSTAmount(cGSTAmount);
        spareLabour.setItemSGSTAmount(sGSTAmount);
        spareLabour.setPerCGSTAmount(perCGSTAmount);
        spareLabour.setPerSGSTAmount(perSGSTAmount);
        spareLabour.setQtyTotalAmount(totalAmount);
        spareLabour.setItemAmount(amount);
        spareLabour.setQty(qty);
        holder.cGSTAmountTextView.setText(" - " + activity.getString(R.string.rs) + " " + String.format("%.2f", cGSTAmount));
        holder.sGSTAmountTextView.setText(" - " + activity.getString(R.string.rs) + " " + String.format("%.2f", sGSTAmount));
        holder.totalAmountTextView.setText(activity.getString(R.string.rs) + " " + String.format("%.2f", totalAmount));
//        holder.quantityEditText.setText(String.valueOf(qty));

    }

    public List<SpareLabour> getSpareList() {
        return spareLabourList;
    }

    public void setSpareList(List<SpareLabour> spareLabourList) {
        this.spareLabourList = spareLabourList;
    }

    @Override
    public int getItemCount() {
        return spareLabourList.size();
    }


    public class DescriptionViewHolder extends RecyclerView.ViewHolder implements TextCallBackListener {


        private TextView spareNameTextView;
        private EditText quantityEditText;
        private EditText amountEditText;
        private TextView totalAmountTextView;
        private ImageView deleteImageView;

        private EditText cGSTPercentageEditText;
        private EditText sGSTPercentageEditText;
        private TextView cGSTAmountTextView;
        private TextView sGSTAmountTextView;

        public CustomEtListener amountEditTextListener;
        public CustomEtListener quantityEditTextListener;
        public CustomEtListener cGSTEditTextListener;
        public CustomEtListener sGSTEditTextListener;


        public DescriptionViewHolder(View itemView) {
            super(itemView);
            spareNameTextView = (TextView) itemView.findViewById(R.id.spareNameTextView);
            quantityEditText = (EditText) itemView.findViewById(R.id.quantityEditText);
            amountEditText = itemView.findViewById(R.id.amountTextView);
            totalAmountTextView = (TextView) itemView.findViewById(R.id.TotalAmountTextView);
            deleteImageView = itemView.findViewById(R.id.delete);

            cGSTPercentageEditText = itemView.findViewById(R.id.cGSTPercentageEditText);
            sGSTPercentageEditText = itemView.findViewById(R.id.sGSTPercentageEditText);
            cGSTAmountTextView = itemView.findViewById(R.id.cGSTAmount);
            sGSTAmountTextView = itemView.findViewById(R.id.sGSTAmount);

            amountEditTextListener = new CustomEtListener();
            quantityEditTextListener = new CustomEtListener();
            cGSTEditTextListener = new CustomEtListener();
            sGSTEditTextListener = new CustomEtListener();
            amountEditText.addTextChangedListener(amountEditTextListener);
            quantityEditText.addTextChangedListener(quantityEditTextListener);
            cGSTPercentageEditText.addTextChangedListener(cGSTEditTextListener);
            sGSTPercentageEditText.addTextChangedListener(sGSTEditTextListener);
        }

        @Override
        public void updateText(String val) {

        }
    }


    public interface SpareListener {

        void onUpdateItem();

        void onRemoveSpare(SpareLabour spareLabour);

    }

    public interface TextCallBackListener {
        public void updateText(String val);
    }

    /**
     * Custom class which implements Text Watcher
     */
    private class CustomEtListener implements TextWatcher {
        private int position;
        TextCallBackListener textCallBackListener;
        DescriptionViewHolder holder;
        EditText editText;

        /**
         * Updates the position according to onBindViewHolder
         *
         * @param position - position of the focused item
         * @param pa       - object of ProductListHolder Class
         */
        public void updatePosition(int position, DescriptionViewHolder pa, EditText editText) {
            this.position = position;
            // assigning it to the instance of the CallBackListener
            textCallBackListener = (TextCallBackListener) pa;
            this.holder = pa;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            SpareLabour spareLabour = spareLabourList.get(position);
            if(editText.getId() == R.id.amountTextView) {
                double amount = 0;
                if (TextUtils.isEmpty(charSequence) || charSequence.equals(".")) {
                    //holder.amountEditText.setText("0.0");
                } else {
                    amount = getDoubleFromEditText(editText);
                }
                double cGSTPercentage = spareLabour.getItemCGSTPercentage(); //getDoubleFromEditText(holder.cGSTPercentageEditText);
                double sGSTPercentage = spareLabour.getItemSGSTPercentage(); //getDoubleFromEditText(holder.sGSTPercentageEditText);
                double qty = spareLabour.getQty(); //getDoubleFromEditText(holder.quantityEditText);
                setUpAmount(holder, qty, amount, cGSTPercentage, sGSTPercentage, spareLabour);
                spareListener.onUpdateItem();
            }

            if(editText.getId() == R.id.quantityEditText){
                double qty = 1;
                if (TextUtils.isEmpty(charSequence)) {
                    spareLabour.setQty(1);
                    //holder.quantityEditText.setText("1");
                } else {
                    qty = getDoubleFromEditText(editText);
                }
                double cGSTPercentage = spareLabour.getItemCGSTPercentage();//getDoubleFromEditText(holder.cGSTPercentageEditText);
                double sGSTPercentage = spareLabour.getItemSGSTPercentage();//getDoubleFromEditText(holder.sGSTPercentageEditText);
                double amount = spareLabour.getItemAmount(); // getDoubleFromEditText(holder.amountEditText);
                setUpAmount(holder, qty, amount, cGSTPercentage, sGSTPercentage, spareLabour);
                spareListener.onUpdateItem();
            }

            if(editText.getId() == R.id.cGSTPercentageEditText){
                double cGSTPercentage = 0;
                if (TextUtils.isEmpty(charSequence) || charSequence.equals(".")) {
                    //holder.cGSTPercentageEditText.setText("0.0");
                } else {

                    cGSTPercentage = getDoubleFromEditText(editText);
                }
                double sGSTPercentage = spareLabour.getItemSGSTPercentage(); //getDoubleFromEditText(holder.sGSTPercentageEditText);
                double amount = spareLabour.getItemAmount(); //getDoubleFromEditText(holder.amountEditText);
                double qty = spareLabour.getQty(); //getDoubleFromEditText(holder.quantityEditText);
                setUpAmount(holder, qty, amount, cGSTPercentage, sGSTPercentage, spareLabour);
                spareListener.onUpdateItem();

            }

            if(editText.getId() == R.id.sGSTPercentageEditText){
                double sGSTPercentage = 0;
                if (TextUtils.isEmpty(charSequence) || charSequence.equals(".")) {
                    // holder.sGSTPercentageEditText.setText("0.0");
                } else {
                    sGSTPercentage = getDoubleFromEditText(editText);
                }
                double cGSTPercentage =  spareLabour.getItemCGSTPercentage(); //getDoubleFromEditText(holder.cGSTPercentageEditText);
                double amount = spareLabour.getItemAmount(); //getDoubleFromEditText(holder.amountEditText);
                double qty = spareLabour.getQty(); // getDoubleFromEditText(holder.quantityEditText);
                setUpAmount(holder, qty, amount, cGSTPercentage, sGSTPercentage, spareLabour);
                spareListener.onUpdateItem();
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            editText.requestFocus();
        }

    }




}
