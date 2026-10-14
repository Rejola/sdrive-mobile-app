package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by creatah1 on 05-05-2017.
 */

public class DescriptionListResponse extends BaseResponse {

    @SerializedName("labour_list1")
    private List<Description> labourList;

    @SerializedName("spare_list1")
    private List<Description> spareList;

    @SerializedName("labour_subcost")
    private double labourCost;

    @SerializedName("spares_subcost")
    private double spareCost;

    @SerializedName("estimate_cost")
    private double estimateCost;

    @SerializedName("total_cost")
    private double finalCost;

    @SerializedName("labour_cGST_cost")
    private double labourCGSTCost;

    @SerializedName("labour_sGST_cost")
    private double labourSGSTCost;

    @SerializedName("spare_cGST_cost")
    private double spareCGSTCost;

    @SerializedName("spare_sGST_cost")
    private double spareSGSTCost;

    @SerializedName("description")
    private String description;

    @SerializedName("spare_list")
    private List<SpareLabour> spares;

    @SerializedName("labour_list")
    private List<SpareLabour> labours;

    public double getLabourCGSTCost() {
        return labourCGSTCost;
    }

    public void setLabourCGSTCost(double labourCGSTCost) {
        this.labourCGSTCost = labourCGSTCost;
    }

    public double getLabourSGSTCost() {
        return labourSGSTCost;
    }

    public void setLabourSGSTCost(double labourSGSTCost) {
        this.labourSGSTCost = labourSGSTCost;
    }

    public double getSpareCGSTCost() {
        return spareCGSTCost;
    }

    public void setSpareCGSTCost(double spareCGSTCost) {
        this.spareCGSTCost = spareCGSTCost;
    }

    public double getSpareSGSTCost() {
        return spareSGSTCost;
    }

    public void setSpareSGSTCost(double spareSGSTCost) {
        this.spareSGSTCost = spareSGSTCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Description> getLabourList() {
        return labourList;
    }

    public void setLabourList(List<Description> labourList) {
        this.labourList = labourList;
    }

    public List<Description> getSpareList() {
        return spareList;
    }

    public void setSpareList(List<Description> spareList) {
        this.spareList = spareList;
    }

    public double getLabourCost() {
        return labourCost;
    }

    public void setLabourCost(double labourCost) {
        this.labourCost = labourCost;
    }

    public double getSpareCost() {
        return spareCost;
    }

    public void setSpareCost(double spareCost) {
        this.spareCost = spareCost;
    }

    public double getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(double estimateCost) {
        this.estimateCost = estimateCost;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public List<SpareLabour> getSpares() {
        return spares;
    }

    public void setSpares(List<SpareLabour> spares) {
        this.spares = spares;
    }

    public List<SpareLabour> getLabours() {
        return labours;
    }

    public void setLabours(List<SpareLabour> labours) {
        this.labours = labours;
    }
}
