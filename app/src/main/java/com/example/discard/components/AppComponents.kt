package com.example.discard.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discard.R
import com.example.discard.models.CardModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*


//import androidx.compose.runtime.livedata.observeAsState

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

@Composable
fun CardComponent(cardModel: CardModel){
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(50.dp)
            .height(75.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Image(
            painter = painterResource(id = getDrawableResource(cardModel.suit, cardModel.rank)),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)


        )
    }

}

@Composable
fun getDrawableResource(suit: String, rank:String) : Int {
    val suitResource = when(suit){
        "heart" -> "_heart"
        "diamond" -> "_diamond"
        "club" -> "_club"
        "spade" -> "_spade"
        else -> "_placeholder"
    }

    val rankResource = when(rank){
        "10" -> "rank_10"
        "J" -> "rank_jack"
        "Q" -> "rank_queen"
        "K" -> "rank_king"
        "A" -> "rank_ace"
        else -> "rank_$rank"
    }

    val resourceName = "$rankResource$suitResource"
    print(R.drawable::class.java.getField(resourceName).getInt(null))
    return R.drawable::class.java.getField(resourceName).getInt(null)
}
@Composable
fun CardDeckContainerComponent(listOfCards: List<CardModel>) {
    var startIndex by remember { mutableStateOf(0)}

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight(.333f)
                .padding(5.dp)
                .background(Color.White),

            ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(.7f)
                        .fillMaxWidth(),
                      //  .background(Color.Red),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ){
                   Column(
                       modifier = Modifier
                           .weight(.1f)
                           .fillMaxHeight()
                           .background(Color.Red),
                       verticalArrangement = Arrangement.Center
                   ){
                       if (listOfCards.size >= 4 && startIndex > 0){
                           IconButton(
                               onClick = {
                                   startIndex --
                                   Log.d("Nigs", "StartIndex: $startIndex")
                               }
                           ) {
                               Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous")
                           }
                       }


                   }
                    Box(
                        modifier = Modifier
                            .weight(.8f)
                            .fillMaxHeight()
                            .background(Color.Yellow)
                    ){
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically

                        ){
                            val visibleCards = listOfCards.subList(startIndex, minOf(startIndex + 4, listOfCards.size))
                            itemsIndexed(visibleCards) { _, cardModel ->
                                CardComponent(cardModel)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(.1f)
                            .fillMaxHeight()
                            .background(Color.Cyan),
                        verticalArrangement = Arrangement.Center
                    ){
                        if (listOfCards.size >= 4 && (startIndex+4 < listOfCards.size)  ){
                            IconButton(
                                onClick = {
                                    startIndex ++
                                    Log.d("Nigs", "StartIndex: $startIndex")
                                }
                            ) {
                                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")

                            }
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun PreviewComponent(){
    val listOfCards = mutableListOf<CardModel>(
        CardModel("diamond", "1"),
        CardModel("diamond", "2"),
        CardModel("diamond", "3"),
        CardModel("diamond", "4"),
        CardModel("diamond", "5"),
        CardModel("diamond", "6"),
        CardModel("diamond", "7"),
        CardModel("diamond", "8"),
        CardModel("diamond", "9"),
        CardModel("diamond", "10"),
        CardModel("diamond", "jack"),
        CardModel("diamond", "queen"),
        CardModel("diamond", "king"),



    )

    CardDeckContainerComponent(listOfCards = listOfCards)
}

@Preview
@Composable
fun DefaultPreview(){
    PreviewComponent()
}