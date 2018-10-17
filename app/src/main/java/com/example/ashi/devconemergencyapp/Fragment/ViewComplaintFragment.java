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
import android.widget.ProgressBar;
import com.example.ashi.devconemergencyapp.Adapter.ViewComplainAdapter;
import com.example.ashi.devconemergencyapp.Model.Complaint;
import com.example.ashi.devconemergencyapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewComplaintFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=database.getReference().child("Complaint");
        View view= inflater.inflate(R.layout.fragment_view_complaint, container, false);
        recyclerView=view.findViewById(R.id.view_complain_recycler_view);
        progressBar=view.findViewById(R.id.view_complain_progressBar);
        final List<Complaint> complaints=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Complaint complaint=snapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                Collections.reverse(complaints);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                ViewComplainAdapter viewComplainAdapter=new ViewComplainAdapter(complaints);
                recyclerView.setAdapter(viewComplainAdapter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }
}
