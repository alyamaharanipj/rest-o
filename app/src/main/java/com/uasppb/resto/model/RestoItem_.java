package com.uasppb.resto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestoItem_ {
    @SerializedName("restaurant")
    @Expose
    private RestoItem restaurant;

    public RestoItem getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestoItem restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "RestoItem_{" +
                "restaurant=" + restaurant +
                '}';
    }
}
