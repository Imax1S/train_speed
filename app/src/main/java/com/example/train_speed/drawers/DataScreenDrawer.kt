package com.example.train_speed.drawers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.view_models.DataScreenViewModel

class DataScreenDrawer(private val dataScreenViewModel: DataScreenViewModel) {
    private val padding = 16.dp

    @Composable
    fun DataScreen() {

        val measurements by dataScreenViewModel.items.observeAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            measurements?.forEach { measurement ->
                MeasurementRow(measurement)
            }
        }
    }

    @Composable
    fun MeasurementRow(measurement: SpeedMeasurement) {
        val date = measurement.date

        Button(
            modifier = Modifier
                .padding(vertical = 8.dp),
            onClick = {}
        ) {
            Column(
            ) {
                Text(text = measurement.title)
                Text(text = "Avr. Speed: ${measurement.avgSpeed}")
                Text(text = "Date: $date")
            }
        }
    }
}