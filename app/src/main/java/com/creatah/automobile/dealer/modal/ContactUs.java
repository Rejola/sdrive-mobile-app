package com.creatah.automobile.dealer.modal;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContactUs implements Serializable{


    @SerializedName("admin_email")
    private String adminEmail;

    @SerializedName("admin_mobileno")
    private String adminMobile;

    @SerializedName("technical_email")
    private String technicalEmail;

    @SerializedName("technical_mobileno")
    private String technicalMobileNo;

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public String getTechnicalEmail() {
        return technicalEmail;
    }

    public void setTechnicalEmail(String technicalEmail) {
        this.technicalEmail = technicalEmail;
    }

    public String getTechnicalMobileNo() {
        return technicalMobileNo;
    }

    public void setTechnicalMobileNo(String technicalMobileNo) {
        this.technicalMobileNo = technicalMobileNo;
    }
}
