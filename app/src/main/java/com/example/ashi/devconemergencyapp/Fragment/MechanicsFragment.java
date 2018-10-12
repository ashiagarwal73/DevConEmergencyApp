package com.example.ashi.devconemergencyapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashi.devconemergencyapp.Adapter.MechanicsAdapter;
import com.example.ashi.devconemergencyapp.Adapter.RecyclerViewAdapter;
import com.example.ashi.devconemergencyapp.R;
public class MechanicsFragment extends Fragment {
    RecyclerView mechanicsRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mechanics, container, false);
        mechanicsRecyclerView=view.findViewById(R.id.mechanics_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mechanicsRecyclerView.setLayoutManager(linearLayoutManager);
        MechanicsAdapter mechanicsAdapter=new MechanicsAdapter();
        mechanicsRecyclerView.setAdapter(mechanicsAdapter);
        return view;
    }
}
