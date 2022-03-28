package com.example.train_speed.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.train_speed.database.DatabaseRepository

class DataScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseRepository = DatabaseRepository.get()
    val items = databaseRepository.getSpeedMeasurements()
    val
}