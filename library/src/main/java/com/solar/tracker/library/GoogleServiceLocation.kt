package com.solar.tracker.library

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

@SuppressLint("MissingPermission")
class GoogleServiceLocation(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun getLastLocation(result: (latitude: Double, longitude: Double) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener {

            }
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    result(it.latitude, it. longitude)
                }
            }
    }

    fun requestUpdateLocation(result: (latitude: Double, longitude: Double) -> Unit) {
        fusedLocationClient.requestLocationUpdates(
            request,
            object: LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return

                    val locations = locationResult.locations

                    if (locations.isNotEmpty()) {
                        locations.first().let {
                            result(it.latitude, it.longitude)
                        }
                    }
                }

                override fun onLocationAvailability(p0: LocationAvailability?) {
                    super.onLocationAvailability(p0)
                }
            },
            Looper.getMainLooper()
        )
    }

    private val request: LocationRequest by lazy {
        LocationRequest.create()
            .setInterval(LOCATION_UPDATE_INTERVAL)
            .setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL)
            .setPriority(LOCATION_UPDATE_PRIORITY)
    }

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5000L // 5 seconds
        private const val LOCATION_UPDATE_FASTEST_INTERVAL = 16L // 16ms = 60fps

        /**
         *  LocationRequest.PRIORITY_HIGH_ACCURACY
         *  LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
         *  LocationRequest.PRIORITY_NO_POWER
         *  LocationRequest.PRIORITY_LOW_POWER
         */
        private const val LOCATION_UPDATE_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
}