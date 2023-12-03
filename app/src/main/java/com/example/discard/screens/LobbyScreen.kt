package com.example.discard.screens

import GameViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.discard.MainActivity
import com.example.discard.ui.theme.Purple40


@Composable
fun LobbyScreen(navHostController: NavHostController, gameViewModel: GameViewModel, mainActivity: MainActivity){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp)
        ) {

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
            Column {
                mainActivity.peers.forEach { peer ->
                    Text(peer.deviceName)
                    Button(onClick = {
                        mainActivity.connectToPeer(peer)
                    }) {
                        Text("Connect")
                    }
                }
            }
            mainActivity.connectionInfo.value?.let { info ->
                if (info.groupFormed) {
                    Column(){
                        Text("Connected to group.")
                        Text("Group Owner: ${if (info.isGroupOwner) "Yes" else "No"}")
                        Text("Group Owner Address: ${info.groupOwnerAddress}")
                    }

                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(200.dp))
                Button(onClick = {
//                    Log.d(TAG, "Discover Peers button clicked")
                    if (!mainActivity.allPermissionsGranted()) {
                        mainActivity.requestMissingPermissions()
                    } else {
                        mainActivity.discoverPeers()
                    }

                }) {
                    Text(text="Discover Peers", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
//                    Log.d(TAG, "Create Group button clicked")
                    if (!mainActivity.allPermissionsGranted()) {
                        mainActivity.requestMissingPermissions()
                    } else {
                        mainActivity.createGroup()
                    }
                }) {
                    Text(text="Create Group", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navHostController.navigate("UnoScreen")
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




//@Preview
//@Composable
//fun DefaultPreviewLobbyScreen(){
//    LobbyScreen()
//}
