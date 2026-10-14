package com.creatah.automobile.dealer.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.ReminderAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.RefreshHome;
import com.creatah.automobile.dealer.modal.Reminder;
import com.creatah.automobile.dealer.modal.ReminderListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class ReminderListActivity extends BaseActivity {

    private RecyclerView reminderRecyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private boolean isService = false;
    private int currentPageNumber = 1;
    private int totalPageCount;
    private boolean isFirstPage = false;
    private ReminderAdapter reminderAdapter;
    private List<Reminder> reminders = new ArrayList<>();
    private TextView noResultFound;
    private String FILTER_KEY = null;
    private String SERVICE_DATE = null;
    public static int REMINDER_REQ_CODE = 459;

    private int year, month, day;
    private String Date, wsDateFormat, Time = null;
    private int pHour;
    private int pMinute;
    private int cHour;
    private int cMinute;
    private String cAM_PM;
    DatePickerDialog serviceDatePicker;
    LinearLayout footerLayout;
    LinearLayout remainderLayout;
    private ImageView addButton;
    private LinearLayout noReminderLayout;
    private TextView noReminderFound;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout progressLayout;
    private Button retryButton;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_list);
        setTitle(getString(R.string.remainderTitle));
        initializeViews();
        context = this;
        layoutManager = new LinearLayoutManager(this);
        reminderRecyclerView.setLayoutManager(layoutManager);
        addButton.setVisibility(View.VISIBLE);
        //productRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
        reminderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0)
                    addButton.setVisibility(View.GONE);
                else if (dy < 0)
                    addButton.setVisibility(View.VISIBLE);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = visibleItemCount + firstVisibleItemPosition;

                loadNextListProduct(totalItemCount, lastVisiblePosition);

            }
        });


        initializeDatePicker();
        FILTER_KEY = getString(R.string.thisMonthKey);
        getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(context, AddRemainderActivity.class);
                startActivityForResult(addIntent, REMINDER_REQ_CODE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                remainderLayout.setVisibility(View.GONE);
                currentPageNumber = 1;
                getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryButton.setVisibility(View.GONE);
                noReminderLayout.setVisibility(View.GONE);
                noReminderFound.setVisibility(View.GONE);
                getReminders(currentPageNumber);

            }
        });

    }

    private void initializeViews() {
        reminderRecyclerView = (RecyclerView) findViewById(R.id.remainderRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        remainderLayout = findViewById(R.id.remainderLayout);
        footerLayout = findViewById(R.id.footerLayout);
        noReminderFound = (TextView) findViewById(R.id.noReminderFound);
        noReminderLayout = (LinearLayout) findViewById(R.id.noReminderLayout);
        addButton = (ImageView) findViewById(R.id.addButton);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        retryButton = (Button) findViewById(R.id.retryButton);
        progressLayout=(LinearLayout) findViewById(R.id.progressLayout);

    }

    public void loadNextListProduct(int totalItemCount, int lastVisiblePosition) {

        if (lastVisiblePosition == totalItemCount) {

            if (DeviceManager.isDeviceOffline(this)) {

                return;
            }

            if (!isService) {
                if (totalPageCount != 0 && currentPageNumber <= totalPageCount && !isService) {
                    getRemainderList(currentPageNumber, false, FILTER_KEY, SERVICE_DATE);
                    return;
                }

            }
        }
    }

    private void getRemainderList(int pageNumber, boolean isFirst, String filter, String serviceDate) {

        if (DeviceManager.isDeviceOffline(context)) {
            remainderLayout.setVisibility(View.GONE);
            reminderRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("page_no", String.valueOf(pageNumber));
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("filter_type", filter);
        requestParams.put("service_date", serviceDate);
        isFirstPage = isFirst;

        if (!isService) {

            isService = true;

            if (isFirst) {
                noResultFound.setVisibility(View.GONE);
                noReminderLayout.setVisibility(View.GONE);
                retryButton.setVisibility(View.GONE);
                progressLayout.setVisibility(View.VISIBLE);
            } else {
                footerLayout.setVisibility(View.VISIBLE);
            }

            ServiceManager.getRemainderList(context, requestParams, new ServiceResponseListener<ReminderListResponse>() {
                        @Override
                        public void onSuccess(ReminderListResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {

                                if (response.getReminderList() != null && response.getReminderList().size() > 0) {
                                    noResultFound.setVisibility(View.GONE);
                                    noReminderLayout.setVisibility(View.GONE);
                                    remainderLayout.setVisibility(View.VISIBLE);
                                    reminderRecyclerView.setVisibility(View.VISIBLE);
                                    retryButton.setVisibility(View.GONE);
                                    addButton.setVisibility(View.VISIBLE);

                                    if (isFirstPage) {
                                        reminderAdapter = null;
                                        reminders.clear();
                                    }
                                    reminders.addAll(response.getReminderList());
                                    totalPageCount = response.getTotal_count();
                                    totalPageCount = response.getPage_count();
                                    updateUi();
                                } else {

                                    if (isFirstPage) {
                                        noReminderFound.setVisibility(View.VISIBLE);
                                        noReminderLayout.setVisibility(View.VISIBLE);
                                        remainderLayout.setVisibility(View.VISIBLE);
                                        reminderRecyclerView.setVisibility(View.GONE);
                                        addButton.setVisibility(View.VISIBLE);
                                        noReminderFound.setText(getString(R.string.no_reminder_found));
                                    } else {
                                        Toast.makeText(context, getString(R.string.noMoreReminderFound), Toast.LENGTH_SHORT).show();
                                    }

                                }

                            } else if (response != null) {

                                if (isFirstPage) {
                                    noReminderLayout.setVisibility(View.VISIBLE);
                                    noReminderFound.setVisibility(View.VISIBLE);
                                    noReminderFound.setText("No Reminders Found");
                                    remainderLayout.setVisibility(View.VISIBLE);
                                    reminderRecyclerView.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.GONE);
                                    addButton.setVisibility(View.VISIBLE);


                                } else {

                                    Toast.makeText(context, getString(R.string.no_reminder_found), Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                if (isFirstPage) {
                                    noReminderLayout.setVisibility(View.VISIBLE);
                                    remainderLayout.setVisibility(View.VISIBLE);
                                    reminderRecyclerView.setVisibility(View.GONE);
                                    retryButton.setVisibility(View.VISIBLE);
                                    noReminderFound.setVisibility(View.VISIBLE);
                                    addButton.setVisibility(View.GONE);
                                    noReminderFound.setText(getString(R.string.errorOccurred));
                                } else {
                                    Toast.makeText(context, getString(R.string.noMoreReminderFound), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            footerLayout.setVisibility(View.GONE);
                            showErrorResponse();
                           /* footerLayout.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            noResultFound.setVisibility(View.GONE);
                            remainderLayout.setVisibility(View.GONE);
                            addButton.setVisibility(View.GONE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    currentPageNumber = 1;
                                    getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);

                                }
                            });*/

                          /*  if (isFirstPage) {
                                noResultFound.setVisibility(View.VISIBLE);
                                remainderLayout.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.tryAgainLater));
                            } else {
                                Toast.makeText(context, getString(R.string.tryAgainLater), Toast.LENGTH_SHORT).show();
                            }*/
                        }
                    }

            );
        }

    }

    private void showErrorResponse() {

        if (isFirstPage) {

            noReminderLayout.setVisibility(View.VISIBLE);
            noReminderFound.setVisibility(View.VISIBLE);
            reminderRecyclerView.setVisibility(View.GONE);
            remainderLayout.setVisibility(View.GONE);
            noReminderFound.setText(getString(R.string.errorOccurred));
            retryButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
        } else {
            showSnakeBar("Some error occurred. Please try again", "Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getReminders(currentPageNumber);

                }
            });
        }
    }

    private void getReminders(int pageNo) {
        currentPageNumber=pageNo;
        getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
    }

    private void updateUi() {

        if (reminderAdapter == null) {

            reminderAdapter = new ReminderAdapter(ReminderListActivity.this, reminders);
            reminderRecyclerView.setAdapter(reminderAdapter);

        } else {

            reminderAdapter.setReminders(reminders);
            reminderAdapter.notifyDataSetChanged();
        }
        currentPageNumber++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.remainder_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;

            case R.id.action_month:
                remainderLayout.setVisibility(View.GONE);
                currentPageNumber = 1;
                FILTER_KEY = getString(R.string.thisMonthKey);
                SERVICE_DATE = null;
                getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
                break;

            case R.id.action_upcoming:
                remainderLayout.setVisibility(View.GONE);
                currentPageNumber = 1;
                FILTER_KEY = getString(R.string.upcomingKey);
                SERVICE_DATE = null;
                getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
                break;

            case R.id.action_past:
                remainderLayout.setVisibility(View.GONE);
                currentPageNumber = 1;
                FILTER_KEY = getString(R.string.pastKey);
                SERVICE_DATE = null;
                getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
                break;

            case R.id.action_date:
                serviceDatePicker.show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeDatePicker() {
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        serviceDatePicker = new DatePickerDialog(this, R.style.AppTheme_DialogTheme, servicePickerListener, year, month, day);
        serviceDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        cHour = pHour;
        if (cHour >= 12) {
            cAM_PM = " PM";
        } else {
            cAM_PM = " AM";
        }
        pMinute = cal.get(Calendar.MINUTE);
        cMinute = pMinute;

    }

    private DatePickerDialog.OnDateSetListener servicePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;

                String dates = String.valueOf(day);
                String months = String.valueOf(month + 1);
                if (dates.length() == 1) {
                    dates = "0" + dates;
                }
                if (months.length() == 1) {
                    months = "0" + months;
                }
                SERVICE_DATE = year + "-" + months + "-" + dates;

                currentPageNumber = 1;
                FILTER_KEY = null;
                getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REMINDER_REQ_CODE && resultCode == RESULT_OK) {
            refreshHomePage();
            currentPageNumber = 1;
            getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
        }

        if (requestCode == REMINDER_REQ_CODE && resultCode == AppConstants.CLOSE_REMINDER) {
            refreshHomePage();
            currentPageNumber = 1;
            getRemainderList(currentPageNumber, true, FILTER_KEY, SERVICE_DATE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void refreshHomePage() {
        EventBus.getDefault().post(new RefreshHome());
    }
}
