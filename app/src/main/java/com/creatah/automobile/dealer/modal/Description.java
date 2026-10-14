package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by creatah1 on 05-05-2017.
 */

public class Description implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("item_tax_percentage")
    private double itemTaxPercentage;

    @SerializedName("item_tax_amount")
    private double itemTaxAmount;

    @SerializedName("item_amount")
    private double amount;

    @SerializedName("qty")
    private int qty;

    @SerializedName("rate")
    private double rate;

    @SerializedName("action")
    private String action;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("is_completed")
    private boolean isCompleted;

    @SerializedName("is_issued")
    private boolean isIssued;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemTaxPercentage() {
        return itemTaxPercentage;
    }

    public void setItemTaxPercentage(double itemTaxPercentage) {
        this.itemTaxPercentage = itemTaxPercentage;
    }

    public double getItemTaxAmount() {
        return itemTaxAmount;
    }

    public void setItemTaxAmount(double itemTaxAmount) {
        this.itemTaxAmount = itemTaxAmount;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
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
}
