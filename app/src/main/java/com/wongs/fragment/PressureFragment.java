package com.wongs.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wongs.emidemo.R;
import com.wongs.views.PressureLevelView;


/**
 * Created by ryze.liu on 7/14/2016.
 */
public class PressureFragment extends Fragment {
    private TextView tv_pressure_value;
    private ImageView iv_home;
    private PressureLevelView pressureLevelView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_pressure,null);
         tv_pressure_value = (TextView) view.findViewById(R.id.tv_pressure_value);
         pressureLevelView = (PressureLevelView) view.findViewById(R.id.view_pressure);
         pressureLevelView.setPressureLevel(7);
         iv_home = (ImageView) view.findViewById(R.id.iv_home);
         iv_home.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getActivity().finish();
             }
         });
         return view;
    }

}
