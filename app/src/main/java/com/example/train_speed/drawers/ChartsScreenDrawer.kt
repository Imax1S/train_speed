package com.example.train_speed.drawers

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.ui.theme.Train_speedTheme
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.EmptyPointDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun LineChartDrawer(speedMeasurement: SpeedMeasurement) {
    var i = 0
    val points: List<LineChartData.Point>? =
        speedMeasurement.measurements?.map {
            LineChartData.Point(
                it.toFloatOrNull() ?: 0f,
                i++.toString()
            )
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 100.dp),
    ) {
        Text(text = speedMeasurement.title, fontSize = 20.sp)
        Text(text = speedMeasurement.date.toString(), fontSize = 16.sp)
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LineChart(
                lineChartData = LineChartData(
                    points = points ?: listOf(LineChartData.Point(0f, "")),
                    startAtZero = true
                ),
                // Optional properties.
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                animation = simpleChartAnimation(),
                pointDrawer = EmptyPointDrawer,
                lineDrawer = SolidLineDrawer(color = MaterialTheme.colors.primary),
                xAxisDrawer = SimpleXAxisDrawer(
                    drawLabelEvery = 10,
                    labelTextColor = MaterialTheme.colors.primary,
                    axisLineColor = MaterialTheme.colors.primary
                ),
                yAxisDrawer = SimpleYAxisDrawer(
                    drawLabelEvery = 2,
                    labelTextColor = MaterialTheme.colors.primary,
                    axisLineColor = MaterialTheme.colors.primary,
                    labelValueFormatter = { value -> "${value.toInt()}" }),
                horizontalOffset = 1f,
            )
        }
    }
}