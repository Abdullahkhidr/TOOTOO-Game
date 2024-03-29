package x.bod.game.tootoo.components

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import x.bod.game.tootoo.GameSettings
import x.bod.game.tootoo.R
import x.bod.game.tootoo.StatusGame

@Composable
fun GameStatusController() {
    if (GameSettings.over) {
        resumeGameDialog("Game Over", StatusGame.Over)
    } else if (GameSettings.pause.value) {
        resumeGameDialog("Pause", StatusGame.Pause)
    }
    FilledTonalIconButton(
        onClick = {
            when {
                GameSettings.over -> GameSettings.start()
                else -> GameSettings.pause.value = !GameSettings.pause.value
            }

        }, colors = IconButtonDefaults.filledTonalIconButtonColors(
            containerColor = Color.White
        )
    ) {
        Icon(
            painterResource(
                when {
                    GameSettings.over -> R.drawable.ic_refresh
                    GameSettings.pause.value -> R.drawable.ic_pause_circle_filled
                    else -> R.drawable.ic_play_circle_filled
                }
            ),
            "Play Icon"
        )
    }
}