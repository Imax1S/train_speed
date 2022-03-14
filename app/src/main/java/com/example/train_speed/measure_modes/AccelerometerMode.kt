package com.example.train_speed.measure_modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.AccSpeedometer
import com.example.train_speed.AccelerometerPresenter
import com.example.train_speed.R

class AccelerometerMode(context: Context) : IMeasureMode {
    private val accelerometer = AccelerometerPresenter(context)
    private val hintText = context.getString(R.string.accelerometer_hint)

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp() {

    }

    override fun countSpeed(): LiveData<String> {
        return accelerometer.currentSpeed
    }

    private fun reset() {

    }

    @Composable
    override fun display() {
        AccSpeedometer({ accelerometer.onButtonClicked() }, { reset() })
    }
}