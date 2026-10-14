package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class WorkshopInfo implements Serializable {

    @SerializedName("id")
    private int workshopId;

    @SerializedName("workshop_name")
    private String workshopName;

    @SerializedName("email_id")
    private String workshopEmail;

    @SerializedName("phone_no")
    private String workshopPhoneNo;

    @SerializedName("address")
    private String workshopAddress;

    @SerializedName("auth_token")
    private String authToken;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("dm_image")
    private String workshopImage;

    @SerializedName("owner_first_name")
    private String firstOwnerName;

    @SerializedName("owner_last_name")
    private String lastOwnerName;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    @SerializedName("postal_code")
    private String postalCode;

    @SerializedName("subscription_date")
    private String subscriptionDate;

    @SerializedName("sms_user_name")
    private String smsUserName;


    @SerializedName("sms_api_key")
    private String smsApiKey;

    @SerializedName("sms_sender_id")
    private String smsSenderId;

    @SerializedName("workshop_time")
    private String workshopTime;

    @SerializedName("dworkshop_days")
    private String dworkshopDays;


    @SerializedName("square_feet")
    private String squareFeet;

    @SerializedName("pan_no")
    private String panNo;

    @SerializedName("workshop_logo")
    private String workshopLogo;

    @SerializedName("type")
    private String type;

    public int getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(int workshopId) {
        this.workshopId = workshopId;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkshopEmail() {
        return workshopEmail;
    }

    public void setWorkshopEmail(String workshopEmail) {
        this.workshopEmail = workshopEmail;
    }

    public String getWorkshopPhoneNo() {
        return workshopPhoneNo;
    }

    public void setWorkshopPhoneNo(String workshopPhoneNo) {
        this.workshopPhoneNo = workshopPhoneNo;
    }

    public String getWorkshopAddress() {
        return workshopAddress;
    }

    public void setWorkshopAddress(String workshopAddress) {
        this.workshopAddress = workshopAddress;
    }

    public String getWorkshopImage() {
        return workshopImage;
    }

    public void setWorkshopImage(String workshopImage) {
        this.workshopImage = workshopImage;
    }

    public String getFirstOwnerName() {
        return firstOwnerName;
    }

    public void setFirstOwnerName(String firstOwnerName) {
        this.firstOwnerName = firstOwnerName;
    }

    public String getLastOwnerName() {
        return lastOwnerName;
    }

    public void setLastOwnerName(String lastOwnerName) {
        this.lastOwnerName = lastOwnerName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public String getSmsUserName() {
        return smsUserName;
    }

    public void setSmsUserName(String smsUserName) {
        this.smsUserName = smsUserName;
    }

    public String getSmsApiKey() {
        return smsApiKey;
    }

    public void setSmsApiKey(String smsApiKey) {
        this.smsApiKey = smsApiKey;
    }

    public String getSmsSenderId() {
        return smsSenderId;
    }

    public void setSmsSenderId(String smsSenderId) {
        this.smsSenderId = smsSenderId;
    }

    public String getWorkshopTime() {
        return workshopTime;
    }

    public void setWorkshopTime(String workshopTime) {
        this.workshopTime = workshopTime;
    }

    public String getDworkshopDays() {
        return dworkshopDays;
    }

    public void setDworkshopDays(String dworkshopDays) {
        this.dworkshopDays = dworkshopDays;
    }

    public String getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(String squareFeet) {
        this.squareFeet = squareFeet;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getWorkshopLogo() {
        return workshopLogo;
    }

    public void setWorkshopLogo(String workshopLogo) {
        this.workshopLogo = workshopLogo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SerializedName("description")
    private String description;


    public String getworkshopImage() {
        return workshopImage;
    }

    public void setworkshopImage(String workshopImage) {
        this.workshopImage = workshopImage;
    }

    public int getworkshopId() {
        return workshopId;
    }

    public void setworkshopId(int workshopId) {
        this.workshopId = workshopId;
    }

    public String getworkshopName() {
        return workshopName;
    }

    public void setworkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getworkshopEmail() {
        return workshopEmail;
    }

    public void setworkshopEmail(String workshopEmail) {
        this.workshopEmail = workshopEmail;
    }

    public String getworkshopPhoneNo() {
        return workshopPhoneNo;
    }

    public void setworkshopPhoneNo(String workshopPhoneNo) {
        this.workshopPhoneNo = workshopPhoneNo;
    }

    public String getworkshopAddress() {
        return workshopAddress;
    }

    public void setworkshopAddress(String workshopAddress) {
        this.workshopAddress = workshopAddress;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
