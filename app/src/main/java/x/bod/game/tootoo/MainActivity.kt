package x.bod.game.tootoo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import x.bod.game.tootoo.components.GameController
import x.bod.game.tootoo.components.GameStatusController
import x.bod.game.tootoo.components.TOOBoard
import x.bod.game.tootoo.ui.theme.PrimaryColor
import x.bod.game.tootoo.ui.theme.TOOTOOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen()
        }
    }
}

@Composable
fun GameScreen() {
    TOOTOOTheme {
        Box(modifier = Modifier.background(PrimaryColor)) {
            Column {
                Row(modifier = Modifier.padding(top = 15.dp, start = 15.dp)) {
                    GameStatusController()
                }
                TOOBoard(
                    activePoints = GameSettings.activePoints,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 20.dp, horizontal = 20.dp)
                )
                GameController()
            }
        }
    }
    remember {
        GameSettings.start()
        ""
    }
}
