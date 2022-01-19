package com.example.train_speed.models

import java.util.*

class MeasureData(interval: Long) {
    // points from accelerometr
    private var accData: LinkedList<Point>
    private var data: LinkedList<Point>
    // timer interval of generating points
    private val interval: Long = interval

    init {
        accData = LinkedList()
        data = LinkedList()
    }

    fun addPoint(p: Point) {
        accData.add(p)
    }

    fun process() {
        for (i in 0..accData.size) {
            val p = accData[i]
            val speed = 0

            if (i > 0) {
//                speed = data.get(i - 1).
            }
        }
    }

    private fun getLastSpeed() : Float {
        return data.last.x
    }

    fun getLastSpeedKm() : Float {
        val ms = getLastSpeed()
        return ms * 3.6f
    }
}