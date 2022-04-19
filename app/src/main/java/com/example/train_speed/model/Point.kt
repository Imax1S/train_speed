package com.example.train_speed.model

data class Point(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val cnt: Int = 1
) {

    fun getCntX() = x / cnt
    fun getCntY() = y / cnt
    fun getCntZ() = z / cnt

    fun getForce(): Float {
        return getCntX() * getCntX() + getCntY() * getCntY() + getCntZ() * getCntZ()
    }
}