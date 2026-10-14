package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by creatah1 on 02-05-2017.
 */

public class BookingImageListResponse extends BaseResponse {

    @SerializedName("status_image_list")
    private List<BookingImage> imageList;

    private boolean isCompleted;

    public List<BookingImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<BookingImage> imageList) {
        this.imageList = imageList;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
