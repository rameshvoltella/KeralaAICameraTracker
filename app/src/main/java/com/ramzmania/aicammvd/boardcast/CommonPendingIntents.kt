package com.ramzmania.aicammvd.boardcast

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity

fun homePagePendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, HomeActivity::class.java)
    // Add any extras you want to pass
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    return PendingIntent.getActivity(
        context,
        303,  // Request code
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

fun stopAiTrackerPendingIntent(context: Context): PendingIntent {
    val stopIntent = Intent(context, NotificationDismissReceiver::class.java)
    return PendingIntent.getBroadcast(
        context, 209, stopIntent,
        PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}