package com.example.discard.screens


import GameViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.discard.R
import com.example.discard.components.NickNameTextField
import com.example.discard.components.NormalTextComponent
import com.example.discard.components.RoomCodeTextField


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Lobby_join(navHostController: NavHostController, gameViewModel: GameViewModel = viewModel())
{
    val gameUiState by gameViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp)
        ){
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ){
                TextButton(onClick = {
                    navHostController.navigate("LobbyScreen")
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Black)
                }
            }


            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NormalTextComponent(value = stringResource(id = R.string.room_code))
                Spacer(modifier = Modifier.height(20.dp))
                RoomCodeTextField(labelValue = stringResource(id = R.string.room_code), gameViewModel)
                NickNameTextField(labelValue = stringResource(id = R.string.name), gameViewModel)
                Spacer(modifier = Modifier.height(100.dp))
                val context = LocalContext.current
                if (gameUiState.playerRole == "creator") {
                    Button(onClick = {
                        navHostController.navigate("Lobby_create")
                    }) {
                        Text(text="Create",
                            fontSize=20.sp)

                    }
                }else {
                    Button(onClick = {
//                        navHostController.navigate("Lobby_create")
                    }) {
                        Text(text="Join",
                            fontSize=20.sp)

                    }
                }


                Log.d("DEBUG", "${gameUiState.playerRole}")
                Log.d("DEBUG", "${gameUiState.roomCode}")


            }

        }
    }
}

//@Preview
//@Composable
//fun PreviewLobby_join()
//{
//    Lobby_join()
//}