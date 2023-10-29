package com.example.discard.models

data class CardModel(val suit: String, val rank: String){
    val cardId: String
        get() = "$suit-$rank"


}
