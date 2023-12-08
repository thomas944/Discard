package com.example.discard.navigation

import GameViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discard.MainActivity
import com.example.discard.screens.GameScreen
import com.example.discard.screens.HomeScreen
import com.example.discard.screens.LobbyScreen
import com.example.discard.screens.Lobby_create
import com.example.discard.screens.Lobby_join
import com.example.discard.screens.Score
import com.example.discard.screens.UnoScreen

/* This contains the logic for the navigation logic to handle the app's flow.
 * Developed by Young Min Kwon
 */
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Navigation(mainActivity: MainActivity){

    val navController = rememberNavController()
    val gameViewModel = remember { GameViewModel() } // Create the instance of GameViewModel

    NavHost(navController = navController, startDestination = "HomeScreen")
    {
        composable(route = "HomeScreen")
        {
            HomeScreen(navController, gameViewModel)
        }
        composable(route = "LobbyScreen")
        {
            LobbyScreen(navController, gameViewModel, mainActivity)
        }
        composable(route = "Lobby_join")
        {
            Lobby_join(navController, gameViewModel)
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
        composable(route="UnoScreen")
        {
            UnoScreen(navController, gameViewModel)
        }





    }

}