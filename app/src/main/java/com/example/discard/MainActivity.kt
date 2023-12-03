package com.example.discard

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.discard.navigation.Navigation
import com.example.discard.ui.theme.DiscardTheme

class MainActivity : ComponentActivity() {
//    private lateinit var wifiP2pManager: WifiP2pManager
//    private lateinit var channel: WifiP2pManager.Channel
//    private lateinit var wifiDirectReceiver: BroadcastReceiver
//    private val intentFilter: IntentFilter = IntentFilter()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            DiscardTheme{

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
//                   Navigation(context = this)
                   //PreviewComponent()
                    Navigation(context = this)
                    //CardComponent(cardModel =         CardModel("diamond", "king"))

                }
            }
        }


    }
//    override fun onStart() {
//        super.onStart()
//        registerReceiver(wifiDirectReceiver, intentFilter)
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unregisterReceiver(wifiDirectReceiver)
//    }


}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DiscardTheme {


        //Greeting("Android")
//        Navigation()
 //   }
//}