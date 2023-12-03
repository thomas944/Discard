package com.example.discard.data

import com.example.discard.models.CardModel

data class GameUiState(
    val gameOver: Boolean = false,
    val playerRankings: Map<String, Int> = emptyMap(),
    val connectedPlayers: List<String> = listOf("Camile", "Andy", "Austin", "Sarah", "Steven", "Jeremy", "Anthony", "Jerry"),
    val playerAtTurn: String? = null,
    val playerDecks: Map<String, List<CardModel>> = emptyMap(),
    val playerDeck: List<CardModel> = emptyList(),
    val playedCardsPile: List<CardModel> = emptyList(),
    val currentCard: CardModel? = null,
    val deck: List<CardModel> = emptyList(),
    val playerRole: String? = null,
    val roomCode: String? = null,
    val nickName: String? = null,
    val reverse: Boolean = false,
    val playerMoved: Boolean = false
)
