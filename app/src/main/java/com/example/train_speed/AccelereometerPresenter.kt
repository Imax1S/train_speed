package com.example.train_speed

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.train_speed.models.MeasureData
import com.example.train_speed.sensors.Calibrator
import com.example.train_speed.sensors.XYZAccelerometer
import java.util.*


class AccelerometerPresenter(context: Context) {
    companion object {

        //status of accelerometer
        const val IN_PROGRESS = 0
        const val TIMER_DONE = 2
        const val START = 3
        const val CAL_TIMER_DONE = 4
        const val ERROR = 5

        private const val UPDATE_INTERVAL: Long = 400
        private const val MEASURE_TIMES: Long = 60
    }

    var currentSpeed = MutableLiveData("...")

    private var xyzAcc: XYZAccelerometer? = null
    private var mSensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var timer: Timer? = null
    private var counter = 0
    private var mdXYZ: MeasureData? = null

    init {
        setAccelerometer()
    }

    fun onButtonClicked() {
        mdXYZ = MeasureData(UPDATE_INTERVAL)
        currentSpeed.value = "Calibrating..."
        counter = 0
        val cal = Calibrator(hRefresh, xyzAcc, START)
        cal.calibrate()
    }

    @SuppressLint("HandlerLeak")
    inner class Refresh : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TIMER_DONE -> {
                    onMeasureDone()
                    val es1 = mdXYZ?.getLastSpeedKm() ?: 1 * 100 / 100f
                    currentSpeed.value = "$es1\n  km/h"
                }
                START -> {
                    currentSpeed.value = "START"
                    timer = Timer()
                    timer!!.scheduleAtFixedRate(
                        object : TimerTask() {
                            override fun run() {
                                dumpSensor()
                            }
                        },
                        0,
                        UPDATE_INTERVAL
                    )
                }
                IN_PROGRESS -> {
                    currentSpeed.value = mdXYZ?.getCurrentSpeed().toString() + " km/h"
                }
                ERROR -> {
                }
            }
        }
    }

    private val hRefresh = Refresh()

    fun dumpSensor() {
        ++counter
        xyzAcc?.getPoint()?.let { mdXYZ?.addPoint(it) }

        hRefresh.sendEmptyMessage(0)

        if (counter > MEASURE_TIMES) {
            timer!!.cancel()
            hRefresh.sendEmptyMessage(TIMER_DONE)
        }
    }

    private fun onMeasureDone() {
        try {
            mdXYZ?.process()
        } catch (ex: Throwable) {
            Log.e("TAG", "Error")
        }
    }

    private fun setAccelerometer() {
        xyzAcc = XYZAccelerometer()
        mSensorManager.registerListener(
            xyzAcc,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }
}