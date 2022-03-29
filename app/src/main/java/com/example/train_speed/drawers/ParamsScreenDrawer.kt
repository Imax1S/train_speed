package com.example.train_speed.drawers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.train_speed.R
import com.example.train_speed.model.InputData

class ParamsScreenDrawer {
    private val padding = 16.dp

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
}