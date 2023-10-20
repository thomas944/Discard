package com.example.discard.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDestination

sealed class Screen()
{
    object HomeScreen : Screen()
    object LobbyScreen : Screen()
    object Lobby_create : Screen()
    object Lobby_join : Screen ()
}

