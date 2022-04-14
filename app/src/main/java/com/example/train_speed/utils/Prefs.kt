package com.example.train_speed.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "input_data"

        const val RAIL_LENGTH_NAME = "rail_length"
        const val DISTANCE_BETWEEN_CARRIAGES_NAME = "distance_between_carriages_name"
        const val DARK_MODE = "dark_mode"
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var savedRailLength: Int
        get() = preferences.getInt(RAIL_LENGTH_NAME, 25)
        set(value) = preferences.edit().putInt(RAIL_LENGTH_NAME, value).apply()

    var savedDistanceBetweenCarriages: Double
        get() = preferences.getFloat(DISTANCE_BETWEEN_CARRIAGES_NAME, 1.5f).toDouble()
        set(value) = preferences.edit().putFloat(DISTANCE_BETWEEN_CARRIAGES_NAME, value.toFloat())
            .apply()

    var savedDarkMode: Boolean
        get() = preferences.getBoolean(DARK_MODE, false)
        set(value) = preferences.edit().putBoolean(DARK_MODE, value)
            .apply()
}