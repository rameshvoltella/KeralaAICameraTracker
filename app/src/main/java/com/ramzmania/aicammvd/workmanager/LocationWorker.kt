package com.ramzmania.aicammvd.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

import androidx.work.Worker
import androidx.work.WorkerParameters
import android.location.Location
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.NotificationDismissReceiver
import com.ramzmania.aicammvd.boardcast.homePagePendingIntent
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.geofencing.LocationUtils
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.Constants.CHANNEL_ID
import com.ramzmania.aicammvd.utils.Constants.FAKE_SERVICE_NOTIFICATION_ID
import com.ramzmania.aicammvd.utils.Logger
import com.ramzmania.aicammvd.utils.NotificationUtil
import com.ramzmania.aicammvd.utils.PreferencesUtil
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

@HiltWorker
class LocationWorker @AssistedInject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val localRepository: LocalRepository
) : Worker(context, workerParams) {


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

                    CoroutineScope(Dispatchers.IO).launch {
                        Logger.d(
                            "Current location: Latitude ${it.latitude}, Longitude ${it.longitude}"
                        )


                        localRepository.setNewAiCameraCircle(it.latitude, it.longitude)
                            .collect {response->
                                if(response.data==true)
                                {
                                    Logger.d("SUCCESS FULL WORKER")
                                }
                                else{
                                    Logger.d("FAILED WORKER")

                                }

                            }
                    }
                    Logger.d("Updated location: Latitude ${it.latitude}, Longitude ${it.longitude}")
                    updateNotification()
                    result = Result.success()
                }
                latch.countDown()
            }

            override fun onLocationError(e: Exception) {
                Logger.e("Error fetching location", e)
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


    private fun updateNotification() {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.red_location) // Replace with actual icon drawable
            .setContentTitle(Constants.NOTIFY_TRACKING_TITLE)
            .setContentText(Constants.NOTIFY_TRACKING_SUBTITLE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setContentIntent(homePagePendingIntent(applicationContext))
        val stopIntent = Intent(applicationContext, NotificationDismissReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            applicationContext, 209, stopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        builder.setAutoCancel(false)
        builder.addAction(
            R.drawable.stop,
            "Stop",
            stopPendingIntent
        )

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(FAKE_SERVICE_NOTIFICATION_ID, builder.build())

//        NotificationUtil.createNotificationChannel(
//            applicationContext,
//            CHANNEL_ID,
//            NotificationCompat.PRIORITY_HIGH
//        )
//        NotificationUtil.showNotification(
//            applicationContext,
//            "Location Tracker Started.from work manager.",
//            "work manage tracking now...",
//            homePagePendingIntent(applicationContext),
//            R.drawable.red_location,
//            NotificationCompat.PRIORITY_HIGH,
//            Constants.FAKE_SERVICE_NOTIFICATION_ID,
//            CHANNEL_ID,
//            false
//        )
    }
}