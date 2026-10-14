package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by creatah1 on 16-05-2017.
 */

public class AddEstimateAmount implements Serializable {

    private int id;

    private int qty;

    private double amount;

    public AddEstimateAmount(int id, int qty, double amount) {
        this.id = id;
        this.amount = amount;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("qty", qty);
            obj.put("rate", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
