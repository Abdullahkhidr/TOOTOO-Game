package x.bod.game.tootoo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import x.bod.game.tootoo.GameSettings
import x.bod.game.tootoo.R
import x.bod.game.tootoo.StatusGame

@Preview
@Composable
fun ResumeGameDialog(title: String = "Start", status: StatusGame = StatusGame.NotStarted) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledTonalIconButton(onClick = {
            when (status) {
                StatusGame.NotStarted -> GameSettings.start()
                StatusGame.Over -> GameSettings.start()
                else -> GameSettings.pause.value = false
            }
        }, Modifier.size(80.dp)) {
            Icon(
                painterResource(
                    when (status) {
                        StatusGame.NotStarted -> R.drawable.ic_play_circle_filled
                        StatusGame.Pause -> R.drawable.ic_pause_circle_filled
                        StatusGame.Over -> R.drawable.ic_refresh
                        else -> R.drawable.ic_play_circle_filled
                    }
                ),
                "",
                modifier = Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun resumeGameDialog(title: String, status: StatusGame) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        ResumeGameDialog(title, status)
    }
}