package com.example.train_speed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import com.example.train_speed.models.InputData
import com.example.train_speed.ui.theme.Train_speedTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private val speedometerViewModel by viewModels<SpeedometerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startTime = Calendar.getInstance().timeInMillis
        setContent {
            Train_speedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SpeedometerActivityScreen(speedometerViewModel)
                }
            }
        }
    }
}

@Composable
fun SpeedometerActivityScreen(speedometerViewModel: SpeedometerViewModel) {
    val params: State<InputData?> = speedometerViewModel.params.observeAsState()
    SpeedometerScreen(params)
}