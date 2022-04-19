package com.example.train_speed.view_models

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import com.example.train_speed.database.DatabaseRepository
import com.example.train_speed.model.SpeedMeasurement

class DataScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseRepository = DatabaseRepository.get()
    val items = databaseRepository.getSpeedMeasurements()

    fun deleteMeasurement(speedMeasurement: SpeedMeasurement) {
        databaseRepository.deleteMeasurement(speedMeasurement)
    }
}