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
import com.wongs.utils.LogUtil;
import com.wongs.views.ChartView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class TemperatureFragment extends Fragment {
    private static  final String TAG = "TemperatureFragment";
    private ChartView chartView;
    ImageView iv_home;
    double temp_test[] = {21.2,35.5,12.5,10.4,16.7,19.8,20.8,35.9,31.7,20.1,
            33.4,25.0,5.6,18.9,10.4,35.4,17.6,19.4,20.0,21.2,
            35.5,12.5,10.4,20.8,35.9,31.7,20.1,17.6,19.4,20.0,
            33.4,25.0,5.6,18.9,10.4,35.4,17.6,19.4,20.0,21.2,
            10.4,16.7,19.8,20.8,35.9,17.6,19.4,20.0,21.2,17.6};
    private Queue<Double> queue = new LinkedList<>();
    private TextView lpo_value,tv_title;
    private String texts[] = {"10","20","30","40"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pollution, null);
        chartView = (ChartView) view.findViewById(R.id.chart_view);
        chartView.setScaleText(texts);
        chartView.setTitle("C");
        iv_home = (ImageView) view.findViewById(R.id.iv_home);
        tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText(R.string.temperature);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        lpo_value = (TextView) view.findViewById(R.id.temp_value);
        lpo_value.setText("0.0" + getActivity().getResources().getString(R.string.temp_symbol)+ "C");
        timer.schedule(task, 2000, 1000);
        fillTestData();
        return view;
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 8) {
                LogUtil.d(TAG, "handleMessage");
                if (!queue.isEmpty()){
                    double temp = queue.poll();
                    queue.add(temp);
                    String value  = String.valueOf(temp);
                    lpo_value.setText(value + getActivity().getResources().getString(R.string.temp_symbol)+ "C");
                    chartView.invalidateView(temp);
                }

            }
        }
    };

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            LogUtil.d(TAG,"send   handleMessage");
            Message msg = handler.obtainMessage();
            msg.what=8;
            msg.sendToTarget();
        }
    };

    public void fillTestData(){

        for(int i = 0;i<temp_test.length;i++){
            queue.add(temp_test[i]);
        }
    }

    @Override
    public void onDestroyView() {
        timer.cancel();
        task = null;
        super.onDestroyView();
    }
}