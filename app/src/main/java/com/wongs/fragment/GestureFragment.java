package com.wongs.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.wongs.emidemo.R;
import com.wongs.utils.Constant;
import com.wongs.utils.LogUtil;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class GestureFragment extends Fragment{
    private static final String TAG = "GestureFragment";
    private LinkedList list = new LinkedList();
    private ImageView iv_home,bt_reset;
    private CheckBox[] lefts = new CheckBox[3];
    private CheckBox[] rights = new CheckBox[3];
    private CheckBox[] downs = new CheckBox[2];
    private CheckBox[] ups = new CheckBox[2];
    private int temp = 0;
    private int direction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gesture, null);
        lefts[0] = (CheckBox) view.findViewById(R.id.gesture_left_1);
        lefts[1] = (CheckBox) view.findViewById(R.id.gesture_left_2);
        lefts[2] = (CheckBox) view.findViewById(R.id.gesture_left_3);

        rights[0] = (CheckBox) view.findViewById(R.id.gesture_right_1);
        rights[1] = (CheckBox) view.findViewById(R.id.gesture_right_2);
        rights[2] = (CheckBox) view.findViewById(R.id.gesture_right_3);

        downs[0] = (CheckBox) view.findViewById(R.id.gesture_down_1);
        downs[1] = (CheckBox) view.findViewById(R.id.gesture_down_2);

        ups[0] = (CheckBox) view.findViewById(R.id.gesture_up_1);
        ups[1] = (CheckBox) view.findViewById(R.id.gesture_up_2);
        iv_home = (ImageView) view.findViewById(R.id.iv_home);
        bt_reset = (ImageView) view.findViewById(R.id.iv_reset);
        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCheckbox();
            }
        });
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        direction = Constant.mSensorData.getDirection();
        showGesture(direction);
        timer.schedule(task,1000,500);
        return view;
    }

    public void showGesture(int direction){

        switch (direction){
            case 0:


                break;
            case 1:
                setCheckbox(lefts,R.drawable.gesture_left);
                break;
            case 2:
                setCheckbox(rights, R.drawable.gesture_right);
                break;
            case 3:
                setCheckbox(ups, R.drawable.gesture_up);
                break;
            case 4:
                setCheckbox(downs, R.drawable.gesture_down);
                break;
        }

    }
    public void setCheckbox(CheckBox[] boxes,int drawable){

            for (int i = 0;i<boxes.length;i++){
                boxes[i].setBackgroundResource(drawable);
            }

    }
    public void removeCheckbox(){

        if (direction == 1 || direction == 2){

            for (int i = 0;i<lefts.length;i++){
                lefts[i].setBackgroundResource(R.drawable.ic_gesture_left_off);
                rights[i].setBackgroundResource(R.drawable.ic_gesture_right_off);
            }
        }
        else if (direction == 3 || direction==4){
            for (int i = 0;i<ups.length;i++){
                ups[i].setBackgroundResource(R.drawable.ic_gesture_up_off);
                downs[i].setBackgroundResource(R.drawable.ic_gesture_down_off);
            }
        }

    }

    private Bundle bundle;
    private Message msg;
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
             msg = handler.obtainMessage();
             bundle = new Bundle();
             bundle.putInt("KEY",temp);
             msg.setData(bundle);
             msg.what = 1;
             msg.sendToTarget();

        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            int number = msg.getData().getInt("KEY");
            LogUtil.d(TAG,"handleMessage:"+number);
            if (msg.what == 1){
                if (direction == 1){
                    showDirection(lefts,number);
                }else if (direction == 2){
                    showDirection(rights,number);
                }else if (direction == 3){
                    showDirection(ups,number);
                }else if (direction == 4){
                    showDirection(downs,number);
                }
            }

            }



    };

    public void showDirection(CheckBox[] checkBoxes,int number) {
        LogUtil.d(TAG,"showDirection:"+checkBoxes.length);
        if (checkBoxes.length == 3) {
            switch (number) {
                case 0:
                    checkBoxes[2].setChecked(true);
                    checkBoxes[1].setChecked(false);
                    checkBoxes[0].setChecked(false);
                    break;
                case 1:
                    checkBoxes[2].setChecked(false);
                    checkBoxes[1].setChecked(true);
                    checkBoxes[0].setChecked(false);
                    break;
                case 2:
                    checkBoxes[2].setChecked(false);
                    checkBoxes[1].setChecked(false);
                    checkBoxes[0].setChecked(true);
                    break;
            }
        } else if (checkBoxes.length == 2) {

            switch (number) {
                case 0:
                    checkBoxes[1].setChecked(true);
                    checkBoxes[0].setChecked(false);
                    break;
                case 1:
                    checkBoxes[1].setChecked(false);
                    checkBoxes[0].setChecked(true);
                    break;

            }
        }
        temp++;
        if (temp == checkBoxes.length) {
            temp = 0;
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d(TAG,"onDestroyView");
        timer.cancel();
        task = null;
        super.onDestroyView();
    }


}
