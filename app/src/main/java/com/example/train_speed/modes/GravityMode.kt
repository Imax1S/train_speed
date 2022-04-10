package com.example.train_speed.modes

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import com.example.train_speed.R
import com.example.train_speed.model.SpeedMeasurement

class GravityMode(context: Context) : IMeasureMode {
    private val hintText = context.getString(R.string.gravity_hint)


    override fun getHintText(): String {
       return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun countSpeed(): LiveData<String> {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Display() {
        TODO("Not yet implemented")
    }
}