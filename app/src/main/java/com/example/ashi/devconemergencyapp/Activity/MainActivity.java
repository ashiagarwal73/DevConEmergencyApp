package com.example.ashi.devconemergencyapp.Activity;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ashi.devconemergencyapp.Fragment.EmergencyFragment;
import com.example.ashi.devconemergencyapp.Fragment.MainFragment;
import com.example.ashi.devconemergencyapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MainFragment mainFragment=new MainFragment();
        fragmentTransaction.replace(R.id.fragment,mainFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EmergencyFragment emergencyFragment=new EmergencyFragment();
        emergencyFragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
