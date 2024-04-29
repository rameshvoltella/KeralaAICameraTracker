package com.ramzmania.aicammvd.utils

import android.location.Location

object SpeedTracker {

    private var lastLocation: Location? = null
    private var lastTime: Long = 0

    fun calculateSpeed(currentLocation: Location): Float {
        var speed = 0f
        val currentTime = System.currentTimeMillis()

        if (lastLocation != null) {
            val distance = currentLocation.distanceTo(lastLocation!!)
            val timeDifference = currentTime - lastTime
            if (timeDifference > 0) {
                speed = (distance / timeDifference) * 1000 * 60 * 60 // Convert to meters per hour
            }
        }

        lastLocation = currentLocation
        lastTime = currentTime

        return speed
    }
}
