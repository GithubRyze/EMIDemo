package com.wongs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.wongs.emidemo.R;
import com.wongs.utils.Constant;
import com.wongs.utils.LogUtil;
import com.wongs.views.CommonView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private CommonView temperature_View;
    private CommonView humidity_View;
    private CommonView pressure_View;
    private CommonView pollution_View;
    private CommonView tap_View;
    private CommonView gesture_View;
    private boolean unable = false;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperature_View = (CommonView) findViewById(R.id.temp_view);
        humidity_View = (CommonView) findViewById(R.id.humidity_view);
        pressure_View = (CommonView) findViewById(R.id.pressure_view);
        pollution_View = (CommonView) findViewById(R.id.pollution_view);
        tap_View = (CommonView) findViewById(R.id.tap_view);
        gesture_View = (CommonView) findViewById(R.id.gesture_view);
        initViews();
        temperature_View.setOnTouchListener(listener);
  //    humidity_View.setOnTouchListener(listener);
        pressure_View.setOnTouchListener(listener);
        pollution_View.setOnTouchListener(listener);
        tap_View.setOnTouchListener(listener);
        gesture_View.setOnTouchListener(listener);

    }

    public void initViews(){
        temperature_View.getIv_icon_1().setBackgroundResource(R.drawable.bt_tem_emi);
        temperature_View.getIv_icon_2().setVisibility(View.VISIBLE);
        temperature_View.getIv_icon_2().setClickable(true);
        temperature_View.getIv_icon_2().setOnClickListener(this);
        temperature_View.getIv_icon_2().setBackgroundResource(R.drawable.bt_farenheit_disable_emi);
        temperature_View.getIv_icon_3().setVisibility(View.VISIBLE);
        temperature_View.getIv_icon_3().setClickable(true);
        temperature_View.getIv_icon_3().setOnClickListener(this);
        temperature_View.getIv_icon_3().setBackgroundResource(R.drawable.bt_celcius_emi);
        temperature_View.getTv_item_name().setText(R.string.temperature);
        temperature_View.getTv_item_value().setText("32.4");
        temperature_View.getTv_symbol().setText(R.string.temp_symbol);
        temperature_View.getTv_temp_symbol().setVisibility(View.VISIBLE);

        humidity_View.getIv_icon_1().setBackgroundResource(R.drawable.icon_humid_emi);
        humidity_View.getTv_item_name().setText(R.string.humidity);
        humidity_View.getTv_item_value().setText("25.2");
        humidity_View.getTv_symbol().setText(R.string.percent_symbol);

        pressure_View.getIv_icon_1().setBackgroundResource(R.drawable.bt_pressure_emi);
        pressure_View.getTv_item_name().setText(R.string.pressure);
        pressure_View.getTv_item_value().setText("2000");
        pressure_View.getTv_symbol().setTextSize(28f);
        pressure_View.getTv_symbol().setText(R.string.pressure_symbol);

        pollution_View.getIv_icon_1().setBackgroundResource(R.drawable.bt_particle_emi);
        pollution_View.getTv_item_name().setText(R.string.pollution);
        pollution_View.getTv_pollution_level().setVisibility(View.VISIBLE);
        pollution_View.getTv_pollution_level().setText("Clear");
        pollution_View.getTv_pollution_level().setTextColor(this.getResources().getColor(R.color.pollution_clear));
        pollution_View.getTv_lpo_value().setVisibility(View.VISIBLE);
        pollution_View.getTv_lpo_value().setText("LPO : 30.2%");
        pollution_View.getTv_symbol().setVisibility(View.GONE);

        tap_View.getIv_icon_1().setBackgroundResource(R.drawable.bt_fan_disable_emi);
        tap_View.getIv_icon_2().setVisibility(View.VISIBLE);
        tap_View.getIv_icon_2().setBackgroundResource(R.drawable.ic_heater);
        tap_View.getTv_item_name().setText(R.string.tap_text);
        tap_View.getTv_symbol().setVisibility(View.GONE);

        gesture_View.getIv_icon_1().setBackgroundResource(R.drawable.bt_gesture_control_emi);
        gesture_View.getTv_item_name().setText(R.string.tap_gesture);
        gesture_View.getTv_item_value().setVisibility(View.GONE);
        gesture_View.getTv_symbol().setVisibility(View.GONE);


    }
    int temp_id = 0;
    private View.OnTouchListener listener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (temp_id != 0) {
                if (temp_id != v.getId())
                    return true;
            }
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    LogUtil.d(TAG,v.getId()+"down");
                    v.setBackgroundResource(R.color.theme_color);
                    temp_id = v.getId();
                    break;
                case MotionEvent.ACTION_UP:
                    if (temp_id == v.getId()) {
                        temp_id = 0;
                        LogUtil.d(TAG, v.getId() + "up");
                        v.setBackgroundResource(R.color.item_background);
                        starShowActivity(v.getId());
                    }
                    break;



            }
            return false;
        }
    };
    public void starShowActivity(int view_id){
        String tag = "";
        switch (view_id){

            case R.id.pressure_view:
                tag = Constant.PRESSURE;
                break;
            case R.id.temp_view:
                tag = Constant.TEMPERATURE;
                break;

            case R.id.humidity_view:
                tag = Constant.HUMIDITY;
                break;

            case R.id.pollution_view:
                tag = Constant.POLLUTION;
                break;

            case R.id.tap_view:
                tag = Constant.TAP_ADJUST;
                break;

            case R.id.gesture_view:
                tag = Constant.GESTURE;

                break;

        }
      Intent intent = new Intent(this,ShowActivity.class);
      intent.putExtra(ShowActivity.TAG,tag);
      startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_icon_2:
                temperature_View.getIv_icon_2().setBackgroundResource(R.drawable.bt_farenheit_emi);
                temperature_View.getIv_icon_3().setBackgroundResource(R.drawable.bt_celcius_disable_emi);
                break;

            case R.id.iv_icon_3:
                temperature_View.getIv_icon_2().setBackgroundResource(R.drawable.bt_farenheit_disable_emi);
                temperature_View.getIv_icon_3().setBackgroundResource(R.drawable.bt_celcius_emi);
                break;

          /*  case R.id.pressure_view:
                break;

            case R.id.pollution_view:
                break;

            case R.id.tap_view:
                break;

            case R.id.gesture_view:
                break;*/

            default:
                break;

        }

        unable =false;

    }
}
