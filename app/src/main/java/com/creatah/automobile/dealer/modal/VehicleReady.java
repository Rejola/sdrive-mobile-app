package com.creatah.automobile.dealer.modal;

import java.io.Serializable;

/**
 * Created by user on 06-04-2018.
 */

public class VehicleReady implements Serializable{
    private int serviceId;
    private String userId;
    private int qty;
    private String cost;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
