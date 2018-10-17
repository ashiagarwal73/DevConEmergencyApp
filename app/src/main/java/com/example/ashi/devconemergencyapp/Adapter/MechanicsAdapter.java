package com.example.ashi.devconemergencyapp.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashi.devconemergencyapp.Model.Complaint;
import com.example.ashi.devconemergencyapp.Model.Location;
import com.example.ashi.devconemergencyapp.R;

import java.util.List;

public class MechanicsAdapter extends RecyclerView.Adapter {
    List<Complaint> datamodels;
    public MechanicsAdapter(List<Complaint> datamodels)
    {
        this.datamodels=datamodels;
    }
    class MechanicsHolder extends RecyclerView.ViewHolder{
        TextView name,review;
        RatingBar rating;
        ImageView imageView;

        public MechanicsHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            review=itemView.findViewById(R.id.review);
            rating=itemView.findViewById(R.id.rating);
            imageView=itemView.findViewById(R.id.image);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_layout, viewGroup, false);
                return new MechanicsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

                final MechanicsHolder mechanicsHolder=(MechanicsHolder) viewHolder;
                Glide.with(mechanicsHolder.itemView.getContext())
                .load(datamodels.get(position).getImage())
                .into(mechanicsHolder.imageView);
        mechanicsHolder.name.setText(datamodels.get(position).getName());
        mechanicsHolder.review.setText(datamodels.get(position).getReview());
        mechanicsHolder.rating.setRating(datamodels.get(position).getRating());
        mechanicsHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location=new Location();
                location.setLat(Double.parseDouble(datamodels.get(position).getLati()));
                location.setLng(Double.parseDouble(datamodels.get(position).getLongi()));
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+location.getLat()+","+location.getLng());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(mechanicsHolder.itemView.getContext().getPackageManager()) != null) {
                    mechanicsHolder.itemView.getContext().startActivity(mapIntent);
                }
            }
        });
        }
    @Override
    public int getItemCount() {
        return datamodels.size();
    }

}
