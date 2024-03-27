package x.bod.game.tootoo

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.util.Queue

object GameSettings {
    const val HEIGHT = 30
    const val WIDTH = 15
    val activePoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()
    val staticPoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()
    private val operations = ArrayDeque<() -> Unit>()
    private val shapes = arrayOf(
        mapOf(1 to 1 to true, 1 to 2 to true, 2 to 1 to true),
        mapOf(1 to 1 to true, 2 to 1 to true, 3 to 1 to true),
        mapOf(1 to 1 to true, 1 to 2 to true, 2 to 1 to true, 3 to 1 to true),
        mapOf(1 to 1 to true, 1 to 2 to true, 1 to 3 to true, 2 to 2 to true),
    )
    var movePoints = newShape
    val newShape: Map<Pair<Int, Int>, Boolean>
        get() {
            shapes.shuffle()
            return shapes.first()
        }

    fun doOperation(): Boolean {
        if (operations.isNotEmpty()) {
            operations.first().invoke()
            operations.removeFirst()
            return true
        }
        return false
    }


    fun addOperationToQueue(operation: () -> Unit) {
        operations.addLast { operation() }
    }

    fun shiftRight(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        movePoints.forEach {
            val pair = it.key.first to it.key.second + 1
            if (pair.second > WIDTH) return false
            temp[pair] = true
        }
        if (staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            return false
        }
        movePoints = temp;
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
        Log.i("Operation: ", "Shift Right")
        return true
    }

    fun shiftLeft(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        movePoints.forEach {
            val pair = it.key.first to it.key.second - 1
            if (pair.second < 0) return false
            temp[pair] = true
        }
        if (staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            return false
        }
        movePoints = temp;
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
        Log.i("Operation: ", "Shift Left")
        return true
    }

    fun shiftDown(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        movePoints.forEach {
            val pair = it.key.first + 1 to it.key.second
            temp[pair] = true
        }
        if (staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            staticPoints.putAll(movePoints)
            return false
        }
        movePoints = temp;
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
        Log.i("Operation: ", "Shift Down")
        return true
    }
}