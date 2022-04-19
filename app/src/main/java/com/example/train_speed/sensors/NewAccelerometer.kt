package com.example.train_speed.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.train_speed.utils.round
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


class NewAccelerometer : SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private var kmph: Int = 0
    var calibration = Float.NaN
    private var lastUpdate: Long = 0

    var appliedAcceleration = 0f
    var currentAcceleration = 0f
    private var velocity = 0f
    private var lastUpdatedate: Date? = null
    private val gravity = arrayListOf(0f, 0f, 0f)


    init {

        lastUpdatedate = Date(System.currentTimeMillis())
        lastUpdate = System.currentTimeMillis()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            getAccelerometer(event)
        }

        //high pass and low pass filters
        val alpha = 0.8f

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

        val x = event.values[0] - gravity[0]
        val y = event.values[1] - gravity[1]
        val z = event.values[2] - gravity[2]

        val sign = if (x * y * z > 0) 1 else -1
        val a: Float = sign * sqrt(x.pow(2) + y.pow(2) + z.pow(2)).round(9)

        if (calibration.isNaN()) calibration = a else {
            currentAcceleration = a
            updateVelocity()
        }
    }

    private fun getAccelerometer(event: SensorEvent) {
        val values = event.values
        // Movement
        val x = values[0]
        val y = values[1]
        val z = values[2]


        val accelationSquareRoot = ((x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH))
        val actualTime = event.timestamp
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return
            }
            lastUpdate = actualTime
        }
    }

    private fun updateVelocity() {
        // Calculate how long this acceleration has been applied.
        val timeNow = Date(System.currentTimeMillis())
        val timeDelta: Long = timeNow.time - (lastUpdatedate?.time ?: 0)
        lastUpdatedate?.time = timeNow.time

        // Calculate the change in velocity at the
        // current acceleration since the last update.
        val deltaVelocity = (appliedAcceleration * (timeDelta / 1000f)).round(9)
        appliedAcceleration = currentAcceleration


        // Add the velocity change to the current velocity.
        velocity += deltaVelocity
        val speed = (velocity.round(9) * 3.6)
        kmph = speed.roundToInt()
    }

    fun getLastSpeed(): Int {
        return abs(kmph)
    }
}