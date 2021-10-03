package com.uasppb.resto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.uasppb.resto.adapters.RestoAdapter;
import com.uasppb.resto.model.RestoItem_;
import com.uasppb.resto.viewmodels.RestoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<RestoItem_> arrayRestoItems = new ArrayList<>();
    RestoAdapter restoAdapter;
    RecyclerView rvResto;
    RestoViewModel restoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvResto = findViewById(R.id.recycler_view);

        restoViewModel = ViewModelProviders.of(this).get(RestoViewModel.class);
        restoViewModel.init();
        restoViewModel.getRestoRepository().observe(this, restoResponse -> {
            List<RestoItem_> restoItems = restoResponse.getRestaurants();
            arrayRestoItems.addAll(restoItems);
            restoAdapter.notifyDataSetChanged();
        });

        setupRecyclerView();

    }

    private void setupRecyclerView() {
        if (restoAdapter == null) {
            restoAdapter = new RestoAdapter(MainActivity.this, arrayRestoItems);
            rvResto.setLayoutManager(new LinearLayoutManager(this));
            rvResto.setAdapter(restoAdapter);
            rvResto.setItemAnimator(new DefaultItemAnimator());
            rvResto.setNestedScrollingEnabled(true);
        } else {
            restoAdapter.notifyDataSetChanged();
        }
    }

}