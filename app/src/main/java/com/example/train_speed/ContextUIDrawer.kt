package com.example.train_speed

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ManualSpeedometer(onButtonClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(vertical = 24.dp),
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
        Text(text = "Start measure!")
    }

    if (!enabled) {
        Button(
            modifier = Modifier.padding(bottom = 16.dp),
            onClick = {
                enabled = true
            }
        ) {
            Text(text = "Reset")
        }
    }
}