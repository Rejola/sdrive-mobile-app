package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by santhana on 27/04/18.
 */

public class WorkItem implements Serializable {

    private int id;

    private boolean isCompleted;

    private boolean isIssued;


    public WorkItem(int id, boolean isCompleted,boolean isIssued) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.isIssued=isIssued;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", id);
            obj.put("is_completed", isCompleted? "1":"0");
            obj.put("is_issued", isIssued? "1":"0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
