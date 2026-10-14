package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class InstantServiceListResponse extends BaseResponse {

    @SerializedName("instance_service_list")
    private List<InstantService> serviceList;

    private int total_count;

    private int page_count;

    public List<InstantService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<InstantService> serviceList) {
        this.serviceList = serviceList;
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
}
