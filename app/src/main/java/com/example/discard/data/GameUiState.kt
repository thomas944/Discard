package com.example.discard.data

import com.example.discard.models.CardModel

data class GameUiState(
    val gameOver: Boolean = false,
    val playerRankings: Map<String, Int> = emptyMap(),
    val connectedPlayers: List<String> = emptyList(),
    val playerAtTurn: String? = null,
    //val playerDecks: Map<String, List<String>> = emptyMap(),
    val playerDeck: List<CardModel> = emptyList(),
    val playedCardsPile: List<CardModel> = emptyList(),
    val currentCard: CardModel? = null,
    val deck: List<CardModel> = emptyList()
)
