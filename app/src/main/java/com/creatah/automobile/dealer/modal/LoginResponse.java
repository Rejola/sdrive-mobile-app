package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class LoginResponse extends BaseResponse {

    @SerializedName("workshop_details")
    private WorkshopInfo workshopInfo;

    public WorkshopInfo getWorkshopInfo() {
        return workshopInfo;
    }

    public void setWorkshopInfo(WorkshopInfo workshopInfo) {
        this.workshopInfo = workshopInfo;
    }
}

