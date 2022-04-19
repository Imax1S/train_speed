package com.example.train_speed.drawers

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.ui.theme.DeleteColor
import com.example.train_speed.ui.theme.Gray
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
    private val exportMeasurement = ExportMeasurement(context)


    @OptIn(ExperimentalMaterialApi::class)
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

                itemsIndexed(items = measurements?.toList() ?: listOf(), key = { _, listItem ->
                    listItem.hashCode()
                }) { index, measurement ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                dataScreenViewModel.deleteMeasurement(measurement)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = state, background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.EndToStart -> DeleteColor
                                DismissDirection.StartToEnd -> Gray
                                null -> Color.Magenta
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = color)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colors.secondary,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            SampleItem(measurement = measurement)
                        },
                        directions = setOf(DismissDirection.EndToStart)
                    )
                    Spacer(Modifier.padding(vertical = 8.dp))
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SampleItem(measurement: SpeedMeasurement) {
        val date = measurement.date
        ListItem(
            text = {
                Text(
                    text = measurement.title,
                    color = MaterialTheme.colors.secondary
                )
            },
            secondaryText = {
                Text(
                    text = "Avr. Speed: ${measurement.avgSpeed} \n" +
                            "Date: ${
                                date?.let {
                                    SimpleDateFormat(
                                        "dd/MM/yyyy HH:mm:ss",
                                        Locale.getDefault(Locale.Category.FORMAT)
                                    ).format(it)
                                }
                            }",
                    color = MaterialTheme.colors.secondary
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .padding(vertical = 8.dp)
                .clickable {
                    navigationController.navigate(
                        "chart_screen",
                        bundleOf("CHART_KEY" to measurement)
                    )
                },

            trailing = {
                Icon(
                    imageVector = Icons.Outlined.FileUpload,
                    "file upload",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .clickable {
                            exportMeasurement.exportMeasurement(measurement)
                        })
            }
        )
    }
}