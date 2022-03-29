package com.example.train_speed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.train_speed.model.SpeedMeasurement
import java.util.*

@Dao
interface SpeedMeasurementDAO {
    @Query("SELECT * FROM speed_measurement_table")
    fun getAllSpeedMeasurements(): LiveData<List<SpeedMeasurement>>

    @Query("SELECT * FROM speed_measurement_table WHERE id = (:uuid)")
    fun getOneSpeedMeasurement(uuid: UUID): LiveData<SpeedMeasurement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpeedMeasurement(measurement: SpeedMeasurement)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSpeedMeasurement(measurement: SpeedMeasurement)

    @Delete
    fun deleteSpeedMeasurement(measurement: SpeedMeasurement)
}