package com.example.discard.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.discard.components.BackArrowComponent
import com.example.discard.components.NormalTextComponent

@Composable
fun LobbyScreen(onNavigateToHomeScreen : () -> Unit){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                BackArrowComponent(onNavigateToHomeScreen = onNavigateToHomeScreen)

                NormalTextComponent(value = "Create Lobby")
                NormalTextComponent(value = "Join Lobby")


            }

        }
    }
}



//@Preview
//@Composable
//fun DefaultPreviewLobbyScreen(){
//    LobbyScreen()
//}
