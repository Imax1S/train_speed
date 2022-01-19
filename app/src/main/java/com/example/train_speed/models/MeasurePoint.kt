package com.example.train_speed.models

data class MeasurePoint(
    private val x: Float,
    private val y: Float,
    private val z: Float,
    private val speedBefore: Float,
    private val interval: Long
) {
    init {
        calc()
    }

    private var speedAfter = 0f
    private var distance = 0f
    private var acceleration = 0f

    private fun calc() {
        acceleration = Math.sqrt((x * x + y * y * +z * z).toDouble()).toFloat()
        val t = interval.toFloat() / 1000f
        speedAfter = speedBefore + acceleration * t
        distance = speedBefore * t + acceleration * t * t / 2
    }
}