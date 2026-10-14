package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.VehicleReadyAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.DescriptionListResponse;
import com.creatah.automobile.dealer.modal.SpareLabour;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by santhana on 27/04/18.
 */

public class BillAmountActivity extends BaseActivity {

    @BindView(R.id.spareRecyclerView)
    RecyclerView spareRecyclerView;

    @BindView(R.id.labourRecyclerView)
    RecyclerView labourRecyclerView;

    @BindView(R.id.labouTextView)
    TextView labourTextView;

    @BindView(R.id.labourCGstCost)
    TextView labourGstCost;

    @BindView(R.id.spareTextView)
    TextView spareTextView;

    @BindView(R.id.totalTextView)
    TextView totalTextView;

    @BindView(R.id.billLayout)
    LinearLayout billLayout;

    @BindView(R.id.noResultFound)
    TextView noResultFound;

    @BindView(R.id.addSpareLayout)
    LinearLayout spareLayout;

    @BindView(R.id.addLabourLayout)
    LinearLayout labourLayout;


    @BindView(R.id.sGstLabourText)
    TextView sGstLabourText;

    @BindView(R.id.sGStspareCostTextView)
    TextView sGStspareCostTextView;

    @BindView(R.id.spareCGstCost)
    TextView spareCGstCost;



    private Context context;

    private VehicleReadyAdapter labourAdapter;

    private VehicleReadyAdapter spareAdapter;

    private DealerBooking booking;

    private List<SpareLabour> labourList = new ArrayList<>();

    private List<SpareLabour> spareList = new ArrayList<>();

    private boolean isService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_amount);
        setTitle("Bill Summary");
        ButterKnife.bind(this);
        context = this;

        getBundle();

        labourRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        labourAdapter = new VehicleReadyAdapter(BillAmountActivity.this, labourList);
        labourRecyclerView.setAdapter(labourAdapter);

        spareRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        spareAdapter = new VehicleReadyAdapter(BillAmountActivity.this, spareList);
        spareRecyclerView.setAdapter(spareAdapter);
        getWorkList();
    }


    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.BILL_AMOUNT_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.BILL_AMOUNT_EXTRA);
        }
    }

    private void getWorkList() {


        if (DeviceManager.isDeviceOffline(context)) {
            billLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.internet_failed_toast));
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("jobcard_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("page_no", String.valueOf(1));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());

        if (!isService) {

            isService = true;

            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            ServiceManager.getServiceSpareLabourList(context, requestParams, new ServiceResponseListener<DescriptionListResponse>() {
                        @Override
                        public void onSuccess(DescriptionListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {

                                billLayout.setVisibility(View.VISIBLE);

                                spareTextView.setText(getString(R.string.rs).concat(" ").concat(String.format("%.2f", response.getSpareCost())));
                                labourTextView.setText(getString(R.string.rs).concat(" ").concat(String.format("%.2f", response.getLabourCost())));
                                spareCGstCost.setText(getString(R.string.rs).concat(" ")+ String.format("%.2f", response.getSpareCGSTCost()));
                                labourGstCost.setText(getString(R.string.rs).concat(" ")+ String.format("%.2f", response.getLabourCGSTCost()));
                                sGstLabourText.setText(getString(R.string.rs).concat(" ")+ String.format("%.2f", response.getLabourSGSTCost()));
                                sGStspareCostTextView.setText(getString(R.string.rs).concat(" ")+ String.format("%.2f", response.getSpareSGSTCost()));


                                if(response.getFinalCost()!=0) {
                                    totalTextView.setText(getString(R.string.rs).concat(" ").concat(String.format("%.2f", response.getFinalCost())));
                                }else {
                                    totalTextView.setText(getString(R.string.rs).concat(" ").concat(String.format("%.2f", response.getEstimateCost())));
                                }
                                if (response.getLabours() != null && response.getLabours().size() > 0) {
                                    labourRecyclerView.setVisibility(View.VISIBLE);
                                    labourList = response.getLabours();
                                    labourAdapter.setSpareLabourList(labourList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    labourAdapter.notifyDataSetChanged();
                                    labourLayout.setVisibility(View.VISIBLE);
                                } else {
                                    labourLayout.setVisibility(View.GONE);
                                }


                                if (response.getSpares() != null && response.getSpares().size() > 0) {
                                    spareRecyclerView.setVisibility(View.VISIBLE);
                                    spareList = response.getSpares();
                                    spareAdapter.setSpareLabourList(spareList);// = new DescriptionAdapter(RepairDescriptionActivity.this, descriptionList);
                                    spareAdapter.notifyDataSetChanged();
                                    spareLayout.setVisibility(View.VISIBLE);
                                } else {
                                    spareLayout.setVisibility(View.GONE);
                                }

                                if((response.getLabours()!=null && response.getLabours().size()>0) || (response.getSpares()!=null && response.getSpares().size()>0)){
                                    billLayout.setVisibility(View.VISIBLE);
                                    noResultFound.setVisibility(View.GONE);
                                }else {
                                    billLayout.setVisibility(View.GONE);
                                    noResultFound.setVisibility(View.VISIBLE);
                                    noResultFound.setText(getString(R.string.pleaseSelectSpareAndLabours));
                                }
                            } else if (response != null && response.isError()) {
                                billLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.pleaseSelectSpareAndLabours));

                            } else {
                                billLayout.setVisibility(View.GONE);
                                noResultFound.setVisibility(View.VISIBLE);
                                noResultFound.setText(getString(R.string.title_of_data_message));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            billLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.title_of_data_message));
                        }
                    }

            );
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supportFinishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
