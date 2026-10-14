package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class CheckOut implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("dealer_id")
    private int dealerId;

    @SerializedName("name")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updateDate;

    @SerializedName("count")
    private int count;

    @SerializedName("is_checked")
    private boolean checked;

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("is_verified")
    private boolean isVerified;

    public CheckOut(String title, boolean checked) {
        this.checked = checked;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
