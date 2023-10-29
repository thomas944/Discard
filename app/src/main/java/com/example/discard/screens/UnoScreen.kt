package com.example.discard.screens

import GameViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discard.components.CardDeckContainerComponent
import com.example.discard.components.PlayingCardContainerComponent

@Composable
fun UnoScreen(gameViewModel: GameViewModel = viewModel()) {

    val gameUiState by gameViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.333f)
                    .background(Color.White)
            ){

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.333f)
            ){
                PlayingCardContainerComponent(
                    deck = gameUiState.deck,
                    playedCardsPile = gameUiState.playedCardsPile,
                    handleCardClick = gameViewModel::handleCardClick
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.333f)
            ){
                CardDeckContainerComponent(
                    listOfCards = gameUiState.playerDeck,
                    handleCardClick = gameViewModel::handleCardClick
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewUnoScreen(){
    UnoScreen()
}