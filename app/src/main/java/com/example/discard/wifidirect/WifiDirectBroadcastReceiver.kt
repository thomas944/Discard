package com.example.discard.wifidirect

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.Channel
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.discard.MainActivity

/* This is the broadcast receiver class that extends the BroadcastReceiver class for receiving wifi
* direct intents and responding to them corresponding to the logic which is outlined in the
* main activity.
* This broadcast receiver handles different wifi direct events such as:
* - wifi direct state changes
* - wifi direct peer changes
* - wifi direct state changes
* Developed by Jason John.
 */
class WifiDirectBroadcastReceiver(private val manager: WifiP2pManager?, private val channel: Channel, private val activity : MainActivity) : BroadcastReceiver()
{

    //define a tag to associate with the broadcast receiver
    companion object {
        private const val TAG = "WifiDirectReceiver"
    }
    @SuppressLint("MissingPermission")
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

            //handle the intent received when wifi direct peer list changes
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d(TAG, "Wifi direct p2p peers changed")

                //request updated list of peers
                manager?.requestPeers(channel) { peers: WifiP2pDeviceList ->
                    //passes new peer list to the corresponding function in the main activity
                    activity.handlePeersChanged(peers)
                }
            }

            //handle the intent received when the wifi direct connection changes
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                //passes new connection info to corresponding function in main activity
                manager?.requestConnectionInfo(channel) { info: WifiP2pInfo ->
                    activity.handleConnectionChanged(info)
                }
            }
        }
    }
}