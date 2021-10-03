package com.uasppb.resto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review_ {
    @SerializedName("review")
    @Expose
    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Review_{" +
                "review=" + review +
                '}';
    }
}
