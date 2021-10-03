package com.uasppb.resto.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("reviews_count")
    @Expose
    private Integer reviewsCount;
    @SerializedName("reviews_start")
    @Expose
    private Integer reviewsStart;
    @SerializedName("reviews_shown")
    @Expose
    private Integer reviewsShown;
    @SerializedName("user_reviews")
    @Expose
    private List<Review_> userReviews = null;

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewwCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public Integer getReviewwStart() {
        return reviewsStart;
    }

    public void setReviewwStart(Integer reviewStart) {
        this.reviewsStart = reviewStart;
    }

    public Integer getReviewsShown() {
        return reviewsShown;
    }

    public void setReviewsShown(Integer reviewsShown) {
        this.reviewsShown = reviewsShown;
    }

    public List<Review_> getUserReviews() {
        return userReviews;
    }

    public void setUserReview(List<Review_> userReviews) {
        this.userReviews = userReviews;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "reviewsCount=" + reviewsCount +
                ", reviewsStart=" + reviewsStart +
                ", reviewsShown=" + reviewsShown +
                ", userReviews=" + userReviews +
                '}';
    }
}
