package com.example.discard.data

import java.util.Dictionary

data class GameState(
    val gameOver: Boolean = false,
    val playerRankings: Dictionary<String, Int>,
    val connectedPlayers: List<String>,
    val playerAtTurn: String,
    val playerDecks: Dictionary<String, List<String>>,
    val playedCardsPile: List<String>,
    val currentCard: String,

    val deck: List<String> = listOf(
        "rank_1_clover", "rank_1_spade", "rank_1_heart", "rank_1_diamond",
        "rank_2_clover", "rank_2_spade", "rank_2_heart", "rank_2_diamond",
        "rank_3_clover", "rank_3_spade", "rank_3_heart", "rank_3_diamond",
        "rank_4_clover", "rank_4_spade", "rank_4_heart", "rank_4_diamond",
        "rank_5_clover", "rank_5_spade", "rank_5_heart", "rank_5_diamond",
        "rank_6_clover", "rank_6_spade", "rank_6_heart", "rank_6_diamond",
        "rank_7_clover", "rank_7_spade", "rank_7_heart", "rank_7_diamond",
        "rank_8_clover", "rank_8_spade", "rank_8_heart", "rank_8_diamond",
        "rank_9_clover", "rank_9_spade", "rank_9_heart", "rank_9_diamond",
        "rank_10_clover", "rank_10_spade", "rank_10_heart", "rank_10_diamond",
        "rank_jack_clover", "rank_jack_spade", "rank_jack_heart", "rank_jack_diamond",
        "rank_queen_clover", "rank_queen_spade", "rank_queen_heart", "rank_queen_diamond",
        "rank_king_clover", "rank_king_spade", "rank_king_heart", "rank_king_diamond"
    )
)
