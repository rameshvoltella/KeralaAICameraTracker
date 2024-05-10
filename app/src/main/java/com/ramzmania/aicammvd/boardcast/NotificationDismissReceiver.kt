package com.ramzmania.aicammvd.boardcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.utils.Constants.FAKE_SERVICE_NOTIFICATION_ID
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import com.ramzmania.aicammvd.utils.PreferencesUtil
/**
 * This test Receiver calls for checking notification dismiss listener
 * */
class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            // Dismiss the notification
            try {

                val notificationManager =
                    ContextCompat.getSystemService(it, NotificationManager::class.java)
                notificationManager?.cancel(FAKE_SERVICE_NOTIFICATION_ID) // Replace with your notification ID
                if(PreferencesUtil.isTrackerRunning(context)) {
                    removeAllGeofences(context, false,null)
                    WorkManager.getInstance(context).cancelAllWork()
                    LocationSharedFlow.serviceStopStatus.tryEmit(true)
                    PreferencesUtil.setTrackerRunning(context,false)
                }
            }catch (ex:Exception)
            {

            }

        }
    }
}