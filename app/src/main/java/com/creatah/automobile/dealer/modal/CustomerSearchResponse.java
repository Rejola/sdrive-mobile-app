package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by santhana on 02/06/18.
 */

public class CustomerSearchResponse extends BaseResponse{

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_no")
    private String mobileNo;

    @SerializedName("address")
    private String address;

    @SerializedName("vehicle_details")
    private List<Vehicle> vehicleList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
