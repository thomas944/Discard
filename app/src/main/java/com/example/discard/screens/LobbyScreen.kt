package com.example.discard.screens

import GameViewModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun LobbyScreen(navHostController: NavHostController,  gameViewModel: GameViewModel = viewModel()){

    val gameUiState by gameViewModel.uiState.collectAsState()

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
                    navHostController.navigate("Lobby_join")
                    gameViewModel.handleCreateLobby()
                    Log.d("DEBUG", "${gameViewModel.uiState.value.playerRole}")
                }) {
                    Text(text="Create Lobby", fontSize=20.sp)
                }
                Spacer(modifier = Modifier.height(45.dp))
                Button(onClick = {
                    navHostController.navigate("Lobby_join")
                    gameViewModel.handleJoinLobby()
                    Log.d("DEBUG", "${gameViewModel.uiState.value.playerRole}")

                }) {
                    Text(text="Join Lobby", fontSize=20.sp)
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
