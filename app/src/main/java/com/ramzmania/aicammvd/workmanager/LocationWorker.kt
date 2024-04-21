package com.ramzmania.aicammvd.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log

import androidx.work.Worker
import androidx.work.WorkerParameters
import android.location.Location
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.geofencing.LocationUtils
import dagger.assisted.AssistedInject
import java.util.concurrent.CountDownLatch

@HiltWorker
class LocationWorker @AssistedInject constructor(context: Context, workerParams: WorkerParameters, private val localRepository: LocalRepository) : Worker(context, workerParams) {
    companion object {
        const val CHANNEL_ID = "location_updates"
        const val NOTIFICATION_ID = 1
    }

    init {
        createNotificationChannel()
    }
    override fun doWork(): Result {
        val latch = CountDownLatch(1)
        var result: Result = Result.failure()

        // Initialize your LocationUtils here
        val locationUtils = LocationUtils(applicationContext)
        locationUtils.startLocationUpdates(object : LocationUtils.LocationListener {
            override fun onLocationResult(location: Location?) {
                location?.let {
                    // Log or handle the location
                    Log.d("LocationWorker", "Current location: Latitude ${it.latitude}, Longitude ${it.longitude}")
                    updateNotification("Updated location: Latitude ${it.latitude}, Longitude ${it.longitude}")
                    result = Result.success()
                }
                latch.countDown()
            }

            override fun onLocationError(e: Exception) {
                Log.e("LocationWorker", "Error fetching location", e)
                result = Result.failure()
                latch.countDown()
            }
        })

        try {
            latch.await()  // Wait for the location update to complete
        } catch (e: InterruptedException) {
            return Result.failure()
        } finally {
            locationUtils.stopLocationUpdates()  // Make sure to stop updates to avoid leaks
        }

        return result
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Location Updates"
            val descriptionText = "Channel for Location Updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateNotification(contentText: String) {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with actual icon drawable
            .setContentTitle("Location Update")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}