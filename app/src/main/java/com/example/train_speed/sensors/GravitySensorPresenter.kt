package com.example.train_speed.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.modes.ManualMode
import java.util.*

class GravitySensorPresenter(
    context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit
) {

    private var mSensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private lateinit var timer: CountDownTimer
    val trainSpeed = MutableLiveData("...")
    private var isStart = true
    private var secondsFromToock = 0L
    private var averageSpeed = 0
    private val data: ArrayList<String> = arrayListOf("0")

    private var gravitySensor = GravitySensor()

    fun startMeasure() {
        isStart = false
        timer = object : CountDownTimer(ManualMode.TIMER_LONG, ManualMode.INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                secondsFromToock += ManualMode.INTERVAL
                averageSpeed =
                    ((inputData.railLength * (gravitySensor.tooks.value
                        ?: 2)) / (secondsFromToock / 1000.0)).toInt()
                trainSpeed.value = "$averageSpeed km/h"
                data.add(averageSpeed.toString())
            }

            override fun onFinish() {
                resetTimer()
            }
        }
        timer.start()
    }

    fun resetTimer() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Gravity Measure",
                date = Date(),
                avgSpeed = trainSpeed.value,
                measurements = data.toList()
            )

        onFinish(speedMeasurement)
        gravitySensor = GravitySensor()
        trainSpeed.value = "..."
    }

    fun setupGravitySensor() {
        mSensorManager.registerListener(
            gravitySensor,
            mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_UI
        )
    }
}