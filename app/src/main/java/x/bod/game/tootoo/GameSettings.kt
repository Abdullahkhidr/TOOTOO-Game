package x.bod.game.tootoo

import androidx.compose.runtime.mutableStateMapOf
import kotlin.random.Random
import kotlin.random.nextInt

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

    private val fromCol = Random.nextInt(1..WIDTH / 2)
    private val shapes = arrayOf(
        mapOf(-2 to fromCol + 1 to true, -2 to fromCol + 2 to true, -1 to fromCol + 1 to true),
        mapOf(-3 to fromCol + 1 to true, -2 to fromCol + 1 to true, -1 to fromCol + 1 to true),
        mapOf(
            -3 to fromCol + 1 to true,
            -3 to fromCol + 2 to true,
            -2 to fromCol + 1 to true,
            -1 to fromCol + 1 to true
        ),
        mapOf(
            -2 to fromCol + 1 to true,
            -2 to fromCol + 2 to true,
            -2 to fromCol + 3 to true,
            -1 to fromCol + 2 to true
        ),
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

    private fun cleanBoxes() {
        val frq = mutableMapOf<Int, Int>()
        staticPoints.keys.forEach {
            if (frq[it.first] == null) frq[it.first] = 1
            frq[it.first] = frq[it.first]!! + 1
        }
        frq.forEach {
            if (it.value == WIDTH) {
                staticPoints.clear()
                staticPoints.putAll(staticPoints.filter { pair -> pair.key.first != it.key })
            }
        }
    }
}