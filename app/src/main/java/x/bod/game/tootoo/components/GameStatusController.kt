package x.bod.game.tootoo.components

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import x.bod.game.tootoo.GameSettings
import x.bod.game.tootoo.R

@Composable
fun GameStatusController() {
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