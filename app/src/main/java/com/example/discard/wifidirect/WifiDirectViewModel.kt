package com.example.discard.wifidirect

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import androidx.lifecycle.ViewModel

class WifiDirectViewModel(context: Context): ViewModel() {
    val wifiP2pManager: WifiP2pManager = context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    val channel: WifiP2pManager.Channel =
        wifiP2pManager.initialize(context, context.mainLooper, null)
    val wifiDirectReceiver: WifiDirectBroadcastReceiver

    init {
        wifiDirectReceiver = WifiDirectBroadcastReceiver(wifiP2pManager, channel)
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        context.registerReceiver(wifiDirectReceiver, intentFilter)
    }

//    override fun onCleared() {
//        super.onCleared()
//        context.unregisterReceiver(wifiDirectReceiver)
//    }
}