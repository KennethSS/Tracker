package com.solar.tracker.library

import android.os.Build

object DeviceUtil {
    fun isAndroid11Later() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
    fun isAndroid10Later() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    fun isAndroid6Later() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    fun isAndroid5Later() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}