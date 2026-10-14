package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.ServiceConstants;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.CustomerAdapter;
import com.creatah.automobile.dealer.adapter.JobCardLabourAdapter;
import com.creatah.automobile.dealer.adapter.JobCardSpareAdapter;
import com.creatah.automobile.dealer.adapter.QuickSearchAdapter;
import com.creatah.automobile.dealer.adapter.SpareSuggestionAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddRemainderResponse;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.Customer;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.modal.MakeList;
import com.creatah.automobile.dealer.modal.Modallist;
import com.creatah.automobile.dealer.modal.QuickSearchRespose;
import com.creatah.automobile.dealer.modal.QuickSearchResult;
import com.creatah.automobile.dealer.modal.SpareLabour;
import com.creatah.automobile.dealer.modal.SpareLabourResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuickSearch extends BaseActivity implements JobCardSpareAdapter.SpareListener, JobCardLabourAdapter.LabourListener {
    private LinearLayout descriptionLayout;
    private AutoCompleteTextView  searchSpareAutoCompleteTextView,labourAutoCompleteTextView;
    private TextView noResultFound;
    //private Button retryButton;
    private final int ADD_REMOVE_REQ_CODE = 150;

    private Spinner makeSpinner,modelSpinner;
    private EditText spareName;
    private Button qsbutton;
    private Context context;
    private List<SpareLabour> selectedLabourList = new ArrayList<>();

    @BindView(R.id.spareRecyclerView)
    RecyclerView spareRecyclerView;

    private String lastSearched = "";private List<SpareLabour> selectedSpareList = new ArrayList<>();

    private boolean isService = false;
    private List<MakeList> makeList;
    private List<String> makeStrList = new ArrayList<>();
    private RecyclerView QuickSearchRecycler;
    private List<Modallist> modalList;
    private List<String> modalStrList = new ArrayList<>();
    private List<QuickSearchResult> quicksearchList = new ArrayList<>();
    List<SpareLabour> allSpareList = new ArrayList<>();
    List<SpareLabour> allLabourList = new ArrayList<>();
    private String serviceID, makeId, modelId, serviceDate = null;
    private QuickSearchAdapter quickSearchAdapter;
    private LinearLayoutManager layoutManager;
    @BindView(R.id.addSpareAndLabour)
    TextView addSpareAndLabour;
    private AppInfo appInfo;
    @BindView(R.id.addSpareLayout)
    LinearLayout addSpareLayout;
    private JobCardLabourAdapter labourAdapter;
    @BindView(R.id.totalCostTextView)
    TextView totalTextView;
    @BindView(R.id.spareCostTextView)
    TextView spareCostTextView;
    private SpareLabour spare;
    @BindView(R.id.labourCGSTTextView)
    TextView labourCGSTextView;
    @BindView(R.id.labourRecyclerView)
    RecyclerView labourRecyclerView;
    @BindView(R.id.spareCGSTTextView)
    TextView spareCGSTTextView;
    private static AsyncHttpClient client = new AsyncHttpClient();
    @BindView(R.id.labourSGSTTextView)
    TextView labourSGSTextView;
    @BindView(R.id.spareSGSTTextView)
    TextView spareSGSTTextView;
    private boolean isDelivered;
    @BindView(R.id.labourCostTextView)
    TextView labourCostTextView;
    @BindView(R.id.totalLayout)
    LinearLayout costLinearLayout;
    boolean isSpareSearching = true;

    boolean isLabourSearching = true;
    private List<SpareLabour> selectedSpareLabourList = new ArrayList<>();
    private DealerBooking booking;


    private SpareLabour labour;

    @BindView(R.id.addLabourLayout)
    LinearLayout addLabourLayout;
    private JobCardSpareAdapter spareAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_search);
        ButterKnife.bind(this);
        setTitle(getString(R.string.QuickSearchTitle));
        context = this;

        makeSpinner = (Spinner) findViewById(R.id.qsmake);
        modelSpinner = (Spinner) findViewById(R.id.qsmodel);
        searchSpareAutoCompleteTextView=(AutoCompleteTextView)  findViewById(R.id.searchSpareAutoCompleteTextView);
        labourAutoCompleteTextView=(AutoCompleteTextView)  findViewById(R.id.searchLabourAutoCompleteTextView);
       // descriptionLayout=(LinearLayout) findViewById(R.id.descriptionLayout);
        noResultFound=(TextView) findViewById(R.id.noResultFound);
       // retryButton=(Button) findViewById(R.id.retryButton);
        searchSpareAutoCompleteTextView.setVisibility(View.VISIBLE);
        labourAutoCompleteTextView.setVisibility(View.VISIBLE);
        //spareName = (EditText) findViewById(R.id.qsspare);
        //qsbutton    = (Button)  findViewById(R.id.qsbutton);
       // QuickSearchRecycler = (RecyclerView) findViewById(R.id.quick_search_result);

       // LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
       // QuickSearchRecycler.setLayoutManager(llm);
        //QuickSearchRecycler.setAdapter(quickSearchAdapter);
       /* qsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    RequestParams requestParams = new RequestParams();
                    String makestring = makeSpinner.getSelectedItem().toString();
                    String modelstring = modelSpinner.getSelectedItem().toString();
                    String sparestring = spareName.getText().toString();
                    requestParams.put("make", makestring);
                    requestParams.put("model", modelstring);
                    requestParams.put("spare", sparestring);
                    get_qsresult(requestParams);


            }
        });*/

        //getSpareList();
        //getLabourList();
        load_make();
        searchSpareAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isSpareSearching) {
                    spare = null;
                }
                isSpareSearching = true;
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        labourRecyclerView.setLayoutManager(layoutManager);

        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        spareRecyclerView.setLayoutManager(layoutManager1);



        labourAdapter = new JobCardLabourAdapter(QuickSearch.this, selectedLabourList, QuickSearch.this, isDelivered);
        labourRecyclerView.setAdapter(labourAdapter);
        labourRecyclerView.setNestedScrollingEnabled(false);

        spareAdapter = new JobCardSpareAdapter(QuickSearch.this, selectedSpareLabourList, QuickSearch.this, isDelivered);
        spareRecyclerView.setAdapter(spareAdapter);
        spareRecyclerView.setNestedScrollingEnabled(false);

        labourAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isLabourSearching) {
                    labour = null;
                }
                isLabourSearching = true;
            }
        });

        searchSpareAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                onSpareSearchLayout();
                return false;
            }
        });

        labourAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                onLabourSearchLayout();
                return false;
            }
        });

    }
    public AdapterView.OnItemSelectedListener modalListener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedSpareList.clear();
            getSpareList();
            getLabourList();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    public AdapterView.OnItemSelectedListener makeListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedSpareList.clear();
            if (position != 0) {
                String makestring = String.valueOf(makeList.get(position - 1).getMakeId());
                modalStrList.clear();

                int pos = makeSpinner.getSelectedItemPosition() - 1;
                modalList = makeList.get(pos).getBrandlist();
                if (modalList != null && modalList.size() > 0) {
                    modalStrList.add(getString(R.string.selectModel));
                    for (Modallist list : modalList) {
                        modalStrList.add(list.getBrandName());
                    }
                    ArrayAdapter<String> modaladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
                    modelSpinner.setAdapter(modaladapter);
                    System.out.println("RESULT MAKE");
                    modelSpinner.setOnItemSelectedListener(modalListener);
                } else {
                    modalStrList.add(getString(R.string.selectModel));
                    ArrayAdapter<String> modaladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
                    modelSpinner.setAdapter(modaladapter);
                    Toast.makeText(context, getString(R.string.modelsNotAvailables), Toast.LENGTH_SHORT).show();
                }

                makeId = makestring;
            } else {
                makeId = null;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }



    };

    public void load_make(){
        appInfo = AppUtils.getAppInfo(getApplication());
        makeStrList.add(getString(R.string.selectMake));
        if (appInfo.getCarMakeList() != null) {
            makeList = appInfo.getCarMakeList();
            for (int i = 0; i < makeList.size(); i++) {
                makeStrList.add(makeList.get(i).getMakeName());
            }
        }
        ArrayAdapter<String> makeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, makeStrList);
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setOnItemSelectedListener(makeListener);

//        modalStrList.add(getString(R.string.selectModel));
//        ArrayAdapter<String> modalAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, modalStrList);
//        modelSpinner.setAdapter(modalAdapter);
//        modelSpinner.setOnItemSelectedListener(modalListener);
    }




























    private void getSpareList() {
        System.out.println("RESULT : SPARE CALLES");
        if (DeviceManager.isDeviceOffline(context)) {
        //    descriptionLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.internet_failed_toast));
           // retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("make", makeSpinner.getSelectedItem().toString());
        requestParams.put("model", modelSpinner.getSelectedItem().toString());

        //if (!isService) {

        isService = true;

        mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
        ServiceManager.getSpareQuickList(context, requestParams, new ServiceResponseListener<SpareLabourResponse>() {
                    @Override
                    public void onSuccess(SpareLabourResponse response) {
                        isService = false;
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                        if (response != null && !response.isError()) {

                            allSpareList = response.getSpareLabourList();
                            System.out.println("RESULT"+allSpareList.size());
                            if (allSpareList != null && allSpareList.size() > 0) {
                                //descriptionLayout.setVisibility(View.VISIBLE);
                                //noResultFound.setVisibility(View.GONE);
                                //retryButton.setVisibility(View.GONE);
                                setUpSpareSearchView();
                            }/* else {
                                noResultFound.setText(getString(R.string.noSpareLabourFound));
                                noResultFound.setVisibility(View.VISIBLE);
                                descriptionLayout.setVisibility(View.GONE);
                            }*/
                            onUpdateItem();
                        } else if (response != null && response.isError()) {
                          //  descriptionLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                           // retryButton.setVisibility(View.GONE);
                            noResultFound.setText(response.getMessage());

                        } else {
                          //  descriptionLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            //retryButton.setVisibility(View.GONE);
                            noResultFound.setText(getString(R.string.internet_failed_toast));
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String errorResponse) {
                        isService = false;
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                      //  descriptionLayout.setVisibility(View.GONE);
//                        noResultFound.setVisibility(View.VISIBLE);
                       /* retryButton.setVisibility(View.VISIBLE);
                        retryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getSpareList();
                            }
                        });*/
//                        noResultFound.setText(getString(R.string.internet_failed_toast));
                    }
                }

        );
        // }


    }


    private void getLabourList() {

        if (DeviceManager.isDeviceOffline(context)) {
          //  descriptionLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.internet_failed_toast));
           // retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("make", makeSpinner.getSelectedItem().toString());
        requestParams.put("model", modelSpinner.getSelectedItem().toString());

        //  if (!isService) {

        isService = true;

        mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
        ServiceManager.getLabourQuickList(context, requestParams, new ServiceResponseListener<SpareLabourResponse>() {
                    @Override
                    public void onSuccess(SpareLabourResponse response) {
                        isService = false;
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                        if (response != null && !response.isError()) {

                            allLabourList = response.getSpareLabourList();
                            if (allLabourList != null && allLabourList.size() > 0) {
                               // retryButton.setVisibility(View.GONE);
                                // descriptionLayout.setVisibility(View.VISIBLE);
                                // noResultFound.setVisibility(View.GONE);
                                setUpLabourSearchView();
                            } /*else {
                                noResultFound.setText(getString(R.string.noSpareLabourFound));
                                noResultFound.setVisibility(View.VISIBLE);
                                descriptionLayout.setVisibility(View.GONE);
                            }*/
                            onUpdateItem();

                        } else if (response != null && response.isError()) {
                           // descriptionLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                            //retryButton.setVisibility(View.GONE);
                            noResultFound.setText(response.getMessage());
                        } else {
                          //  descriptionLayout.setVisibility(View.GONE);
                            noResultFound.setVisibility(View.VISIBLE);
                           // retryButton.setVisibility(View.GONE);
                            noResultFound.setText(getString(R.string.internet_failed_toast));
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String errorResponse) {
                        isService = false;
                        mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                     //   descriptionLayout.setVisibility(View.GONE);
                        //retryButton.setVisibility(View.VISIBLE);
                       /* retryButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getLabourList();
                            }
                        });*/

                       /* noResultFound.setVisibility(View.VISIBLE);
                        noResultFound.setText(getString(R.string.internet_failed_toast));*/
                    }
                }

        );
        //  }


    }


    private void setUpSpareSearchView() {

        ArrayAdapter<SpareLabour> itemDesc = new SpareSuggestionAdapter(context, allSpareList);
        searchSpareAutoCompleteTextView.setAdapter(itemDesc);
        searchSpareAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                isSpareSearching = false;
                spare = (SpareLabour) arg0.getAdapter().getItem(arg2);
                searchSpareAutoCompleteTextView.setText(spare.getItemName());
                // onAddSpare();
                boolean isAdded = false;
                for (SpareLabour selectedSpare : selectedSpareList) {
                    if (selectedSpare.getItemId() == spare.getItemId()) {
                        isAdded = true;
                    }
                }


                for (int i = 0; i < spare.getItemList().size(); i++) {

                    for (int j = 0; j < selectedLabourList.size(); j++) {

                        if (selectedLabourList.get(j).getItemId() == spare.getItemList().get(i).getItemId()) {
                            spare.getItemList().get(i).setCanRemove(false);
                        }

                    }

                }

                if (!isAdded) {
                    if (spare.getItemList() != null && spare.getItemList().size() > 0) {
                        getUserConfirmation(AppConstants.ADD_TYPE, AppConstants.SPARE_LIST, spare);
                    } else {
                        onAddSpare1();
                    }

                    labourAutoCompleteTextView.setFocusable(false);
                    searchSpareAutoCompleteTextView.setFocusable(false);
                    lastSearched = AppConstants.SPARE_LIST;
                } else {

                    try {
                        if (spareAdapter != null) {
                            spareAdapter.setEnable(false);
                            spareAdapter.notifyDataSetChanged();
                        }
                    } finally {
                        onSpareSearchLayout();
                    }

                    Toast.makeText(context, "Spare Already added", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private void setUpLabourSearchView() {

        ArrayAdapter<SpareLabour> itemDesc = new SpareSuggestionAdapter(context, allLabourList);
        labourAutoCompleteTextView.setAdapter(itemDesc);
        labourAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                isLabourSearching = false;
                labour = (SpareLabour) arg0.getAdapter().getItem(arg2);
                labourAutoCompleteTextView.setText(labour.getItemName());
                //onAddLabour();

                boolean isAdded = false;

                for (SpareLabour selectedLabour : selectedLabourList) {
                    if (selectedLabour.getItemId() == labour.getItemId()) {
                        isAdded = true;
                    }
                }


                for (int i = 0; i < labour.getItemList().size(); i++) {

                    for (int j = 0; j < selectedSpareList.size(); j++) {

                        if (selectedSpareList.get(j).getItemId() == labour.getItemList().get(i).getItemId()) {
                            labour.getItemList().get(i).setCanRemove(false);
                        }

                    }

                }


                if (!isAdded) {

                    if (labour.getItemList() != null && labour.getItemList().size() > 0) {
                        getUserConfirmation(AppConstants.ADD_TYPE, AppConstants.LABOUR_LIST, labour);
                    } else {
                        onAddLabour1();
                    }

                    labourAutoCompleteTextView.setFocusable(false);
                    searchSpareAutoCompleteTextView.setFocusable(false);
                    lastSearched = AppConstants.LABOUR_LIST;
                } else {

                    try {
                        if (labourAdapter != null) {
                            labourAdapter.setEnable(false);
                            labourAdapter.notifyDataSetChanged();
                        }
                    } finally {

                        onLabourSearchLayout();

                    }

                    Toast.makeText(context, "Labour Already added", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void getUserConfirmation(String addRemoveType, String spareLabourType, SpareLabour spareLabour) {
        Intent confirmationIntent = new Intent(context, AddSubSpareLabourActivity.class);
        confirmationIntent.putExtra(AppConstants.SPARE_LABOUR_EXTRA, spareLabour);
        confirmationIntent.putExtra(AppConstants.SPARE_LABOUR_TYPE_EXTRA, spareLabourType);
        confirmationIntent.putExtra(AppConstants.ADD_REMOVE_EXTRA, addRemoveType);
        startActivityForResult(confirmationIntent, ADD_REMOVE_REQ_CODE);

    }

    @Override
    public void onUpdateItem() {

        List<SpareLabour> labourList = labourAdapter.getLabourList();
        List<SpareLabour> spareList = spareAdapter.getSpareList();

        double labourCost = 0;
        double labourCGST = 0;
        double labourSGST = 0;

        double spareCost = 0;
        double spareCGST = 0;
        double spareSGST = 0;

        for (SpareLabour labour : labourList) {
            double labourQty = labour.getQty() == 0 ? 1 : labour.getQty();
            labourCost = labourCost + (labourQty * labour.getItemAmount());
            labourCGST = labourCGST + (labourQty * labour.getPerCGSTAmount());
            labourSGST = labourSGST + (labourQty * labour.getPerSGSTAmount());
        }

        for (SpareLabour spare : spareList) {
            double spareQty = spare.getQty() == 0 ? 1 : spare.getQty();
            spareCost = spareCost + (spareQty * spare.getItemAmount());
            spareCGST = spareCGST + (spareQty * spare.getPerCGSTAmount());
            spareSGST = spareSGST + (spareQty * spare.getPerSGSTAmount());
        }

        spareCostTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", spareCost));
        spareCGSTTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", spareCGST));
        spareSGSTTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", spareSGST));

        labourCostTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", labourCost));
        labourCGSTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", labourCGST));
        labourSGSTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", labourSGST));

        double totalCost = labourCost + spareCost + labourCGST + spareCGST + labourSGST + spareSGST;
//        totalTextView.setText(getString(R.string.rs) + " " + String.format("%.2f", totalCost));
        int totalAmount = (int) Math.round(totalCost);

        totalTextView.setText(String.valueOf(totalAmount) + ".00");

        if (spareList.size() > 0 && labourList.size() > 0) {
            addSpareAndLabour.setVisibility(View.GONE);
            costLinearLayout.setVisibility(View.VISIBLE);
            addSpareLayout.setVisibility(View.VISIBLE);
            addLabourLayout.setVisibility(View.VISIBLE);
        } else if (spareList.size() > 0 || labourList.size() > 0) {
            addSpareAndLabour.setVisibility(View.GONE);
            costLinearLayout.setVisibility(View.VISIBLE);
            addSpareLayout.setVisibility(View.GONE);
            addLabourLayout.setVisibility(View.GONE);
            if (spareList.size() > 0) {
                addSpareLayout.setVisibility(View.VISIBLE);
            }
            if (labourList.size() > 0) {
                addLabourLayout.setVisibility(View.VISIBLE);
            }
        } else {
            addSpareLayout.setVisibility(View.GONE);
            addLabourLayout.setVisibility(View.GONE);
            costLinearLayout.setVisibility(View.GONE);
            addSpareAndLabour.setVisibility(View.VISIBLE);
            if (isDelivered) {
                addSpareAndLabour.setText(getString(R.string.noSpareLabourFound));
            } else {
                addSpareAndLabour.setText(getString(R.string.addSpareLabour));
              //  updateButton.setText("Add");
            }
        }

        if (isDelivered) {
           // descriptionLayout.setVisibility(View.VISIBLE);
            noResultFound.setVisibility(View.GONE);
            addSpareAndLabour.setVisibility(View.GONE);
            searchSpareAutoCompleteTextView.setVisibility(View.GONE);
            labourAutoCompleteTextView.setVisibility(View.GONE);
        } else if (allLabourList != null && allLabourList.size() > 0 && allSpareList != null && allSpareList.size() > 0) {
           // descriptionLayout.setVisibility(View.VISIBLE);
            noResultFound.setVisibility(View.GONE);
            addSpareAndLabour.setVisibility(View.GONE);
        } else {
           // descriptionLayout.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.addSpareLabourContactAdmin));
        }
        //labourAdapter.setEnable(false);
        //spareAdapter.setEnable(false);
    }


    @Override
    public void onRemoveLabour(SpareLabour spareLabour) {


        int count = 0;

        List<SpareLabour> selectedSpareList = null;


        for (int i = 0; i < allLabourList.size(); i++) {

            if (allLabourList.get(i).getItemId() == spareLabour.getItemId()) {
                selectedSpareList = new ArrayList<>();

                for (SpareLabour spare : this.selectedSpareList) {
                    for (SpareLabour allSpare : allLabourList.get(i).getItemList()) {
                        if (allSpare.getItemId() == spare.getItemId()) {
                            selectedSpareList.add(spare);
                        }
                    }
                }
            }

            for (int j = 0; j < selectedLabourList.size(); j++) {

                if (selectedLabourList.get(j).getItemId() == allLabourList.get(i).getItemId()) {

                    List<SpareLabour> spareList = new ArrayList<>();

                    for (SpareLabour spare : this.selectedSpareList) {
                        for (SpareLabour allSpare : allLabourList.get(i).getItemList()) {
                            if (allSpare.getItemId() == spare.getItemId()) {
                                spareList.add(spare);
                            }
                        }
                    }

                    selectedLabourList.get(j).setItemList(spareList);

                }

            }

        }


        if (selectedSpareList != null && selectedSpareList.size() > 0) {

            for (int i = 0; i < selectedSpareList.size(); i++) {

                for (SpareLabour labour : this.selectedLabourList) {

                    for (SpareLabour spare : labour.getItemList()) {

                        if (spare.getItemId() == selectedSpareList.get(i).getItemId()) {
                            count = count + 1;
                        }

                    }

                }


                if (count == 1) {
                    selectedSpareList.get(i).setCanRemove(true);
                } else {
                    selectedSpareList.get(i).setCanRemove(false);
                }
                count = 0;
            }

            spareLabour.setItemList(selectedSpareList);


            getUserConfirmation(AppConstants.REMOVE_TYPE, AppConstants.LABOUR_LIST, spareLabour);


        } else {

            for (int i = 0; i < selectedLabourList.size(); i++) {
                if (selectedLabourList.get(i).getItemId() == spareLabour.getItemId()) {
                    selectedLabourList.remove(i);
                    break;
                }
            }

            labourAdapter.setLabourList(selectedLabourList);
            labourAdapter.notifyDataSetChanged();

            addSpareLayout.setVisibility(this.selectedSpareList.size() > 0 ? View.VISIBLE : View.GONE);
            addLabourLayout.setVisibility(selectedLabourList.size() > 0 ? View.VISIBLE : View.GONE);
            onUpdateItem();
        }


    }

    @Override
    public void onRemoveSpare(SpareLabour spareLabour) {

        int count = 0;

        List<SpareLabour> selectedLabourList = null;


        for (int i = 0; i < allSpareList.size(); i++) {

            if (allSpareList.get(i).getItemId() == spareLabour.getItemId()) {
                selectedLabourList = new ArrayList<>();

                for (SpareLabour labour : this.selectedLabourList) {
                    for (SpareLabour allLabour : allSpareList.get(i).getItemList()) {
                        if (allLabour.getItemId() == labour.getItemId()) {
                            selectedLabourList.add(labour);
                        }
                    }
                }

            }

            for (int j = 0; j < selectedSpareList.size(); j++) {

                if (selectedSpareList.get(j).getItemId() == allSpareList.get(i).getItemId()) {

                    List<SpareLabour> labourList = new ArrayList<>();

                    for (SpareLabour labour : this.selectedLabourList) {
                        for (SpareLabour allLabour : allSpareList.get(i).getItemList()) {
                            if (allLabour.getItemId() == labour.getItemId()) {
                                labourList.add(labour);
                            }
                        }
                    }

                    selectedSpareList.get(j).setItemList(labourList);

                }

            }
        }

        if (selectedLabourList != null && selectedLabourList.size() > 0) {
            for (int i = 0; i < selectedLabourList.size(); i++) {

                for (SpareLabour spare : this.selectedSpareList) {

                    for (SpareLabour labour : spare.getItemList()) {

                        if (labour.getItemId() == selectedLabourList.get(i).getItemId()) {
                            count = count + 1;
                        }

                    }

                }

                if (count == 1) {
                    selectedLabourList.get(i).setCanRemove(true);
                } else {
                    selectedLabourList.get(i).setCanRemove(false);
                }
                count = 0;
            }

            spareLabour.setItemList(selectedLabourList);

            getUserConfirmation(AppConstants.REMOVE_TYPE, AppConstants.SPARE_LIST, spareLabour);

        } else {

            for (int i = 0; i < selectedSpareList.size(); i++) {
                if (selectedSpareList.get(i).getItemId() == spareLabour.getItemId()) {
                    selectedSpareList.remove(i);
                    break;
                }
            }
            spareAdapter.setSpareList(selectedSpareList);
            spareAdapter.notifyDataSetChanged();

            addSpareLayout.setVisibility(selectedSpareList.size() > 0 ? View.VISIBLE : View.GONE);
            onUpdateItem();
        }

    }



    private void updateSpareList() throws UnsupportedEncodingException, JSONException {


        if ((selectedSpareList != null && selectedSpareList.size() > 0) || (selectedLabourList != null && selectedLabourList.size() > 0)) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < selectedSpareList.size(); i++) {
                jsonArray.put(selectedSpareList.get(i).getAddSpareJSONObject());
            }

            JSONArray jsonArray1 = new JSONArray();
            for (int i = 0; i < selectedLabourList.size(); i++) {
                jsonArray1.put(selectedLabourList.get(i).getAddSpareJSONObject());
            }

            JSONObject jsonParams = new JSONObject();
            jsonParams.put("jobcard_id", String.valueOf(booking.getJobcardId()));
            jsonParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            jsonParams.put("spares_list", jsonArray);
            jsonParams.put("labour_list", jsonArray1);
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(context, ServiceManager.getServiceAPIUrl(ServiceConstants.ADD_CHECK_IN_WORK_LIST), entity, "application/json",
                    new AsyncHttpResponseHandler() {

                        public void onStart() {

                            super.onStart();
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.SHOW_PROGRESS_DIALOG);

                        }

                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject json = new JSONObject(arg1);
                                boolean error = json.getBoolean("error");
                                String message = json.getString("message");
                                if (!error) {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable arg0, String arg1) {
                            super.onFailure(arg0, arg1);

                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), OK, null, null);
                        }

                        public void onFinish() {
                            super.onFinish();
                            mainCategoryHandler.sendEmptyMessage(com.android.theme.utils.Constants.DISMISS_PROGRESS_DIALOG);

                        }
                    });


        } else {
            Toast.makeText(context, getString(R.string.addSpareErr), Toast.LENGTH_SHORT).show();
        }
    }

    public DialogInterface.OnClickListener OK = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REMOVE_REQ_CODE && resultCode == RESULT_OK) {

            SpareLabour spareLabour = (SpareLabour) data.getSerializableExtra(AppConstants.SPARE_LABOUR_EXTRA);
            String spareLabourType = data.getStringExtra(AppConstants.SPARE_LABOUR_TYPE_EXTRA);
            String addRemoveType = data.getStringExtra(AppConstants.ADD_REMOVE_EXTRA);

            if (addRemoveType.equals(AppConstants.ADD_TYPE)) {

                if (spareLabourType.equals(AppConstants.SPARE_LIST)) {
                    spare = spareLabour;
                    onAddSpare1();
                } else if (spareLabourType.equals(AppConstants.LABOUR_LIST)) {
                    labour = spareLabour;
                    onAddLabour1();
                }

            } else if (addRemoveType.equals(AppConstants.REMOVE_TYPE)) {

                if (spareLabourType.equals(AppConstants.SPARE_LIST)) {
                    spare = spareLabour;
                    removeSpareLabourFromSpare(spare);
                } else if (spareLabourType.equals(AppConstants.LABOUR_LIST)) {
                    labour = spareLabour;
                    removeSpareLabourFromLabour(labour);
                }


            }

        } else {

            try {
                if (spareAdapter != null) {
                    spareAdapter.setEnable(false);
                    spareAdapter.notifyDataSetChanged();
                }
                if (labourAdapter != null) {
                    labourAdapter.setEnable(false);
                    labourAdapter.notifyDataSetChanged();
                }
            } finally {
                if (lastSearched.equals(AppConstants.SPARE_LIST)) {
                    onSpareSearchLayout();
                } else if (lastSearched.equals(AppConstants.LABOUR_LIST)) {
                    onLabourSearchLayout();
                }
            }

        }

    }


    public void onAddLabour1() {

        if (labour == null) {
            Toast.makeText(context, "Please search Labour", Toast.LENGTH_SHORT).show();

        } else if (!labour.getItemName().isEmpty()) {

            boolean isAdded = false;

            for (SpareLabour labour : selectedLabourList) {
                if (labour.getItemId() == this.labour.getItemId()) {
                    isAdded = true;
                }
            }

            if (!isAdded) {

                addLabourLayout.setVisibility(View.VISIBLE);
                selectedLabourList.add(labour);
                labourAdapter.setLabourList(selectedLabourList);
                labourAdapter.setEnable(true);
                labourAdapter.notifyDataSetChanged();
                labourRecyclerView.setAdapter(labourAdapter);


                if (labour.getItemList() != null && labour.getItemList().size() > 0) {
                    addSpareLayout.setVisibility(View.VISIBLE);
                    // labourList.addAll(spareLabour.getLabourList());
                    addSpareItemFromLabour1();

                }
            } else {
                Toast.makeText(context, "Labour Already added", Toast.LENGTH_SHORT).show();
            }

        }
        labourAutoCompleteTextView.setText("");
        labour = null;
        onUpdateItem();
    }

    public void onAddSpare1() {

        if (spare == null) {
            Toast.makeText(context, "Please search spares", Toast.LENGTH_SHORT).show();

        } else if (!spare.getItemName().isEmpty()) {

            boolean isAdded = false;

            for (SpareLabour spare : selectedSpareList) {
                if (spare.getItemId() == this.spare.getItemId()) {
                    isAdded = true;
                }
            }

            if (!isAdded) {

                addSpareLayout.setVisibility(View.VISIBLE);
                selectedSpareList.add(spare);
                spareAdapter.setEnable(true);
                spareAdapter.setSpareList(selectedSpareList);
                spareAdapter.notifyDataSetChanged();
                spareRecyclerView.setAdapter(spareAdapter);


                if (spare.getItemList() != null && spare.getItemList().size() > 0) {
                    addLabourLayout.setVisibility(View.VISIBLE);
                    // labourList.addAll(spareLabour.getLabourList());
                    addLabourItemFromSpare1();

                }
            } else {
                Toast.makeText(context, "Spare Already added", Toast.LENGTH_SHORT).show();
            }

        }
        searchSpareAutoCompleteTextView.setText("");
        spare = null;
        onUpdateItem();
    }

    private void addLabourItemFromSpare1() {
        boolean isAdded = false;
        List<SpareLabour> newLabourList = spare.getItemList();

        boolean canAdd = false;
        for (SpareLabour selectedLabour : newLabourList) {


            canAdd = selectedLabour.isAdded();
            for (SpareLabour labour : selectedLabourList) {

                if (labour.getItemId() == selectedLabour.getItemId()) {
                    isAdded = true;
                }

            }

            if (!isAdded && canAdd) {
                selectedLabourList.add(selectedLabour);
            }
            isAdded = false;

        }

        labourAdapter.setLabourList(selectedLabourList);
        labourRecyclerView.setAdapter(labourAdapter);
        onUpdateItem();
    }

    private void addSpareItemFromLabour1() {
        boolean isAdded = false;
        boolean canAdd = false;
        List<SpareLabour> newSpareList = labour.getItemList();


        for (SpareLabour selectedSpare : newSpareList) {
            canAdd = selectedSpare.isAdded();
            for (SpareLabour spare : selectedSpareList) {

                if (spare.getItemId() == selectedSpare.getItemId()) {
                    isAdded = true;
                }

            }

            if (!isAdded && canAdd) {
                selectedSpareList.add(selectedSpare);
            }
            isAdded = false;
        }

        spareAdapter.setSpareList(selectedSpareList);
        spareRecyclerView.setAdapter(spareAdapter);
        onUpdateItem();
    }

    private void removeSpareLabourFromSpare(SpareLabour spareLabour) {
        int count = 0;

        List<SpareLabour> selectedLabourList = spareLabour.getItemList();


        /*for(int i =0; i<allSpareList.size(); i++){

            if(allSpareList.get(i).getItemId() == spareLabour.getItemId()){
                selectedLabourList = allSpareList.get(i).getItemList();
            }

            for(int j =0; j<selectedSpareList.size(); j++){

                if(selectedSpareList.get(j).getItemId() == allSpareList.get(i).getItemId()){
                    selectedSpareList.get(j).setItemList(allSpareList.get(i).getItemList());
                }

            }
        }*/
        if (selectedLabourList != null && selectedLabourList.size() > 0) {
            for (SpareLabour selectedLabour : selectedLabourList) {
                boolean canRemove = selectedLabour.isAdded();

                for (SpareLabour spare : this.selectedSpareList) {

                    for (SpareLabour labour : spare.getItemList()) {

                        if (labour.getItemId() == selectedLabour.getItemId()) {
                            count = count + 1;
                        }

                    }

                }


                if (count == 1 && canRemove) {

                    for (int i = 0; i < this.selectedLabourList.size(); i++) {
                        if (this.selectedLabourList.get(i).getItemId() == selectedLabour.getItemId()) {
                            this.selectedLabourList.remove(i);
                            break;
                        }
                    }

                }
                count = 0;

            }

            labourAdapter.setEnable(false);
            labourAdapter.setLabourList(this.selectedLabourList);
            labourAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < selectedSpareList.size(); i++) {
            if (selectedSpareList.get(i).getItemId() == spareLabour.getItemId()) {
                selectedSpareList.remove(i);
                break;
            }
        }
        spareAdapter.setEnable(false);
        spareAdapter.setSpareList(selectedSpareList);
        spareAdapter.notifyDataSetChanged();


        addSpareLayout.setVisibility(selectedSpareList.size() > 0 ? View.VISIBLE : View.GONE);
        addLabourLayout.setVisibility(this.selectedLabourList.size() > 0 ? View.VISIBLE : View.GONE);
        onUpdateItem();
    }

    private void removeSpareLabourFromLabour(SpareLabour spareLabour) {
        int count = 0;

        List<SpareLabour> selectedSpareList = spareLabour.getItemList();

        if (selectedSpareList != null && selectedSpareList.size() > 0) {

            for (SpareLabour selectedSpare : selectedSpareList) {
                boolean canRemove = selectedSpare.isAdded();

                for (SpareLabour labour : this.selectedLabourList) {

                    for (SpareLabour spare : labour.getItemList()) {
                        if (spare.getItemId() == selectedSpare.getItemId()) {
                            count = count + 1;
                        }
                    }

                }

                if (count == 1 && canRemove) {

                    for (int i = 0; i < this.selectedSpareList.size(); i++) {
                        if (this.selectedSpareList.get(i).getItemId() == selectedSpare.getItemId()) {
                            this.selectedSpareList.remove(i);
                            break;
                        }
                    }

                }
                count = 0;

            }

            spareAdapter.setEnable(false);
            spareAdapter.setSpareList(this.selectedSpareList);
            spareAdapter.notifyDataSetChanged();
        }

        for (int i = 0; i < selectedLabourList.size(); i++) {
            if (selectedLabourList.get(i).getItemId() == spareLabour.getItemId()) {
                selectedLabourList.remove(i);
                break;
            }
        }

        labourAdapter.setEnable(false);
        labourAdapter.setLabourList(selectedLabourList);
        labourAdapter.notifyDataSetChanged();

        addSpareLayout.setVisibility(this.selectedSpareList.size() > 0 ? View.VISIBLE : View.GONE);
        addLabourLayout.setVisibility(selectedLabourList.size() > 0 ? View.VISIBLE : View.GONE);
        onUpdateItem();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @OnClick(R.id.searchSpareAutoCompleteTextView)
    public void onSpareSearchLayout() {
        labourAutoCompleteTextView.setFocusable(true);
        searchSpareAutoCompleteTextView.setFocusable(true);
        searchSpareAutoCompleteTextView.requestFocus();
        searchSpareAutoCompleteTextView.setFocusableInTouchMode(true);
        searchSpareAutoCompleteTextView.setSelection(searchSpareAutoCompleteTextView.getText().length());
    }

    @OnClick(R.id.searchLabourAutoCompleteTextView)
    public void onLabourSearchLayout() {
        labourAutoCompleteTextView.setFocusable(true);
        searchSpareAutoCompleteTextView.setFocusable(true);
        labourAutoCompleteTextView.requestFocus();
        labourAutoCompleteTextView.setFocusableInTouchMode(true);
        labourAutoCompleteTextView.setSelection(labourAutoCompleteTextView.getText().length());
    }





    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}