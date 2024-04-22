package com.ramzmania.aicammvd.utils

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFS_NAME = "AiCameraPrefs"
    private const val IS_SERVICE_RUNNING = "IsServiceRunning"
    private const val GEO_PENDING_INTENT = "pendingintent_geo"

    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setServiceRunning(context: Context, isRunning: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(IS_SERVICE_RUNNING, isRunning)
        editor.apply()
    }

    fun setString(context: Context, value: String,key:String) {
        val editor = getPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun getString(context: Context,key:String): String? =
        getPreferences(context).getString(key, "")

    fun setPendingIntentId(context: Context, id: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(GEO_PENDING_INTENT, id)
        editor.apply()
    }
    fun getPendingIntentId(context: Context): Int =
        getPreferences(context).getInt(GEO_PENDING_INTENT, 10)
    fun isServiceRunning(context: Context): Boolean =
        getPreferences(context).getBoolean(IS_SERVICE_RUNNING, false)
}
