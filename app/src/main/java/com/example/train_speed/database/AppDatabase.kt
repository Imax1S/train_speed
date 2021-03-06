package com.example.train_speed.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.train_speed.model.SpeedMeasurement

@Database(entities = [(SpeedMeasurement::class)], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun speedMeasurementListDAO(): SpeedMeasurementDAO
}