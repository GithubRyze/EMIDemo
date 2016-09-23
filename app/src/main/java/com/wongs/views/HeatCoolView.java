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
 * Created by ryze.liu on 8/4/2016.
 */
public class HeatCoolView extends LinearLayout{

    private static final String TAG = "PressureLevelView";
    private List<View> list;
    private LinearLayout layout;
    private Context mContext;
    public HeatCoolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.heat_cool_view, this, true);
        layout = (LinearLayout) view.findViewById(R.id.heat_cool_display);
        list = new ArrayList<View>(8);
        for (int i = 0; i<layout.getChildCount();i++){
            list.add(layout.getChildAt(i));
        }
        LogUtil.d(TAG, layout.getChildCount() + "size");
    }


    public HeatCoolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPressureLevel(int level,int color){

        for (int i = 0;i < level ;i++){
            list.get(7-i).setBackgroundResource(color);
        }

    }



}
