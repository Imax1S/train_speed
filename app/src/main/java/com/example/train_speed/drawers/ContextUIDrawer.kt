package com.example.train_speed.drawers

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement

@Composable
fun ManualSpeedometer(onButtonClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .width(120.dp)
            .height(70.dp),
        onClick = {
            onButtonClick.invoke()
        }
    ) {
        Text(text = "Tap!")
    }
}

@Composable
fun AccSpeedometer(onStartClick: () -> Unit, onResetClicked: () -> Unit) {
    var enabled by remember { mutableStateOf(true) }
    Button(
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
        enabled = enabled,
        onClick = {
            enabled = false
            onStartClick.invoke()
        }
    ) {
        Text(text = stringResource(id = R.string.start_measure))
    }

    if (!enabled) {
        Button(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                enabled = true
                onResetClicked()
            }
        ) {
            Text(text = stringResource(id = R.string.reset))
        }
    }
}

@Composable
fun MicroSpeedometer(
    onStartClick: () -> Unit,
    onPauseResumeClick: () -> Unit,
    onStopClick: () -> Unit
) {
    var recordIsStarted by remember {
        mutableStateOf(false)
    }
    var isRecording by remember { mutableStateOf(false) }
    var resumePauseText by remember {
        mutableStateOf(
            "Pause"
        )
    }

    Row {
        Button(
            enabled = !recordIsStarted,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                onStartClick.invoke()
                recordIsStarted = true
                isRecording = true
            }
        ) {
            Text(text = stringResource(id = R.string.micro_start))
        }
        Button(
            enabled = recordIsStarted,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                onPauseResumeClick.invoke()
                resumePauseText = if (!isRecording) {
                    "Pause"
                } else {
                    "Resume"
                }
                isRecording = !isRecording
            }) {
            Text(text = resumePauseText)
        }
        Button(
            enabled = recordIsStarted,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                recordIsStarted = false
                isRecording = false
                onStopClick.invoke()
            }) {
            Text(text = stringResource(id = R.string.stop))
        }
    }
}