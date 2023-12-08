package com.example.discard.models
/* Data class representing a card, and is part of game logic.
* Developed by Thomas Pham, Huy Tran, and Ngoc Tran.
*/

data class CardModel(val suit: String, val rank: String){
    val cardId: String
        get() = "$suit-$rank"


}
