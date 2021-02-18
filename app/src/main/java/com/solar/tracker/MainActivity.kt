package com.solar.tracker

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.solar.tracker.library.DeviceUtil
import com.solar.tracker.library.PermissionHelper
import com.solar.tracker.library.PermissionHelper.requestPermission
import com.solar.tracker.library.Tracker
import java.security.Permission

class MainActivity : AppCompatActivity() {
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        }


    private val tracker: Tracker by lazy { Tracker(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.location_gps_network_state).let { tv ->
            tv.text = "GPS: ${tracker.isOnGps()}  NETWORK: ${tracker.isOnNetwork()}"
        }

        findViewById<Button>(R.id.request_location).let { btn ->
            btn.setOnClickListener {
                tracker.getLocation { latitude, longitude, ->
                    findViewById<TextView>(R.id.location).let { tv ->
                        tv.text = "위도: $latitude  경도: $longitude"
                    }
                }
            }
        }

        findViewById<Button>(R.id.move_to_setting).let { btn ->
            btn.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                })
            }
        }



        if (DeviceUtil.isAndroid6Later()) {
            Log.d("MainActivity", shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION).toString())
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                Dialog(this).run {
                    setTitle("위치 권한을 허용해야 합니다 이동?")
                    show()
                }
            } else {

            }
        } else {

        }


        /*when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.

            }
            shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.

            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                //requestPermissionLauncher.launch(Manifest.permission.REQUESTED_PERMISSION)
            }
        }

        requestPermissionLauncher*/

        findViewById<TextView>(R.id.location_permission_state).let { tv ->
            val fine = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            val coarse = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            tv.text = "Find: ${PermissionHelper.getPermissionStr(fine)}\n" +
                    "coarse: ${PermissionHelper.getPermissionStr(coarse)}\n"
        }
    }
}