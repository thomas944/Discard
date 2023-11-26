package com.example.discard.wifidirect

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.Channel

class WifiDirectManager(private val context: Context) {
    val wifiP2pManager: WifiP2pManager = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    val channel: Channel = wifiP2pManager.initialize(context, context.mainLooper, null)

    companion object {
        fun create(context: Context): WifiDirectManager {
            return WifiDirectManager(context)

        }
    }

}