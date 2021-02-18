package com.solar.tracker.library

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.location.LocationManager.NETWORK_PROVIDER


class Tracker(private val context: Context) : LocationListener{

    private val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val googleServiceLocation: GoogleServiceLocation by lazy { GoogleServiceLocation(context) }

    private val minTimeMs = 0L

    private inline fun provideLocationEnable(result: (isProvide: Boolean) -> Unit) {
        result( lm.isProviderEnabled(NETWORK_PROVIDER) or lm.isProviderEnabled(GPS_PROVIDER))
    }

    fun getLocation(result: (latitude: Double, longitude: Double) -> Unit) {
        if (isOnGps()) {

        }

        PermissionHelper.isGrantedLocationPermissions(context) { isGranted ->
            if (isGranted) {
                provideLocationEnable { isProvide ->
                    if (isProvide) {
                        googleServiceLocation.getLastLocation(result)
                        try {
                            //lm.getLastKnownLocation(GPS_PROVIDER)
                            //lm.getLastKnownLocation(NETWORK_PROVIDER)
                        } catch (e: SecurityException) {

                        }
                    }
                }
            } else {
                context.startActivity(Intent(context, LocationPermissionActivity::class.java))
            }
        }
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