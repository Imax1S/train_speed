package com.example.train_speed.models

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*

class MeasureData(interval: Long) {
    // points from accelerometer
    private var accData: LinkedList<Point> = LinkedList()
    private var data: LinkedList<MeasurePoint> = LinkedList()

    // timer interval of generating points
    private val interval: Long = interval

    fun addPoint(p: Point) {
        accData.add(p)
        var speed = 0f

//        if (data.size > 0) {
//            speed = data.last.speedAfter.toFloat()
//        }
        data.add(MeasurePoint(p.x, p.y, p.z, speed, interval, getAveragePoint()))
    }

    fun process() {
//        for (i in 0 until accData.size) {
//            val p = accData[i]
//            var speed = 0f
//
//            if (i > 0) {
//                speed = data[i - 1].speedAfter.toFloat()
//            }
//            data.add(MeasurePoint(p.x, p.y, p.z, speed, interval, getAveragePoint()))
//        }
    }

    @Throws(Throwable::class)
    fun saveExt(con: Context, fname: String?): Boolean {
        try {
            val file = File(con.getExternalFilesDir(null), fname)
            val os = FileOutputStream(file)
            val out = OutputStreamWriter(os)
            for (i in data.indices) {
                val m = data[i]
                out.write(m.getStoreString())
            }
            out.close()
        } catch (t: Throwable) {
            throw t
        }
        return true
    }

    private fun getAveragePoint(): Point {
        var x = 0f
        var y = 0f
        var z = 0f
        for (i in accData.indices) {
            val (x1, y1, z1) = accData[i]
            x += x1
            y += y1
            z += z1
        }
        return Point(x, y, z, 1)
    }

    private fun getLastSpeed(): Float {
        return data.last.speedAfter.toFloat()
    }

    fun getCurrentSpeed(): Int {
        return ((data.sumOf { it.speedAfter } / data.size) * 36).toInt()
    }

    fun getLastSpeedKm(): Int {
        val ms = getLastSpeed()
        return (ms * 3.6f).toInt()
    }
}