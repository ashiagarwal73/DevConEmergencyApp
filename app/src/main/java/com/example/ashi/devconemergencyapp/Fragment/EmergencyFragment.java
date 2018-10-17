package com.example.ashi.devconemergencyapp.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ashi.devconemergencyapp.R;
import com.example.ashi.devconemergencyapp.Adapter.RecyclerViewAdapter;
import com.example.ashi.devconemergencyapp.rest.ApiClient;
import com.example.ashi.devconemergencyapp.rest.ApiInterface;
import com.example.ashi.devconemergencyapp.Model.Places;
import com.example.ashi.devconemergencyapp.Model.Results;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencyFragment extends Fragment {
    private FusedLocationProviderClient mFusedLocationClient;
    private String longi;
    private String lati;
    List<Results> PoliceStations=new ArrayList<>(),Hospitals=new ArrayList<>();
    private final static String appid = "AIzaSyB4rsmNHuxsLaktneAh0YfFiFgEVJu_PBs";
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progressBar);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lati = String.valueOf(location.getLatitude());
                    longi = String.valueOf(location.getLongitude());
                    if (appid.isEmpty()) {
                        Toast.makeText(getContext(), "Please obtain your API KEY ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        final ApiInterface apiInterface=ApiClient.getClient().create(ApiInterface.class);
                        Call<Places> call = apiInterface.getPlacesReport(lati+","+longi,"2000","police",appid);
                        call.enqueue(new Callback<Places>() {
                            @Override
                            public void onResponse(Call<Places> call, Response<Places> response) {
                                Log.d("response",""+response.body());
                                PoliceStations=response.body().getResults();
                                Call<Places> call2 = apiInterface.getPlacesReport(lati+","+longi,"3000","hospital",appid);
                                call2.enqueue(new Callback<Places>() {
                                    @Override
                                    public void onResponse(Call<Places> call, Response<Places> response) {
                                        Log.d("response",""+response.body());
                                        Hospitals=response.body().getResults();
                                        EnterValuesInList();
                                        progressBar.setVisibility(View.GONE);


                                    }
                                    @Override
                                    public void onFailure(Call<Places> call, Throwable t) {

                                    }
                                });

                            }
                            @Override
                            public void onFailure(Call<Places> call, Throwable t) {

                            }
                        });

                    }
                }
            }
        });
        return view;
    }
    List<Results> totalList=new ArrayList<>();
    RecyclerView recyclerView;
    public void EnterValuesInList(){
        /*function used to enter random values in a list to show some test data*/
        if(!PoliceStations.isEmpty()){
        Results tempData=new Results();
        tempData.setHeaderText("Police Stations");
        tempData.setViewType(Results.HEADER_TYPE);
        totalList.add(tempData);
        totalList.addAll(PoliceStations);}
        if(!Hospitals.isEmpty()){
        Results tempData2=new Results();
        tempData2.setHeaderText("Hospitals");
        tempData2.setViewType(Results.HEADER_TYPE);
        totalList.add(tempData2);
        totalList.addAll(Hospitals);}
        if(totalList.isEmpty())
        {
            Results tempData=new Results();
            tempData.setHeaderText("No Data Available Please try after some time");
            tempData.setViewType(Results.HEADER_TYPE);
            totalList.add(tempData);

        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(totalList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
