package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karthikeyan on 06-02-2017.
 */

public class AutoMobileService implements Serializable {

    @SerializedName("service_id")
    private int serviceId;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("service_image")
    private String serviceImage;

    @SerializedName("service_type_id")
    private String serviceTypeId;

    @SerializedName("service_type")
    private String serviceType;

    @SerializedName("description")
    private String description;

    @SerializedName("service_cost")
    private String serviceCost;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }
}
