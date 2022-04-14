package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.GravitySpeedometer
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.sensors.presenters.GravitySensorPresenter

class GravityMode(
    val context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit
) : IMeasureMode {
    private val hintText = context.getString(R.string.gravity_hint)

    private var gravitySensorPresenter = GravitySensorPresenter(context, inputData, onFinish)

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        gravitySensorPresenter.setupGravitySensor()
    }

    override fun countSpeed(): LiveData<String> {
        return gravitySensorPresenter.trainSpeedText
    }

    @Composable
    override fun Display() {
        GravitySpeedometer(
            { gravitySensorPresenter.startMeasure() },
            { gravitySensorPresenter.resetTimer() })
    }
}