package com.creatah.automobile.dealer.modal;

import java.io.Serializable;

/**
 * Created by karthikeyan on 20-03-2017.
 */

public class Complaints implements Serializable {

    private String id;
    private String description;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
