package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class Reminder implements Serializable {

    @SerializedName("reminder_id")
    private int reminderId;

    @SerializedName("customer_id")
    private int customerId;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("mobile_mo")
    private String mobileNo;

    @SerializedName("vehicle_no")
    private String vehicleNo;

    @SerializedName("make_name")
    private String makeName;

    @SerializedName("make_image")
    private String makeImage;

    @SerializedName("model")
    private String model;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("service_reminder_date")
    private String followUpDate;

    @SerializedName("description")
    private String description;

    public int getRemainderId() {
        return reminderId;
    }

    public void setRemainderId(int remainderId) {
        this.reminderId = remainderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getMakeImage() {
        return makeImage;
    }

    public void setMakeImage(String makeImage) {
        this.makeImage = makeImage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
