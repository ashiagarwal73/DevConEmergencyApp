package com.example.ashi.devconemergencyapp.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashi.devconemergencyapp.R;
import com.example.ashi.devconemergencyapp.Model.Location;
import com.example.ashi.devconemergencyapp.Model.Results;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    List<Results> dataModels;
    static final int HEADER_VIEW_TYPE=1;
    static final int SUBHEADER_VIEW_TYPE=2;
    public RecyclerViewAdapter(List<Results> dataModels)
    {
        this.dataModels=dataModels;

    }
    class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.header);
        }
    }
    class SubHeaderViewHolder extends RecyclerView.ViewHolder{
        TextView name,address;
        public SubHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType){
            case HEADER_VIEW_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_view_type, viewGroup, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_header_view_type, viewGroup, false);
                return new SubHeaderViewHolder(view) ;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        switch (dataModels.get(position).getViewType()) {
            case Results.HEADER_TYPE:
                HeaderViewHolder headerViewHolder=(HeaderViewHolder) viewHolder;
                headerViewHolder.textView.setText(dataModels.get(position).getHeaderText());
                break;
            default:
                final SubHeaderViewHolder subHeaderViewHolder=(SubHeaderViewHolder) viewHolder;
                subHeaderViewHolder.name.setText(dataModels.get(position).getName());
                subHeaderViewHolder.address.setText(dataModels.get(position).getVicinity());
                subHeaderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Location location=dataModels.get(position).getGeometry().getLocation();
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+location.getLat()+","+location.getLng());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(subHeaderViewHolder.itemView.getContext().getPackageManager()) != null) {
                            subHeaderViewHolder.itemView.getContext().startActivity(mapIntent);
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(dataModels.get(position).getViewType().equals(Results.HEADER_TYPE)){
            return HEADER_VIEW_TYPE;
        }
        else {
            return SUBHEADER_VIEW_TYPE;
        }
    }
}
