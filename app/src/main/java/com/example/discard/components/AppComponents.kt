package com.example.discard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NormalTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center

    )
}

@Composable
fun PlayButtonComponent(
    value: String,
    onNavigateToLobbyScreen: () -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Button(
            modifier = Modifier
                .width(200.dp)
                .heightIn(48.dp),
            onClick = {
                      onNavigateToLobbyScreen()
            },
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(10.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),

                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BackArrowComponent(onNavigateToHomeScreen : () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        IconButton(
            onClick = {onNavigateToHomeScreen()}
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Black)
        }

    }
}
//@Preview
//@Composable
//fun DefaultPreviewBackArrowComponent(){
//    BackArrowComponent()
//}
