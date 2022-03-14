package com.example.train_speed.models

data class Point(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val cnt: Int = 1
) {
    fun getForce() : Float {
        return x * x + y * y + z * z
    }
}