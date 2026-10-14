package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Labour implements Serializable {

    @SerializedName("labour_id")
    private int labourID;

    @SerializedName("labour_category")
    private String labourServiceType;

    @SerializedName("labour_amount")
    private String labourAmount;

    @SerializedName("labour_tax_amount")
    private String labourTaxAmount;

    public int getLabourID() {
        return labourID;
    }

    public void setLabourID(int labourID) {
        this.labourID = labourID;
    }

    public String getLabourServiceType() {
        return labourServiceType;
    }

    public void setLabourServiceType(String labourServiceType) {
        this.labourServiceType = labourServiceType;
    }

    public String getLabourAmount() {
        return labourAmount;
    }

    public void setLabourAmount(String labourAmount) {
        this.labourAmount = labourAmount;
    }

    public String getLabourTaxAmount() {
        return labourTaxAmount;
    }

    public void setLabourTaxAmount(String labourTaxAmount) {
        this.labourTaxAmount = labourTaxAmount;
    }
}
