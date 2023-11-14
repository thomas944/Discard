package com.example.discard

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.discard.navigation.Navigation
import com.example.discard.screens.Lobby_join
import com.example.discard.screens.UnoScreen
import com.example.discard.ui.theme.DiscardTheme
import com.example.discard.wifidirect.WifiDirectBroadcastReceiver

class MainActivity : ComponentActivity() {
    private val wifiP2pManager: WifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
    private val channel: WifiP2pManager.Channel = wifiP2pManager.initialize(this, mainLooper, null)
    private val wifiDirectReceiver = WifiDirectBroadcastReceiver(wifiP2pManager, channel, this)

    /* Peer Discovery: creates list of discovered peers
     * Might not be necessary for the app

    private val listDiscoveredPeers : MutableList<WifiP2pDevice> = mutableListOf()
    val peerListListener = WifiP2pManager.PeerListListener { peerList: WifiP2pDeviceList ->
        //stuff
        listDiscoveredPeers.clear()
        for(peer in peerList.deviceList)
        {
            listDiscoveredPeers.add(peer)
        }

    }
    */
    /* Function to initiate peer discovery process

    private fun discoverPeers() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
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
        wifiP2pManager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {

            }
            override fun onFailure(reason: Int) {

            }
        })
    }

     */


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                DiscardTheme {

                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Gray
                    ) {
                        Navigation()
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
           /*
           val intentFilter = IntentFilter().apply {
                addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
            }
            registerReceiver(wifiDirectReceiver, intentFilter)
        */
        }

        override fun onPause() {
            super.onPause()
            //unregisterReceiver(wifiDirectReceiver)
        }

    }


//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   DiscardTheme {


        //Greeting("Android")
      Navigation()
   }
}