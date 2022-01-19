package com.example.train_speed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.train_speed.models.InputData


@Composable
fun SpeedometerScreen(params: State<InputData?>, speedometerDisplay: SpeedometerDisplay) {

    val trainSpeed = rememberSaveable { mutableStateOf(0.0) }
    val railLength = rememberSaveable { mutableStateOf(25) }
    val selectedAccelerometer = rememberSaveable { mutableStateOf(false) }
    val padding = 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = speedometerDisplay.selectedMeasureMode.toString())
        Switch(
            checked = selectedAccelerometer.value,
            onCheckedChange = {
                if (speedometerDisplay.selectedMeasureMode == MeasureMode.MANUAL) {
                    speedometerDisplay.selectedMeasureMode = MeasureMode.ACCELEROMETER
                    selectedAccelerometer.value = true
                } else {
                    speedometerDisplay.selectedMeasureMode = MeasureMode.MANUAL
                    selectedAccelerometer.value = false
                }
            })

        Text(text = "Длинна рейльс:")
        TextField(
            value = railLength.value.toString(),
            onValueChange = {
                try {
                    railLength.value = it.toInt()
                    speedometerDisplay.railLength = railLength.value
                } catch (exc: NumberFormatException) {
                    railLength.value = 0
                    speedometerDisplay.railLength = 0
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.wrapContentWidth()
        )
        Spacer(Modifier.size(padding))
        speedometerDisplay.SpeedometerDisplay()
//        if (!selectedAccelerometer.value) {
//            Button(
//                modifier = Modifier.padding(vertical = 24.dp),
//                onClick = {
//                    if (isStart.value) {
//                        railsBehind.value = 0
//                        isStart.value = false
//
//                        val timer = object : CountDownTimer((railLength.value * 1000).toLong(), 400) {
//                            override fun onTick(millisUntilFinished: Long) {
//                                secondsFromToock.value += 400
//
//                                trainSpeed.value =
//                                    ((railLength.value * railsBehind.value) / (secondsFromToock.value / 1000.0))
//
//                                railsBehind.value = 0
//                            }
//
//                            override fun onFinish() {
//                                isStart.value = true
//                            }
//                        }
//                        timer.start()
//                    } else {
//                        secondsFromToock.value = 0
//                        railsBehind.value += 1
//                    }
//                }
//            ) {
//                Text(text = "Tap!")
//            }
//        }
    }
}