package com.example.train_speed.drawers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.utils.navigate
import com.example.train_speed.view_models.DataScreenViewModel

class DataScreenDrawer(
    val navigationController: NavController,
    private val dataScreenViewModel: DataScreenViewModel
) {
    private val padding = 16.dp

    @Composable
    fun DataScreen() {

        val measurements by dataScreenViewModel.items.observeAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp, top = padding, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            items(measurements ?: listOf()) { measurement ->
                MeasurementRow(measurement)
            }
        }
    }

    @Composable
    fun MeasurementRow(measurement: SpeedMeasurement) {
        val date = measurement.date

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(end = 8.dp)
                    .width(350.dp),
                onClick = {
                    navigationController.navigate(
                        "chart_screen",
                        bundleOf("CHART_KEY" to measurement)
                    )
                }
            ) {
                Column(
                ) {
                    Text(text = measurement.title)
                    Text(text = "Avr. Speed: ${measurement.avgSpeed}")
                    Text(text = "Date: $date")
                }
            }

            Icon(Icons.Outlined.FileUpload,
                "file upload",
                Modifier
                    .clickable {
                //TODO export as csv
            })
        }

    }
}