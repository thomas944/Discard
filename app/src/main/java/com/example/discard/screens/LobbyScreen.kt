package com.example.discard.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

//variables to represent users choice
//can either be lobby creator or lobby joiner
const val CREATE_LOBBY = 1
const val JOIN_LOBBY = 2

object LobbyScreen {
    private var _lobbyOption: Int? = null
    var lobbyOption: Int?
        get() = _lobbyOption
        set(value) {
            _lobbyOption = value
        }
}
@Composable
fun LobbyScreen(navHostController: NavHostController){

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

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(200.dp))
                Button(onClick = {
                    LobbyScreen.lobbyOption = CREATE_LOBBY
                    navHostController.navigate("Lobby_create")
                }) {
                    Text(text="Create Lobby", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(45.dp))
                Button(onClick = {
                    LobbyScreen.lobbyOption = JOIN_LOBBY
                    navHostController.navigate("Lobby_join")
                }) {
                    Text(text="Join Lobby", fontSize=20.sp)
                }




            }

        }
    }
}



@Preview
@Composable
fun DefaultPreviewLobbyScreen(){
val navHostController = rememberNavController()
    LobbyScreen(navHostController = navHostController)
}
