package com.example.train_speed.modes

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.MicroSpeedometer
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.sensors.MicrophoneSensor
import java.util.*

class MicrophoneMode(
    context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit,
) : IMeasureMode {
    private val hintText = context.getString(R.string.microphone_hint)

    private val microphoneSensor = MicrophoneSensor(context, inputData) { saveMeasure() }

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null

    init {
        val date = Date().toString()
        output = context.getExternalFilesDir(null)?.absolutePath + "/${date}_recording.mp3"
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)
    }

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
//        TODO("Not yet implemented")
    }

    override fun countSpeed(): LiveData<String> {
        return microphoneSensor.trainSpeed
    }

    private fun saveMeasure() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Microphone Measure",
                date = Date(),
                avgSpeed = microphoneSensor.trainSpeed.value,
                measurements = microphoneSensor.data.toList()
            )

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