package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class Booking implements Serializable {

    @SerializedName("booking_id")
    private int bookingId;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("customer_phone_no")
    private String customerPhoneNo;

    @SerializedName("brand")
    private String brand;

    @SerializedName("model")
    private String modal;

    @SerializedName("vehicle_no")
    private String vehicleNo;

    @SerializedName("service_name")
    private String serviceType;

    @SerializedName("service_date")
    private String serviceDate;

    @SerializedName("delivery_date")
    private String deliveryDate;

    @SerializedName("follow_up_date")
    private String followUpDate;

    @SerializedName("final_bill")
    private String finalBill;

    @SerializedName("ratting")
    private String ratting;

    @SerializedName("created_date")
    private String CreatedDate;

    @SerializedName("updated_date")
    private String UpdatedDate;

    @SerializedName("status")
    private String status;

    @SerializedName("is_acknowledged")
    private int isAcknowledged;

    @SerializedName("pickup_date_time")
    private String pickUpDateTime;

    @SerializedName("latitude")
    private String  latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("pick_up_address")
    private String pickUpAddress;

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPickUpDateTime() {
        return pickUpDateTime;
    }

    public void setPickUpDateTime(String pickUpDateTime) {
        this.pickUpDateTime = pickUpDateTime;
    }

    public int getIsAcknowledged() {
        return isAcknowledged;
    }

    public void setIsAcknowledged(int isAcknowledged) {
        this.isAcknowledged = isAcknowledged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getFinalBill() {
        return finalBill;
    }

    public void setFinalBill(String finalBill) {
        this.finalBill = finalBill;
    }

    public String getRatting() {
        return ratting;
    }

    public void setRatting(String ratting) {
        this.ratting = ratting;
    }
}
