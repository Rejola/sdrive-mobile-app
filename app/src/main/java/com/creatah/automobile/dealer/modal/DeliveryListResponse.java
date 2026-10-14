package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class DeliveryListResponse extends BaseResponse {

    @SerializedName("delivery_list")
    private List<Customer> deliveryList;

    private int total_count;

    private int page_count;

    public List<Customer> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Customer> deliveryList) {
        this.deliveryList = deliveryList;
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
