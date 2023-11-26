package com.example.discard.navigation

import GameViewModel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discard.screens.GameScreen
import com.example.discard.screens.HomeScreen
import com.example.discard.screens.LobbyScreen
import com.example.discard.screens.Lobby_create
import com.example.discard.screens.Lobby_join
import com.example.discard.screens.Score
import com.example.discard.wifidirect.WifiDirectViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(context: Context){

    val navController = rememberNavController()
    val gameViewModel = remember { GameViewModel() } // Create the instance of GameViewModel
    val wifiDirectViewModel = remember { WifiDirectViewModel(context)}

    NavHost(navController = navController, startDestination = "HomeScreen")
    {
        composable(route = "HomeScreen")
        {
            HomeScreen(navController, gameViewModel)
        }
        composable(route = "LobbyScreen")
        {
            LobbyScreen(navController, gameViewModel)
        }
        composable(route = "Lobby_join")
        {
            Lobby_join(navController, gameViewModel, wifiDirectViewModel)
        }
        composable(route = "Lobby_create")
        {
            Lobby_create(navController, gameViewModel)
        }
        composable(route = "GameScreen")
        {
            GameScreen(navController, gameViewModel)
        }
        composable(route="Score")
        {
            Score(navController, gameViewModel)
        }






    }

}