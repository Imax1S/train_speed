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
fun ManualSpeedometer(onButtonClick: () -> Unit, finish: () -> Unit) {

    var isStarted by remember { mutableStateOf(false) }
    Button(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .width(160.dp)
            .height(70.dp),
        onClick = {
            isStarted = true
            onButtonClick.invoke()
        }
    ) {
        Text(
            text = if (isStarted)
                stringResource(R.string.tap)
            else
                stringResource(id = R.string.start_measure)
        )
    }

    if (isStarted) {
        Button(onClick = {
            finish.invoke()
        }) {
            Text(text = stringResource(id = R.string.stop))
        }
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
            Text(text = stringResource(id = R.string.stop))
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

    var isFinished by remember {
        mutableStateOf(false)
    }

    Row {
        Button(
            enabled = !recordIsStarted,
            modifier = Modifier.padding(horizontal = 8.dp),
            onClick = {
                onStartClick.invoke()
                recordIsStarted = true
                isRecording = true
                isFinished = false
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
                isFinished = true
                onStopClick.invoke()
            }) {
            Text(text = stringResource(id = R.string.stop))
        }
    }

    if (isFinished) {
        Text(text = "Speed: ...")
    }
}


@Composable
fun AutoSpeedometer(
    onButtonClick: () -> Unit, finish: () -> Unit
) {
    var isStarted by remember { mutableStateOf(false) }

    if (!isStarted) {
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .width(160.dp)
                .height(70.dp),
            onClick = {
                isStarted = true
                onButtonClick.invoke()
            }
        ) {
            Text(text = stringResource(id = R.string.start_measure))
        }
    } else {
        Button(onClick = {
            finish.invoke()
        }) {
            Text(text = stringResource(id = R.string.stop))
        }
    }
}