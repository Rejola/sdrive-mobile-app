package com.creatah.automobile.dealer.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.adapter.ImageGalleryAdapter;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.AddImageResponse;
import com.creatah.automobile.dealer.modal.BookingImageListResponse;
import com.creatah.automobile.dealer.modal.DealerBooking;
import com.creatah.automobile.dealer.utils.AppConstants;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by creatah1 on 02-05-2017.
 */

public class ImageListActivity extends BaseActivity {

    private RecyclerView imageRecyclerView;
    private TextView noResultFound;

    private int IMAGE_REQ_CODE = 457;
    private int REQUEST_PERMISSION_SETTING = 302;
    private final int MY_REQUEST_PERMISSION = 77;
    private DealerBooking booking;
    private String status;
    private Context context;
    private boolean isService;
    public static int ZOOM_REQ_CODE = 489;
    private int refresh = RESULT_CANCELED;
    private Bitmap bitmap;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button retryButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        setTitle(getString(R.string.imageList));
        initializeViews();
        context = this;
        getBundle();
        imageRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        swipeRefreshLayout.setEnabled(true);
        getImageList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImageList();
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

            }
        });
    }

    private void getImageList() {
        if (DeviceManager.isDeviceOffline(context)) {
            imageRecyclerView.setVisibility(View.GONE);
            noResultFound.setVisibility(View.VISIBLE);
            noResultFound.setText(getString(R.string.title_of_data_message));
            retryButton.setVisibility(View.GONE);
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("status", status);


        if (!isService) {

            isService = true;
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);

            ServiceManager.getBookingImageList(context, requestParams, new ServiceResponseListener<BookingImageListResponse>() {
                        @Override
                        public void onSuccess(BookingImageListResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError() && response.getImageList() != null && response.getImageList().size() > 0) {
                                noResultFound.setVisibility(View.GONE);
                                imageRecyclerView.setVisibility(View.VISIBLE);
                                response.setCompleted(booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey)));
                                imageRecyclerView.setAdapter(new ImageGalleryAdapter(ImageListActivity.this, response));
                            } else {
                                noResultFound.setVisibility(View.VISIBLE);
                                imageRecyclerView.setVisibility(View.GONE);
                                noResultFound.setText(getString(R.string.no_image_found));
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            imageRecyclerView.setVisibility(View.GONE);
                            retryButton.setVisibility(View.VISIBLE);
                            retryButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getImageList();
                                }
                            });
                           /* noResultFound.setVisibility(View.VISIBLE);
                            noResultFound.setText(getString(R.string.server_unavailable));*/
                        }
                    }

            );
        }

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.ADD_IMAGE_EXTRA) && bundle.containsKey(AppConstants.BOOKING_DETAILS_EXTRA)) {
            booking = (DealerBooking) bundle.getSerializable(AppConstants.BOOKING_DETAILS_EXTRA);
            status = bundle.getString(AppConstants.ADD_IMAGE_EXTRA);
        }
    }

    private void initializeViews() {
        imageRecyclerView = (RecyclerView) findViewById(R.id.imageRecyclerView);
        noResultFound = (TextView) findViewById(R.id.noResultFound);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        retryButton=(Button) findViewById(R.id.retryButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    if (!booking.getStatus().equalsIgnoreCase(getString(R.string.vehicleDeliveredKey))) {
            getMenuInflater().inflate(R.menu.image_menu, menu);
       }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_add:
                checkpermission();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    private void checkpermission() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(ImageListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(ImageListActivity.this, ImageCropperActivity.class), IMAGE_REQ_CODE);

                //startActivityForResult(getPickImageChooserIntent(), IMAGE_PICK_REQ_CODE);
            } else if (ContextCompat.checkSelfPermission(ImageListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(ImageListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showAlert(ImageListActivity.this, getString(R.string.storagepermission), getString(R.string.storagepermissiondesc), "Try", Try, "No", No);
                } else {
                    ActivityCompat.requestPermissions(ImageListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_PERMISSION);
                }
            }
        } else {
            startActivityForResult(new Intent(ImageListActivity.this, ImageCropperActivity.class), IMAGE_REQ_CODE);
            //startActivityForResult(getPickImageChooserIntent(), IMAGE_PICK_REQ_CODE);
        }


    }

    DialogInterface.OnClickListener Try = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCompat.requestPermissions(ImageListActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_REQUEST_PERMISSION);
        }
    };

    DialogInterface.OnClickListener No = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

// the main intent is the last in the  list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }


    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            isCamera = false;
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == ZOOM_REQ_CODE && resultCode == RESULT_OK) {
            refresh = RESULT_OK;
            getImageList();
        }

        if (requestCode == IMAGE_REQ_CODE && resultCode == RESULT_OK) {
            bitmap = ImageCropperActivity.bitmap;

            uploadFile(AppUtils.getRotatedPictureFile(context, bitmap));

        }
    }

    private void uploadFile(File file) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("auth_token", AppUtils.getWorkshopInfo(getApplication()).getAuthToken());
        requestParams.put("booking_id", String.valueOf(booking.getJobcardId()));
        requestParams.put("status", status);
        if (file != null && file.exists() && file.isFile()) {
            try {
                requestParams.put("files", file);//AppUtils.getRotatedPictureFile(ImageListActivity.this, bitmap));

            } catch (FileNotFoundException ex) {

                ex.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addImage(requestParams);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_PERMISSION:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(ImageListActivity.this, ImageCropperActivity.class), IMAGE_REQ_CODE);

                } else {

                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                }
                break;


        }
    }

    private void addImage(RequestParams requestParams) {


        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.addImage(context, requestParams, new ServiceResponseListener<AddImageResponse>() {
                        @Override
                        public void onSuccess(AddImageResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                refresh = RESULT_OK;
                                getImageList();
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
    public void onBackPressed() {
        setResult(refresh);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
