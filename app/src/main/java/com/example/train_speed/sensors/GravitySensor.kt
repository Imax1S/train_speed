package com.example.train_speed.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlin.math.abs

class GravitySensor : SensorEventListener {
    var valueOfTook = 0.5
    var lastY = 0f
    var y = 0f
    var tooks = 0


    override fun onSensorChanged(event: SensorEvent?) {
        if (abs(lastY - (event?.values?.get(1) ?: 0f)) > valueOfTook) {
            tooks++
        }
        lastY = event?.values?.get(1) ?: 0f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}