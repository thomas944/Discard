package com.example.discard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.discard.components.PreviewComponent
import com.example.discard.navigation.Navigation
import com.example.discard.ui.theme.DiscardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscardTheme{

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Gray
                ) {
                   //Navigation()
                    PreviewComponent()
                }
            }
        }
    }


}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DiscardTheme {


        //Greeting("Android")
//        Navigation()
 //   }
//}