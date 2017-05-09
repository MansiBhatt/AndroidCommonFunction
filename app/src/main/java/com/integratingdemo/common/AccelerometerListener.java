package com.integratingdemo.common;

/**
 * Created by Mansi on 11/28/2016.
 * This listner is used for sensor on shake
 */
public interface AccelerometerListener {

    void onAccelerationChanged(float x, float y, float z);

    void onShake(float force);

}
