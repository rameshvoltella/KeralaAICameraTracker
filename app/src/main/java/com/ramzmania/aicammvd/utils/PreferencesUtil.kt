package com.ramzmania.aicammvd.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility class for managing SharedPreferences in the application.
 */
object PreferencesUtil {
    private const val PREFS_NAME = "AiCameraPrefs"
    private const val IS_TRACKER_RUNNING = "IsTrackerRunning"
    private const val GEO_PENDING_INTENT = "pendingintent_geo"
    /**
     * Retrieves the SharedPreferences instance.
     * @param context The context used to retrieve SharedPreferences.
     * @return SharedPreferences instance.
     */
    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Sets the value indicating whether the tracker is running.
     * @param context The context used to access SharedPreferences.
     * @param isRunning The value indicating whether the tracker is running.
     */
    fun setTrackerRunning(context: Context, isRunning: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putBoolean(IS_TRACKER_RUNNING, isRunning)
        editor.apply()
    }

    /**
     * Sets a string value in SharedPreferences.
     * @param context The context used to access SharedPreferences.
     * @param value The value to be stored.
     * @param key The key under which the value is stored.
     */
    fun setString(context: Context, value: String,key:String) {
        val editor = getPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }
    /**
     * Retrieves a string value from SharedPreferences.
     * @param context The context used to access SharedPreferences.
     * @param key The key under which the value is stored.
     * @return The retrieved string value, or an empty string if not found.
     */
    fun getString(context: Context,key:String): String? =
        getPreferences(context).getString(key, "")

    /**
     * Sets the pending intent ID in SharedPreferences.
     * @param context The context used to access SharedPreferences.
     * @param id The pending intent ID to be stored.
     */
    fun setPendingIntentId(context: Context, id: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(GEO_PENDING_INTENT, id)
        editor.apply()
    }

    /**
     * Retrieves the pending intent ID from SharedPreferences.
     * @param context The context used to access SharedPreferences.
     * @return The pending intent ID, or a default value if not found.
     */
    fun getPendingIntentId(context: Context): Int =
        getPreferences(context).getInt(GEO_PENDING_INTENT, 10)

    /**
     * Checks if the tracker is running by retrieving the corresponding value from SharedPreferences.
     * @param context The context used to access SharedPreferences.
     * @return True if the tracker is running, false otherwise.
     */
    fun isTrackerRunning(context: Context): Boolean =
        getPreferences(context).getBoolean(IS_TRACKER_RUNNING, false)
}
