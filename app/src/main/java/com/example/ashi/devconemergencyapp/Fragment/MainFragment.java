package com.example.ashi.devconemergencyapp.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.ashi.devconemergencyapp.Adapter.SlideshowAdapter;
import com.example.ashi.devconemergencyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    TabLayout tabLayout;
    ViewPager viewPager;

    int currentPage=0;
    private FusedLocationProviderClient mFusedLocationClient;
    double lati;
    double longi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
         fragmentManager=getActivity().getSupportFragmentManager();
         tabLayout = view.findViewById(R.id.tab_layout_fragment_main);
         viewPager = view.findViewById(R.id.view_pager_fragment_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lati=location.getLatitude();
                            longi=location.getLongitude();
                            String androidId = Settings.Secure.getString(getActivity().getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                            com.example.ashi.devconemergencyapp.Model.Location location1=new com.example.ashi.devconemergencyapp.Model.Location();
                            location1.setLat(lati);
                            location1.setLng(longi);
                            databaseReference.child("Coordinates").child(androidId).setValue(location1);
                        }
                    }
                });
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000,2 , (LocationListener) listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000,2 , listener);

        final ArrayList<Integer> imgRes = new ArrayList<>();
        imgRes.add(R.drawable.complainslider);
        imgRes.add(R.drawable.emergencyslider);
        imgRes.add(R.drawable.mechanicslider);
        SlideshowAdapter slideshowAdapter = new SlideshowAdapter(imgRes);
        viewPager.setAdapter(slideshowAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        final Handler handler = new Handler();
        final Runnable updater = new Runnable() {
            @Override
            public void run() {
                if (currentPage == imgRes.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("running");
                handler.post(updater);
            }
        }, 5000, 5000);



         fragmentTransaction=fragmentManager.beginTransaction();
        view.findViewById(R.id.complain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComplainSystemFragment complainSystemFragment=new ComplainSystemFragment();
                fragmentTransaction.replace(R.id.fragment,complainSystemFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.emergency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmergencyFragment emergencyFragment=new EmergencyFragment();
                fragmentTransaction.replace(R.id.fragment,emergencyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.mechanics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MechanicsFragment mechanicsFragment=new MechanicsFragment();
                fragmentTransaction.replace(R.id.fragment,mechanicsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.view_complain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewComplaintFragment viewComplaintFragment=new ViewComplaintFragment();
                fragmentTransaction.replace(R.id.fragment,viewComplaintFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.crowd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CrowdFragment.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // Logic to handle location object
                lati=location.getLatitude();
                longi=location.getLongitude();
                String androidId = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                com.example.ashi.devconemergencyapp.Model.Location location1=new com.example.ashi.devconemergencyapp.Model.Location();
                location1.setLat(lati);
                location1.setLng(longi);
                databaseReference.child("Coordinates").child(androidId).setValue(location1);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
