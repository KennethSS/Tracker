package com.solar.tracker.library

import android.content.pm.PackageManager

object PermissionHelper {
    fun getPermissionStr(permission: Int): String {
        return when(permission) {
            PackageManager.PERMISSION_GRANTED -> "Granted"
            PackageManager.PERMISSION_DENIED -> "Denied"
            else -> "None"
        }
    }
}