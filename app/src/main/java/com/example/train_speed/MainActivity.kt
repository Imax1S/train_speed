package com.example.train_speed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.train_speed.ui.theme.Train_speedTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startTime = Calendar.getInstance().timeInMillis
        setContent {
            Train_speedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Speedometer(startTime = startTime)
                }
            }
        }
    }
}


@Composable
fun Speedometer(startTime: Long) {

    val totalClicks = rememberSaveable { mutableStateOf(0) }
    val clicksSpeed = rememberSaveable { mutableStateOf(0L) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${clicksSpeed.value} click/sec")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = {
                totalClicks.value += 1
                clicksSpeed.value =
                    totalClicks.value / ((Calendar.getInstance().timeInMillis - startTime) / 1000)
            }
        ) {
            Text(text = "Tap!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val startTime = Calendar.getInstance().timeInMillis
    Train_speedTheme {
        Speedometer(startTime)
    }
}