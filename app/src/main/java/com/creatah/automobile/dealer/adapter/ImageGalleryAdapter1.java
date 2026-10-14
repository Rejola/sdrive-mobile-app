package com.creatah.automobile.dealer.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.framework.utils.Utils;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.activity.ImageListActivity;
import com.creatah.automobile.dealer.activity.ImageZoomActivity;
import com.creatah.automobile.dealer.manager.ImageLoader;
import com.creatah.automobile.dealer.modal.BookingImage;
import com.creatah.automobile.dealer.modal.BookingImageListResponse;
import com.creatah.automobile.dealer.utils.AppConstants;

import java.util.List;

public class ImageGalleryAdapter1 extends RecyclerView.Adapter<ImageGalleryAdapter1.ImageViewHolder> {

    private Activity activity;
    private List<BookingImage> imageList;
    private BookingImageListResponse response;
    private ImageLoader imageLoader;
    private int height, width;

    public ImageGalleryAdapter1(Activity activity, BookingImageListResponse response) {
        this.activity = activity;
        this.response = response;
        this.imageList = response.getImageList();
        imageLoader = new ImageLoader(activity, R.drawable.loading_image, R.drawable.no_image);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout_gallery1, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        final BookingImage image = imageList.get(position);


        if (image.getImagePath() != null) {
            String imgURL = Utils.getBaseImageUrl(activity) + image.getImagePath();
            imageLoader.displayImage(imgURL, holder.horizontalScrollViewImage);
        } else {
            holder.horizontalScrollViewImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_image));
        }

        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zoomIntent = new Intent(activity, ImageZoomActivity.class);
                zoomIntent.putExtra(AppConstants.FULLSIZE_IMAGE_EXTRA, response);
                zoomIntent.putExtra(AppConstants.FULLSIZE_IMAGE_POSITION, position);
                zoomIntent.putExtra(AppConstants.IMAGE_DELETE_OPTION, false);


                activity.startActivityForResult(zoomIntent, ImageListActivity.ZOOM_REQ_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView horizontalScrollViewImage;
        private CardView imageLayout;

        public ImageViewHolder(View itemView) {
            super(itemView);
            horizontalScrollViewImage = (ImageView) itemView.findViewById(R.id.horizontalScrollViewImage);
            imageLayout = (CardView) itemView.findViewById(R.id.imageLayout);
        }
    }
}