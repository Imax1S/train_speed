package com.example.train_speed

import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
fun SpeedometerScreen(params: State<InputData?>) {

    val trainSpeed = rememberSaveable { mutableStateOf(0.0) }
    val railLength = rememberSaveable { mutableStateOf(25) }
    val railsBehind = rememberSaveable { mutableStateOf(0) }
    val isStart = rememberSaveable { mutableStateOf(true) }
    val secondsFromToock = rememberSaveable { mutableStateOf(0) }
    val isGravitySensorOn = rememberSaveable { mutableStateOf(false) }
    val selectedMode = rememberSaveable { mutableStateOf("Manual") }

    val padding = 16.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = selectedMode.value)
        Switch(
            checked = isGravitySensorOn.value,
            onCheckedChange = { isGravitySensorOn.value = !isGravitySensorOn.value })
        Text(text = "Длинна рейльс:")
        TextField(
            value = railLength.value.toString(),
            onValueChange = {
                try {
                    railLength.value = it.toInt()
                } catch (exc: NumberFormatException) {
                    railLength.value = 0
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.wrapContentWidth()
        )
        Spacer(Modifier.size(padding))
        Text(text = "${"%.2f".format(trainSpeed.value).toDouble()} m / sec")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = {
                if (isStart.value) {
                    railsBehind.value = 0
                    isStart.value = false

                    val timer = object : CountDownTimer((railLength.value * 1000).toLong(), 400) {
                        override fun onTick(millisUntilFinished: Long) {
                            secondsFromToock.value += 400

                            trainSpeed.value =
                                ((railLength.value * railsBehind.value) / (secondsFromToock.value / 1000.0))

                            railsBehind.value = 0
                        }

                        override fun onFinish() {
                            isStart.value = true
                        }
                    }
                    timer.start()
                } else {
                    secondsFromToock.value = 0
                    railsBehind.value += 1
                }
            }
        ) {
            Text(text = "Tap!")
        }
    }
}