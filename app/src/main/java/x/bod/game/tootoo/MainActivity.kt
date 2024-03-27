package x.bod.game.tootoo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import x.bod.game.tootoo.ui.theme.TOOTOOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }
}

@Preview
@Composable
fun GameScreen() {
    TOOTOOTheme {
        Box {
            TOOBoard(activePoints = GameSettings.activePoints)
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        GameSettings.addOperationToQueue {
                            GameSettings.shiftLeft()
                        }
                    }) {
                }
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable {
                        GameSettings.addOperationToQueue {
                            GameSettings.shiftRight()
                        }
                    }) {
                }
            }
        }
    }

    remember {
        GlobalScope.launch {
            while (true) {
                delay(500)
                GameSettings.addOperationToQueue { GameSettings.shiftDown() }
                Log.i("Operation: ", "Add Down Operation")
            }
        }
        GlobalScope.launch {
            while (true) {
                delay(100)
                GameSettings.doOperation()
                Log.i("Operation: ", "Do Operation")
            }
        }
        ""
    }
}

