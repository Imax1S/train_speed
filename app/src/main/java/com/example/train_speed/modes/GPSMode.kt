package com.example.train_speed.modes

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.sensors.GPSListener

class GPSMode(val context: Context) : IMeasureMode {
    private val gpsListener = GPSListener(context)
    private val hintText = context.getString(R.string.gps_hint)

    override fun getHintText(): String {
        return hintText
    }

    @SuppressLint("MissingPermission")
    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        gpsListener.locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1,
            0f,
            gpsListener
        )
    }

    override fun countSpeed(): LiveData<String> {
        return gpsListener.currentSpeed
    }

    @Composable
    override fun Display() {
        TODO("Not yet implemented")
    }
}