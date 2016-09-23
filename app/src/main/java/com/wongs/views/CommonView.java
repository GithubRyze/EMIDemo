package com.wongs.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wongs.emidemo.R;

/**
 * Created by ryze.liu on 7/14/2016.
 */
public class CommonView extends RelativeLayout{

    private ImageView iv_icon_1;
    private ImageView iv_icon_2;
    private ImageView iv_icon_3;
    private TextView  tv_item_name;
    private TextView  tv_item_value;
    private TextView  tv_pollution_level;
    private TextView  tv_lpo_value;
    private TextView  tv_symbol;
    private TextView  tv_temp_symbol;

    public CommonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.common_view, this, true);
        iv_icon_1 = (ImageView) findViewById(R.id.iv_icon_1);
        iv_icon_2 = (ImageView) findViewById(R.id.iv_icon_2);
        iv_icon_3 = (ImageView) findViewById(R.id.iv_icon_3);
        tv_item_name = (TextView) findViewById(R.id.tv_item_name);
        tv_item_value = (TextView) findViewById(R.id.tv_item_value);
        tv_pollution_level = (TextView) findViewById(R.id.tv_pollution_level);
        tv_lpo_value = (TextView) findViewById(R.id.tv_lpo_value);
        tv_symbol = (TextView) findViewById(R.id.tv_symbol);
        tv_temp_symbol = (TextView) findViewById(R.id.tv_temp_symbol);
    }

    public ImageView getIv_icon_1(){
          return iv_icon_1;
      }

    public ImageView getIv_icon_2(){
        return iv_icon_2;
      }

    public ImageView getIv_icon_3(){
        return iv_icon_3;
      }

    public TextView getTv_item_name(){
        return tv_item_name;
    }

    public TextView getTv_item_value(){
        return tv_item_value;
    }

    public TextView getTv_pollution_level(){
        return tv_pollution_level;
    }

    public TextView getTv_lpo_value(){
        return tv_lpo_value;
    }

    public TextView getTv_symbol(){
        return tv_symbol;
    }
    public TextView getTv_temp_symbol(){
        return tv_temp_symbol;
    }

}
