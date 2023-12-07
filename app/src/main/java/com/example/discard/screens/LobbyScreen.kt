package com.example.discard.screens

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.ui.tooling.preview.Preview
import com.example.discard.MainActivity
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pManager.ActionListener
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import com.example.discard.ui.theme.Purple40
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


@Composable
fun LobbyScreen(navHostController: NavHostController, mainActivity: MainActivity){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp)
        ) {
            //Back arrow
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
                TextButton(onClick = {
                    navHostController.navigate("HomeScreen")
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Black)
                }
            }

            //List discovered peers
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 150.dp), // Adjust the top padding to move the list lower
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

               // mainActivity.peers.forEach { peer ->
                items(mainActivity.peers) { peer ->
                    Text(peer.deviceName)
                    Button(onClick = {
                        mainActivity.connectToPeer(peer)
                    }) {
                        Text("Connect")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            //Show group info when connected to a group
            mainActivity.connectionInfo.value?.let { info ->
                if (info.groupFormed) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp) // Add some vertical padding between Text elements
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))
                        Text("Connected to group.")
                        Text("Group Owner: ${if (info.isGroupOwner) "Yes" else "No"}")
                        Text("Group Owner Address: ${info.groupOwnerAddress}")
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(200.dp))
                Button(onClick = {
                    Log.d(TAG, "Discover Peers button clicked")
                    mainActivity.discoverPeers()


                }) {
                    Text(text="Discover Peers", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    Log.d(TAG, "Create Group button clicked")
                    mainActivity.createGroup()
                }) {
                    Text(text="Create Group", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navHostController.navigate("GameScreen")
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Purple40,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 100.dp),
                    border = BorderStroke(1.dp, Purple40)
                ) {
                    Text("Play", fontSize=20.sp)
                }

            }

        }
    }
}





@Preview
@Composable
fun DefaultPreviewLobbyScreen(){
    val navHostController = rememberNavController()
    LobbyScreen(navHostController = navHostController, MainActivity())
}
