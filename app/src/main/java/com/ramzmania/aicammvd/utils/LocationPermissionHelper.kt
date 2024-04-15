package com.ramzmania.aicammvd.utils
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationPermissionHelper(private val activity: Activity) {
    companion object {
        private const val REQUEST_LOCATION_PERMISSIONS = 101
    }

    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    fun checkAndRequestLocationPermissions(callback: PermissionCallback) {
        if (checkPermissions()) {
            callback.onPermissionGranted()
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted && coarseLocationGranted
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), REQUEST_LOCATION_PERMISSIONS
        )
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray, callback: PermissionCallback) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                callback.onPermissionGranted()
            } else {
                callback.onPermissionDenied()
            }
        }
    }
}
