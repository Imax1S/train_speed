package com.example.train_speed.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

class GravitySensorPresenter(context: Context) {


    private var mSensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gravitySensor = GravitySensor()

    private fun setGravity() {
        mSensorManager.registerListener(
            gravitySensor,
            mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_UI
        )
    }


}