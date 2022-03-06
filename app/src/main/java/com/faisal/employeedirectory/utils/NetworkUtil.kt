package com.faisal.employeedirectory.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

/**
 * utility functions related to network
 * todo: uses deprecated fields. Needs to be updated later
 */
object NetworkUtil {

    /**
     * checks for internet
     * @return true, if online
     */
    fun Context.isOnline(): Boolean {
        val connMgr = getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connMgr != null) {
            val activeNetworkInfo = connMgr.activeNetworkInfo
            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                return if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    true
                } else activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
        }
        return false
    }
}