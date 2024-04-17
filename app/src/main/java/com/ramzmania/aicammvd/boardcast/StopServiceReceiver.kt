package com.ramzmania.aicammvd.boardcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService

class StopServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val stopIntent = Intent(context, AiCameraLocationUpdateService::class.java)
        context?.stopService(stopIntent)
        if (context !=null)
        {
            removeAllGeofences(context)

        }


    }
}
