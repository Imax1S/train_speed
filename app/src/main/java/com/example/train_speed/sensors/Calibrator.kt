package com.example.train_speed.sensors

import android.os.Handler
import com.example.train_speed.model.Point
import java.util.*

class Calibrator(
    val hRefresh: Handler?,
    val acc: XYZAccelerometer?,
    val eventNumber: Int
) {
    companion object {
        const val UPDATE_INTERVAL = 1000L
        const val ITERATIONS = 5
    }

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
                    addCalData()
                    if (calData?.size ?: 0 > ITERATIONS) {
                        calTimer.cancel()
                        try {
                            calSensor()
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

    private fun addCalData() {
        val p: Point = acc?.getPoint() ?: return
        calData?.add(p)
        acc.reset()
    }

    @Throws
    private fun calSensor() {
        if (calData?.size ?: 0 < ITERATIONS - 1) {
            throw Exception("not enough data to calibrate")
        }
        var x = 0f
        var y = 0f
        var z = 0f

        // Don't use first measure
        for (i in 1 until calData!!.size) {
            val element = calData?.get(i) ?: Point(0f, 0f, 0f)
            x += element.x
            y += element.y
            z += element.z
        }

        val calDataSize = calData?.size ?: 1
        x /= (calDataSize - 1)
        y /= (calDataSize - 1)
        z /= (calDataSize - 1)

        acc?.X = -x
        acc?.Y = -y
        acc?.Z = -z
    }
}