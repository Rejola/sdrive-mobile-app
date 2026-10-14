package com.creatah.automobile.dealer.manager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoader {
	private final com.nostra13.universalimageloader.core.ImageLoader imageLoader;
	private final DisplayImageOptions options;

	public ImageLoader(Context context, int onLoading, int onFailed) {
		imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(onLoading)
		.showImageForEmptyUri(onFailed)
		.showImageOnFail(onFailed)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.ARGB_8888)
		.build();
	}

	public void displayImage(String url, ImageView imageView) {
		imageLoader.displayImage(url, imageView, options);
	}
	
	
	public void getBitmap(String imageUrl, final ImageDownloaderListener imageDownloaderListener){
		imageLoader.loadImage(imageUrl, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
				if(imageDownloaderListener != null){
					imageDownloaderListener.imageDownloadFailed();
				}
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if(imageDownloaderListener != null){
					imageDownloaderListener.imageDownloadComplete(loadedImage);
				}
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				if(imageDownloaderListener != null){
					imageDownloaderListener.imageDownloadFailed();
				}
			}
		});
	}

	public void pause(){
		imageLoader.pause();
	}

	public void resume(){
		imageLoader.resume();
	}

	public void clearCache() {
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public void stop() {
		imageLoader.stop();
	}


}
