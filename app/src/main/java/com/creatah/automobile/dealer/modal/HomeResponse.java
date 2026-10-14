package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karthikeyan on 17-02-2017.
 */

public class HomeResponse extends BaseResponse {

    public String getInProgressService() {
        return inProgressService;
    }

    public void setInProgressService(String inProgressService) {
        this.inProgressService = inProgressService;
    }

    public String getOpenCount() {
        return openCount;
    }

    public void setOpenCount(String openCount) {
        this.openCount = openCount;
    }

    public String getVehicleReady() {
        return vehicleReady;
    }

    public void setVehicleReady(String vehicleReady) {
        this.vehicleReady = vehicleReady;
    }

    public int getReminderCount() {
        return reminderCount;
    }

    public void setReminderCount(int reminderCount) {
        this.reminderCount = reminderCount;
    }

    @SerializedName("lead_count")
    private int leadsCount;

    @SerializedName("completed_count")
    private String completedService;

    @SerializedName("in_progress_count")
    private String inProgressService;

//    @SerializedName("rating")
//    private int rating;

    @SerializedName("average_count")
    private int averageCount;

    @SerializedName("open_count")
    private String openCount;

    @SerializedName("check_in_estimate")
    private String checkInEstimate;

    @SerializedName("vehicle_ready")
    private String vehicleReady;

    @SerializedName("reminder_count")
    private  int reminderCount;

    public int getLeadsCount() {
        return leadsCount;
    }

    public void setLeadsCount(int leadsCount) {
        this.leadsCount = leadsCount;
    }


    public int getAverageCount() {
        return averageCount;
    }

    public void setAverageCount(int averageCount) {
        this.averageCount = averageCount;
    }

    public String getCheckInEstimate() {
        return checkInEstimate;
    }

    public void setCheckInEstimate(String checkInEstimate) {
        this.checkInEstimate = checkInEstimate;
    }

    public String getCompletedService() {
        return completedService;
    }

    public void setCompletedService(String completedService) {
        this.completedService = completedService;
    }
}
