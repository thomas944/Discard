package com.example.discard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.discard.navigation.Navigation
import com.example.discard.ui.theme.DiscardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Navigation()

                }
            }
        }
    }
}

@Composable
fun MyImage(suit: String, rank: String){

    Image(
        painter = painterResource(id = getDrawableResource(suit, rank)),
        contentDescription = "Image",
        modifier = Modifier
            .padding(16.dp)
            .width(50.dp)
            .height(100.dp)

    )
}
@Composable
fun getDrawableResource(suit: String, rank:String) : Int {
    val suitResource = when(suit){
        "hearts" -> "_heart"
        "diamonds" -> "_diamond"
        "clubs" -> "_club"
        "spades" -> "_spade"
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiscardTheme {
        //Greeting("Android")
        Navigation()
    }
}