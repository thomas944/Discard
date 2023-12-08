
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.discard.data.GameUiState
import com.example.discard.data.allCards
import com.example.discard.models.CardModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/* This Kotlin class contains that defines the game logic for the app. The game, called Discard, follows
* similar logic to the game Uno.
* Developed by Thomas Pham, Huy Tran, and Ngoc Tran.
 */
class GameViewModel : ViewModel() {

    //variable to hold game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    //function which creates the game's initial UI state
    private fun createInitialGameUiState(){
        val currentState = _uiState.value
        //creates initial deck of shuffled cards
        val initialDeck = allCards
        val shuffledDeck = initialDeck.shuffled(Random)
        //deal 5 cards to each player
        val cardsPerPlayer = 5

        //distribute cards to each player
        val playerDecks: Map<String, List<CardModel>> = currentState.connectedPlayers
            .mapIndexed { index, player ->
                val startIndex = index * cardsPerPlayer
                val endIndex = startIndex + cardsPerPlayer
                player to shuffledDeck.subList(startIndex, endIndex)
            }
            .toMap()

        // Remove distributed cards from the deck
        val remainingDeck = shuffledDeck.toMutableList()
        playerDecks.values.flatten().forEach { distributedCard ->
            remainingDeck.remove(distributedCard)
        }

        val firstPlayer = currentState.connectedPlayers.first()
        //update UI state with the initial game state
        _uiState.value = currentState.copy(
            deck = remainingDeck,
            playerAtTurn = firstPlayer,
            playerDecks = playerDecks
        )
        Log.d("InitialState", playerDecks.toString())
        Log.d("InitialState", remainingDeck.size.toString())

//        Log.d("Initial State", "Initial Player Deck ${_uiState.value.playerDeck}")
//        Log.d("Initial State", "Initial Deck ${_uiState.value.deck}")
//
//        Log.d("Initial State", "Initial Discarded Cards${_uiState.value.playedCardsPile}")


    }

    //create initial game state
    init {
        startGame()
        //Log.d("INIT", "INITCALLED")
    }

    //funtion to start game
    fun startGame(){
        createInitialGameUiState()
    }


    // Re-initialize game data to restart the game
    fun resetGame(){

    }

    //implements logic to pick which player goes next
    fun pickNextPlayer(Skip: Boolean){
        Log.d("Next", "Next player has been selected")
        val currentState = _uiState.value

        val currentPlayerIndex = currentState.connectedPlayers.indexOf(currentState.playerAtTurn)

        if (Skip){
            Log.d("Game Logic", currentPlayerIndex.toString())
            val nextPlayerIndex = if (currentState.reverse) {
                (currentPlayerIndex - 2 + currentState.connectedPlayers.size) % currentState.connectedPlayers.size
            } else {
                (currentPlayerIndex + 2) % currentState.connectedPlayers.size
            }

            // Set the next player based on the calculated index
            val nextPlayer = currentState.connectedPlayers[nextPlayerIndex]
            Log.d("Game Logic", nextPlayerIndex.toString())

            _uiState.value = currentState.copy(
                playerAtTurn = nextPlayer
            )
        }
        else{
            // Calculate the next player index based on the reverse flag
            val nextPlayerIndex = if (currentState.reverse) {
                (currentPlayerIndex - 1 + currentState.connectedPlayers.size) % currentState.connectedPlayers.size
            } else {
                (currentPlayerIndex + 1) % currentState.connectedPlayers.size
            }

            // Set the next player based on the calculated index
            val nextPlayer = currentState.connectedPlayers[nextPlayerIndex]

            _uiState.value = currentState.copy(
                playerAtTurn = nextPlayer
            )
        }
    }

    //function to discard the played card
    //handles game logic based on which card was played
    fun playCard(card: CardModel){
        Log.d("PlayCard", "PlayCardFunctionCalled")

        val currentState = _uiState.value

        //check if the played card pile is empty and handle it
        if (currentState.playedCardsPile.isEmpty()){
            val currentPlayerDeck = currentState.playerDecks[currentState.playerAtTurn]
            //check if the current player's deck contains the card
            if (currentPlayerDeck != null && card in currentPlayerDeck) {
                val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                    //removes this specific card from the player's deck
                    this[currentState.playerAtTurn!!] = currentPlayerDeck.toMutableList().apply {
                        remove(card)
                    }
                }
                val updatedPlayedCardsPile = currentState.playedCardsPile.toMutableList().apply {
                    //updates the pile of played cards to include the card
                    add(0,card)
                }
                //handle if the played card is a king
                if (card.rank === "king") {

                    val currentPlayerIndex = currentState.connectedPlayers.indexOf(currentState.playerAtTurn)

                    val nextPlayerIndex = if (currentState.reverse) {
                        (currentPlayerIndex - 1 + currentState.connectedPlayers.size) % currentState.connectedPlayers.size
                    } else {
                        (currentPlayerIndex + 1) % currentState.connectedPlayers.size
                    }
                    //get name of next player
                    val nextPlayer = currentState.connectedPlayers[nextPlayerIndex]
                    //get deck of ext player
                    val nextPlayerCurrentDeck = currentState.playerDecks[nextPlayer] ?: emptyList()


                    val firstCard: CardModel? = currentState.deck.getOrNull(0)
                    val secondCard: CardModel? = currentState.deck.getOrNull(1)
                    Log.d("Game Logic", nextPlayerCurrentDeck.toString())
                    Log.d("Game Logic", currentState.playerDecks[nextPlayer].toString())
                    if (firstCard != null && secondCard != null) {
                        val updatedNextPlayerDeck = nextPlayerCurrentDeck.toMutableList().apply {
                            add(firstCard)
                            add(secondCard)
                        }
                        Log.d("Game Logic", updatedNextPlayerDeck.toString())

                        val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                            this[nextPlayer] = updatedNextPlayerDeck
                        }

                        _uiState.value = currentState.copy(
                            playerDecks = updatedPlayerDecks
                        )
                        Log.d("Game Logic", _uiState.value.playerDecks[nextPlayer].toString())


                    } else if (firstCard != null && secondCard == null) {
                        val updatedNextPlayerDeck = nextPlayerCurrentDeck.toMutableList().apply {
                            add(firstCard)
                        }
                        val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                            this[nextPlayer] = updatedNextPlayerDeck
                        }

                        _uiState.value = currentState.copy(
                            playerDecks = updatedPlayerDecks
                        )
                    }
                    else {
                        _uiState.value = currentState
                    }
                }
                //handle if played card is a queen
                else if (card.rank === "queen"){
                    Log.d("Game Logic", currentState.reverse.toString())
                    val newDirection = !currentState.reverse
                    Log.d("Game Logic", currentState.reverse.toString())
                    _uiState.value = currentState.copy(
                        reverse = newDirection
                    )

                }
                _uiState.value = currentState.copy(
                    playerDecks = updatedPlayerDecks,
                    playedCardsPile = updatedPlayedCardsPile
                )

            }
        }

        else {
            if (currentState.playedCardsPile.first().suit === card.suit){
                if (card.rank === "king") {

                    val currentPlayerIndex = currentState.connectedPlayers.indexOf(currentState.playerAtTurn)

                    val nextPlayerIndex = if (currentState.reverse) {
                        (currentPlayerIndex - 1 + currentState.connectedPlayers.size) % currentState.connectedPlayers.size
                    } else {
                        (currentPlayerIndex + 1) % currentState.connectedPlayers.size
                    }
                    //get name of next player
                    val nextPlayer = currentState.connectedPlayers[nextPlayerIndex]
                    //get deck of ext player
                    val nextPlayerCurrentDeck = currentState.playerDecks[nextPlayer] ?: emptyList()


                    val firstCard: CardModel? = currentState.deck.getOrNull(0)
                    val secondCard: CardModel? = currentState.deck.getOrNull(1)
                    Log.d("Game Logic", nextPlayerCurrentDeck.toString())
                    Log.d("Game Logic", currentState.playerDecks[nextPlayer].toString())
                    if (firstCard != null && secondCard != null) {
                        val updatedNextPlayerDeck = nextPlayerCurrentDeck.toMutableList().apply {
                            add(firstCard)
                            add(secondCard)
                        }
                        Log.d("Game Logic", updatedNextPlayerDeck.toString())

                        val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                            this[nextPlayer] = updatedNextPlayerDeck
                        }

                        _uiState.value = currentState.copy(
                            playerDecks = updatedPlayerDecks
                        )
                        Log.d("Game Logic", currentState.playerDecks[nextPlayer].toString())


                    } else if (firstCard != null && secondCard == null) {
                        val updatedNextPlayerDeck = nextPlayerCurrentDeck.toMutableList().apply {
                            add(firstCard)
                        }
                        val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                            this[nextPlayer] = updatedNextPlayerDeck
                        }

                        _uiState.value = currentState.copy(
                            playerDecks = updatedPlayerDecks
                        )
                    }
                    else {
                        _uiState.value = currentState
                    }
                }
                else if (card.rank === "queen"){
                    Log.d("Game Logic", currentState.reverse.toString())
                    val newDirection = !currentState.reverse
                    Log.d("Game Logic", currentState.reverse.toString())
                    _uiState.value = currentState.copy(
                       reverse = newDirection
                    )

                }

                val currentPlayerDeck = currentState.playerDecks[currentState.playerAtTurn]
                if (currentPlayerDeck != null && card in currentPlayerDeck) {
                    val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                        this[currentState.playerAtTurn!!] = currentPlayerDeck.toMutableList().apply {
                            remove(card)
                        }
                    }
                    val updatedPlayedCardsPile = currentState.playedCardsPile.toMutableList().apply {
                        add(0,card)
                    }

                    _uiState.value = currentState.copy(
                        playerDecks = updatedPlayerDecks,
                        playedCardsPile = updatedPlayedCardsPile,
                    )
                }
            }
            //do not allow cards with not matching suit to be placed
            else {
                _uiState.value = currentState
            }

        }

        viewModelScope.launch {
            delay(1000) // 5000 milliseconds = 5 seconds
            Log.d("Next", "Timer done")
            var skip: Boolean
            if (card.rank === "jack") {

                skip = true
                pickNextPlayer(skip)
            }
            else {
                skip = false
                pickNextPlayer(skip)

            }
        }
//        Log.d("GameViewModel", "Player Deck After, ${_uiState.value.playerDeck}")
//        Log.d("GameViewModel", "Deck After, ${_uiState.value.deck}")
//        Log.d("GameViewModel", "Trash Pile After, ${_uiState.value.playedCardsPile} \n")
    }

    //draw card from pile
    fun drawCard(currentCard: CardModel, currentPlayer: String){
        val currentState = _uiState.value
        val currentPlayerDeck = currentState.playerDecks[currentPlayer]

        Log.d("IMPORTANTSHIT", "${_uiState.value.deck}")
        if (currentState.deck.size >= 2) {

            val updatedDeck = currentState.deck.drop(1)
            val updatedPlayerDecks = currentState.playerDecks.toMutableMap().apply {
                if (currentPlayerDeck != null) {
                    this[currentPlayer] = currentPlayerDeck.toMutableList().apply {
                        add(currentCard)
                    }
                }
            }
            _uiState.value = currentState.copy(
                deck = updatedDeck,
                playerDecks = updatedPlayerDecks,
            )
        }


        viewModelScope.launch {
            delay(1000) // 5000 milliseconds = 5 seconds
            Log.d("Next", "Timer done")
            pickNextPlayer(false)
        }

//        Log.d("GameViewModel", "Player Deck After, ${_uiState.value.playerDeck}")
//        Log.d("GameViewModel", "Deck After, ${_uiState.value.deck}")
//        Log.d("GameViewModel", "Trash Pile After, ${_uiState.value.playedCardsPile} \n")
    }

    fun handleCardClick(currentCard: CardModel){
        //Log.d("GameViewModel", "PRESSED BUTTON, ${currentCard}")
        val currentState = _uiState.value
        Log.d("GameViewModel", "Player Deck Before, ${currentState.playerDeck}")
        Log.d("GameViewModel", "Deck Before, ${currentState.deck}")
        Log.d("GameViewModel", "Trash Pile Before, ${currentState.playedCardsPile} \n")


        if (currentState.playedCardsPile.contains(currentCard)) {
            Log.d("Options", "TRASH CARD")
            return
        }

        // If a card is in the deck and clicked, add it to the player deck
        else if (currentState.deck.contains(currentCard)){
            Log.d("Options", "ADD TO PALYER DECK")
            currentState.playerAtTurn?.let { drawCard(currentCard, it) }
        }

        // If a card is in a players deck and clicked, add it to discard pile
        else if (currentState.playerDecks[currentState.playerAtTurn]?.contains(currentCard) == true){
            Log.d("Options", "play card called")

            playCard(currentCard)
        }
    }

    fun handleCreateLobby(){
        Log.d("DEBUG FROM FUNC", "${_uiState.value.playerRole}")
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            playerRole = "creator"
        )
        Log.d("DEBUG FROM FUNC", "${_uiState.value.playerRole}")
    }

    fun handleJoinLobby(){
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            playerRole = "player"
        )
    }

    fun updateRoomCode(roomCode: String){
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            roomCode = roomCode
        )
    }

    fun updateNickName(nickName: String){
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            nickName = nickName
        )
    }
}
