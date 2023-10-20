package com.example.discard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discard.screens.HomeScreen
import com.example.discard.screens.LobbyScreen
import com.example.discard.screens.Lobby_create
import com.example.discard.screens.Lobby_join

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomeScreen")
    {
        composable(route = "HomeScreen")
        {
            HomeScreen(navController)
        }
        composable(route = "LobbyScreen")
        {
            LobbyScreen(navController)
        }
        composable(route = "Lobby_join")
        {
            Lobby_join(navController)
        }
        composable(route = "Lobby_create")
        {
            Lobby_create(navController)
        }


    }

}