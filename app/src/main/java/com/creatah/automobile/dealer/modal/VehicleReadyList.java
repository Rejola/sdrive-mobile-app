package com.creatah.automobile.dealer.modal;

import com.android.theme.activity.BaseActivity;

import java.util.List;

/**
 * Created by user on 06-04-2018.
 */

public class VehicleReadyList extends BaseResponse {
    private List<VehicleReady> vehicleReadyList;

    private int total_count;

    private int page_count;

    public List<VehicleReady> getVehicleReadyList() {
        return vehicleReadyList;
    }

    public void setVehicleReadyList(List<VehicleReady> vehicleReadyList) {
        this.vehicleReadyList = vehicleReadyList;
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
