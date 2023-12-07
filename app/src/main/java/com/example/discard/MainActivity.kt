package com.example.discard

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pGroup
import android.net.wifi.p2p.WifiP2pManager.*
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.discard.navigation.Navigation
import com.example.discard.screens.Lobby_join
import com.example.discard.screens.UnoScreen
import com.example.discard.ui.theme.DiscardTheme
import com.example.discard.wifidirect.WifiDirectBroadcastReceiver

class MainActivity : ComponentActivity() {

    private var manager: WifiP2pManager? = null
    private var isWifiDirectP2pEnabled = false

    val peers = mutableStateListOf<WifiP2pDevice>()
    val connectionInfo = mutableStateOf<WifiP2pInfo?>(null)

    //Create intent filter for wifi direct events
    private val intentFilter = IntentFilter().apply {
        addAction(WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }
    private val REQUEST_CODE_PERMISSIONS = 10 // Choose an integer value for the request code

    // List of permissions to request
    private val REQUIRED_PERMISSIONS =
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES, Manifest.permission.CHANGE_WIFI_STATE) // for android 13

    // Check if all permissions are granted
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    // Request missing permissions
    fun requestMissingPermissions() {
        Log.d("Permissions", "Not all permissions granted, had to be requested")
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
        Log.d(TAG, "End request permissions")
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Log.d(TAG, "Permissions granted")
            } else {
                Log.d(TAG, "Not all permissions granted")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun discoverPeers() {
        if (manager == null) {
            Log.d(TAG, "WifiP2pManager is null.")
        } else {
            Log.d(TAG, "WifiP2pManager is not null.")
        }

        if (channel == null) {
            Log.d(TAG, "Channel is null.")
        } else {
            Log.d(TAG, "Channel is not null.")
        }
        //Check for required permissions

        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d(TAG, "Discovery initiated.")
                Toast.makeText(this@MainActivity, "Peer discovery started", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(reasonCode: Int) {
                Log.d(TAG, "Discovery failed: $reasonCode")
                Toast.makeText(
                    this@MainActivity,
                    "Peer discovery failed: $reasonCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //Function to create wifi direct peer group
    @SuppressLint("MissingPermission")
    fun createGroup() {
            manager?.createGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Toast.makeText(this@MainActivity, "Group creation successful", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(reasonCode: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Group creation failed: $reasonCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //Update list of peers
    fun handlePeersChanged(peers: WifiP2pDeviceList) {
        Log.d(TAG, "Handling ${peers.deviceList.size} new peers")
        this.peers.clear()
        this.peers.addAll(peers.deviceList)
    }

    //Display updated device info if changed
    fun handleConnectionChanged(info: WifiP2pInfo) {
        this.connectionInfo.value = info
    }

    @SuppressLint("MissingPermission")
    fun connectToPeer(peer: WifiP2pDevice) {
        val config = WifiP2pConfig().apply {
            deviceAddress = peer.deviceAddress
            wps.setup = WpsInfo.PBC
        }
        manager?.connect(channel, config, object : ActionListener {
            override fun onSuccess() {
                Toast.makeText(
                    this@MainActivity,
                    "Connection to ${peer.deviceName} successful.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Connection to peer successful")
            }

            override fun onFailure(reason: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Connection to ${peer.deviceName} failed.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Connection to peer unsuccessful")

            }
        })
    }

    //Set boolean var to true to be used to display messages whether wifi direct enabled/disabled
    fun setIsWifiDirectP2pEnabled(isWifiDirectP2pEnabled: Boolean) {
        this.isWifiDirectP2pEnabled = isWifiDirectP2pEnabled
    }

    private var channel: Channel? = null
    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startTime = System.currentTimeMillis()
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager?.initialize(this, mainLooper, null)
        channel?.also { channel ->
            receiver = WifiDirectBroadcastReceiver(manager, channel, this)
        }

        requestMissingPermissions()

        setContent {
            DiscardTheme {


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                    Navigation(mainActivity = this@MainActivity)
                    //PreviewComponent()
                    //UnoScreen()
                    //CardComponent(cardModel =         CardModel("diamond", "king"))
                    //val navHostController = rememberNavController()

                    //Lobby_join(navHostController = navHostController)
                }
            }
        }
        val endTime = System.currentTimeMillis()
        val startupTime = endTime - startTime
        Log.d(TAG, "App startup time: $startupTime ms")
    }

    override fun onResume() {

        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)

    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
  // DiscardTheme {


        //Greeting("Android")
    //  Navigation()
  // }
//}