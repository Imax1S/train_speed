package com.example.train_speed.drawers

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.view_models.DataScreenViewModel

class DataScreenDrawer(private val dataScreenViewModel: DataScreenViewModel) {

    @Composable
    fun DataScreen() {

        val measurements by dataScreenViewModel.items.observeAsState()
        Column {
            measurements?.forEach { measurement ->
                MeasurementRow(measurement)
            }
        }
    }

    @Composable
    fun MeasurementRow(measurement: SpeedMeasurement) {
        Card {
            Column {
                Text(text = measurement.title)
                Text(text = "Avr. Speed: ${measurement.avgSpeed}")
                Text(text = measurement.date.toString())
            }
        }
    }
}