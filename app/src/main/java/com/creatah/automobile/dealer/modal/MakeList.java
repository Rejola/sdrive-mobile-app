package com.creatah.automobile.dealer.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karthikeyan on 31-07-2016.
 */
public class MakeList implements Serializable {

    @SerializedName("description")
    private String MakeName;

    @SerializedName("image")
    private String makeIcon;

    @SerializedName("id")
    private int MakeId;

    @SerializedName("type")
    private String type;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("model_list")
    private List<Modallist> brandlist;

    public String getMakeName() {
        return MakeName;
    }

    public void setMakeName(String makeName) {
        MakeName = makeName;
    }

    public int getMakeId() {
        return MakeId;
    }

    public void setMakeId(int makeId) {
        MakeId = makeId;
    }

    public List<Modallist> getBrandlist() {
        return brandlist;
    }

    public void setBrandlist(List<Modallist> brandlist) {
        this.brandlist = brandlist;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getMakeIcon() {
        return makeIcon;
    }

    public void setMakeIcon(String makeIcon) {
        this.makeIcon = makeIcon;
    }
}
