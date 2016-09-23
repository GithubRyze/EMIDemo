package com.wongs.utils;

import com.wongs.mode.SensorData;

/**
 * Created by ryze.liu on 7/18/2016.
 */
public class Constant {

    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;
    public static final int DOWN = 4;

    public static final String  TEMPERATURE= "temperature";
    public static final String  HUMIDITY= "humidity";
    public static final String  PRESSURE= "pressure";
    public static final String  POLLUTION= "pollution";
    public static final String  TAP_ADJUST= "tap_adjust";
    public static final String  GESTURE= "gesture";

    public static SensorData mSensorData = new SensorData(20,15,27,30,60,30,2);

}
