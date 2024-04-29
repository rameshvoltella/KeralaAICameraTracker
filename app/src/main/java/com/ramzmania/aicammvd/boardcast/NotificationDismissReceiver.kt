package com.ramzmania.aicammvd.boardcast
/**
 * THis test calls for checking notification dismiss listener
 * */
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

class NotificationDismissReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            // Dismiss the notification
            try {

                val notificationManager =
                    ContextCompat.getSystemService(it, NotificationManager::class.java)
                notificationManager?.cancel(FAKE_SERVICE_NOTIFICATION_ID) // Replace with your notification ID
                if(PreferencesUtil.isServiceRunning(context)) {
                    removeAllGeofences(context)
                    WorkManager.getInstance(context).cancelAllWork()
                    LocationSharedFlow.serviceStopStatus.tryEmit(true)
                    PreferencesUtil.setServiceRunning(context,false)
                }
            }catch (ex:Exception)
            {

            }

        }
    }
}