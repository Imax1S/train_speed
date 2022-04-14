package com.example.train_speed.drawers

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.utils.ExportMeasurement
import com.example.train_speed.utils.navigate
import com.example.train_speed.view_models.DataScreenViewModel
import java.text.SimpleDateFormat
import java.util.*

class DataScreenDrawer(
    private val navigationController: NavController,
    private val dataScreenViewModel: DataScreenViewModel,
    context: Context
) {
    private val padding = 16.dp
    val exportMeasurement = ExportMeasurement(context)


    @Composable
    fun DataScreen() {

        val measurements by dataScreenViewModel.items.observeAsState()

        if (measurements?.size ?: 0 > 0) {
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
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 66.dp, top = padding, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.empty_data_screen))
            }
        }

    }

    @Composable
    fun MeasurementRow(measurement: SpeedMeasurement) {
        val date = measurement.date

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 8.dp)
                    .width(342.dp),
                onClick = {
                    navigationController.navigate(
                        "chart_screen",
                        bundleOf("CHART_KEY" to measurement)
                    )
                }
            ) {
                Box(Modifier.fillMaxSize()) {
                    Text(
                        text = "${measurement.title} \n" +
                                "Avr. Speed: ${measurement.avgSpeed} \n" +
                                "Date: ${
                                    SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm:ss",
                                        Locale.getDefault(Locale.Category.FORMAT)
                                    ).format(date)
                                }"
                    )
                }
            }

            Icon(Icons.Outlined.FileUpload,
                "file upload",
                Modifier
                    .clickable {
                        exportMeasurement.exportMeasurement(measurement)
                    })
        }

    }
}