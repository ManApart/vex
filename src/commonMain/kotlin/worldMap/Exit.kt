package worldMap

import level.LevelTemplate
import physics.Rectangle
import physics.Vector

class Exit(val id: Int, val level: LevelTemplate, position: Vector){
    val bounds = Rectangle(position.x, position.y, 1f, 1f)
    var unlocked = false
    val connections = mutableListOf<Connection>()

    override fun toString(): String {
        return "$id: $level"
    }
}

