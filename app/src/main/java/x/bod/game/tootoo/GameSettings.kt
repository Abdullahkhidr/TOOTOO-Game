package x.bod.game.tootoo

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.util.Queue

object GameSettings {
    val over: Boolean
        get() {
            staticPoints.keys.forEach {
                if (it.first < 0) {
                    operations.clear()
                    return true
                }
            }
            return false
        }
    const val HEIGHT = 20
    const val WIDTH = 10
    val activePoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()
    private val staticPoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()
    private val operations = ArrayDeque<() -> Unit>()
    private val shapes = arrayOf(
        mapOf(-2 to 1 to true, -2 to 2 to true, -1 to 1 to true),
        mapOf(-3 to 1 to true, -2 to 1 to true, -1 to 1 to true),
        mapOf(-3 to 1 to true, -3 to 2 to true, -2 to 1 to true, -1 to 1 to true),
        mapOf(-2 to 1 to true, -2 to 2 to true, -2 to 3 to true, -1 to 2 to true),
    )
    private var movePoints = newShape
    private val newShape: Map<Pair<Int, Int>, Boolean>
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
        return true
    }

    fun shiftLeft(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        movePoints.forEach {
            val pair = it.key.first to it.key.second - 1
            if (pair.second <= 0) return false
            temp[pair] = true
        }
        if (staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            return false
        }
        movePoints = temp;
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
        return true
    }

    fun shiftDown(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        movePoints.forEach {
            val pair = it.key.first + 1 to it.key.second
            if (pair.first > HEIGHT) {
                staticPoints.putAll(movePoints)
                movePoints = newShape
                return false
            }
            temp[pair] = true
        }
        if (staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            staticPoints.putAll(movePoints)
            movePoints = newShape
            return false
        }
        movePoints = temp;
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
        return true
    }
}