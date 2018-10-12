package com.example.ashi.devconemergencyapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    TabLayout tabLayout;
    ViewPager viewPager;

    int currentPage=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
         fragmentManager=getActivity().getSupportFragmentManager();
         tabLayout = view.findViewById(R.id.tab_layout_fragment_main);
         viewPager = view.findViewById(R.id.view_pager_fragment_main);


        final ArrayList<Integer> imgRes = new ArrayList<>();
        imgRes.add(R.drawable.complainslider);
        imgRes.add(R.drawable.emergencyslider);
        imgRes.add(R.drawable.mechanic2);
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
        }, 2000, 1000);



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
        return view;
    }

}
