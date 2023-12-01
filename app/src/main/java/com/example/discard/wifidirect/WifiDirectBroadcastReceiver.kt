package com.example.discard.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.Channel
import android.net.wifi.p2p.WifiP2pManager.PeerListListener
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.example.discard.MainActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pInfo

class WifiDirectBroadcastReceiver(private val manager: WifiP2pManager?, private val channel: Channel, private val activity : MainActivity) : BroadcastReceiver()
{
    companion object {
        private const val TAG = "WifiDirectReceiver"
    }
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {   // Wifi direct state changed event
                //Check if wifi direct is enabled or not and display popup alerting user of state
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    //Toast.makeText(context, "Wifi enabled", Toast.LENGTH_SHORT).show()
                    activity.setIsWifiDirectP2pEnabled(true)

                } else {
                    //Toast.makeText(context, "Wifi Disabled", Toast.LENGTH_SHORT).show()
                    activity.setIsWifiDirectP2pEnabled(false)
                }
                Log.d(TAG, "Wifi direct p2p state changed - $state")


            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d(TAG, "Wifi direct p2p peers changed")
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.NEARBY_WIFI_DEVICES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    activity.requestMissingPermissions()
                    return
                }
                manager?.requestPeers(channel) { peers: WifiP2pDeviceList ->
                    activity.handlePeersChanged(peers)
                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                manager?.requestConnectionInfo(channel) { info: WifiP2pInfo ->
                    activity.handleConnectionChanged(info)

                }
            }
        }
    }
}