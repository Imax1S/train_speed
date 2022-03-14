package com.example.train_speed.measure_modes

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.ManualSpeedometer
import com.example.train_speed.R
import com.example.train_speed.models.InputData

class ManualMode(context: Context, val inputData: InputData) : IMeasureMode {
    private val hintText = context.getString(R.string.manual_hint)
    private var trainSpeed = MutableLiveData("...")
    var railsBehind = 0
    private var isStart = true
    var secondsFromToock = 0L

    companion object {
        const val INTERVAL = 100L
        const val TIMER_LONG = 10000L
    }

    override fun getHintText(): String {
        return hintText
    }

    override fun setUp() {

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

                    trainSpeed.value =
                        ((inputData.railLength * railsBehind) / (secondsFromToock / 1000.0)).toInt()
                            .toString() + " km/h"
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
        isStart = true
        trainSpeed.value = "0"
        railsBehind = 0
        secondsFromToock = 0
    }


    @Composable
    override fun display() {
        ManualSpeedometer { measureSpeed() }
    }
}