package x.bod.game.tootoo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import x.bod.game.tootoo.GameSettings

@Composable
fun TOOBoard(
    activePoints: SnapshotStateMap<Pair<Int, Int>, Boolean>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(Color.White)) {
        for (i in 1..GameSettings.HEIGHT) {
            Row(Modifier.weight(1f)) {
                for (j in 1..GameSettings.WIDTH) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(0.5.dp)
                            .background(
                                if (activePoints.containsKey(i to j)) Color.Black else Color.White,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .border(2.dp, Color(0x3917C4B6), RoundedCornerShape(5.dp))
                    )
                }
            }
        }
    }
}