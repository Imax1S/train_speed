package com.example.train_speed

import android.os.CountDownTimer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManualSpeedometer(railLength: Int) {
    val trainSpeed = rememberSaveable { mutableStateOf(0.0) }
    val railsBehind = rememberSaveable { mutableStateOf(0) }
    val isStart = rememberSaveable { mutableStateOf(true) }
    val secondsFromToock = rememberSaveable { mutableStateOf(0) }

    Text(text = "${"%.2f".format(trainSpeed.value)} m / sec")

    Button(
        modifier = Modifier.padding(vertical = 24.dp),
        onClick = {
            if (isStart.value) {
                railsBehind.value = 0
                isStart.value = false

                val timer = object : CountDownTimer((railLength * 1000).toLong(), 400) {
                    override fun onTick(millisUntilFinished: Long) {
                        secondsFromToock.value += 400

                        trainSpeed.value =
                            ((railLength * railsBehind.value) / (secondsFromToock.value / 1000.0))

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
    Text(text = "This is manual")
}