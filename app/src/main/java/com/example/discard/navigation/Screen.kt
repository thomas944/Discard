package com.example.discard.navigation

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object LobbyScreen : Screen("lobby_screen")
}
