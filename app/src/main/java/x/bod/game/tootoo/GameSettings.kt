package x.bod.game.tootoo

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import kotlin.random.Random
import kotlin.random.nextInt

object GameSettings {
    /** Check if Game is over*/
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

    /**Number of Columns and Row of Board*/
    const val HEIGHT = 20
    const val WIDTH = 10

    /**Positions of Active Points On The Board. ([staticPoints] and [movePoints])*/
    val activePoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()

    /**Positions Of Active Points Without Falling Shape*/
    internal val staticPoints = mutableStateMapOf<Pair<Int, Int>, Boolean>()


    /**Create Random Position To Falling off*/
    private var fromCol = Random.nextInt(1..WIDTH - 3)

    /**Types of shapes*/
    private val shapes
        get() = arrayOf(
            /** Shape
             * ▬▬
             * ▬
             * */
            mapOf(-2 to fromCol + 1 to true, -2 to fromCol + 2 to true, -1 to fromCol + 1 to true),
            /** Shape
             *   ▬
             * ▬▬
             * */
            mapOf(-1 to fromCol + 2 to true, -2 to fromCol + 2 to true, -1 to fromCol + 1 to true),
            /** Shape
             * ▬▬
             *   ▬
             * */
            mapOf(-2 to fromCol + 1 to true, -2 to fromCol + 2 to true, -1 to fromCol + 2 to true),
            /** Shape
             * ▬▬▬
             * */
            mapOf(-1 to fromCol + 1 to true, -1 to fromCol + 2 to true, -1 to fromCol + 3 to true),
            /** Shape
             * ▬
             * ▬
             * ▬
             * */
            mapOf(-3 to fromCol + 1 to true, -2 to fromCol + 1 to true, -1 to fromCol + 1 to true),
            /** Shape
             * ▬▬
             * ▬
             * ▬
             * */
            mapOf(
                -3 to fromCol + 1 to true,
                -3 to fromCol + 2 to true,
                -2 to fromCol + 1 to true,
                -1 to fromCol + 1 to true
            ),
            /** Shape
             * ▬▬▬
             *   ▬
             * */
            mapOf(
                -2 to fromCol + 1 to true,
                -2 to fromCol + 2 to true,
                -2 to fromCol + 3 to true,
                -1 to fromCol + 2 to true
            ),
        )

    /**Positions Of Points Of Falling Shape*/
    internal var movePoints = newShape

    /**Choose Random Shape*/
    internal val newShape: Map<Pair<Int, Int>, Boolean>
        get() {
            fromCol = Random.nextInt(1..WIDTH - 3)
            shapes.shuffle()
            return shapes.first()
        }

    /**When the player moves the shape to the right or left at the same time as the fall occurs,
     * unfortunately, it may cause interference between them and the operation does not occur correctly.
     * So I created a queue for operations so that no interference occurs,
     * and when the player wants to move the shape to the right or left,
     * this operation is added to the queue.*/
    private val operations = ArrayDeque<() -> Unit>()

    /**Add New Operation To The Queue. like Shift Down, Shift Right and Shift Left*/
    fun addOperationToQueue(operation: () -> Unit) {
        operations.addLast { operation() }
    }

    /**It verifies that there are operations in the queue and then performs only the first operation*/
    fun doOperation(): Boolean {
        if (operations.isNotEmpty()) {
            operations.first().invoke()
            operations.removeFirst()
            return true
        }
        return false
    }

    /**Check if There is Complete Row (Every Points in this row is active)
     * and remove all points in this row from active points*/
    internal fun cleanBoxes() {
        Log.i("Clean", "Clean Boxes")
        val frq = mutableMapOf<Int, Int>()
        staticPoints.keys.forEach {
            if (frq[it.first] == null) frq[it.first] = 0
            frq[it.first] = frq[it.first]!! + 1
        }
        frq.forEach {
            if (it.value == WIDTH) {
                val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
                staticPoints.forEach { s ->
                    if (s.key.first > it.key) {
                        temp[s.key] = true
                    } else if (s.key.first < it.key) {
                        temp[s.key.first + 1 to s.key.second] = true
                    }
                }
                staticPoints.clear()
                staticPoints.putAll(temp)
                refresh()
            }
        }
    }

    /**Refresh Positions of Active Points. It Used When Do any Operation*/
    internal fun refresh() {
        activePoints.clear()
        activePoints.putAll(staticPoints)
        activePoints.putAll(movePoints)
    }
}