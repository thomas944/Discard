
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.discard.data.GameUiState
import com.example.discard.data.allCards
import com.example.discard.models.CardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private fun createInitialGameUiState(){
        val currentState = _uiState.value
        val initialDeck = allCards
        val shuffledDeck = initialDeck.shuffled(Random)

        //Log.d("Initial State", "Initial State ${shuffledDeck}")

        _uiState.value = currentState.copy(
            deck = shuffledDeck
        )

        Log.d("Initial State", "Initial Player Deck ${_uiState.value.playerDeck}")
        Log.d("Initial State", "Initial Deck ${_uiState.value.deck}")

        Log.d("Initial State", "Initial Discarded Cards${_uiState.value.playedCardsPile}")


    }

    init {
        startGame()
        //Log.d("INIT", "INITCALLED")
    }

    fun startGame(){
        createInitialGameUiState()

    }


    // Re-initialize game data to restart the game
    fun resetGame(){

    }

    //discard card
    fun playCard(card: CardModel){
        Log.d("PlayCard", "PlayCardFunctionCalled")

        val currentState = _uiState.value

        val updatedPlayerDeck = currentState.playerDeck.toMutableList().apply {
            remove(card)
        }
        val updatedPlayedCardsPile = currentState.playedCardsPile.toMutableList().apply {
            add(0,card)
        }

        _uiState.value = currentState.copy(
            playerDeck = updatedPlayerDeck,
            playedCardsPile = updatedPlayedCardsPile,
        )
        Log.d("GameViewModel", "Player Deck After, ${_uiState.value.playerDeck}")
        Log.d("GameViewModel", "Deck After, ${_uiState.value.deck}")
        Log.d("GameViewModel", "Trash Pile After, ${_uiState.value.playedCardsPile} \n")
    }

    //draw card from pile
    fun drawCard(currentCard: CardModel){
        val currentState = _uiState.value
        Log.d("IMPORTANTSHIT", "${_uiState.value.deck}")
        if (currentState.deck.size >= 2) {

            val updatedDeck = currentState.deck.drop(1)
            val updatedPlayerDeck = currentState.playerDeck.toMutableList().apply{
                add(currentCard)
            }

            _uiState.value = currentState.copy(
                deck = updatedDeck,
                playerDeck = updatedPlayerDeck,
            )
        }
        Log.d("GameViewModel", "Player Deck After, ${_uiState.value.playerDeck}")
        Log.d("GameViewModel", "Deck After, ${_uiState.value.deck}")
        Log.d("GameViewModel", "Trash Pile After, ${_uiState.value.playedCardsPile} \n")
    }

    fun handleCardClick(currentCard: CardModel){
        //Log.d("GameViewModel", "PRESSED BUTTON, ${currentCard}")
        val currentState = _uiState.value
        Log.d("GameViewModel", "Player Deck Before, ${currentState.playerDeck}")
        Log.d("GameViewModel", "Deck Before, ${currentState.deck}")
        Log.d("GameViewModel", "Trash Pile Before, ${currentState.playedCardsPile} \n")


        if (currentState.playedCardsPile.contains(currentCard)) {
            return
        }

        // If a card is in the deck and clicked, add it to the player deck
        if (currentState.deck.contains(currentCard)){
            drawCard(currentCard)
        }

        // If a card is in a players deck and clicked, add it to discard pile
        else if (currentState.playerDeck.contains(currentCard)){
            Log.d("Options", "play card called")

            playCard(currentCard)
        }

    }
}
