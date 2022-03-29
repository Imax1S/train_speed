package com.example.train_speed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.PermDataSetting
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.train_speed.database.DatabaseRepository
import com.example.train_speed.drawers.DataScreenDrawer
import com.example.train_speed.drawers.ParamsScreenDrawer
import com.example.train_speed.drawers.SpeedometerScreenDrawer
import com.example.train_speed.drawers.ScreensEnum
import com.example.train_speed.model.InputData
import com.example.train_speed.ui.theme.Train_speedTheme
import com.example.train_speed.view_models.DataScreenViewModel
import com.example.train_speed.view_models.SpeedometerScreenDrawerViewModel
import java.util.*

class MainActivity : ComponentActivity() {

    private val speedometerViewModel by viewModels<SpeedometerScreenDrawerViewModel>()
    private val dataScreenViewModel by viewModels<DataScreenViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseRepository.initialize(this)

        val startTime = Calendar.getInstance().timeInMillis
        speedometerViewModel.permissionCheck.requestPermissions(this)
        setContent {
            Train_speedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DrawMainScreen(speedometerViewModel, dataScreenViewModel)
                }
            }
        }
    }
}

@Composable
fun DrawMainScreen(
    speedometerScreenDrawerViewModel: SpeedometerScreenDrawerViewModel,
    dataScreenViewModel: DataScreenViewModel
) {
    val params: State<InputData?> = speedometerScreenDrawerViewModel.params.observeAsState()

    val navController = rememberNavController()
    val bottomItems = ScreensEnum.values()
    val screenDrawer = SpeedometerScreenDrawer(speedometerScreenDrawerViewModel)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigation {
                bottomItems.forEach {
                    BottomNavigationItem(
                        selected = currentRoute == it.name,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(0.4f),
                        onClick = {
                            navController.navigate(it.name)
                        },
                        label = { Text(it.name) },
                        icon = {
                            Icon(
                                when (it) {
                                    ScreensEnum.Params -> Icons.Outlined.PermDataSetting
                                    ScreensEnum.Measure -> Icons.Outlined.Speed
                                    ScreensEnum.Data -> Icons.Outlined.Assessment
                                }, contentDescription = it.name
                            )

                        }
                    )
                }
            }
        }
    ) {
        NavHost(startDestination = ScreensEnum.Measure.name, navController = navController) {
            composable(ScreensEnum.Params.name) { ParamsScreenDrawer().ParamsScreen(params) }
            composable(ScreensEnum.Measure.name) { screenDrawer.SpeedometerScreen(params) }
            composable(ScreensEnum.Data.name) { DataScreenDrawer(dataScreenViewModel).DataScreen() }
        }
    }
}