package com.example.train_speed.drawers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.train_speed.MeasureMode
import com.example.train_speed.R
import com.example.train_speed.ScreenDrawerViewModel
import com.example.train_speed.models.InputData

class ScreenDrawer(private val screenDrawerViewModel: ScreenDrawerViewModel) {
    private val padding = 16.dp

    //Main screen drawer
    @Composable
    fun SpeedometerScreen(params: State<InputData?>) {
        val railLength = rememberSaveable { mutableStateOf(params.value?.railLength ?: 0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            //Dropdown menu of measure modes
            ModesDropDownMenu()
        }
    }

    @Composable
    fun ParamsScreen(params: State<InputData?>) {
        val railLength = rememberSaveable { mutableStateOf(params.value?.railLength ?: 0) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            InputParams(railLength)
        }
    }

    @Composable
    fun DataScreen() {

    }

    @Composable
    private fun SpeedometerCard(selectedMode: MeasureMode) {
        val padding = 16.dp
        val trainSpeedLiveData = screenDrawerViewModel.trainSpeed

        val trainSpeed =
            trainSpeedLiveData.observeAsState()

        Card(
            shape = RoundedCornerShape(14.dp),
            elevation = 10.dp,
            modifier = Modifier
                .padding(padding)
                .width(180.dp)
                .height(100.dp)
        ) {
            Box(Modifier.wrapContentSize(Alignment.Center)) {
                Text(
                    text = "${trainSpeed.value}",
                    fontSize = 30.sp,
                )
            }
        }
    }

    @Composable
    private fun InputParams(railLength: MutableState<Int>) {

        Text(text = stringResource(id = R.string.rail_length))
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
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)
        )
    }

    @Composable
    fun ModesDropDownMenu() {
        val padding = 8.dp
        var expandedMenuView by remember { mutableStateOf(false) }
        var showHint by remember { mutableStateOf(false) }
        var selectedMode by rememberSaveable {
            mutableStateOf(MeasureMode.MANUAL)
        }
        val context = LocalContext.current

        //Speedometer card
        SpeedometerCard(selectedMode)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                modifier = Modifier
                    .padding(padding)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
                ) {
                    Text(text = "Selected mode: ${selectedMode.name}", Modifier
                        .clickable {
                            expandedMenuView = true
                        }
                        .padding(8.dp)
                    )
                    DropdownMenu(
                        expanded = expandedMenuView,
                        onDismissRequest = { expandedMenuView = false },
                    ) {

                        MeasureMode.values().forEach { mode ->
                            DropdownMenuItem(
                                onClick = {
                                    screenDrawerViewModel.changeMode(mode, context)
                                    selectedMode = mode
                                    expandedMenuView = false
                                }) {
                                Text(text = "$mode")
                            }
                        }
                    }
                }
            }

            Box {
                Icon(Icons.Outlined.Info, "question", Modifier.clickable {
                    showHint = true
                })

                //hint
                DropdownMenu(
                    expanded = showHint,
                    onDismissRequest = { showHint = false },
                ) {
                    Text(
                        modifier = Modifier
                            .width(160.dp)
                            .padding(8.dp),
                        text = screenDrawerViewModel.getHintText(),
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

        }

        when (selectedMode) {
            MeasureMode.MANUAL -> screenDrawerViewModel.DrawMode()
            MeasureMode.ACCELEROMETER -> screenDrawerViewModel.DrawMode()
            MeasureMode.MICROPHONE -> screenDrawerViewModel.DrawMode()
            else -> {}
        }
    }
}