package com.example.train_speed.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.lifecycle.MutableLiveData
import kotlin.math.abs

class GravitySensor : SensorEventListener {
    companion object {
        const val valueOfTook = 0.1
    }

    var lastY = 0f
    var tooks = MutableLiveData(0)

    override fun onSensorChanged(event: SensorEvent?) {
        if (abs(lastY - (event?.values?.get(1) ?: 0f)) > valueOfTook) {
            tooks.value = tooks.value?.plus(1)
        }
        lastY = event?.values?.get(1) ?: 0f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}