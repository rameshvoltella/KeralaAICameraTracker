package com.ramzmania.aicammvd.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFS_NAME = "AiCameraPrefs"
    private const val IS_SERVICE_RUNNING = "IsServiceRunning"

    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setServiceRunning(context: Context, isRunning: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(IS_SERVICE_RUNNING, isRunning)
        editor.apply()
    }

    fun isServiceRunning(context: Context): Boolean =
        getPreferences(context).getBoolean(IS_SERVICE_RUNNING, false)
}
