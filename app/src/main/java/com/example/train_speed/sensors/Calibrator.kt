package com.example.train_speed.sensors

import android.os.Handler
import com.example.train_speed.models.Point
import java.util.*

class Calibrator(
    val hRefresh: Handler?,
    val acc: XYZAccelerometer?,
    val eventNumber: Int
) {
    val UPDATE_INTERVAL = 1000L
    val ITERATIONS = 5
    private var calData: LinkedList<Point>? = null


    fun calibrate() {
        val calTimer = Timer()
        calData = LinkedList<Point>()
        acc?.X = 0f
        acc?.Y = 0f
        acc?.Z = 0f
        calTimer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    addCalData(calData)
                    if (calData?.size ?: 0 > ITERATIONS) {
                        calTimer.cancel()
                        try {
                            calSensor(calData)
                        } catch (ex: Exception) {
                            try {
                                throw ex
                            } catch (ex1: Exception) {
                                hRefresh?.sendEmptyMessage(5)
                            }
                        }
                        hRefresh?.sendEmptyMessage(eventNumber)
                    }
                }
            },
            0,
            UPDATE_INTERVAL
        )
    }

    private fun addCalData(cD: LinkedList<Point>?) {
        val p: Point = acc?.getPoint() ?: return
        cD?.add(p)
        acc.reset()
    }

    @Throws
    private fun calSensor(cD: LinkedList<Point>?) {
        if (cD?.size ?: 0 < ITERATIONS - 1) {
            throw Exception("not enough data to calibrate")
        }
        var x = 0f
        var y = 0f
        var z = 0f
        // Don't use first measure
        for (i in 1 until cD!!.size) {
            x += cD[i].x
            y += cD[i].y
            z += cD[i].z
        }
        x /= (cD.size - 1)
        y /= (cD.size - 1)
        z /= (cD.size - 1)
        acc?.X = -x
        acc?.Y = -y
        acc?.Z = -z
    }
}