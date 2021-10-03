package com.uasppb.resto.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uasppb.resto.R;
import com.uasppb.resto.model.RestoItem;
import com.uasppb.resto.model.RestoItem_;
import com.uasppb.resto.model.Review;
import com.uasppb.resto.model.Review_;
import com.uasppb.resto.model.User_;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    Context context;
    ArrayList<Review_> reviews;

    public ReviewAdapter(Context context, ArrayList<Review_> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position).getReview();
        String thumb = review.getUser().getProfileImage();

        try {
            if (!TextUtils.isEmpty(thumb))
                Picasso.get().load(thumb)
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.noimage)
                        .into(holder.userImage);
            else {
                holder.userImage.setImageDrawable(context.getDrawable(R.drawable.noimage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.userName.setText(review.getUser().getName());
        holder.userRating.setText(review.getRating());
        holder.userReview.setText(review.getReviewText());
        float ratingfloat = Float.parseFloat(holder.userRating.getText().toString());
        holder.ratingBar.setRating(ratingfloat);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        ImageView userImage;
        RatingBar ratingBar;
        TextView userName, userRating, userReview, userReviewTime;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.review_image);
            userName = itemView.findViewById(R.id.user_name);
            ratingBar = itemView.findViewById(R.id.ratingbar);
            userRating = itemView.findViewById(R.id.user_rating);
            userReview = itemView.findViewById(R.id.review_desc);

        }
    }
}
