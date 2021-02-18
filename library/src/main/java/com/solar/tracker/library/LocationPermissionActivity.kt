package com.solar.tracker.library

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class LocationPermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), LOCATION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult : $requestCode")
        permissions.forEach {
            Log.d(TAG, "onRequestPermissionsResult : $it")
        }
        grantResults.forEach {
            Log.d(TAG, "onRequestPermissionsResult : $it")
        }
    }

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        Log.d(TAG, permission)
        return super.shouldShowRequestPermissionRationale(permission)
    }

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
        private const val TAG = "LocationPermission"
    }
}