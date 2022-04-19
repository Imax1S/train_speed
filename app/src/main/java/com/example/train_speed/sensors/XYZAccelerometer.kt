package com.example.train_speed.sensors

import android.hardware.SensorEvent
import com.example.train_speed.model.Point
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt

class XYZAccelerometer : Accelerometer() {
    private val BUFFER_SIZE = 500

    // calibration
    private val dX = 0f
    private val dY = 0f
    private val dZ = 0f

    // buffer variables
    var X = 0f
    var Y = 0f
    var Z = 0f
    private var cnt = 0

    // returns last SenorEvent parameters
    fun getLastPoint(): Point {
        return Point(lastX, lastY, lastZ, 1)
    }

    override fun getPoint(): Point {
        if (cnt == 0) {
            return Point(lastX, lastY, lastZ, 1)
        }

        val p = Point(X, Y, Z, cnt)

        reset()
        return p
    }

    override fun onSensorChanged(se: SensorEvent) {
        val x = se.values[0] + dX
        val y = se.values[1] + dY
        val z = se.values[2] + dZ
        lastX = x
        lastY = y
        lastZ = z
        X += x
        Y += y
        Z += z

        if (cnt < BUFFER_SIZE - 1) {
            cnt++
        } else {
            reset()
        }
    }

    // resets buffer
    fun reset() {
        cnt = 0
        X = 0f
        Y = 0f
        Z = 0f
    }
}