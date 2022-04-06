package com.example.train_speed.database

import androidx.room.TypeConverter
import java.util.*
import kotlin.collections.ArrayList

class Converters {
    @TypeConverter
    fun uuidToString(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun stringToUUID(uuidString: String) = UUID.fromString(uuidString)

    @TypeConverter
    fun longToDate(timestamp: Long?): Date? = if (timestamp == null) null else Date(timestamp)

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = (date?.time)

    @TypeConverter
    fun arrayListToString(list: List<String>?): String? = list?.joinToString(",")

    @TypeConverter
    fun stringToArrayList(list: String): List<String>? = list.split(",")
}