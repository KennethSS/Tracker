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

    fun getLocation(result: (result: TrackerResult) -> Unit) {
        if (isOnGps()) {

        } else {
            result(ErrorResult(ERROR_CODE_GPS_OFF, "GPS is off"))
        }

        PermissionHelper.isGrantedLocationPermissions(context) { isGranted ->
            if (isGranted) {
                provideLocationEnable { isProvide ->
                    if (isProvide) {
                        googleServiceLocation.getLastLocation { result(LocationResult(it)) }

                        try {
                            //lm.getLastKnownLocation(GPS_PROVIDER)
                            //lm.getLastKnownLocation(NETWORK_PROVIDER)
                        } catch (e: SecurityException) {

                        }
                    }
                }
            } else {
                result(ErrorResult(ERROR_CODE_PERMISSION_DENIED, "Location permission is denied"))
                //context.startActivity(Intent(context, LocationPermissionActivity::class.java))
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        if (isVerifyLocation(location)) {

        }
    }

    fun isOnNetwork() = lm.isProviderEnabled(NETWORK_PROVIDER)
    fun isOnGps() = lm.isProviderEnabled(GPS_PROVIDER)

    private fun isVerifyLocation(location: Location?) = run {
        (location?.latitude?: 0.0) != 0.0 && (location?.longitude?:0.0) != 0.0
    }

    companion object {
        private const val MIN_TIME_MS = 0L
        private const val MIN_DISTANCE_M = 0F

        const val REQUEST_CODE_LOCATION_PERMISSION = 100
        const val ERROR_CODE_GPS_OFF = 400
        const val ERROR_CODE_PERMISSION_DENIED = 401
    }
}