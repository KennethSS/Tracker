package com.solar.tracker.library

import android.location.Location

sealed class TrackerResult

data class LocationResult(
    val location: Location
) : TrackerResult()

data class ErrorResult(
    val code: Int,
    val msg: String
) : TrackerResult()