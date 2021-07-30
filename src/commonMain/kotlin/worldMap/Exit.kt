package worldMap

import com.soywiz.korma.geom.Rectangle
import level.LevelTemplate
import org.jbox2d.common.Vec2

class Exit(val id: Int, val level: LevelTemplate, position: Vec2){
    val bounds = Rectangle(position.x, position.y, 1f, 1f)
    var unlocked = false
    val connections = mutableListOf<Connection>()

    override fun toString(): String {
        return "$id: $level"
    }
}

