package com.example.train_speed.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


internal const val PERMISSIONS_KEY = "permissions"
internal const val ACTION_PERMISSIONS_GRANTED = "GetPermissionsActivity.permissions_granted"
internal const val ACTION_PERMISSIONS_DENIED = "GetPermissionsActivity.permissions_denied"

class GetPermissionsActivity : AppCompatActivity() {
    private val permissionRequestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringArrayExtra(PERMISSIONS_KEY)?.let {
            ActivityCompat.requestPermissions(
                this,
                it,
                permissionRequestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionRequestCode) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendBroadcast(Intent(ACTION_PERMISSIONS_GRANTED))
            } else {
                sendBroadcast(Intent(ACTION_PERMISSIONS_DENIED))
            }
            finish()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}