package com.solar.tracker.library

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHelper {
    fun getPermissionStr(permission: Int): String {
        return when(permission) {
            PackageManager.PERMISSION_GRANTED -> "Granted"
            PackageManager.PERMISSION_DENIED -> "Denied"
            else -> "None"
        }
    }


    inline fun isGrantedLocationPermissions(context: Context, result: (isGranted: Boolean) -> Unit) {
        val fine = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarse = ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        result(fine and coarse == PackageManager.PERMISSION_GRANTED)
    }


    fun Activity.requestPermission(vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE)
    }

    const val LOCATION_REQUEST_CODE = 1
}