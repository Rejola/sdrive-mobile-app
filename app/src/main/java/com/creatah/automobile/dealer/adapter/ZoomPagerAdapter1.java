package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.framework.utils.Utils;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ImageLoader;
import com.creatah.automobile.dealer.modal.BookingImage;
import com.creatah.automobile.dealer.widgets.ImageZoom.ImageViewTouch;
import com.creatah.automobile.dealer.widgets.ImageZoom.ImageViewTouchBase;

import java.util.List;

/**
 * Created by karthikeyan on 09-08-2016.
 */
public class ZoomPagerAdapter1 extends PagerAdapter {

    private ImageViewTouch customImageVIew;

    private LinearLayout deleteLayout;

    private List<BookingImage> imageurls;

    private Activity activity;

    private LayoutInflater inflater;

    private ImageLoader imageLoader;
    private String IMAGE_ENDPOINT_URL;
    private onImageClickListener clickListener;
    private boolean isCompleted;
    private boolean imageDeleteOption;

    public ZoomPagerAdapter1(Activity activity, List<BookingImage> imageurls, boolean isCompleted, onImageClickListener clickListener) {
        this.activity = activity;
        this.imageurls = imageurls;
        this.clickListener = clickListener;
        this.isCompleted = isCompleted;
        this.imageDeleteOption=imageDeleteOption;
        IMAGE_ENDPOINT_URL = Utils.getBaseImageUrl(activity);
        imageLoader = new ImageLoader(activity, R.drawable.loading_image, R.drawable.no_image);
    }

    @Override
    public int getCount() {
        return imageurls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.layout_zoom_viewpager, container, false);
        customImageVIew = (ImageViewTouch) itemView.findViewById(R.id.carimageView);
        deleteLayout = (LinearLayout) itemView.findViewById(R.id.deleteLayout);
        customImageVIew.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        if (imageurls.get(position) != null) {
            String imgURL = IMAGE_ENDPOINT_URL + imageurls.get(position).getImagePath();
            imageLoader.displayImage(imgURL, customImageVIew);
        } else {
            customImageVIew.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_image));
        }
        if(isCompleted){
            deleteLayout.setVisibility(View.GONE);
        }else {
            deleteLayout.setVisibility(View.VISIBLE);
        }



        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        if (options.outWidth > 3000 || options.outHeight > 2000) {
            options.inSampleSize = 4;
        } else if (options.outWidth > 2000 || options.outHeight > 1500) {
            options.inSampleSize = 3;
        } else if (options.outWidth > 1000 || options.outHeight > 1000) {
            options.inSampleSize = 2;
        }

        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onDeleteImage(position);
            }
        });


            ((ViewPager) container).addView(itemView);

            return itemView;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    public interface onImageClickListener{
        void onDeleteImage(int post);
    }
}