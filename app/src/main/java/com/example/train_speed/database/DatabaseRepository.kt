package com.example.train_speed.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.train_speed.model.SpeedMeasurement
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME = "speed-measurement-database"

class DatabaseRepository private constructor(context: Context) {
    companion object {
        private var INSTANCE: DatabaseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DatabaseRepository(context)
            }
        }

        fun get(): DatabaseRepository {
            return INSTANCE
                ?: throw IllegalStateException("Database repository must be initialized")
        }
    }

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val speedMeasurementDAO = database.speedMeasurementListDAO()

    private val executor = Executors.newSingleThreadExecutor()

    fun getSpeedMeasurements(): LiveData<List<SpeedMeasurement>> =
        speedMeasurementDAO.getAllSpeedMeasurements()

    fun getSpeedMeasurement(id: UUID): LiveData<SpeedMeasurement> =
        speedMeasurementDAO.getOneSpeedMeasurement(id)

    fun updateMeasurement(measurement: SpeedMeasurement){
        executor.execute {
            speedMeasurementDAO.updateSpeedMeasurement(measurement)
        }
    }

    fun addMeasurement(measurement: SpeedMeasurement) {
        executor.execute{
            speedMeasurementDAO.insertSpeedMeasurement(measurement)
        }
    }
}