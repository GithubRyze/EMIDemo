package com.wongs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wongs.emidemo.R;
import com.wongs.utils.Constant;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ryze.liu on 7/18/2016.
 */
public class GestureIndirectionView extends LinearLayout{
    private LinearLayout layout;
    private List<View> list;
    public GestureIndirectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GestureIndirectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        list = new ArrayList<>(4);
        View view = LayoutInflater.from(context).inflate(R.layout.gestrue_control_view,this,true);
        layout = (LinearLayout) view.findViewById(R.id.gesture_indirection);
        for (int i = 0 ;i < layout.getChildCount();i++){
            list.add(layout.getChildAt(i));
        }
    }

    public void setGestureIndirection(int indirection){

          switch (indirection){
              case Constant.LEFT:
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    for (View view : list){
                        view.setBackgroundResource(R.drawable.ic_gesture_bar_left_emi);
                    }
                  break;
              case Constant.UP:
                  layout.setOrientation(LinearLayout.VERTICAL);
                  for (View view : list){
                      view.setBackgroundResource(R.drawable.ic_gesture_bar_up_emi);
                  }
                  break;
              case Constant.RIGHT:
                  layout.setOrientation(LinearLayout.HORIZONTAL);
                  for (View view : list){
                      view.setBackgroundResource(R.drawable.ic_gesture_bar_right_emi);
                  }
                  break;
              case Constant.DOWN:
                  layout.setOrientation(LinearLayout.VERTICAL);
                  for (View view : list){
                      view.setBackgroundResource(R.drawable.ic_gesture_bar_down_emi);
                  }
                  break;
          }

    }
}
