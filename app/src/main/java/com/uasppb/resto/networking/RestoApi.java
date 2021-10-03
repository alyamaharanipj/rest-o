package com.uasppb.resto.networking;

import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;
import com.uasppb.resto.model.RestoResponse;
import com.uasppb.resto.model.Review;
import com.uasppb.resto.model.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestoApi {
    @Headers("user-key: 53d890d323b2c6c8fd35f972c216c7c9")
    @GET("search")
    Call<RestoResponse> getRestaurants();
    @Headers("user-key: 53d890d323b2c6c8fd35f972c216c7c9")
    @GET("restaurant")
    Call<RestoItem> getRestaurant(@Query("res_id") int restoId);
    @Headers("user-key: 53d890d323b2c6c8fd35f972c216c7c9")
    @GET("reviews")
    Call<ReviewResponse> getReview(@Query("res_id") int restoId);
}
