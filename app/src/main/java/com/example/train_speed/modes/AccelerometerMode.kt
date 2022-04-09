package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.drawers.AccSpeedometer
import com.example.train_speed.AccelerometerPresenter
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement
import java.util.*

class AccelerometerMode(val context: Context, val onFinish: (SpeedMeasurement) -> Unit) : IMeasureMode {
    private var accelerometer = AccelerometerPresenter(context, onFinish)
    private val hintText = context.getString(R.string.accelerometer_hint)

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
    }


    override fun countSpeed(): LiveData<String> {
        return accelerometer.currentSpeed
    }

    private fun finish() {
        val newMeasurement = SpeedMeasurement(
            title = "Accelerometer Measure",
            date = Date(),
            avgSpeed = accelerometer.currentSpeed.value
        )
        onFinish(newMeasurement)

        accelerometer.currentSpeed.value = "..."
        accelerometer = AccelerometerPresenter(context, onFinish)
    }

    @Composable
    override fun Display() {
        AccSpeedometer({ accelerometer.onButtonClicked() }, { finish() })
    }
}