package com.example.train_speed.permission

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class PermissionCheck : BroadcastReceiver() {

    var permissionGranted = false

    fun requestPermissions(context: Context) {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_PERMISSIONS_GRANTED)
        intentFilter.addAction(ACTION_PERMISSIONS_DENIED)
        context.registerReceiver(this, intentFilter)
        val intent = Intent(context, GetPermissionsActivity::class.java)
        intent.putExtra(
            PERMISSIONS_KEY, arrayOf(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
        context.startActivity(intent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_PERMISSIONS_GRANTED -> {
                context?.unregisterReceiver(this)
                onPermissionsGranted()
            }
            ACTION_PERMISSIONS_DENIED -> {
                context?.unregisterReceiver(this)
                onPermissionsDenied()
            }
        }
    }

    private fun onPermissionsGranted() {
        permissionGranted = true
    }

    private fun onPermissionsDenied() {
        permissionGranted = false
    }
}