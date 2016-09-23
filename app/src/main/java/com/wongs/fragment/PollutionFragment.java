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
import com.wongs.views.ChartView;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class PollutionFragment extends Fragment {
    ImageView iv_home;
    ChartView mChartView;
    private TextView temp_value,tv_title;
    private static  final String TAG = "PollutionFragment";
    double temp_test[] = {18.2,12.5,12.5,10.4,16.7,19.8,10.8,14.9,17.7,20.1,
            5.4,8.0,5.6,18.9,10.4,3.4,17.6,19.4,12.6,9.2,
            15.5,12.5,10.4,13.8,4.9,11.7,2.1,17.6,19.4,20.0,
            2.4,12.0,5.6,18.9,10.4,13.4,17.6,19.4,15.0,21.2,
            10.4,16.7,19.8,9.8,7.9,17.6,19.4,3.0,9.2,17.6};
    private Queue<Double> queue = new LinkedList<>();
    private String texts[] = {" 5","10","15","20"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pollution,null);
        iv_home = (ImageView) view.findViewById(R.id.iv_home);
        mChartView = (ChartView) view.findViewById(R.id.chart_view);
        mChartView.setScaleText(texts);
        mChartView.setTitle("%");
        mChartView.setScale(5);
        temp_value = (TextView) view.findViewById(R.id.temp_value);
        temp_value.setText("0.0"+ getActivity().getResources().getString(R.string.percent_symbol));
        tv_title = (TextView) view.findViewById(R.id.title);
        tv_title.setText("LPO");
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        timer.schedule(task, 2000, 1000);
        fillTestData();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 8) {
                if (!queue.isEmpty()){
                    double temp = queue.poll();
                    queue.add(temp);
                    String value  = String.valueOf(temp);
                    temp_value.setText(value + getActivity().getResources().getString(R.string.percent_symbol));
                    mChartView.invalidateView(temp);
                }

            }
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

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
