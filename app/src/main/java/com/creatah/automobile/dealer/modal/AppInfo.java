package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karthikeyan on 16-11-2016.
 */

public class AppInfo extends BaseResponse {

    @SerializedName("service_list")
    private List<AutoMobileService> serviceList;

    @SerializedName("car_make_list")
    private List<MakeList> carMakeList;

    @SerializedName("bike_make_list")
    private List<MakeList> bikeMakeList;

    @SerializedName("update_status")
    private int updateStatus;

    @SerializedName("employee_list")
    private List<Employee> employeeList;

    @SerializedName("insurance_list")
    private List<Insurance> insuranceList;

    @SerializedName("designation_list")
    private List<Designation> designationList;

    @SerializedName("contact_details")
    private ContactUs contactDetails;

    @SerializedName("gst_list")
    private List<GstPercentage> gstList;

    @SerializedName("jobcard_price_edit_status")
    private boolean editJobCardPrice;

    public List<GstPercentage> getGstList() {
        return gstList;
    }

    public void setGstList(List<GstPercentage> gstList) {
        this.gstList = gstList;
    }

    public ContactUs getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactUs contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<AutoMobileService> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<AutoMobileService> serviceList) {
        this.serviceList = serviceList;
    }

    public List<MakeList> getCarMakeList() {
        return carMakeList;
    }

    public void setCarMakeList(List<MakeList> carMakeList) {
        this.carMakeList = carMakeList;
    }

    public List<MakeList> getBikeMakeList() {
        return bikeMakeList;
    }

    public void setBikeMakeList(List<MakeList> bikeMakeList) {
        this.bikeMakeList = bikeMakeList;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Insurance> getInsuranceList() {
        return insuranceList;
    }

    public void setInsuranceList(List<Insurance> insuranceList) {
        this.insuranceList = insuranceList;
    }

    public List<Designation> getDesignationList() {
        return designationList;
    }

    public void setDesignationList(List<Designation> designationList) {
        this.designationList = designationList;
    }

    public boolean isEditJobCardPrice() {
        return editJobCardPrice;
    }

    public void setEditJobCardPrice(boolean editJobCardPrice) {
        this.editJobCardPrice = editJobCardPrice;
    }
}
