package com.wongs.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wongs.emidemo.R;
import com.wongs.fragment.AdjustFragment;
import com.wongs.fragment.GestureFragment;
import com.wongs.fragment.PollutionFragment;
import com.wongs.fragment.PressureFragment;
import com.wongs.fragment.TemperatureFragment;
import com.wongs.utils.Constant;

/**
 * Created by ryze.liu on 7/15/2016.
 */
public class ShowActivity extends AppCompatActivity {
    public static final String TAG = "ShowActivity";
    Intent intent;
    Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        intent = getIntent();
        addFragment(intent.getStringExtra(this.TAG));
    }




    private void addFragment(String tag) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (tag.equals(Constant.PRESSURE)) {
            fragment = new PressureFragment();
        }
        else if (tag.equals(Constant.GESTURE)){
            fragment = new GestureFragment();
        }
        else if (tag.equals(Constant.HUMIDITY)){

        }
        else if (tag.equals(Constant.POLLUTION)){
            fragment = new PollutionFragment();
        }
        else if (tag.equals(Constant.TAP_ADJUST)){
            fragment = new AdjustFragment();
        }
        else if (tag.equals(Constant.TEMPERATURE)){
            fragment = new TemperatureFragment();
        }
        transaction.add(R.id.container,fragment,tag);
        transaction.commitAllowingStateLoss();
    }

}
