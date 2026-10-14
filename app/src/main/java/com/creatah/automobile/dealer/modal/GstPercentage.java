package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GstPercentage implements Serializable {


    @SerializedName("gst_percentage")
    private  String gstPercentage;

    public String getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(String gstPercentage) {
        this.gstPercentage = gstPercentage;
    }
}
