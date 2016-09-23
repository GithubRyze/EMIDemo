package com.wongs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wongs.emidemo.R;
import com.wongs.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryze.liu on 7/18/2016.
 */
public class PressureLevelView extends LinearLayout{
    private static final String TAG = "PressureLevelView";
   private List<View> list;
   private LinearLayout layout;
    private Context mContext;
   private int[] colors = {R.color.pressure_color_8,R.color.pressure_color_7,R.color.pressure_color_6,
            R.color.pressure_color_5,R.color.pressure_color_4,R.color.pressure_color_3,R.color.pressure_color_2,R.color.pressure_color_1};
    public PressureLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.pressure_level_view, this, true);
        layout = (LinearLayout) view.findViewById(R.id.layout_pressure);
        list = new ArrayList<View>(8);
        for (int i = 0; i<layout.getChildCount();i++){
            list.add(layout.getChildAt(i));
        }
        LogUtil.d(TAG,layout.getChildCount()+"size");
    }


    public PressureLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPressureLevel(int level){

        for (int i = 0;i < level ;i++){
            list.get(7-i).setBackgroundResource(colors[7-i]);
        }

    }



}
