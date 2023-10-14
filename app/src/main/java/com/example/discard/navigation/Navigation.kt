package com.example.discard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discard.screens.HomeScreen
import com.example.discard.screens.LobbyScreen

@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeScreen(
                onNavigateToLobbyScreen = { navController.navigate(Screen.LobbyScreen.route)}
            )
        }
        composable(route = Screen.LobbyScreen.route){
            LobbyScreen(
                onNavigateToHomeScreen = {navController.navigate(Screen.HomeScreen.route)}
            )

        }

    }

}