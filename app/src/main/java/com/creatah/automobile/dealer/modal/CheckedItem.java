package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by creatah1 on 05-05-2017.
 */

public class CheckedItem implements Serializable {

    public CheckedItem(int id, int count) {
        this.id = id;
        this.count = count;
    }

    private int id;

    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("check_list_id", id);
            obj.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
