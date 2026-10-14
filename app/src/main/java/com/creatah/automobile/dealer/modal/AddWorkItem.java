package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by creatah1 on 16-05-2017.
 */

public class AddWorkItem implements Serializable {

    private String workItem;

    private boolean isIssued;

    public AddWorkItem(String description, boolean issued) {
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public AddWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getWorkItem() {
        return workItem;
    }

    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }



    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("description", workItem);
            obj.put("is_issued", isIssued? "1" : "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
