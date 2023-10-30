package com.example.discard.components


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
fun HeadingTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = Color.Black,
        textAlign = TextAlign.Center

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(labelValue:String)
{
    val textValue= remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label= {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black)
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value= it
    })
}


@Composable
//fun CardComponent(cardModel: CardModel, onClick: () -> Unit = {}){
fun CardComponent(
    currentCard: CardModel,
    handleCardClick: (CardModel) -> Unit
){
    
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(50.dp)
            .height(75.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = getDrawableResource(currentCard.suit, currentCard.rank)),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    handleCardClick(currentCard)
                }
        )
    }
}

@Composable
fun getDrawableResource(
    suit: String, rank:String
) : Int {
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
fun CardDeckContainerComponent(
    listOfCards: List<CardModel>,
    handleCardClick: (CardModel) -> Unit
) {
    var startIndex by remember { mutableIntStateOf(0) }
    val cardsToDisplay = 5
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                        .fillMaxSize(),
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
                       if (listOfCards.size >= cardsToDisplay && startIndex > 0){
                           IconButton(
                               onClick = {
                                   startIndex --
                                   //Log.d("StateTest", "StartIndex: $startIndex")
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
                            val visibleCards = listOfCards.subList(startIndex, minOf(startIndex + cardsToDisplay, listOfCards.size))
                            itemsIndexed(visibleCards) { _, cardModel ->
                                CardComponent(currentCard = cardModel, handleCardClick = handleCardClick )
//                                    val mutableList = listOfCards.toMutableList()
//                                    mutableList.removeAt(startIndex + index)
//
//                                }
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
                        if (listOfCards.size >= cardsToDisplay && (startIndex+cardsToDisplay < listOfCards.size)  ){
                            IconButton(
                                onClick = {
                                    startIndex ++
                                   // Log.d("StateTest", "StartIndex: $startIndex")
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
//fun PlayingCardContainerComponent(playedCardsPile: List<CardModel>, newCardsPile: List<CardModel>){
fun PlayingCardContainerComponent(
   deck: List<CardModel>,
   playedCardsPile: List<CardModel>,
   handleCardClick: (CardModel) -> Unit
){
    Log.d("playedCardsPile", "$playedCardsPile")
    Log.d("playedCardsPile", "$deck")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()

        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.5f)
                    .background(Color.Red)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (playedCardsPile.isNotEmpty()) {
                        CardComponent(currentCard = playedCardsPile.first(), handleCardClick = handleCardClick )

                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.5f)
                    .background(Color.Green)
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (deck.isNotEmpty()) {
                        CardComponent(currentCard = deck.first(), handleCardClick = handleCardClick )
                    }
                }
            }
        }
    }
}


@Composable
//fun PlayerIconComponent(name: String, score: Int){
fun PlayerIconComponent(){
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .width(65.dp)
            .height(75.dp)
            .clip(RoundedCornerShape(5.dp))
    ){
        Column(
            modifier = Modifier.background(Color.Gray)
        ) {
            Row(
                modifier = Modifier
                    .weight(.8f)
                    .fillMaxSize(),
            ){
                Text(
                    text = "Hello14r341343214",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                //Text(text = name)
            }
            Row(
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "5",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                //Text(text = score.toString())
            }
        }

    }
}

@Composable
//fun DisplayTurnComponent(PlayerTurn: String){
fun DisplayTurnComponent(){
    Box(modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Currently Player1sdafasdfsf's \n Turn",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PlayersContainerComponent(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxSize()
                    .background(Color.Red),
                horizontalArrangement = Arrangement.Center

            ){
                PlayerIconComponent()
                PlayerIconComponent()
                PlayerIconComponent()

            }
            Row(
                modifier = Modifier
                    .weight(.7f)
                    .fillMaxSize()
                    .background(Color.Green)
            ){
                Column (
                    modifier = Modifier
                        .weight(.3f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerIconComponent()
                    PlayerIconComponent()

                }
                Column (
                    modifier = Modifier
                        .weight(.4f)
                        .fillMaxSize()
                ) {
                    DisplayTurnComponent()
                }
                Column (
                    modifier = Modifier
                        .weight(.3f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerIconComponent()
                    PlayerIconComponent()

                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    PlayersContainerComponent()
}
