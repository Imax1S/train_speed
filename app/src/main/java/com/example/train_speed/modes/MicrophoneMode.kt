package com.example.train_speed.modes

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.MicroSpeedometer
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import java.io.IOException
import java.util.*

class MicrophoneMode(
    private val context: Context,
    val inputData: InputData,
    val onFinish: (SpeedMeasurement) -> Unit,
) : IMeasureMode {
    private val hintText = context.getString(R.string.microphone_hint)
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false
    private var recordingStopped: Boolean = false
    private var trainSpeed = MutableLiveData("...")
    private var railsBehind = 0
    private var secondsFromToock = 0L
    private var averageSpeed = 0
    private val data: ArrayList<String> = arrayListOf("0")
    private lateinit var timer: CountDownTimer


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
        return trainSpeed
    }

    private fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            isRecording = true
            startTimer()
            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkForTook() {
        if (mediaRecorder?.maxAmplitude ?: 0 > 5000) {
            railsBehind++
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(ManualMode.TIMER_LONG, ManualMode.INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                checkForTook()

                secondsFromToock += ManualMode.INTERVAL
                averageSpeed =
                    ((inputData.railLength * railsBehind) / (secondsFromToock / 1000.0)).toInt()
                data.add(averageSpeed.toString())
                Log.d("TAGA", "count: ${trainSpeed.value}")

                trainSpeed.value = "$averageSpeed km/h"
            }

            override fun onFinish() {

            }
        }
        timer.start()
    }


    private fun stopRecording() {
        if (isRecording) {
            timer.cancel()
            mediaRecorder?.stop()
            mediaRecorder?.release()
            isRecording = false
            saveMeasure()
            Toast.makeText(context, "Recording stopped!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveMeasure() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Microphone Measure",
                date = Date(),
                avgSpeed = trainSpeed.value,
                measurements = data.toList()
            )

        onFinish(speedMeasurement)
    }

    private fun pauseRecording() {
        if (isRecording) {
            if (!recordingStopped) {
                Toast.makeText(context, "Paused!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                recordingStopped = true
                timer.cancel()
            } else {
                resumeRecording()
            }
        }
    }

    private fun resumeRecording() {
        Toast.makeText(context, "Resumed!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        recordingStopped = false
        timer.start()
    }

    @Composable
    override fun Display() {
        MicroSpeedometer(
            { startRecording() },
            { pauseRecording() },
            { stopRecording() }
        )
    }
}