package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 10-03-2017.
 */

public class Insurance implements Serializable {

    @SerializedName("id")
    private int insuranceID;

    @SerializedName("ivm_vVendorName")
    private String insuranceName;

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }
}
