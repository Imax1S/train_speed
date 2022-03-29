package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.drawers.AccSpeedometer
import com.example.train_speed.AccelerometerPresenter
import com.example.train_speed.R
import com.example.train_speed.database.DatabaseRepository
import com.example.train_speed.model.SpeedMeasurement
import java.util.*

class AccelerometerMode(context: Context) : IMeasureMode {
    private val accelerometer = AccelerometerPresenter(context)
    private val hintText = context.getString(R.string.accelerometer_hint)
    private val databaseRepository = DatabaseRepository.get()
    private var onFinish: (SpeedMeasurement) -> Unit = {}

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        this.onFinish = onFinish
    }


    override fun countSpeed(): LiveData<String> {
        return accelerometer.currentSpeed
    }

    private fun reset() {
        val newMeasurement = SpeedMeasurement(
            title = "Accelerometer Measure",
            date = Date(),
            avgSpeed = accelerometer.currentSpeed.value
        )
        onFinish(newMeasurement)
    }

    @Composable
    override fun Display() {
        AccSpeedometer({ accelerometer.onButtonClicked() }, { reset() })
    }
}