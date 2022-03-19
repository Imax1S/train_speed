package com.example.train_speed.measure_modes

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.R
import com.example.train_speed.drawers.MicroSpeedometer
import java.io.IOException

class MicrophoneMode(private val context: Context) : IMeasureMode {
    private val hintText = context.getString(R.string.accelerometer_hint)
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording: Boolean = false
    private var recordingStopped: Boolean = false
    private var trainSpeed = MutableLiveData("...")


    init {
        output = context.getExternalFilesDir(null)?.absolutePath + "/recording.mp3"
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

    override fun setUp() {
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
            Toast.makeText(context, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            isRecording = false
            Toast.makeText(context, "Recording stopped!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pauseRecording() {
        if (isRecording) {
            if (!recordingStopped) {
                Toast.makeText(context, "Paused!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                recordingStopped = true
            } else {
                resumeRecording()
            }
        }
    }

    private fun resumeRecording() {
        Toast.makeText(context, "Resumed!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        recordingStopped = false
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