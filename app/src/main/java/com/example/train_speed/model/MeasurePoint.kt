package com.example.train_speed.model

import kotlin.math.sqrt

data class MeasurePoint(
    private val x: Float,
    private val y: Float,
    private val z: Float,
    private val speedBefore: Float,
    private val interval: Long,
    private val averagePoint: Point
) {
    var speedAfter = 0.0f
    private var distance = 0f
    private var acceleration = 0f

    init {
        calc()
    }

    private fun calc() {
        acceleration =
            x * averagePoint.getCntX() + y * averagePoint.getCntY() + z * averagePoint.getCntZ()
        acceleration /= sqrt(averagePoint.getForce())

        val t = interval.toFloat() / 1000f
        speedAfter = speedBefore + acceleration * t
        distance = speedBefore * t + acceleration * t * t / 2
    }

    fun getStoreString(): String {
        return "smith"
    }
}