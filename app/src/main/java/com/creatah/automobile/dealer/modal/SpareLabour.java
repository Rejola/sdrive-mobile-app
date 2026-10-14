package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class SpareLabour implements Serializable {

    @SerializedName("item_id")
    private int itemId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("item_amount")
    private double itemAmount;

    @SerializedName("item_sGST_percentage")
    private double itemSGSTPercentage;

    @SerializedName("item_cGST_percentage")
    private double itemCGSTPercentage;

    private double itemSGSTAmount;

    private double itemCGSTAmount;

    @SerializedName("item_sGST_amount")
    private double perSGSTAmount;

    @SerializedName("item_cGST_amount")
    private double perCGSTAmount;

    @SerializedName("item_list")
    private List<SpareLabour> itemList;

    @SerializedName("item_quantity")
    private double qty = 0;

    @SerializedName("item_quantityu")
    private double quantitytext = 0;

    @SerializedName("rate")
    private double qtyTotalAmount;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @SerializedName("is_active")
    private boolean isActive;

    private int position;

    private boolean canRemove = true;

    private boolean isAdded = true;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public double getItemSGSTPercentage() {
        return itemSGSTPercentage;
    }

    public void setItemSGSTPercentage(double itemSGSTPercentage) {
        this.itemSGSTPercentage = itemSGSTPercentage;
    }

    public double getItemCGSTPercentage() {
        return itemCGSTPercentage;
    }

    public void setItemCGSTPercentage(double itemCGSTPercentage) {
        this.itemCGSTPercentage = itemCGSTPercentage;
    }

    public double getItemSGSTAmount() {
        return itemSGSTAmount;
    }

    public void setItemSGSTAmount(double itemSGSTAmount) {
        this.itemSGSTAmount = itemSGSTAmount;
    }

    public double getItemCGSTAmount() {
        return itemCGSTAmount;
    }

    public void setItemCGSTAmount(double itemCGSTAmount) {
        this.itemCGSTAmount = itemCGSTAmount;
    }

    public double getPerSGSTAmount() {
        return perSGSTAmount;
    }

    public void setPerSGSTAmount(double perSGSTAmount) {
        this.perSGSTAmount = perSGSTAmount;
    }

    public double getPerCGSTAmount() {
        return perCGSTAmount;
    }

    public void setPerCGSTAmount(double perCGSTAmount) {
        this.perCGSTAmount = perCGSTAmount;
    }

    public List<SpareLabour> getItemList() {
        return itemList;
    }

    public void setItemList(List<SpareLabour> itemList) {
        this.itemList = itemList;
    }

    public double getquantitytext() {
        return quantitytext;
    }

    public void setquantitytext(double quantitytext) {
        this.quantitytext = quantitytext;
    }
    public double getQty() {
        return qty;
    }

    public String getQtyVal() {
        return String.valueOf(qty);
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getQtyTotalAmount() {
        return qtyTotalAmount;
    }

    public void setQtyTotalAmount(double qtyTotalAmount) {
        this.qtyTotalAmount = qtyTotalAmount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public JSONObject getAddSpareJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("spare_labour_id", itemId);
            obj.put("quantity", qty == 0 ? 1 : qty);
            obj.put("quantitytexts", quantitytext == 0 ? 1 : quantitytext);
            obj.put("price_per_qty", itemAmount == 0 ? 0.0 : itemAmount);
            obj.put("cGST", itemCGSTPercentage == 0 ? 0.0 : itemCGSTPercentage);
            obj.put("sGST", itemSGSTPercentage == 0 ? 0.0 : itemSGSTPercentage);
            obj.put("cGST_amount", perCGSTAmount == 0 ? 0.0 : perCGSTAmount);
            obj.put("sGST_amount", perCGSTAmount == 0 ? 0.0 : perSGSTAmount);
            obj.put("is_active", isActive);
            double subTotal = qty * itemAmount;
            obj.put("sub_total", subTotal == 0 ? 0.0 : subTotal);
            obj.put("total", qtyTotalAmount == 0 ? 0.0 : qtyTotalAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


    public JSONObject getStatusJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", itemId);
            obj.put("is_completed", isCompleted ? "1" : "0");
            obj.put("is_issued", isIssued ? "1" : "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
