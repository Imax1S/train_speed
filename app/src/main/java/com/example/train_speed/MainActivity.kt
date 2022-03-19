package com.example.train_speed

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.ContextCompat
import com.example.train_speed.drawers.ScreenDrawer
import com.example.train_speed.models.InputData
import com.example.train_speed.ui.theme.Train_speedTheme
import java.util.*

class MainActivity : ComponentActivity() {

    private val speedometerViewModel by viewModels<ScreenDrawerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startTime = Calendar.getInstance().timeInMillis
        speedometerViewModel.permissionCheck.requestPermissions(this)
        setContent {
            Train_speedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DrawMainScreen(speedometerViewModel)
                }
            }
        }
    }
}

@Composable
fun DrawMainScreen(
    screenDrawerViewModel: ScreenDrawerViewModel
) {
    val params: State<InputData?> = screenDrawerViewModel.params.observeAsState()
    val screenDrawer = ScreenDrawer(screenDrawerViewModel)
    screenDrawer.SpeedometerScreen(params)
}