package com.wongs.mode;

/**
 * Created by ryze.liu on 8/4/2016.
 */
public class SensorData {

    private int currentTemp;
    private int heatTemp;
    private int coolTemp;
    private int humidity;
    private int pressure;
    private int LOP;

    private int direction;

    public SensorData(int currentTemp, int heatTemp, int coolTemp, int humidity, int pressure, int LOP, int direction) {
        this.currentTemp = currentTemp;
        this.heatTemp = heatTemp;
        this.coolTemp = coolTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.LOP = LOP;
        this.direction = direction;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getHeatTemp() {
        return heatTemp;
    }

    public void setHeatTemp(int heatTemp) {
        this.heatTemp = heatTemp;
    }

    public int getCoolTemp() {
        return coolTemp;
    }

    public void setCoolTemp(int coolTemp) {
        this.coolTemp = coolTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getLOP() {
        return LOP;
    }

    public void setLOP(int LOP) {
        this.LOP = LOP;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
