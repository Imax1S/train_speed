package com.example.train_speed.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "speed_measurement_table")
data class SpeedMeasurement(

    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "date")
    val date: Date? = Date(),

    @ColumnInfo(name = "avg_speed")
    val avgSpeed: String? = "0"
)