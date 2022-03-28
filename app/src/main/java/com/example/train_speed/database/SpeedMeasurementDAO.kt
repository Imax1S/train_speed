package com.example.train_speed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.train_speed.models.SpeedMeasurement
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SpeedMeasurementDAO {
    @Query("SELECT * FROM speed_measurement_table")
    fun getAllSpeedMeasurements(): LiveData<List<SpeedMeasurement>>

    @Query("SELECT * FROM speed_measurement_table WHERE id = (:uuid)")
    fun getOneSpeedMeasurement(uuid: UUID): LiveData<SpeedMeasurement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeedMeasurement(crime: SpeedMeasurement)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpeedMeasurement(crime: SpeedMeasurement)

    @Delete
    fun deleteSpeedMeasurement(crime: SpeedMeasurement)
}