package com.uasppb.resto.networking;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.uasppb.resto.R;
import com.uasppb.resto.model.RestoResponse;
import com.uasppb.resto.model.ReviewResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoRepository {
    private static RestoRepository restoRepository;

    public static RestoRepository getInstance(){
        if (restoRepository == null){
            restoRepository = new RestoRepository();
        }
        return restoRepository;
    }

    private RestoApi restoApi;

    public RestoRepository(){
        restoApi = RetrofitService.createService(RestoApi.class);
    }

    public MutableLiveData<RestoResponse> getRestaurant(){
        final MutableLiveData<RestoResponse> restoData = new MutableLiveData<>();
        restoApi.getRestaurants().enqueue(new Callback<RestoResponse>() {
            @Override
            public void onResponse(Call<RestoResponse> call,
                                   Response<RestoResponse> response) {
                if (response.isSuccessful()){
                    restoData.setValue(response.body());
                    Log.d("success fetch data","annyeong");
                    Log.d("myTag", String.valueOf(response.body()));
                    Log.d("myTag", String.valueOf(restoData));
                }
            }

            @Override
            public void onFailure(Call<RestoResponse> call, Throwable t) {
                restoData.setValue(null);
                Log.d("error fetch data",t.getMessage());
            }
        }
    );
        return restoData;
    }

    public MutableLiveData<ReviewResponse> getReviews(int res_id){
        final MutableLiveData<ReviewResponse> reviewData = new MutableLiveData<>();
        restoApi.getReview(res_id).enqueue(new Callback<ReviewResponse>() {
                                              @Override
                                              public void onResponse(Call<ReviewResponse> call,
                                                                     Response<ReviewResponse> response) {
                                                  if (response.isSuccessful()){
                                                      reviewData.setValue(response.body());
                                                      Log.d("success fetch data","annyeong");
                                                      Log.d("myTag", String.valueOf(response.body()));
                                                      Log.d("myTag", String.valueOf(reviewData));
                                                  }
                                              }

                                              @Override
                                              public void onFailure(Call<ReviewResponse> call, Throwable t) {
                                                  reviewData.setValue(null);
                                                  Log.d("error fetch data",t.getMessage());
                                              }
                                          }
        );
        return reviewData;
    }

}
