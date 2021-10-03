package com.uasppb.resto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
//import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.uasppb.resto.adapters.RestoAdapter;
import com.uasppb.resto.adapters.ReviewAdapter;
import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;
import com.uasppb.resto.model.Review;
import com.uasppb.resto.model.Review_;
import com.uasppb.resto.networking.RestoApi;
import com.uasppb.resto.networking.RetrofitService;
import com.uasppb.resto.viewmodels.RestoViewModel;
import com.uasppb.resto.viewmodels.ReviewViewModel;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.compass.CompassOverlay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestoDetailActivity extends AppCompatActivity {
    private final static String TAG = RestoDetailActivity.class.getSimpleName();

    private Context ctx;
    private static Retrofit retrofit = null;
    private Toolbar toolbar;
    private MapView map = null;
    private TextView restoName, restoRangePrice, restoLoc, restoCurrency, restoRating, restoLong, restoLat;
    private ImageView restoImage, restoOnlineOrder;
    private RatingBar ratingBar;
    private RecyclerView rvReviews;
    private ReviewAdapter reviewAdapter;
    private ReviewViewModel reviewViewModel;
    private CoordinatorLayout coordinatorLayout;
    float rating;
    double Long, Lat;
    Boolean isScrolling = false;
    ImageButton btn;
    ArrayList<Review_> arrayReviews = new ArrayList<>();
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto_detail);

        ctx = this.getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.getController().setZoom(18.0);
        restoImage = (ImageView) findViewById(R.id.resto_image);
        restoOnlineOrder = (ImageView) findViewById(R.id.resto_flag);
        restoName = (TextView) findViewById(R.id.resto_name);
        restoRangePrice = (TextView) findViewById(R.id.resto_range_price);
        restoCurrency = (TextView) findViewById(R.id.resto_currency);
        restoLoc = (TextView) findViewById(R.id.address);
        restoLong = (TextView) findViewById(R.id.resto_long);
        restoLat = (TextView) findViewById(R.id.resto_lat);
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        restoRating = (TextView) findViewById(R.id.resto_rating);

        rvReviews = (RecyclerView) findViewById(R.id.review_rv);

        Intent iGet = getIntent();
        int restoId = iGet.getIntExtra("restoId", 0);
        RetrofitService.createService(RestoApi.class).getRestaurant(restoId).enqueue(new Callback<RestoItem>() {
            @Override
            public void onResponse(Call<RestoItem> call, Response<RestoItem> response) {
                final RestoItem restoItem_ = response.body();
                Log.d("detail", response.body().toString());
                renderResto(restoItem_);
            }

            @Override
            public void onFailure(Call<RestoItem> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), "Error loading!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back (View v) {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void renderResto(RestoItem resto) {
        String thumb = resto.getFeaturedImage();

        try {
            if (!TextUtils.isEmpty(thumb))
                Picasso.get().load(thumb)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.noimage)
                        .into(restoImage);
            else {
                restoImage.setImageDrawable(this.getDrawable(R.drawable.noimage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String test = resto.getPriceRange().toString();
        Log.e("hello", test);
        restoName.setText(resto.getName());
        restoRangePrice.setText(resto.getPriceRange().toString());
        restoCurrency.setText(resto.getCurrency());
        restoLoc.setText(resto.getLocation().getAddress());
        restoLong.setText(resto.getLocation().getLongitude());
        restoLat.setText(resto.getLocation().getLatitude());
        restoRating.setText(resto.getUserRating().getAggregateRating().toString());
        rating = Float.parseFloat(restoRating.getText().toString());
        ratingBar.setRating(rating);
        Integer onlineChecker = resto.getHasOnlineDelivery();
        if(onlineChecker.equals(1)) {
            restoOnlineOrder.setImageDrawable(this.getDrawable(R.drawable.badge));
        }
        Long = Double.parseDouble(restoLong.getText().toString());
        Lat = Double.parseDouble(restoLat.getText().toString());

        //Show maps
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET
        });
        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        CompassOverlay compassOverlay = new CompassOverlay(this, map);
        compassOverlay.enableCompass();
        map.getOverlays().add(compassOverlay);

        GeoPoint point = new GeoPoint(Lat, Long);
        addMarker(point);

        map.getController().setCenter(point);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        reviewViewModel.init(resto.getId());
        reviewViewModel.getReviewRepository().observe(this, reviewResponse -> {
            List<Review_> reviews = reviewResponse.getUserReviews();
            arrayReviews.addAll(reviews);
            Log.e("test", arrayReviews.toString());
            reviewAdapter.notifyDataSetChanged();
        });

        setupRecyclerView();
    }

    public void addMarker(GeoPoint point){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_marker));
        map.getOverlays().add(startMarker);
    }

    private void setupRecyclerView() {

        if (reviewAdapter == null) {
            reviewAdapter = new ReviewAdapter(this, arrayReviews);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setAutoMeasureEnabled(true);
            rvReviews.setLayoutManager(layoutManager);
            rvReviews.setAdapter(reviewAdapter);
        } else {
            reviewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resto_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_share:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_PACKAGE_NAME, getPackageName());
                share.setType("text/plain");
                startActivity(share);
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
