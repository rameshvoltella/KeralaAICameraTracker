package com.ramzmania.aicammvd.boardcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService

class NotificationDismissedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("tada","YOOOo")
        if (intent.action == "com.ramzmania.aicammvd.ACTION_NOTIFICATION_DISMISSED") {
            // Perform action here to restart the notification or any other task
            startForegroundService(context)
        }
    }

    private fun startForegroundService(context: Context) {
        val intent = Intent(context, AiCameraLocationUpdateService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }
}