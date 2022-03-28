package com.example.train_speed.view_models

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.MeasureMode
import com.example.train_speed.measure_modes.AccelerometerMode
import com.example.train_speed.measure_modes.IMeasureMode
import com.example.train_speed.measure_modes.ManualMode
import com.example.train_speed.measure_modes.MicrophoneMode
import com.example.train_speed.models.InputData
import com.example.train_speed.permission.PermissionCheck

class SpeedometerScreenDrawerViewModel(application: Application) : AndroidViewModel(application) {
    private var _params = MutableLiveData(InputData(25)) //TODO add room

    // state
    val params: LiveData<InputData> = _params
    private var measureMode: IMeasureMode =
        ManualMode(application.applicationContext, params.value ?: InputData(25))
    var trainSpeed = getSpeed()
    private var selectedMeasureMode: MeasureMode = MeasureMode.MANUAL
    val permissionCheck = PermissionCheck()

    fun changeMode(newMode: MeasureMode, context: Context) {
        when (newMode) {
            MeasureMode.MANUAL -> {
                selectedMeasureMode = MeasureMode.MANUAL
                measureMode = ManualMode(context, params.value ?: return)
                trainSpeed = getSpeed()
            }
            MeasureMode.ACCELEROMETER -> {
                selectedMeasureMode = MeasureMode.ACCELEROMETER
                measureMode = AccelerometerMode(context)
                trainSpeed = getSpeed()
            }
            MeasureMode.MICROPHONE -> {
                if (permissionCheck.permissionGranted) {
                    selectedMeasureMode = MeasureMode.MICROPHONE
                    measureMode = MicrophoneMode(context)
                    trainSpeed = getSpeed()
                } else {
                    permissionCheck.requestPermissions(context)
                }
            }
            MeasureMode.GRAVITY -> {
                selectedMeasureMode = MeasureMode.GRAVITY

            }
            MeasureMode.AUTO -> {
                selectedMeasureMode = MeasureMode.AUTO
            }
        }
        startMeasure()
    }

    fun getHintText(): String {
        return measureMode.getHintText()
    }

    @Composable
    fun DrawMode() {
        measureMode.Display()
    }

    private fun startMeasure() {
        measureMode.setUp()
    }

    private fun getSpeed(): LiveData<String> {
        return measureMode.countSpeed()
    }
}