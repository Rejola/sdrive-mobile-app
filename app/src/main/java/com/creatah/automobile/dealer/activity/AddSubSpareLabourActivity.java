package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.theme.activity.DialogActivity;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.SubSpareLabourAdapter;
import com.creatah.automobile.dealer.modal.SpareLabour;
import com.creatah.automobile.dealer.utils.AppConstants;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSubSpareLabourActivity extends DialogActivity {

    @BindView(R.id.spareLabourRecyclerView)
    RecyclerView spareLabourRecyclerView;

    @BindView(R.id.nameTextView)
    TextView titleTextView;

    @BindView(R.id.closeImage)
    ImageView closeImage;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    private SpareLabour spareLabour;

    private String spareLabourType;

    private String addRemoveType;

    private List<SpareLabour> itemList;

    private SubSpareLabourAdapter adapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__sub_spare_labour_layout);
        ButterKnife.bind(this);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);
        getWindow().setLayout(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

        context = this;
        getBundle();
        spareLabourRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        if(spareLabour!=null) {
            titleTextView.setText(spareLabour.getItemName());
            itemList = spareLabour.getItemList();
            if(itemList.size()>0){
                adapter = new SubSpareLabourAdapter(context, itemList, addRemoveType);
                spareLabourRecyclerView.setVisibility(View.VISIBLE);
                noResultFound.setVisibility(View.GONE);
                spareLabourRecyclerView.setAdapter(adapter);
            }
            else {

                spareLabourRecyclerView.setVisibility(View.GONE);
                noResultFound.setVisibility(View.VISIBLE);
            }
        }



        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);
                finish();

            }
        });


    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null && bundle.containsKey(AppConstants.SPARE_LABOUR_EXTRA)){
            spareLabour = (SpareLabour) bundle.getSerializable(AppConstants.SPARE_LABOUR_EXTRA);
            spareLabourType = bundle.getString(AppConstants.SPARE_LABOUR_TYPE_EXTRA);
            addRemoveType = bundle.getString(AppConstants.ADD_REMOVE_EXTRA);
        }
    }

    @OnClick(R.id.submitButton)
    public void onSubmitClicked(){
        spareLabour.setItemList(adapter.getItemList());
        Intent intent = new Intent();
        intent.putExtra(AppConstants.ADD_REMOVE_EXTRA, addRemoveType);
        intent.putExtra(AppConstants.SPARE_LABOUR_TYPE_EXTRA, spareLabourType);
        intent.putExtra(AppConstants.SPARE_LABOUR_EXTRA, spareLabour);
        setResult(RESULT_OK, intent);
        finish();
    }
}
