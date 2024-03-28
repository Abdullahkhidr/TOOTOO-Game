package x.bod.game.tootoo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import x.bod.game.tootoo.GameSettings

@Composable
fun GameController() {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ButtonAction(icon = Icons.Outlined.ArrowBack) {
            GameSettings.addOperationToQueue { GameSettings.shiftLeft() }
        }
        ButtonAction(icon = Icons.Outlined.KeyboardArrowDown) {
            GameSettings.addOperationToQueue { GameSettings.shiftDown() }
        }
        ButtonAction(icon = Icons.Outlined.ArrowForward) {
            GameSettings.addOperationToQueue { GameSettings.shiftRight() }
        }
    }
}

@Composable
fun ButtonAction(icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Icon(
            icon, "Arrow Back",
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .size(60.dp)
                .padding(15.dp),
            tint = Color.Black
        )
    }
}

