package com.example.discard.screens


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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.discard.R
import com.example.discard.components.NormalTextComponent
import com.example.discard.components.TextField

private var lobbyPasscode: String? = null

//function to allow creator to set room passcode
fun setLobbyPasscode(passcode: String) {
    lobbyPasscode = passcode
}
@Composable
fun Lobby_join(navHostController: NavHostController)
{
    //State variables to store passcode and nickname for lobby creator
    //and state variables to store nicknames and valid passcode for other
    //players
    val nicknameState = remember  {mutableStateOf("")}
    val passcodeState = remember { mutableStateOf("") }

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
                TextField(
                    labelValue = stringResource(id = R.string.room_code),
                    value = passcodeState.value,
                    onValueChange = {passcodeState.value = it}
                )
                TextField(
                    labelValue = stringResource(id = R.string.name),
                    value = nicknameState.value,
                    onValueChange = {nicknameState.value = it}
                )
                Spacer(modifier = Modifier.height(100.dp))

                Button(onClick = {
                    //After join button is pressed, save passcode for
                    //lobby creators and validate for other players
                    if (LobbyScreen.lobbyOption == CREATE_LOBBY) {
                        setLobbyPasscode(passcodeState.value)
                        navHostController.navigate("Lobby_create")
                    } else if (LobbyScreen.lobbyOption == JOIN_LOBBY) {
                        val enteredPasscode = passcodeState.value
                        if (validatePasscode(enteredPasscode)) {
                            navHostController.navigate("Lobby_create")
                        } else {
                            //fill in error message for wrong password later
                        }
                    }
                }) {
                    Text(text="Join", fontSize=20.sp)
                }

            }

        }
    }
}
//input validation
private fun validatePasscode(enteredPasscode: String): Boolean {
    return enteredPasscode == lobbyPasscode
}

@Preview
@Composable
fun PreviewLobby_join()
{
val navHostController = rememberNavController()
   Lobby_join(navHostController = navHostController)
}