package worldMap

import level.LevelTemplate

class Exit(val id: Int, val level: LevelTemplate){

    override fun toString(): String {
        return "$id: $level"
    }
}

