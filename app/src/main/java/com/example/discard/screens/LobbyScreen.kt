package com.example.discard.screens

import GameViewModel
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

/*
* Lobby screen for the app which contains a composable function to handle the UI and
* allows users to discover other peers, connect to other peers, and create peer groups.
* This screen displays group information when connected to a group and allows the user to
* move to the game screen.
 */

// create composable function to handle lobby screen UI
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
            //Back arrow for navigation to return to the home screen of the app
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

            //List discovered peers in a Jetpack Compose LazyColumn
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 150.dp), // Adjust the top padding to move the list lower
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //List peers and allow users to connect to them
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
                        //Displays various information about the group to the user
                        //that may be useful
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

                //Display a button to allow users to discover peers
                Button(onClick = {
                    Log.d(TAG, "Discover Peers button clicked")
                        mainActivity.discoverPeers()

                }) {
                    Text(text="Discover Peers", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))

                //Display a button to allow users to create a group
                Button(onClick = {
                    Log.d(TAG, "Create Group button clicked")
                        mainActivity.createGroup()
                }) {
                    Text(text="Create Group", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(20.dp))

                //Display button that allow users to move to the game screen
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
