package com.uasppb.resto.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.uasppb.resto.R;
import com.uasppb.resto.RestoDetailActivity;
import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;

import java.util.ArrayList;

public class RestoAdapter extends RecyclerView.Adapter<RestoAdapter.RestoViewHolder>{
    Context context;
    ArrayList<RestoItem_> restoItems;

    public RestoAdapter(Context context, ArrayList<RestoItem_> restoItems) {
        this.context = context;
        this.restoItems = restoItems;
    }

    @NonNull
    @Override
    public RestoAdapter.RestoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_resto_item, parent, false);
        return new  RestoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoAdapter.RestoViewHolder holder, int position) {
        RestoItem restoItem = restoItems.get(position).getRestaurant();
        String thumb = restoItem.getFeaturedImage();

        try {
            if (!TextUtils.isEmpty(thumb))
                Picasso.get().load(thumb)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.noimage)
                        .into(holder.restoImage);
            else {
                holder.restoImage.setImageDrawable(context.getDrawable(R.drawable.noimage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.restoName.setText(restoItem.getName());
        holder.restoPriceRange.setText(restoItem.getPriceRange().toString());
        holder.restoCurrency.setText(restoItem.getCurrency());
        holder.restoRating.setText(restoItem.getUserRating().getAggregateRating().toString());
        float ratingfloat = Float.parseFloat(holder.restoRating.getText().toString());
        holder.ratingBar.setRating(ratingfloat);
        Integer onlineChecker = restoItems.get(position).getRestaurant().getHasOnlineDelivery();
        if(onlineChecker.equals(1)) {
              holder.onlineBadge.setImageDrawable(context.getDrawable(R.drawable.badge));
        }

        holder.parentLayout.setOnClickListener((view)-> {
            Intent intent = new Intent(context, RestoDetailActivity.class);
            intent.putExtra("restoId", restoItem.getR().getRestoId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return restoItems.size();
    }

    public class RestoViewHolder extends RecyclerView.ViewHolder{

        ImageView restoImage, onlineBadge;
        TextView restoName, restoPriceRange, restoCurrency, restoRating;
        RatingBar ratingBar;
        RelativeLayout parentLayout;

        public RestoViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            restoImage = itemView.findViewById(R.id.resto_image);
            restoName = itemView.findViewById(R.id.resto_name);
            restoPriceRange = itemView.findViewById(R.id.resto_range_price);
            restoCurrency = itemView.findViewById(R.id.resto_currency);
            restoRating = itemView.findViewById(R.id.resto_rating);
            onlineBadge = itemView.findViewById(R.id.badge);
            parentLayout = itemView.findViewById(R.id.resto_item);
        }
    }
}
