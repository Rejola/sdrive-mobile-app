package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class CustomerEnquiry implements Serializable {

    @SerializedName("enquiry_id")
    private int enquiryId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String customerName;

    @SerializedName("email_id")
    private String cusEmail;

    @SerializedName("mobile_no")
    private String cusPhoneNo;

    @SerializedName("vehicle_type")
    private String vehicleType;

    @SerializedName("vehicle_no")
    private String vehicleNo;

    @SerializedName("make")
    private String make;

    @SerializedName("make_image")
    private String makeImage;

    @SerializedName("model")
    private String model;

    @SerializedName("km_driven")
    private String kmsDriven;

    @SerializedName("manufacture_year")
    private String manufactureYear;

    @SerializedName("enquiry_description")
    private String requirement;

    @SerializedName("fuel_type")
    private String fuelType;

    @SerializedName("vehicle_varrient")
    private String vehicleVariant;

    private String updatedKmsDriven;

    @SerializedName("enquiry_vehicle_varrient")
    private String updatedVariant;

    @SerializedName("enquiry_preferred_location")
    private String locality;

    @SerializedName("enquiry_year_of_manufacture")
    private String updatedManufactureYear;

    @SerializedName("updated_date")
    private String date;

    public int getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(int enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusPhoneNo() {
        return cusPhoneNo;
    }

    public void setCusPhoneNo(String cusPhoneNo) {
        this.cusPhoneNo = cusPhoneNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public String getKmsDriven() {
        return kmsDriven;
    }

    public void setKmsDriven(String kmsDriven) {
        this.kmsDriven = kmsDriven;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleVariant() {
        return vehicleVariant;
    }

    public void setVehicleVariant(String vehicleVariant) {
        this.vehicleVariant = vehicleVariant;
    }

    public String getUpdatedKmsDriven() {
        return updatedKmsDriven;
    }

    public void setUpdatedKmsDriven(String updatedKmsDriven) {
        this.updatedKmsDriven = updatedKmsDriven;
    }

    public String getUpdatedVariant() {
        return updatedVariant;
    }

    public void setUpdatedVariant(String updatedVariant) {
        this.updatedVariant = updatedVariant;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getUpdatedManufactureYear() {
        return updatedManufactureYear;
    }

    public void setUpdatedManufactureYear(String updatedManufactureYear) {
        this.updatedManufactureYear = updatedManufactureYear;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
