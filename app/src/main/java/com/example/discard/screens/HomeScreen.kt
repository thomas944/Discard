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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.discard.R
import com.example.discard.components.HeadingTextComponent


@Composable

fun HomeScreen(navHostController: NavHostController)
{
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )


    {
      Surface (
          modifier = Modifier
              .fillMaxSize()
              .background(Color.White)
              .padding(14.dp)
      ) {
          Column(modifier = Modifier.fillMaxSize(),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
          ) {
              HeadingTextComponent(value = stringResource(id = R.string.app_name))
              Spacer(modifier = Modifier.height(100.dp))
              Button(onClick = {
                  navHostController.navigate("LobbyScreen")
              }) {
                  Text(text="Lobby", fontSize=20.sp)
              }
          }
      }
    }
}



//@Preview
//@Composable
//fun DefaultPreviewofHomeScreen(){
//    HomeScreen()
//}
