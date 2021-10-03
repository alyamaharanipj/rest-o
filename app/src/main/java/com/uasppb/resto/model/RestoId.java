package com.uasppb.resto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestoId {
    @SerializedName("res_id")
    @Expose
    private Integer restoId;

    public Integer getRestoId() {
        return restoId;
    }

    public void setRestoId(Integer restoId) {
        this.restoId = restoId;
    }

    @Override
    public String toString() {
        return "RestoId{" +
                "restoId=" + restoId +
                '}';
    }
}
