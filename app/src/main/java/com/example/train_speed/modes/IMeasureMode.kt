package com.example.train_speed.modes

import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.model.SpeedMeasurement

interface IMeasureMode {
    fun getHintText() : String
    fun setUp(onFinish: (SpeedMeasurement) -> Unit)
    fun countSpeed() : LiveData<String>

    @Composable
    fun Display()
}