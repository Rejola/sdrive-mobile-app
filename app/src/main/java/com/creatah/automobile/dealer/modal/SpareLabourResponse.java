package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpareLabourResponse extends BaseResponse{

    @SerializedName("spare_labour_list")
    private List<SpareLabour> spareLabourList;

    public List<SpareLabour> getSpareLabourList() {
        return spareLabourList;
    }

    public void setSpareLabourList(List<SpareLabour> spareLabourList) {
        this.spareLabourList = spareLabourList;
    }
}
