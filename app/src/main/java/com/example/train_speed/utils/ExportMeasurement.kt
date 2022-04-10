package com.example.train_speed.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.contentColorFor
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import com.example.train_speed.model.SpeedMeasurement
import java.io.File

class ExportMeasurement(val context: Context) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/csv"
    }

    fun exportMeasurement(measurement: SpeedMeasurement) {

//        intent.putExtra(
//            Intent.EXTRA_SUBJECT,
//            "Sharing File..."
//        )

        val fileURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            createCSVFile(measurement)
        )
        intent.putExtra(Intent.EXTRA_STREAM, fileURI)
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Measurement...")

        val chooserIntent = Intent.createChooser(intent, "Share Measurement")
        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(chooserIntent)
    }

    private fun createCSVFile(measurement: SpeedMeasurement): File {
        val HEADER = "${measurement.title}, ${measurement.date}"

        val filename = "${measurement.title}.csv"

        val path = context.getExternalFilesDir(null)

        val fileOut = File(path, filename)

//        fileOut.delete()

        fileOut.createNewFile()

        fileOut.appendText(HEADER)
        fileOut.appendText("\n")
        fileOut.appendText(measurement.measurements.toString())
        fileOut.appendText("\n")
        return fileOut
    }
}