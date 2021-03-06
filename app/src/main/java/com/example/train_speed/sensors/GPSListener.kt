package com.example.train_speed.sensors

import android.content.Context
import android.location.*
import android.os.Bundle
import androidx.lifecycle.MutableLiveData

class GPSListener(context: Context) : LocationListener {
    var currentSpeed = MutableLiveData("Calibrating...")
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override fun onLocationChanged(location: Location) {
        currentSpeed.value = "${(location.speed * 3.6).toInt()} km/h"
    }
}