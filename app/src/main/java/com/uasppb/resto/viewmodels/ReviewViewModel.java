package com.uasppb.resto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uasppb.resto.model.RestoResponse;
import com.uasppb.resto.model.ReviewResponse;
import com.uasppb.resto.networking.RestoRepository;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<ReviewResponse> mutableLiveData;
    private RestoRepository restoRepository;

    public void init(int res_id){
        if (mutableLiveData != null){
            return;
        }
        restoRepository = RestoRepository.getInstance();
        mutableLiveData = restoRepository.getReviews(res_id);

    }

    public LiveData<ReviewResponse> getReviewRepository() {
        return mutableLiveData;
    }
}
