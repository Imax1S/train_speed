package com.example.train_speed.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.train_speed.models.SpeedMeasurement

@Database(entities = [(SpeedMeasurement::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun speedMeasurementListDAO(): SpeedMeasurementDAO
}