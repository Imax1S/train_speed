package com.example.train_speed.sensors

import android.hardware.Sensor
import android.hardware.SensorEventListener
import com.example.train_speed.models.Point

abstract class Accelerometer : SensorEventListener {
    protected var lastX = 0f
    protected var lastY = 0f
    protected var lastZ = 0f
    abstract fun getPoint(): Point?
    override fun onAccuracyChanged(arg0: Sensor?, arg1: Int) {

    }
}