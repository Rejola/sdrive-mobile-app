package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by creatah1 on 05-05-2017.
 */

public class CheckListResponse extends BaseResponse {

    @SerializedName("check_list")
    private List<CheckOut> checkOutList;

    private int total_count;

    private int page_count;

    @SerializedName("km_driven")
    private int kmsDriven;

    @SerializedName("description")
    private String description;

    public List<CheckOut> getCheckOutList() {
        return checkOutList;
    }

    public void setCheckOutList(List<CheckOut> checkOutList) {
        this.checkOutList = checkOutList;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getKmsDriven() {
        return kmsDriven;
    }

    public void setKmsDriven(int kmsDriven) {
        this.kmsDriven = kmsDriven;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
