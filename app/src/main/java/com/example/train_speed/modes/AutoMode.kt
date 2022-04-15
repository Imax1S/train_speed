package com.example.train_speed.modes

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.sensors.presenters.AccelerometerSensorPresenter
import com.example.train_speed.R
import com.example.train_speed.drawers.AutoSpeedometer
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.sensors.Accelerometer
import com.example.train_speed.sensors.presenters.GravitySensorPresenter
import com.example.train_speed.sensors.presenters.MicrophoneSensorPresenter
import java.util.*

class AutoMode(
    val context: Context,
    private val onFinish: (SpeedMeasurement) -> Unit,
    inputData: InputData
) : IMeasureMode {
    lateinit var timer: CountDownTimer
    private val hintText = context.getString(R.string.auto_hint)
    private var trainSpeed = MutableLiveData("...")
    private var accelerometer = AccelerometerSensorPresenter(context, onFinish)
    private var gravitySensorPresenter = GravitySensorPresenter(context, inputData, onFinish)
    private var microphoneSensor = MicrophoneSensorPresenter(context, inputData, onFinish)

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        gravitySensorPresenter.setupGravitySensor()
        createTimer()
    }

    override fun countSpeed(): LiveData<String> {
        return trainSpeed
    }

    private fun createTimer() {
        timer = object : CountDownTimer(ManualMode.TIMER_LONG, ManualMode.INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val averageSpeed = arrayListOf(
                    accelerometer.trainSpeed.value ?: 0,
                    gravitySensorPresenter.trainSpeed.value ?: 0,
                    microphoneSensor.trainSpeed.value ?: 0
                ).average()
                trainSpeed.value = "$averageSpeed km/h"
            }

            override fun onFinish() {

            }
        }
    }

    private fun onStart() {
        accelerometer.start()
        gravitySensorPresenter.startMeasure()
        microphoneSensor.startRecording()

        timer.start()
    }

    private fun finish() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Auto Measure",
                date = Date(),
                avgSpeed = trainSpeed.value,
                measurements = listOf()
            )

        trainSpeed.value = "..."
        timer.cancel()
        accelerometer = AccelerometerSensorPresenter(context, onFinish)
        gravitySensorPresenter.resetTimer()
        microphoneSensor.stopRecording()
        onFinish(speedMeasurement)
    }

    @Composable
    override fun Display() {
        AutoSpeedometer({ onStart() }, ::finish)
    }
}