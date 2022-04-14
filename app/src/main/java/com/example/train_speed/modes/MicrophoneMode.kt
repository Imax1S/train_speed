package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.MicroSpeedometer
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.sensors.presenters.MicrophoneSensorPresenter

class MicrophoneMode(
    context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit,
) : IMeasureMode {
    private val hintText = context.getString(R.string.microphone_hint)

    private val microphoneSensor = MicrophoneSensorPresenter(context, inputData, ::saveMeasure)

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
//        TODO("Not yet implemented")
    }

    override fun countSpeed(): LiveData<String> {
        return microphoneSensor.trainSpeedText
    }

    private fun saveMeasure(speedMeasurement: SpeedMeasurement) {
        onFinish(speedMeasurement)
    }

    @Composable
    override fun Display() {
        MicroSpeedometer(
            { microphoneSensor.startRecording() },
            { microphoneSensor.pauseRecording() },
            { microphoneSensor.stopRecording() }
        )
    }
}