package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 16-02-2017.
 */

public class InstantService implements Serializable {

    @SerializedName("id")
    private int serviceId;

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

    @SerializedName("fuel_type")
    private String fuelType;

    @SerializedName("vehicle_varrient")
    private String vehicleVariant;

    @SerializedName("service_name")
    private String serviceName;



    @SerializedName("service_cost")
    private double serviceCost;

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    private String updatedKmsDriven;

    @SerializedName("updated_date")
    private String date;

    @SerializedName("service_description")
    private String description;

    @SerializedName("address")
    private String address;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUpdatedKmsDriven() {
        return updatedKmsDriven;
    }

    public void setUpdatedKmsDriven(String updatedKmsDriven) {
        this.updatedKmsDriven = updatedKmsDriven;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
