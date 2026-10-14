package com.creatah.automobile.dealer.modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by creatah1 on 17-05-2017.
 */

public class VerifiedItem implements Serializable {

    private int itemId;

    private boolean verified;

    public VerifiedItem(int itemId, boolean verified) {
        this.itemId = itemId;
        this.verified = verified;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("item_id", itemId);
            obj.put("status", verified);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
