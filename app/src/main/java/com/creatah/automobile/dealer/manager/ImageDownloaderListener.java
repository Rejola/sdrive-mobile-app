package com.creatah.automobile.dealer.manager;

import android.graphics.Bitmap;

public interface ImageDownloaderListener {
    public void imageDownloadComplete(Bitmap bitmap);

    public void imageDownloadFailed();
}
