package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by creatah1 on 16-05-2017.
 */

public class AddAction implements Serializable {

    private int id;

    private String action;

    public AddAction(int id, String action) {
        this.id = id;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("description_id", id);
            obj.put("action", action);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
