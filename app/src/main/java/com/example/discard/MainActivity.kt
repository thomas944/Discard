package com.example.discard
import android.content.ContentValues.TAG
import android.content.ContentValues
import android.Manifest
import android.annotation.SuppressLint
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
/*
* This is the main activity of the Discard app, and is the entry point when the app is run.
* The app follows a single activity architecture and uses Kotlin and Jetpack Compose
* The main activity follows Android's activity lifecycle and handles state changes using
* callbacks such as onCreate(), onResume(), etc.
*
* This activity displays the entry screen and handles aspects of the wifi direct
* functionality and requesting necessary permissions.
* Developed by Thomas Pham and Azeem Mir
 */
class MainActivity : ComponentActivity() {

    //initialize variables for the wifi direct api
    private var manager: WifiP2pManager? = null
    private var isWifiDirectP2pEnabled = false


    //handle storing discovered wifi direct peers and connection info
    val peers = mutableStateListOf<WifiP2pDevice>()
    val connectionInfo = mutableStateOf<WifiP2pInfo?>(null)

    //create intent filter for wifi direct events
    private val intentFilter = IntentFilter().apply {
        addAction(WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    //request permission code
    private val REQUEST_CODE_PERMISSIONS = 10

    // create a list of permissions to request, some are required for certain Android versions
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.NEARBY_WIFI_DEVICES,
        Manifest.permission.CHANGE_WIFI_STATE
    )

    // check if all permissions are granted
    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    // request missing permissions
    fun requestMissingPermissions() {
        Log.d("Permissions", "Not all permissions granted, had to be requested")
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
        Log.d(TAG, "End request permissions")
    }

    //handle permission request
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

    //function for wifi direct peer discovery, allows a user to view
    //the peers that are eligible for connection
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

        manager?.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {  //handle successful peer discovery
                Log.d(TAG, "Discovery initiated.")
                Toast.makeText(this@MainActivity, "Peer discovery started", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(reasonCode: Int) { //display and log error message if fails
                Log.d(TAG, "Discovery failed: $reasonCode")
                Toast.makeText(
                    this@MainActivity,
                    "Peer discovery failed: $reasonCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //function to allow a user to create a wifi direct peer group
    @SuppressLint("MissingPermission")
    fun createGroup() {
        manager?.createGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() { //show message on successful group creation
                Toast.makeText(this@MainActivity, "Group creation successful", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(reasonCode: Int) { //handle failure of group creation
                Toast.makeText(
                    this@MainActivity,
                    "Group creation failed: $reasonCode",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //function to update peers when peer list changes
    fun handlePeersChanged(peers: WifiP2pDeviceList) {
        this.peers.clear()
        this.peers.addAll(peers.deviceList)
    }

    //update connection info
    fun handleConnectionChanged(info: WifiP2pInfo) {
        this.connectionInfo.value = info
    }

    //function to handle connection to a discovered peer
    @SuppressLint("MissingPermission")
    fun connectToPeer(peer: WifiP2pDevice) {
        val config = WifiP2pConfig().apply {
            deviceAddress = peer.deviceAddress
            wps.setup = WpsInfo.PBC
        }
        manager?.connect(channel, config, object : ActionListener {
            override fun onSuccess() { //display message upon successful connection
                Toast.makeText(
                    this@MainActivity,
                    "Connection to ${peer.deviceName} successful.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Connection to peer successful")
            }

            override fun onFailure(reason: Int) { //display message for a failed peer discovery
                Toast.makeText(
                    this@MainActivity,
                    "Connection to ${peer.deviceName} failed.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "Connection to peer unsuccessful")

            }
        })
    }

    //function returns true or false depending on whether the user has wifi direct enabled or not
    fun setIsWifiDirectP2pEnabled(isWifiDirectP2pEnabled: Boolean) {
        this.isWifiDirectP2pEnabled = isWifiDirectP2pEnabled
    }

    //initialize vars for wifi direct communication and the broadcast receiver
    private var channel: Channel? = null
    private var receiver: BroadcastReceiver? = null

    //override onCreate() function to handle start of the activity
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set variable to hold start time for app for app optimization purposes
        val appStartTime = System.currentTimeMillis()

        //initialize WifiDirectP2pManager from the Android Wifi Direct API
        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager

        //create channel to handle wifi direct communication
        channel = manager?.initialize(this, mainLooper, null)

        //create broadcast receiver for handling wifi direct events
        channel?.also { channel ->
            receiver = WifiDirectBroadcastReceiver(manager, channel, this)
        }

        //call the function to request necessary permissions upon the startup of the app
        requestMissingPermissions()

        //handle UI by displaying main screen, navigation and setting theme
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

        //variable to store end of app startup time
        val appStartupEndTime = System.currentTimeMillis()

        //calculate app startup time for testing and optimization purposes
        val appStartupTime = appStartupEndTime - appStartTime
        Log.d(TAG, "App startup time: $appStartupTime ms")
    }

    //override onResume() function to register the broadcast receiver when activity is resumed
    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    //unregister broadcast receiver when activity is paused to decrease battery usage
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