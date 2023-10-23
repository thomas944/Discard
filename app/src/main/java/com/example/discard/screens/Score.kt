package com.example.discard.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.discard.R
import com.example.discard.components.NormalTextComponent
import com.example.discard.components.TextField


@Composable
fun Score(navHostController: NavHostController)
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
            val fakeNames = listOf(
                "Alice",
                "Bob",
                "Charlie",
                "David",
                "Eve",
                "Frank",
                "Grace",
                "Hannah"
            )

            val fakeRankings = listOf(1, 2, 3, 4, 5, 6, 7, 8)
            val fakeScores = listOf(100, 90, 85, 75, 60, 55, 45, 30)

            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                NormalTextComponent(value = stringResource(id = R.string.score))
                Column(modifier = Modifier.size(width=350.dp,height=600.dp).background(Color.Gray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    fakeNames.forEachIndexed { index, name ->
                        val ranking = fakeRankings[index]
                        val score = fakeScores[index]

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Rank $ranking", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Text(name, fontSize = 24.sp, fontWeight = FontWeight.Normal)
                            Text("Score: $score", fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Button(onClick = {
                    navHostController.navigate("LobbyScreen")
                }) {
                    Text(text="Play again", fontSize=20.sp)
                }




            }

        }
    }
}

