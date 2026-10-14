package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by creatah1 on 11-04-2017.
 */

public class DealerBooking implements Serializable {

    @SerializedName("booking_id")
    private int jobcardId;

    @SerializedName("customer_id")
    private int customerId;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("address")
    private String address;

    @SerializedName("mobile_mo")
    private String mobileNo;

    @SerializedName("email_id")
    private String emailId;

    @SerializedName("vehicle_no")
    private String vehicleNo;

    @SerializedName("make_name")
    private String makeName;

    @SerializedName("make_image")
    private String makeImage;

    @SerializedName("model")
    private String model;

    @SerializedName("fuel_type")
    private String fuelType;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("insurance_vendor_name")
    private String insuranceVendorName;

    @SerializedName("insurance_expiry_date")
    private String insuranceExpiryDate;

    @SerializedName("service_date")
    private String serviceDate;

    @SerializedName("employee_name")
    private String employeeName;

    @SerializedName("employee_no")
    private String employeeNo;

    @SerializedName("designation_name")
    private String designation;

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("pickup_status")
    private boolean pickupStatus;

    @SerializedName("pickup_person")
    private int pickupPerson;


    @SerializedName("pick_up_address")
    private String pickupAddress;


    @SerializedName("pickup_date")
    private String pickDate;

    @SerializedName("pickup_time")
    private String pickUpTime;


    @SerializedName("status")
    private String status;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("check_in_images_count")
    private String checkInCount;

    @SerializedName("inspection_completed_images_count")
    private String inspectionCount;

    @SerializedName("service_under_progress_images_count")
    private String inProgressCount;

    @SerializedName("vehicle_ready_images_count")
    private String readyCount;

    @SerializedName("vehicle_delivery_images_count")
    private String deliveredCount;

    @SerializedName("check_in_status")
    private boolean checkInCompleted;

    @SerializedName("inspection_completed_status")
    private boolean inspectionCompleted;

    @SerializedName("service_under_progress_status")
    private boolean isUnderProgress;

    @SerializedName("vehicle_ready_status")
    private boolean isVehicleReady;

    @SerializedName("vehicle_delivered_status")
    private boolean isVehicleCompleted;

    @SerializedName("delivery_date_time")
    private String deliveryDate;

    @SerializedName("approx_delivery_date")
    private String approximateDeliveryDate;

    @SerializedName("approx_delivery_time")
    private String approximateDeliveryTime;

    @SerializedName("estimate_amount")
    private double estimateAmount;

    @SerializedName("jobcard_description")
    private String jobcardDescription;

    @SerializedName("advisor_id")
    private int adviserId;

    @SerializedName("advisor_name")
    private String adviserName;

    @SerializedName("rating_description")
    private String ratingDescription;

    @SerializedName("check_in_date")
    private String checkInDate;

    @SerializedName("service_progress_date")
    private String serviceProgressDate;

    @SerializedName("vehicle_ready_date")
    private String vehicleReadyDate;

    @SerializedName("vehicle_delivered_date")
    private String vehicleDeliveredDate;




    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getServiceProgressDate() {
        return serviceProgressDate;
    }

    public void setServiceProgressDate(String serviceProgressDate) {
        this.serviceProgressDate = serviceProgressDate;
    }

    public String getVehicleReadyDate() {
        return vehicleReadyDate;
    }

    public void setVehicleReadyDate(String vehicleReadyDate) {
        this.vehicleReadyDate = vehicleReadyDate;
    }

    public String getVehicleDeliveredDate() {
        return vehicleDeliveredDate;
    }

    public void setVehicleDeliveredDate(String vehicleDeliveredDate) {
        this.vehicleDeliveredDate = vehicleDeliveredDate;
    }

    @SerializedName("rating")
    private float ratingCount;


    @SerializedName("date_of_rating")
    private String dateOfRating;

    public String getApproximateDeliveryDate() {
        return approximateDeliveryDate;
    }

    public void setApproximateDeliveryDate(String approximateDeliveryDate) {
        this.approximateDeliveryDate = approximateDeliveryDate;
    }

    public String getApproximateDeliveryTime() {
        return approximateDeliveryTime;
    }

    public void setApproximateDeliveryTime(String approximateDeliveryTime) {
        this.approximateDeliveryTime = approximateDeliveryTime;
    }

    public String getDateOfRating() {
        return dateOfRating;
    }

    public void setDateOfRating(String dateOfRating) {
        this.dateOfRating = dateOfRating;
    }

    public String getRatingDescription() {
        return ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this.ratingDescription = ratingDescription;
    }

    public float getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(float ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public int getJobcardId() {
        return jobcardId;
    }

    public void setJobcardId(int jobcardId) {
        this.jobcardId = jobcardId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInsuranceVendorName() {
        return insuranceVendorName;
    }

    public void setInsuranceVendorName(String insuranceVendorName) {
        this.insuranceVendorName = insuranceVendorName;
    }

    public String getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public void setInsuranceExpiryDate(String insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPickupStatus() {
        return pickupStatus;
    }

    public void setPickupStatus(boolean pickupStatus) {
        this.pickupStatus = pickupStatus;
    }


    public int getPickupPerson() {
        return pickupPerson;
    }

    public void setPickupPerson(int pickupPerson) {
        this.pickupPerson = pickupPerson;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(String checkInCount) {
        this.checkInCount = checkInCount;
    }

    public String getInspectionCount() {
        return inspectionCount;
    }

    public void setInspectionCount(String inspectionCount) {
        this.inspectionCount = inspectionCount;
    }

    public String getInProgressCount() {
        return inProgressCount;
    }

    public void setInProgressCount(String inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    public String getReadyCount() {
        return readyCount;
    }

    public void setReadyCount(String readyCount) {
        this.readyCount = readyCount;
    }

    public String getDeliveredCount() {
        return deliveredCount;
    }

    public void setDeliveredCount(String deliveredCount) {
        this.deliveredCount = deliveredCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isCheckInCompleted() {
        return checkInCompleted;
    }

    public void setCheckInCompleted(boolean checkInCompleted) {
        this.checkInCompleted = checkInCompleted;
    }

    public boolean isInspectionCompleted() {
        return inspectionCompleted;
    }

    public void setInspectionCompleted(boolean inspectionCompleted) {
        this.inspectionCompleted = inspectionCompleted;
    }

    public boolean isUnderProgress() {
        return isUnderProgress;
    }

    public void setUnderProgress(boolean underProgress) {
        isUnderProgress = underProgress;
    }

    public boolean isVehicleReady() {
        return isVehicleReady;
    }

    public void setVehicleReady(boolean vehicleReady) {
        isVehicleReady = vehicleReady;
    }

    public boolean isVehicleCompleted() {
        return isVehicleCompleted;
    }

    public void setVehicleCompleted(boolean vehicleCompleted) {
        isVehicleCompleted = vehicleCompleted;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getEstimateAmount() {
        return estimateAmount;
    }

    public void setEstimateAmount(double estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public void setEstimateAmount(int estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public String getJobcardDescription() {
        return jobcardDescription;
    }

    public void setJobcardDescription(String jobcardDescription) {
        this.jobcardDescription = jobcardDescription;
    }


    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
    public int getAdviserId() {
        return adviserId;
    }

    public void setAdviserId(int adviserId) {
        this.adviserId = adviserId;
    }

    public String getAdviserName() {
        return adviserName;
    }

    public void setAdviserName(String adviserName) {
        this.adviserName = adviserName;
    }
}
