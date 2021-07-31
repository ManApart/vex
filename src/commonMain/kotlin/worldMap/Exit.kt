package worldMap

import level.LevelTemplate

class Exit(val id: Int, val level: LevelTemplate){
    var unlocked = false

    override fun toString(): String {
        return "$id: $level"
    }
}

