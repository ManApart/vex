package worldMap

import level.LevelTemplate
import physics.Rectangle
import physics.Vector

class LevelWrapper(val level: LevelTemplate, val position: Vector){
    val bounds = Rectangle(position.x, position.y, 1f, 1f)
}