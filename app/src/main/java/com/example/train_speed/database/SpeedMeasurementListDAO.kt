package com.example.train_speed.database

import androidx.room.*
import com.example.train_speed.models.SpeedMeasurement
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeedMeasurementListDAO {
    @Query("SELECT * FROM speed_measurement_table")
    fun getAllSpeedMeasurements(): Flow<List<SpeedMeasurement>>

    @Query("SELECT * FROM speed_measurement_table WHERE uuid = :uuidString LIMIT 1")
    fun getOneSpeedMeasurement(uuidString: String): Flow<SpeedMeasurement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeedMeasurement(crime: SpeedMeasurement)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpeedMeasurement(crime: SpeedMeasurement)

    @Delete
    fun deleteSpeedMeasurement(crime: SpeedMeasurement)
}