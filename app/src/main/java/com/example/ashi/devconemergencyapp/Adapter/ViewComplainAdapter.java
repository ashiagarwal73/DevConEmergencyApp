package com.example.ashi.devconemergencyapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashi.devconemergencyapp.Model.Complaint;
import com.example.ashi.devconemergencyapp.R;

import java.util.List;
public class ViewComplainAdapter extends RecyclerView.Adapter {
    List<Complaint> dataModels;
    public ViewComplainAdapter(List<Complaint> dataModels)
    {
        this.dataModels=dataModels;

    }
    class ComplainViewHolder extends RecyclerView.ViewHolder{
        TextView status,category,description;
        ImageView image;
        public ComplainViewHolder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.status);
            category=itemView.findViewById(R.id.category);
            description=itemView.findViewById(R.id.description);
            image=itemView.findViewById(R.id.image);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_complain_item, viewGroup, false);
                return new ComplainViewHolder(view) ;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
                ComplainViewHolder complainViewHolder=(ComplainViewHolder) viewHolder;
                Glide.with(complainViewHolder.itemView.getContext())
                        .load(dataModels.get(position).getImage())
                        .into(complainViewHolder.image);
                complainViewHolder.status.setText(dataModels.get(position).getStatus());
                complainViewHolder.description.setText(dataModels.get(position).getDescription());
                complainViewHolder.category.setText(dataModels.get(position).getCategory());
        }


    @Override
    public int getItemCount() {
        return dataModels.size();
    }
}
