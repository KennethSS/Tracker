package com.solar.tracker.library

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
        finish()
        Log.d(TAG, "onRequestPermissionsResult : $requestCode")
        permissions.forEach {
                val p = shouldShowRequestPermissionRationale(it)
            Log.d(TAG, "onRequestPermissionsResult : $it ${p}")
        }
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) {

                Log.d(TAG, "onRequestPermissionsResult : $it ")
            } else {
                Log.d(TAG, "onRequestPermissionsResult : $it")
            }

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