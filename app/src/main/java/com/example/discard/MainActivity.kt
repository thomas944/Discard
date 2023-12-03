package com.example.discard

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import android.net.wifi.p2p.WifiP2pManager.Channel
import android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
import android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION
import android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION
import android.net.wifi.p2p.WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import com.example.discard.navigation.Navigation
import com.example.discard.ui.theme.DiscardTheme
import com.example.discard.wifidirect.WifiDirectBroadcastReceiver

class MainActivity : ComponentActivity(){

    private var manager: WifiP2pManager? = null
    private var isWifiDirectP2pEnabled = false
    private var receiverRegistered = false


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
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES)

    // Check if all permissions are granted
    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    // Request missing permissions
    fun requestMissingPermissions() {
        // if (!allPermissionsGranted()) {
        Log.d("Permissions", "Not all permissions granted, had to be requested")
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
        //}
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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //if (!allPermissionsGranted()) {
            requestMissingPermissions()
            return
            //}

        }
        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d(TAG, "Discovery initiated.")
                Toast.makeText(this@MainActivity, "Peer discovery started", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(reasonCode: Int) {
                Log.d(TAG, "Discovery failed: $reasonCode")
                Toast.makeText(this@MainActivity, "Peer discovery failed: $reasonCode", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun createGroup() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //if (!allPermissionsGranted()) {
            requestMissingPermissions()
            return
            //}
        }
        manager?.createGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Toast.makeText(this@MainActivity, "Group creation successful", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(reasonCode: Int) {
                Toast.makeText(this@MainActivity, "Group creation failed: $reasonCode", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun handlePeersChanged(peers: WifiP2pDeviceList) {
        this.peers.clear()
        this.peers.addAll(peers.deviceList)
    }

    fun handleConnectionChanged(info: WifiP2pInfo) {
        this.connectionInfo.value = info
    }
    fun connectToPeer(peer: WifiP2pDevice) {
        val config = WifiP2pConfig().apply {
            deviceAddress = peer.deviceAddress
            wps.setup = WpsInfo.PBC
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // if (!allPermissionsGranted()) {
            requestMissingPermissions()
            return
            //}
        }
        manager?.connect(channel, config, object : ActionListener {
            override fun onSuccess() {
                Toast.makeText(this@MainActivity, "Connection to ${peer.deviceName} successful.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Connection to peer successful")
            }

            override fun onFailure(reason: Int) {
                Toast.makeText(this@MainActivity, "Connection to ${peer.deviceName} failed.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Connection to peer unsuccessful")

            }
        })
    }


    fun setIsWifiDirectP2pEnabled(isWifiDirectP2pEnabled: Boolean) {
        this.isWifiDirectP2pEnabled = isWifiDirectP2pEnabled
    }

    private var channel: Channel? = null
    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
    override fun onResume() {

        super.onResume()
        // Register wifi direct broadcast receiver
        //if(!receiverRegistered) {
        registerReceiver(receiver, intentFilter)
        //  receiverRegistered = true
        // }
        //receiver?.also { receiver ->
        // registerReceiver(receiver, intentFilter)
        //}

    }

    override fun onPause() {
        super.onPause()
        //if(receiverRegistered) {
        unregisterReceiver(receiver)
        //  receiverRegistered = false
        // }

        //receiver?.also { receiver ->
        //  unregisterReceiver(receiver)
        // }
    }



    companion object {
        private const val TAG = "wifidirectdemo"
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