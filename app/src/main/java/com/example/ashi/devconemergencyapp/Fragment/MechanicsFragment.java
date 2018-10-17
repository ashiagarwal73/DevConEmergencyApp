package com.example.ashi.devconemergencyapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ashi.devconemergencyapp.Adapter.MechanicsAdapter;
import com.example.ashi.devconemergencyapp.Adapter.RecyclerViewAdapter;
import com.example.ashi.devconemergencyapp.Model.Complaint;
import com.example.ashi.devconemergencyapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MechanicsFragment extends Fragment {
    RecyclerView mechanicsRecyclerView;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference().child("Mechanics");
    private List<Complaint> complaints;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mechanics, container, false);
        floatingActionButton=view.findViewById(R.id.add_mechanic);
        progressBar=view.findViewById(R.id.mechanics_progressbar);
        mechanicsRecyclerView=view.findViewById(R.id.mechanics_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mechanicsRecyclerView.setLayoutManager(linearLayoutManager);
        complaints=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Complaint complaint=snapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                MechanicsAdapter mechanicsAdapter=new MechanicsAdapter(complaints);
                mechanicsRecyclerView.setAdapter(mechanicsAdapter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                FragmentAddToList fragmentAddToList=new FragmentAddToList();
                fragmentTransaction.replace(R.id.fragment,fragmentAddToList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
