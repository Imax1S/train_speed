package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.AutoSpeedometer
import com.example.train_speed.model.SpeedMeasurement
import java.util.*

class AutoMode(context: Context, private val onFinish: (SpeedMeasurement) -> Unit) : IMeasureMode {
    private val hintText = context.getString(R.string.auto_hint)
    private var trainSpeed = MutableLiveData("...")

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun countSpeed(): LiveData<String> {
       return trainSpeed
    }

    private fun stop() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Manual Measure",
                date = Date(),
                avgSpeed = trainSpeed.value,
                measurements = listOf()
            )

        onFinish(speedMeasurement)
    }

    @Composable
    override fun Display() {
        AutoSpeedometer({}, ::stop)
    }
}