package com.solar.tracker.library

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER
import android.os.Looper
import androidx.core.content.ContextCompat


class Tracker(private val context: Context) : LocationListener{

    private val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val minTimeMs = 0L

    private fun isGrantedLocationPermissions(): Boolean {
        val fine = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
        val coarse = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)

        return fine and coarse == PackageManager.PERMISSION_GRANTED
    }
    fun getLocation() {
        if (isOnNetwork()) {

        }

        if (isOnGps()) {

        }

        if (isGrantedLocationPermissions()) {
            lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lm.getLastKnownLocation(NETWORK_PROVIDER)

            lm.isProviderEnabled(NETWORK_PROVIDER)
            lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } else {
            context.startActivity(Intent(context, LocationPermissionActivity::class.java))
        }
    }

    private fun network() {
        lm.isProviderEnabled(NETWORK_PROVIDER)
        lm.requestLocationUpdates(NETWORK_PROVIDER,
            MIN_TIME_MS, MIN_DISTANCE_M, this, Looper.getMainLooper())
    }

    fun isOnNetwork() = lm.isProviderEnabled(NETWORK_PROVIDER)
    fun isOnGps() = lm.isProviderEnabled(GPS_PROVIDER)

    companion object {
        private const val MIN_TIME_MS = 0L
        private const val MIN_DISTANCE_M = 0F
    }

    override fun onLocationChanged(location: Location) {
        if (isVerifyLocation(location)) {

        }
    }

    private fun isVerifyLocation(location: Location?) = run {
        (location?.latitude?: 0.0) != 0.0 && (location?.longitude?:0.0) != 0.0
    }
}