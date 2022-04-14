package com.example.train_speed.sensors.presenters

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.modes.ManualMode
import com.example.train_speed.sensors.GravitySensor
import java.util.*

class GravitySensorPresenter(
    context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit
) {

    private var mSensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private lateinit var timer: CountDownTimer

    val trainSpeedText = MutableLiveData("...")
    val trainSpeed = MutableLiveData(0)

    private var isStart = true
    private var secondsFromToock = 0L
    private val data: ArrayList<String> = arrayListOf("0")

    private var gravitySensor = GravitySensor()

    fun startMeasure() {
        isStart = false
        timer = object : CountDownTimer(ManualMode.TIMER_LONG, ManualMode.INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                secondsFromToock += ManualMode.INTERVAL
                trainSpeed.value =
                    ((inputData.railLength * (gravitySensor.tooks.value
                        ?: 2)) / (secondsFromToock / 1000.0)).toInt()
                trainSpeedText.value = "${trainSpeed.value} km/h"
                data.add(trainSpeed.value.toString())
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
                avgSpeed = trainSpeedText.value,
                measurements = data.toList()
            )

        onFinish(speedMeasurement)
        gravitySensor = GravitySensor()
        trainSpeedText.value = "..."
    }

    fun setupGravitySensor() {
        mSensorManager.registerListener(
            gravitySensor,
            mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
            SensorManager.SENSOR_DELAY_UI
        )
    }
}