package com.ramzmania.aicammvd.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
/**
 * A class representing network connectivity status and information.
 *
 * @property context The context used to access system services.
 * @constructor Creates a Network instance with the provided context.
 */
class Network @Inject constructor(val context: Context) : NetworkConnectivity {

    /**
     * Retrieves the current network information.
     *
     * @return The NetworkInfo object representing the current network, or null if not available.
     */
    override fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    /**
     * Checks if the device is currently connected to a network.
     *
     * @return True if the device is connected to a network, false otherwise.
     */
    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }
}

/**
 * An interface for accessing network connectivity status and information.
 */
interface NetworkConnectivity {

    /**
     * Retrieves the current network information.
     *
     * @return The NetworkInfo object representing the current network, or null if not available.
     */
    fun getNetworkInfo(): NetworkInfo?

    /**
     * Checks if the device is currently connected to a network.
     *
     * @return True if the device is connected to a network, false otherwise.
     */
    fun isConnected(): Boolean
}