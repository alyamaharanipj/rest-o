package com.uasppb.resto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.uasppb.resto.model.RestoResponse;
import com.uasppb.resto.networking.RestoRepository;

public class RestoViewModel extends ViewModel {
    private MutableLiveData<RestoResponse> mutableLiveData;
    private RestoRepository restoRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        restoRepository = RestoRepository.getInstance();
        mutableLiveData = restoRepository.getRestaurant();

    }

    public LiveData<RestoResponse> getRestoRepository() {
        return mutableLiveData;
    }

}
