package x.bod.game.tootoo

object Movement {

    /**Move Falling Shape to Right.*/
    fun shiftRight(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        GameSettings.movePoints.forEach {
            val pair = it.key.first to it.key.second + 1
            if (pair.second > GameSettings.WIDTH) return false
            temp[pair] = true
        }
        if (GameSettings.staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            return false
        }
        GameSettings.movePoints = temp
        GameSettings.refresh()
        return true
    }

    /**Move Falling Shape to Left*/
    fun shiftLeft(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        GameSettings.movePoints.forEach {
            val pair = it.key.first to it.key.second - 1
            if (pair.second <= 0) return false
            temp[pair] = true
        }
        if (GameSettings.staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            return false
        }
        GameSettings.movePoints = temp;
        GameSettings.refresh()
        return true
    }

    /**Move Falling Shape to Down*/
    fun shiftDown(): Boolean {
        val temp = mutableMapOf<Pair<Int, Int>, Boolean>()
        GameSettings.movePoints.forEach {
            val pair = it.key.first + 1 to it.key.second
            if (pair.first > GameSettings.HEIGHT) {
                GameSettings.staticPoints.putAll(GameSettings.movePoints)
                GameSettings.movePoints = GameSettings.newShape
                return false
            }
            temp[pair] = true
        }
        if (GameSettings.staticPoints.keys.intersect(temp.keys).isNotEmpty()) {
            GameSettings.addOperationToQueue { GameSettings.cleanBoxes() }
            GameSettings.staticPoints.putAll(GameSettings.movePoints)
            GameSettings.movePoints = GameSettings.newShape
            return false
        }
        GameSettings.movePoints = temp
        GameSettings.refresh()
        return true
    }
}