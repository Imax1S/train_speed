package com.example.train_speed.drawers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.train_speed.MainActivity
import com.example.train_speed.R
import com.example.train_speed.model.InputData
import com.example.train_speed.ui.theme.Gray
import com.example.train_speed.ui.theme.LightGray
import com.example.train_speed.view_models.SpeedometerScreenDrawerViewModel

class ParamsScreenDrawer(private val speedometerScreenDrawerViewModel: SpeedometerScreenDrawerViewModel) {
    private val padding = 16.dp

    @Composable
    fun ParamsScreen(params: State<InputData?>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            InputParams(params)
        }
    }

    @Composable
    private fun InputParams(params: State<InputData?>) {

        var railLength by rememberSaveable {
            mutableStateOf(params.value?.railLength)
        }

        var distanceBetweenCarriages by rememberSaveable {
            mutableStateOf(params.value?.distanceBetweenCarriages)
        }

        var darkMode by rememberSaveable {
            mutableStateOf(params.value?.darkMode)
        }


        Text(text = stringResource(id = R.string.rail_length))
        TextField(
            value = railLength.toString(),
            onValueChange = {
                railLength = try {
                    it.toInt()
                } catch (exc: NumberFormatException) {
                    0
                }

                params.value?.railLength = railLength ?: 0
                MainActivity.prefs?.savedRailLength = railLength ?: 0
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)
        )

        Text(text = stringResource(id = R.string.distance_between_carriages))
        TextField(
            value = distanceBetweenCarriages.toString(),
            onValueChange = {
                distanceBetweenCarriages = try {
                    it.toDouble()
                } catch (exc: NumberFormatException) {
                    0.0
                }

                params.value?.distanceBetweenCarriages = distanceBetweenCarriages ?: 0.0
                MainActivity.prefs?.savedDistanceBetweenCarriages = distanceBetweenCarriages ?: 0.0
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)
        )

        Text(text = if (darkMode == true) "Dark Theme" else "Light Theme")
        Switch(checked = darkMode ?: false, colors = SwitchDefaults.colors(
            checkedThumbColor = LightGray,
            checkedTrackColor = LightGray,
            uncheckedThumbColor = Gray,
            uncheckedTrackColor = Gray
        ), onCheckedChange = {
            darkMode = !(darkMode ?: false)
            speedometerScreenDrawerViewModel.onThemeChanged(darkMode ?: false)
            MainActivity.prefs?.savedDarkMode = darkMode ?: false
        })
    }
}