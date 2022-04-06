package com.example.train_speed.modes

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.drawers.ManualSpeedometer
import com.example.train_speed.R
import com.example.train_speed.model.InputData
import com.example.train_speed.model.SpeedMeasurement
import java.util.*
import kotlin.collections.ArrayList

class ManualMode(
    context: Context,
    val inputData: InputData,
    private val onFinish: (SpeedMeasurement) -> Unit
) :
    IMeasureMode {
    private val hintText = context.getString(R.string.manual_hint)
    private var trainSpeed = MutableLiveData("...")
    private var isStart = true
    private var railsBehind = 0
    private var secondsFromToock = 0L
    private var averageSpeed = 0
    private val data: ArrayList<String> = arrayListOf("0")

    companion object {
        const val INTERVAL = 100L
        const val TIMER_LONG = 10000L
    }

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp(onFinish: (SpeedMeasurement) -> Unit) {

    }

    override fun countSpeed(): LiveData<String> {
        return trainSpeed
    }

    private fun measureSpeed() {
        if (isStart) {
            railsBehind = 0
            isStart = false

            val timer = object : CountDownTimer(TIMER_LONG, INTERVAL) {
                override fun onTick(millisUntilFinished: Long) {
                    secondsFromToock += INTERVAL
                    averageSpeed =
                        ((inputData.railLength * railsBehind) / (secondsFromToock / 1000.0)).toInt()
                    trainSpeed.value = "$averageSpeed km/h"
                    data.add(averageSpeed.toString())
                }

                override fun onFinish() {
                    resetTimer()
                }
            }

            timer.start()
        } else {
            railsBehind += 1
        }
    }

    fun resetTimer() {
        val speedMeasurement =
            SpeedMeasurement(
                title = "Manual Measure",
                date = Date(),
                avgSpeed = trainSpeed.value,
                measurements = data.toList()
            )

        Log.d("TAGA", data.toString())

        onFinish(speedMeasurement)

        isStart = true
        trainSpeed.value = "0"
        railsBehind = 0
        secondsFromToock = 0
        data.clear()
    }


    @Composable
    override fun Display() {
        ManualSpeedometer { measureSpeed() }
    }
}