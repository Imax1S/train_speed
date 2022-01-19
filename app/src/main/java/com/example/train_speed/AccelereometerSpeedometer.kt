package com.example.train_speed

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Handler
import android.os.Message
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.train_speed.models.MeasureData
import com.example.train_speed.sensors.XYZAccelerometer
import java.lang.ref.WeakReference
import java.util.*
import kotlin.math.roundToInt

class AccelerometerSpeedometer() {
    val TIMER_DONE = 2
    val START = 3

    private var xyzAcc: XYZAccelerometer? = null
    private val mSensorManager: SensorManager? = null
    private val UPDATE_INTERVAL: Long = 500
    private val MEASURE_TIMES: Long = 20
    private var timer: Timer? = null
    private var currentSpeed: Double = 0.0

    var counter = 0
    private val mdXYZ: MeasureData? = null


    inner class Refresh() : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TIMER_DONE -> {
                    onMeasureDone()
                    val es1 =
                        ((mdXYZ!!.getLastSpeedKm() * 100).roundToInt() / 100f).toString()
                  currentSpeed = es1.toDouble()
                }
                START -> {
                    currentSpeed = 0.0
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
            }
        }
    }
    private val hRefresh = Refresh()

    fun dumpSensor() {
        ++counter
        mdXYZ!!.addPoint(xyzAcc!!.getPoint()!!)
        if (counter > MEASURE_TIMES) {
            timer!!.cancel()
            hRefresh.sendEmptyMessage(TIMER_DONE)
        }
    }

    private fun onMeasureDone() {
        mdXYZ?.process()
    }

    private fun setAccelerometer() {
        xyzAcc = XYZAccelerometer()
        mSensorManager!!.registerListener(
            xyzAcc,
            mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI
        )
    }

    @Composable
    fun ShowSpeed() {
        Text(text = "${"%.2f".format(currentSpeed)} m / sec")
    }
}
