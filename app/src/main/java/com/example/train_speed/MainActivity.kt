package com.example.train_speed

import android.content.Context
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
import androidx.navigation.compose.*
import com.example.train_speed.database.DatabaseRepository
import com.example.train_speed.drawers.*
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import com.example.train_speed.ui.theme.Train_speedTheme
import com.example.train_speed.utils.Prefs
import com.example.train_speed.view_models.DataScreenViewModel
import com.example.train_speed.view_models.SpeedometerScreenDrawerViewModel


val prefs: Prefs by lazy {
    MainActivity.prefs!!
}

class MainActivity : ComponentActivity() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: MainActivity
            private set
    }

    private val speedometerViewModel by viewModels<SpeedometerScreenDrawerViewModel>()
    private val dataScreenViewModel by viewModels<DataScreenViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseRepository.initialize(this)
        instance = this
        prefs = Prefs(applicationContext)

        speedometerViewModel.permissionCheck.requestPermissions(this)
        setContent {
            Train_speedTheme {
                Surface(color = MaterialTheme.colors.background) {
                    DrawMainScreen(speedometerViewModel, dataScreenViewModel, applicationContext)
                }
            }
        }
    }
}

@Composable
fun DrawMainScreen(
    speedometerScreenDrawerViewModel: SpeedometerScreenDrawerViewModel,
    dataScreenViewModel: DataScreenViewModel,
    applicationContext: Context
) {
    val params: State<InputData?> = speedometerScreenDrawerViewModel.params.observeAsState()

    val navController = rememberNavController()
    val bottomItems = TabScreensEnum.values()
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
                                    TabScreensEnum.Params -> Icons.Outlined.PermDataSetting
                                    TabScreensEnum.Measure -> Icons.Outlined.Speed
                                    TabScreensEnum.Data -> Icons.Outlined.Assessment
                                }, contentDescription = it.name
                            )

                        }
                    )
                }
            }
        }
    ) {
        NavHost(startDestination = TabScreensEnum.Measure.name, navController = navController) {
            composable(TabScreensEnum.Params.name) { ParamsScreenDrawer().ParamsScreen(params) }
            composable(TabScreensEnum.Measure.name) { screenDrawer.SpeedometerScreen(params) }
            composable(TabScreensEnum.Data.name) {
                DataScreenDrawer(
                    navController,
                    dataScreenViewModel,
                    applicationContext
                ).DataScreen()
            }
            composable("chart_screen") {
                navController.previousBackStackEntry?.arguments?.getParcelable<SpeedMeasurement>("CHART_KEY")
                    ?.let {
                        LineChartDrawer(it)
                    }
            }
        }
    }
}