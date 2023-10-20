package com.example.discard.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.discard.R
import com.example.discard.components.NormalTextComponent
import com.example.discard.components.PlayButtonComponent
import com.example.discard.components.TextField


@Composable
fun Score()
{
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Surface (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                //todo. text needs to be the room code
                NormalTextComponent(value = stringResource(id = R.string.score))
                Box(modifier = Modifier
                    .background(Color.Gray)
                    .width(350.dp)
                    .height(600.dp))




            }

        }
    }
}

@Preview
@Composable
fun PreviewScore()
{
    Score()
}