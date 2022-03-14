package com.example.train_speed.measure_modes

import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData

interface IMeasureMode {
    fun getHintText() : String
    fun setUp()
    fun countSpeed() : LiveData<String>

    @Composable
    fun display()
}