package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.ZoomPagerAdapter1;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddImageResponse;
import com.creatah.automobile.dealer.modal.BookingImage;
import com.creatah.automobile.dealer.modal.BookingImageListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 02-05-2017.
 */

public class ImageZoomActivity extends BaseActivity implements ZoomPagerAdapter1.onImageClickListener {

    private ViewPager viewPager;

    private ZoomPagerAdapter1 zoomPagerAdapter;

    private BookingImageListResponse imageList;

    private List<BookingImage> imageurls = new ArrayList<>();

    private int postion;

    private ImageView previousbutton;

    private ImageView nextbutton;

    private Context context;

    private boolean isService = false;

    private boolean imageDeleteOption;


    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_image);

        hideToolbar();
        previousbutton = (ImageView) findViewById(R.id.prev_button);
        nextbutton = (ImageView) findViewById(R.id.next_button);
        viewPager = (ViewPager) findViewById(R.id.eventDetailsViewpager);
        context = this;
        getbundle();

        if (imageurls != null && imageurls.size() > 0) {
            zoomPagerAdapter = new ZoomPagerAdapter1(ImageZoomActivity.this, imageurls, imageList.isCompleted(), ImageZoomActivity.this);
            viewPager.setAdapter(zoomPagerAdapter);
            viewPager.setCurrentItem(postion);
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 0) {
                    previousbutton.setVisibility(View.INVISIBLE);
                    if (imageurls.size() > 1) {
                        nextbutton.setVisibility(View.VISIBLE);
                    } else {
                        nextbutton.setVisibility(View.INVISIBLE);
                    }
                } else if (position == imageurls.size() - 1) {
                    nextbutton.setVisibility(View.INVISIBLE);
                    previousbutton.setVisibility(View.VISIBLE);

                } else {
                    previousbutton.setVisibility(View.VISIBLE);
                    nextbutton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        previousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

    }

    private void getbundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.FULLSIZE_IMAGE_EXTRA)) {
            imageList = (BookingImageListResponse) bundle.getSerializable(AppConstants.FULLSIZE_IMAGE_EXTRA);
            postion = bundle.getInt(AppConstants.FULLSIZE_IMAGE_POSITION);
            imageDeleteOption=bundle.getBoolean(AppConstants.IMAGE_DELETE_OPTION);
            imageurls = imageList.getImageList();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            supportFinishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void onDeleteImage(final int post) {
        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_status_image_id", String.valueOf(imageurls.get(post).getImageId()));

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.deleteImage(context, requestParams, new ServiceResponseListener<AddImageResponse>() {
                        @Override
                        public void onSuccess(AddImageResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {


                                imageurls.remove(imageurls.get(post));
                                setResult(RESULT_OK);


                                if (imageurls.size() == 0) {
                                    finish();
                                } else {
                                    zoomPagerAdapter = new ZoomPagerAdapter1(ImageZoomActivity.this, imageurls, imageList.isCompleted(), ImageZoomActivity.this);
                                    viewPager.setAdapter(zoomPagerAdapter);
                                    viewPager.setCurrentItem(post-1);
                                }
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}