package com.example.train_speed.models

import androidx.room.*
import com.example.train_speed.database.Converters
import java.util.*

@Entity(tableName = "speed_measurement_table")
data class SpeedMeasurement(

    @PrimaryKey
    @TypeConverters(Converters::class)
    val uuid: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    @TypeConverters(Converters::class)
    val date: Date? = Date(),

    @ColumnInfo(name = "avg_speed")
    val avgSpeed: Int? = 0
)
