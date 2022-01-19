package com.example.train_speed

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable


class SpeedometerDisplay {
    var railLength = 25
    var selectedMeasureMode = MeasureMode.MANUAL



    @Composable
    fun SpeedometerDisplay() {
        Card {
            Column {
                when (selectedMeasureMode) {
                    MeasureMode.MANUAL -> ManualDisplay()
                    MeasureMode.ACCELEROMETER -> AccelerometerDisplay()
                }
            }
        }
    }

    @Composable
    fun ManualDisplay() {
        ManualSpeedometer(railLength)
    }

    @Composable
    fun AccelerometerDisplay() {
        AccelerometerSpeedometer().ShowSpeed()
    }
}