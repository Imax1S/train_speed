package com.example.train_speed.view_models

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.MainActivity
import com.example.train_speed.MeasureMode
import com.example.train_speed.database.DatabaseRepository
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.modes.*
import com.example.train_speed.permission.PermissionCheck
import com.example.train_speed.utils.Prefs

class SpeedometerScreenDrawerViewModel(application: Application) : AndroidViewModel(application) {
    private var _params = MutableLiveData(
        InputData(
            MainActivity.prefs?.savedRailLength ?: 25,
            MainActivity.prefs?.savedDistanceBetweenCarriages ?: 1.5
        )
    )

    private val databaseRepository = DatabaseRepository.get()

    val params: LiveData<InputData> = _params
    private var measureMode: IMeasureMode =
        ManualMode(
            application.applicationContext,
            params.value ?: InputData(25),
            ::saveMeasurement
        )

    var trainSpeed = getSpeed()
    var selectedMeasureMode: MeasureMode = MeasureMode.MANUAL
    val permissionCheck = PermissionCheck()

    fun changeMode(newMode: MeasureMode, context: Context) {
        when (newMode) {
            MeasureMode.MANUAL -> {
                selectedMeasureMode = MeasureMode.MANUAL
                measureMode = ManualMode(context, params.value ?: return, ::saveMeasurement)
                trainSpeed = getSpeed()
            }
            MeasureMode.ACCELEROMETER -> {
                selectedMeasureMode = MeasureMode.ACCELEROMETER
                measureMode = AccelerometerMode(context, ::saveMeasurement)
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
                measureMode = GravityMode(
                    context,
                    params.value ?: return,
                    ::saveMeasurement
                )
                measureMode.setUp {}
                trainSpeed = getSpeed()

            }
            MeasureMode.AUTO -> {
                selectedMeasureMode = MeasureMode.AUTO
                measureMode = AutoMode(context, ::saveMeasurement)
                trainSpeed = getSpeed()
            }
            MeasureMode.GPS -> {
                selectedMeasureMode = MeasureMode.GPS
                measureMode = GPSMode(context)
                measureMode.setUp {}
                trainSpeed = getSpeed()
            }
        }
    }

    fun getHintText(): String {
        return measureMode.getHintText()
    }

    @Composable
    fun DrawMode() {
        measureMode.Display()
    }

    private fun getSpeed(): LiveData<String> {
        return measureMode.countSpeed()
    }

    private fun saveMeasurement(measurement: SpeedMeasurement) {
        Log.d("TAGA", "save measure")
        databaseRepository.addMeasurement(measurement)
    }
}