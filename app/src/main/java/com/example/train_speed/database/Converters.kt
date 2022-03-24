package com.example.train_speed.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun uuidToString(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun stringToUUID(uuidString: String) = UUID.fromString(uuidString)

    @TypeConverter
    fun longToDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = (date?.time)
}