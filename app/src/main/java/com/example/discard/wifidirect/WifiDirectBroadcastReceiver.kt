package com.example.discard.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
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
//This is the Wifi Direct Broadcast Receiver class to handle Wifi Direct events
//
class WifiDirectBroadcastReceiver(private val manager: WifiP2pManager, private val channel: WifiP2pManager.Channel, private val activity : MainActivity) : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {   // Wifi direct state changed event
                //Check if wifi direct is enabled or not and display popup alerting user of state
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    Toast.makeText(context, "Wifi enabled", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Wifi Disabled", Toast.LENGTH_SHORT).show()

                }


            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                //do something
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.NEARBY_WIFI_DEVICES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                //manager?.requestPeers(channel, activity.peerListListener)
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

            }
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION ->
            {
                //do something
            }



        }
    }
}