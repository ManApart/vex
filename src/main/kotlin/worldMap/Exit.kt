package worldMap

import level.LevelTemplate
import physics.Rectangle
import physics.Vector

class Exit(val level: LevelTemplate, val exitId: Int, position: Vector){
    val bounds = Rectangle(position.x, position.y, 1f, 1f)
    var unlocked = false
}