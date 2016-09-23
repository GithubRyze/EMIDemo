package com.wongs.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wongs.emidemo.R;
import com.wongs.utils.Constant;
import com.wongs.utils.LogUtil;
import com.wongs.views.HeatCoolView;
import com.wongs.views.SeekBarView;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class AdjustFragment extends Fragment implements SeekBarView.OnSeekBarCallBack{
    private static final String TAG = "AdjustFragment";
    private SeekBarView mSeekArc1;
    private HeatCoolView mHeatView,mCoolView;
    private Message msg;
    private TextView tv_heat,tv_cool,tv_temp_value,tv_temp_symbol;
    private static final String HEAT_KEY = "HeatValue";
    private static final String COOL_KEY = "CoolValue";
    private ImageView heatImage,coolImage,iv_home;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adjust,null);

        mSeekArc1 = (SeekBarView) view.findViewById(R.id.sa_1);
        mSeekArc1.setOnSeekBarCallBack(this);
        mHeatView = (HeatCoolView) view.findViewById(R.id.heat_display);
        mCoolView = (HeatCoolView) view.findViewById(R.id.cool_display);
        heatImage = (ImageView) view.findViewById(R.id.iv_heat);
        coolImage = (ImageView) view.findViewById(R.id.iv_cool);
        tv_heat = (TextView) view.findViewById(R.id.tv_heat);
        tv_heat.setText(String.valueOf(Constant.mSensorData.getHeatTemp()));
        tv_cool = (TextView) view.findViewById(R.id.tv_cool);
        tv_cool.setText(String.valueOf(Constant.mSensorData.getCoolTemp()));
        tv_temp_value = (TextView) view.findViewById(R.id.tv_temp_value);
        tv_temp_value.setText(String.valueOf(Constant.mSensorData.getCurrentTemp()));
        tv_temp_symbol = (TextView) view.findViewById(R.id.tv_temp_symbol);
        iv_home = (ImageView) view.findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    private Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentTemp = Constant.mSensorData.getCurrentTemp();
            int heatTemp = Constant.mSensorData.getHeatTemp();
            int coolTemp = Constant.mSensorData.getCoolTemp();
            switch (msg.what){

                case 0:
                    tv_temp_value.setText(String.valueOf(currentTemp));
                    tv_temp_value.setTextColor(getActivity().getResources().getColor(R.color.main_color));
                    tv_temp_symbol.setTextColor(getActivity().getResources().getColor(R.color.main_color));
                    if (heatTemp > currentTemp){
                        mHeatView.setPressureLevel(4,R.color.heat_level_color);
                        mCoolView.setPressureLevel(8,R.color.theme_color);
                        heatImage.setBackgroundResource(R.drawable.ic_heater_activated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_disactivated);
                    }else if (coolTemp < currentTemp){
                        heatImage.setBackgroundResource(R.drawable.ic_heater_disactivated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_activated);
                        mHeatView.setPressureLevel(8, R.color.theme_color);
                        mCoolView.setPressureLevel(4, R.color.cool_level_color);
                    }else {
                        heatImage.setBackgroundResource(R.drawable.ic_heater_disactivated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_disactivated);
                        mHeatView.setPressureLevel(8, R.color.theme_color);
                        mCoolView.setPressureLevel(8, R.color.theme_color);
                    }
                    break;
                //handle heat value
                case 1:
                    tv_heat.setText(String.valueOf(heatTemp));
                    tv_temp_value.setTextColor(getActivity().getResources().getColor(R.color.heat_color));
                    tv_temp_symbol.setTextColor(getActivity().getResources().getColor(R.color.heat_color));
                    tv_temp_value.setText(String.valueOf(heatTemp));
                    if (heatTemp > currentTemp){
                        mHeatView.setPressureLevel(4,R.color.heat_level_color);
                        mCoolView.setPressureLevel(8,R.color.theme_color);
                        heatImage.setBackgroundResource(R.drawable.ic_heater_activated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_disactivated);
                    }else if (heatTemp <= currentTemp){
                        heatImage.setBackgroundResource(R.drawable.ic_heater_disactivated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_disactivated);
                        mHeatView.setPressureLevel(8,R.color.theme_color);
                        mCoolView.setPressureLevel(8,R.color.theme_color);
                    }
                    break;
                //handle cool value
                case 2:
                    tv_cool.setText(String.valueOf(coolTemp));
                    tv_temp_value.setTextColor(getActivity().getResources().getColor(R.color.cool_color));
                    tv_temp_symbol.setTextColor(getActivity().getResources().getColor(R.color.cool_color));
                    tv_temp_value.setText(String.valueOf(coolTemp));
                    if (coolTemp < currentTemp){
                        heatImage.setBackgroundResource(R.drawable.ic_heater_disactivated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_activated);
                        mHeatView.setPressureLevel(8,R.color.theme_color);
                        mCoolView.setPressureLevel(4,R.color.cool_level_color);
                    }else if (coolTemp >= currentTemp){
                        heatImage.setBackgroundResource(R.drawable.ic_heater_disactivated);
                        coolImage.setBackgroundResource(R.drawable.ic_cooler_disactivated);
                        mHeatView.setPressureLevel(8,R.color.theme_color);
                        mCoolView.setPressureLevel(8,R.color.theme_color);
                    }
                    break;
            }


        }
    };

    @Override
    public void heatCallBack(int HeatValue) {
        LogUtil.d(TAG,"HeatValue = "+HeatValue);
        msg = mHandler.obtainMessage();
        Constant.mSensorData.setHeatTemp(HeatValue);
        msg.what = 1;
        msg.sendToTarget();
    }

    @Override
    public void coolCallBack(int CoolValue) {
        LogUtil.d(TAG,"CoolValue = "+CoolValue);
        msg = mHandler.obtainMessage();
        Constant.mSensorData.setCoolTemp(CoolValue);
        msg.what = 2;
        msg.sendToTarget();
    }

    @Override
    public void isMoving(boolean up) {
        LogUtil.d(TAG,"isMoving = "+up);
        msg = mHandler.obtainMessage();
        msg.what = 0;
        msg.sendToTarget();
    }

}
