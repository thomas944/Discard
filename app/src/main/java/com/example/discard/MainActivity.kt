package com.example.discard

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.discard.navigation.Navigation
import com.example.discard.ui.theme.DiscardTheme

class MainActivity : ComponentActivity() {
    private lateinit var wifiP2pManager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var wifiDirectReceiver: BroadcastReceiver
    private val intentFilter: IntentFilter = IntentFilter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            DiscardTheme{

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                   Navigation(context = this)
                   //PreviewComponent()
//                    UnoScreen()
                    //CardComponent(cardModel =         CardModel("diamond", "king"))

                }
            }
        }

//        wifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
//        channel = wifiP2pManager.initialize(this, mainLooper, null)
//
//        wifiDirectReceiver = WifiDirectBroadcastReceiver(wifiP2pManager, channel)
//
//        // Add necessary intent filters
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

    }
    override fun onStart() {
        super.onStart()
        registerReceiver(wifiDirectReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiDirectReceiver)
    }


}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DiscardTheme {


        //Greeting("Android")
//        Navigation()
 //   }
//}