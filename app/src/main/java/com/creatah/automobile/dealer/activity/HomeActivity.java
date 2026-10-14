package com.creatah.automobile.dealer.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.HomeAdapter;
import com.creatah.automobile.dealer.adapter.NavigationAdapter;
import com.creatah.automobile.dealer.manager.ImageLoader;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AppInfo;
import com.creatah.automobile.dealer.modal.HomeResponse;
import com.creatah.automobile.dealer.modal.RefreshHome;
import com.creatah.automobile.dealer.modal.WorkshopInfo;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.creatah.automobile.dealer.widgets.ballonImage.BallonImageView;
import com.loopj.android.http.RequestParams;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.creatah.automobile.dealer.utils.AppConstants.APP_UPDATE_FLOW;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener, HomeAdapter.MenuListener {

    @BindView(R.id.reminderTodayLayout)
    LinearLayout reminderTodayLayout;

    @BindView(R.id.leadsLayout)
    LinearLayout leadsLayout;

    @BindView(R.id.avgRatingLayout)
    LinearLayout avgRatingLayout;

    @BindView(R.id.completedLayout)
    LinearLayout completedLayout;

    @BindView(R.id.reminderCountTextView)
    TextView reminderCountTextView;

    @BindView(R.id.leadsCountTextView)
    TextView leadsCountTextView;

    @BindView(R.id.ratingTextView)
    TextView ratingTextView;

    @BindView(R.id.completedCountTextView)
    TextView completedCountTextView;

    @BindView(R.id.logoutRelativeLayout)
    RelativeLayout logoutRelativeLayout;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.progressLayout)
    LinearLayout progressLayout;

   /* @BindView(R.id.noResultFound)
    TextView noResultFound;*/

    @BindView(R.id.noInternetConnection)
    TextView noInternetConnection;

    @BindView(R.id.homeLayout)
    LinearLayout homeLayout;




    private String appUpdateSkip = null;
    private NavigationAdapter navDrawerAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerListView;
    private Toolbar navigationToolbar;
    private Context context;
    private TextView userNameHeaderView;
    private ImageView workshopImage;
    private BallonImageView workShopLogo;

    private WorkshopInfo dealerInfo;
    private ImageLoader profilePicLogo;
    private ImageLoader workshopImageLoader;

    private AppInfo appInfo;
    private RecyclerView menuRecyclerView;
    private boolean isService = false;
    private LinearLayout addNewBookingLayout;


    private HomeAdapter homeAdapter;
    private HomeResponse analysisResponse;
    private List<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hideToolbar();
        ButterKnife.bind(this);
        initializeViews();
        context = HomeActivity.this;
        getBundle();
        appInfo = new AppInfo();
        navigationToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(navigationToolbar);
        getSupportActionBar().setTitle(getString(R.string.home));

        profilePicLogo = new ImageLoader(context, R.drawable.profile_default_picture, R.drawable.profile_default_picture);
        workshopImageLoader = new ImageLoader(context, R.drawable.nav_default_workshop_picture, R.drawable.nav_default_workshop_picture);

        dealerInfo = AppUtils.getWorkshopInfo(getApplication());
        setUpDrawer();
        initializeSettingsDrawer();
        getAppInfo();
        addNewBookingLayout.setOnClickListener(this);
        reminderTodayLayout.setOnClickListener(this);
        leadsLayout.setOnClickListener(this);
        avgRatingLayout.setOnClickListener(this);
        completedLayout.setOnClickListener(this);



        stringArrayList.clear();
        stringArrayList.add("0");
        stringArrayList.add("0");
        stringArrayList.add("0");
        stringArrayList.add("0");

        menuRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        homeAdapter = new HomeAdapter((Activity) context, stringArrayList, HomeActivity.this);
        menuRecyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();


        leadsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UserBookingActivity.class));
            }
        });

        logoutRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showAlert(context, "", getResources().getString(R.string.logout_alert), "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AppUtils.clearAppInfo(getApplication());
                        AppUtils.setLogoutDeatails(getApplication());
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();


                    }
                }, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });


            }
        });


    }

    private void getBundle() {


        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(APP_UPDATE_FLOW)) {
            appUpdateSkip = bundle.getString(APP_UPDATE_FLOW);
        }

    }

    private void setUpDrawer() {

        mDrawerListView.setOnItemClickListener(drawerOnItemClickListener);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                navigationToolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initializeViews() {
        navigationToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.navListView);

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.nav_header, null, false);
        userNameHeaderView = (TextView) headerView.findViewById(R.id.userNameHeaderView);
        workShopLogo = (BallonImageView) headerView.findViewById(R.id.workShopProfileImage);
        workshopImage = (ImageView) headerView.findViewById(R.id.workshopImage);

        mDrawerListView.addHeaderView(headerView);

        menuRecyclerView = (RecyclerView) findViewById(R.id.homeRecyclerView);
        addNewBookingLayout = (LinearLayout) findViewById(R.id.addNewBookingLayout);
    }

    private void getDealerTracking() {

        if (DeviceManager.isDeviceOffline(context)) {
            menuRecyclerView.setVisibility(View.GONE);
            homeLayout.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());


        if (!isService) {
            progressLayout.setVisibility(View.VISIBLE);
            isService = true;
            ServiceManager.getAnalysisReport(context, requestParams, new ServiceResponseListener<HomeResponse>() {
                        @Override
                        public void onSuccess(HomeResponse response) {
                            isService = false;
                            progressLayout.setVisibility(View.GONE);
                            if (response != null && !response.isError()) {
                                homeLayout.setVisibility(View.VISIBLE);
                                menuRecyclerView.setVisibility(View.VISIBLE);
                                noInternetConnection.setVisibility(View.GONE);
                                leadsCountTextView.setText(String.valueOf(response.getLeadsCount()));
                                reminderCountTextView.setText(String.valueOf(response.getReminderCount()));
                                ratingTextView.setText(String.valueOf(response.getAverageCount()));
                                completedCountTextView.setText(response.getCompletedService());

                                stringArrayList.clear();
                                stringArrayList.add(response.getOpenCount());
                                stringArrayList.add(response.getCheckInEstimate());
                                stringArrayList.add(response.getInProgressService());
                                stringArrayList.add(response.getVehicleReady());
                                homeAdapter = new HomeAdapter((Activity) context, stringArrayList, HomeActivity.this);
                                menuRecyclerView.setAdapter(homeAdapter);
                                homeAdapter.notifyDataSetChanged();
                            } else if (response != null) {
//                                String message = response.getMessage();
                                homeLayout.setVisibility(View.GONE);
                                menuRecyclerView.setVisibility(View.GONE);
                                noInternetConnection.setText("No Server Connection");
                                noInternetConnection.setVisibility(View.VISIBLE);
//                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            } else {
                                homeLayout.setVisibility(View.GONE);
                                menuRecyclerView.setVisibility(View.GONE);
                                noInternetConnection.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            homeLayout.setVisibility(View.GONE);
                            noInternetConnection.setVisibility(View.VISIBLE);
                            progressLayout.setVisibility(View.GONE);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.title_of_data_message),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }

    }

    private void getAppInfo() {

        if (DeviceManager.isDeviceOffline(context)) {
            homeLayout.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }
        if (!isService) {
            RequestParams requestParams = new RequestParams();
            requestParams.put("version_code", String.valueOf(Utils.getAppVersion(getApplication())));
            requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
            requestParams.put("workshop_id", String.valueOf(AppUtils.getWorkshopInfo(getApplication()).getworkshopId()));
            ServiceManager.getAppInfo(HomeActivity.this, requestParams, new ServiceResponseListener<AppInfo>() {
                        @Override
                        public void onSuccess(AppInfo response) {
                            isService = false;
                            if (response != null && !response.isError()) {
                                appInfo = response;
                                AppUtils.setAppInfo(appInfo, getApplication());
                                int updateStatus = 0;

                                updateStatus = AppUtils.getAppInfo(getApplication()).getUpdateStatus();
                                getDealerTracking();

                                if (updateStatus == com.android.framework.utils.Constants.FORCE_UPDATE || updateStatus == com.android.framework.utils.Constants.SOFT_UPDATE) {

                                    if (appUpdateSkip == null ) {
                                        startActivity(new Intent(context, AppUpdateActivity.class));
                                        finish();
                                    }

                                }



                            } else {
                                NetWorkFailedTryLater();
                            }

                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            NetWorkFailedTryLater();
                        }
                    }

            );
        }

    }

    private void initializeSettingsDrawer() {
        navDrawerAdapter = new NavigationAdapter(this);
        mDrawerListView.setAdapter(navDrawerAdapter);
        dealerInfo = AppUtils.getWorkshopInfo(getApplication());
        if (AppUtils.isAuthenticatedDealerInfo(getApplication())) {
            userNameHeaderView.setText(dealerInfo.getworkshopName());
        }

        /*if (dealerInfo.getWorkshopLogo() != null) {
            String imgURL = Utils.getBaseImageUrl(context) + dealerInfo.getWorkshopLogo();
            profilePicLogo.displayImage(imgURL, workShopLogo);
        } else {
            workShopLogo.setImageDrawable(getResources().getDrawable(R.drawable.profile_default_picture));

        }

        if (dealerInfo.getWorkshopImage() != null) {
            String imgURL = Utils.getBaseImageUrl(context) + dealerInfo.getworkshopImage();
            workshopImageLoader.displayImage(imgURL, workshopImage);
        } else {
            workshopImage.setImageDrawable(getResources().getDrawable(R.drawable.nav_default_workshop_picture));

        }*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private AdapterView.OnItemClickListener drawerOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View arg1, int pos,
                                long arg3) {
            mDrawerLayout.closeDrawers();

            switch (pos) {

                case 0:

                    break;

                case 1:
                    startActivity(new Intent(context, CustomerListActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(context, EmployeeListActivity.class));
                    break;

                case 3:
                    startActivity(new Intent(context, QuickSearch.class));
                    break;

                case 4:
                    startActivity(new Intent(context, ContactUsActivity.class));
                    break;

                case 5:
                    Utils.displayToast(context, getString(R.string.featureUnderDevelopment), Toast.LENGTH_SHORT);
                    break;

                case 6:
                    Utils.displayToast(context, getString(R.string.featureUnderDevelopment), Toast.LENGTH_SHORT);
                    break;

                case 7:
                    String message = getString(R.string.app_share_text);
                    AppUtils.ShareIntentMessage(HomeActivity.this, message);
                    break;


                default:
                    break;
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addNewBookingLayout:
                startActivity(new Intent(context, AddBookingActivity.class));
                break;
            case R.id.completedLayout:
                startActivity(new Intent(context, CompleteBookingActivity.class));
                break;

            case R.id.reminderTodayLayout:
                startActivity(new Intent(context, ReminderListActivity.class));
                break;

            case R.id.avgRatingLayout:
                Utils.displayToast(context, getString(R.string.featureUnderDevelopment), Toast.LENGTH_SHORT);
                break;

        }
    }


    private void checkAppUpdate() {
        int updateStatus = 0;
        if (AppUtils.isAuthenticatedAppInfo(getApplication())) {
            updateStatus = AppUtils.getAppInfo(getApplication()).getUpdateStatus();
        }
        if (updateStatus == com.android.framework.utils.Constants.FORCE_UPDATE) {
            showAlert(HomeActivity.this, getString(R.string.alert),
                    getString(R.string.new_version_launched), getString(R.string.update_button),
                    checkUpdatePositiveListener, getString(R.string.cancel_button), negativeListener);
        }
    }


    private void NetWorkFailedTryLater() {
        noInternetConnection.setVisibility(View.VISIBLE);
        showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.title_of_data_message), getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish();
            }
        }, null, null);
    }

    public DialogInterface.OnClickListener checkUpdatePositiveListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                finish();
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                finish();
            }
        }
    };

    public DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };

    @Override
    public void onMenuClicked(int Pos) {
        switch (Pos) {

            case 0:
                startActivity(new Intent(context, DealerBookingsActivity.class).putExtra(AppConstants.STATUSTYPE, AppConstants.QUEUEDFORSERVICE));
                break;

            case 1:
                startActivity(new Intent(context, DealerBookingsActivity.class).putExtra(AppConstants.STATUSTYPE, AppConstants.CHECKINCOMPLETED));

                break;

            case 2:
                startActivity(new Intent(context, DealerBookingsActivity.class).putExtra(AppConstants.STATUSTYPE, AppConstants.SERVICE_UNDER_PROGRESS));
                break;

            case 3:
                startActivity(new Intent(context, DealerBookingsActivity.class).putExtra(AppConstants.STATUSTYPE, AppConstants.VEHICLE_READY));
                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEvent(RefreshHome record) {
        getDealerTracking();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


